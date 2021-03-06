package org.kuali.ole.executor;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.kuali.ole.common.marc.xstream.BibMarcRecordProcessor;
import org.kuali.ole.indexer.BibIndexer;
import org.kuali.ole.model.jpa.BibRecord;
import org.kuali.ole.repo.BibRecordRepository;
import org.kuali.ole.service.SolrAdmin;
import org.kuali.ole.util.HelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by sheiks on 27/10/16.
 */
public class BibIndexCallable implements Callable {

    Logger logger = LoggerFactory.getLogger(BibIndexCallable.class);
    private final int pageNum;

    private final int docsPerPage;
    private String coreName;
    private String solrURL;
    private final BibMarcRecordProcessor bibMarcRecordProcessor;
    private Map<String, String> fieldsToTags2IncludeMap = new HashMap<>();
    private Map<String, String> fieldsToTags2ExcludeMap = new HashMap<>();

    public BibIndexCallable(int pageNum, int docsPerPage, String coreName, String solrURL,
                            BibMarcRecordProcessor bibMarcRecordProcessor,
                            Map<String, String> fieldsToTags2IncludeMap,
                            Map<String, String> fieldsToTags2ExcludeMap) {
        this.pageNum = pageNum;
        this.docsPerPage = docsPerPage;
        this.coreName = coreName;
        this.solrURL = solrURL;
        this.bibMarcRecordProcessor = bibMarcRecordProcessor;
        this.fieldsToTags2IncludeMap.putAll(fieldsToTags2IncludeMap);
        this.fieldsToTags2ExcludeMap.putAll(fieldsToTags2ExcludeMap);
    }

    @Override
    public Object call() throws Exception {
        StopWatch mainStopWatch = new StopWatch();
        mainStopWatch.start();
        List<SolrInputDocument> solrInputDocuments = new ArrayList<>();
        Page<BibRecord> pageableBibRecords = HelperUtil.getRepository(BibRecordRepository.class).findAll(new PageRequest(pageNum, docsPerPage));
        mainStopWatch.stop();
        logger.info("Num Bibs Fetched : " + pageableBibRecords.getNumberOfElements() + "   and time taken to fetch : " + mainStopWatch.getTotalTimeSeconds() + " Secs");

        StopWatch processStopWatch = new StopWatch();
        processStopWatch.start();

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future> futures = new ArrayList<>();

        for (Iterator<BibRecord> iterator = pageableBibRecords.getContent().iterator(); iterator.hasNext(); ) {
            BibRecord bibRecord = iterator.next();
            futures.add(executorService.submit(new BibRecordSetupCallable(bibRecord, bibMarcRecordProcessor,
                    fieldsToTags2IncludeMap, fieldsToTags2ExcludeMap)));
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

            message = "Total solr input document for page : " + pageNum + "  docPerPage " + docsPerPage + "  = " + count;
        } else {
            message = "Empty solr input document for page : " + pageNum + "  docPerPage " + docsPerPage;
        }

        processStopWatch.stop();
        logger.info(message + "    time taken for process : " + processStopWatch.getTotalTimeSeconds() + " Secs");

        return count;
    }
}
