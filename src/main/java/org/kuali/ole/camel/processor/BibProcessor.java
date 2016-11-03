package org.kuali.ole.camel.processor;

import org.apache.camel.Exchange;
import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.kuali.ole.executor.BibIndexCallable;
import org.kuali.ole.executor.BibRecordSetupCallable;
import org.kuali.ole.indexer.BibIndexingTxObject;
import org.kuali.ole.model.jpa.BibRecord;
import org.kuali.ole.service.SolrAdmin;
import org.kuali.ole.util.HelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by sheiks on 01/11/16.
 */
@Component
public class BibProcessor {

    Logger logger = LoggerFactory.getLogger(BibIndexCallable.class);

    public void processBib(BibIndexingTxObject bibIndexingTxObject, Exchange exchange){
        List<SolrInputDocument> solrInputDocuments = new ArrayList<>();
        List<BibRecord> bibRecords = bibIndexingTxObject.getBibRecords();
        StopWatch processStopWatch = new StopWatch();
        processStopWatch.start();

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future> futures = new ArrayList<>();

        for (Iterator<BibRecord> iterator = bibRecords.iterator(); iterator.hasNext(); ) {
            BibRecord bibRecord = iterator.next();
            futures.add(executorService.submit(new BibRecordSetupCallable(bibRecord,
                    bibIndexingTxObject.getBibMarcRecordProcessor(),
                    bibIndexingTxObject.getFIELDS_TO_TAGS_2_INCLUDE_MAP(), bibIndexingTxObject.getFIELDS_TO_TAGS_2_INCLUDE_MAP())));
        }

        Integer count = 0;
        for (Iterator<Future> iterator = futures.iterator(); iterator.hasNext(); ) {
            Future future = iterator.next();
            try {
                Object object = future.get();
                if(null != object) {
                    count++;
                    solrInputDocuments.addAll((List<SolrInputDocument>) object);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        String message = "";

        if (CollectionUtils.isNotEmpty(solrInputDocuments)) {
            SolrAdmin solrAdmin = HelperUtil.getBean(SolrAdmin.class);
            UpdateResponse updateResponse = solrAdmin.indexToSolr(solrInputDocuments, false);
            message = "Total solr input document for page : " + bibIndexingTxObject.getPageNum() + "  docPerPage " + bibIndexingTxObject.getDocsPerPage() + "  = " + count;
        } else {
            message = "Empty solr input document for page : " + bibIndexingTxObject.getPageNum() + "  docPerPage " + bibIndexingTxObject.getDocsPerPage();
        }

        processStopWatch.stop();
        logger.info(message + "    time taken for process : " + processStopWatch.getTotalTimeSeconds() + " Secs");

    }
}
