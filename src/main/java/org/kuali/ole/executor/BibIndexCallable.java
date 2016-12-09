package org.kuali.ole.executor;

import org.apache.camel.ProducerTemplate;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.kuali.ole.common.marc.xstream.BibMarcRecordProcessor;
import org.kuali.ole.dao.OleMemorizeService;
import org.kuali.ole.model.jpa.BibRecord;
import org.kuali.ole.repo.BibRecordRepository;
import org.kuali.ole.util.HelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    private ProducerTemplate producerTemplate;
    private SolrTemplate solrTemplate;
    private OleMemorizeService oleMemorizeService;

    public BibIndexCallable(String solrURL, String coreName, int pageNum, int docsPerPage,
                            BibMarcRecordProcessor bibMarcRecordProcessor,
                            Map<String, String> fieldsToTags2IncludeMap,
                            Map<String, String> fieldsToTags2ExcludeMap,
                            ProducerTemplate producerTemplate,
                            SolrTemplate solrTemplate,
                            OleMemorizeService oleMemorizeService) {
        this.pageNum = pageNum;
        this.docsPerPage = docsPerPage;
        this.coreName = coreName;
        this.solrURL = solrURL;
        this.bibMarcRecordProcessor = bibMarcRecordProcessor;
        this.fieldsToTags2IncludeMap.putAll(fieldsToTags2IncludeMap);
        this.fieldsToTags2ExcludeMap.putAll(fieldsToTags2ExcludeMap);
        this.producerTemplate = producerTemplate;
        this.solrTemplate = solrTemplate;
        this.oleMemorizeService = oleMemorizeService;
    }

    @Override
    public Object call() throws Exception {
        StopWatch mainStopWatch = new StopWatch();
        mainStopWatch.start();
        Page<BibRecord> pageableBibRecords = HelperUtil.getRepository(BibRecordRepository.class).findAll(new PageRequest(pageNum, docsPerPage));
        mainStopWatch.stop();
        logger.info("Page Num : " + pageNum + "    Num Bibs Fetched : " + pageableBibRecords.getNumberOfElements() + "   and time taken to fetch : " + mainStopWatch.getTotalTimeSeconds() + " Secs");


        StopWatch processStopWatch = new StopWatch();
        processStopWatch.start();

        Iterator<BibRecord> iterator = pageableBibRecords.iterator();


        ExecutorService executorService = Executors.newFixedThreadPool(50);
        List<Future> futures = new ArrayList<>();
        while (iterator.hasNext()) {
            BibRecord bibRecord = iterator.next();
            Future submit = executorService.submit(new BibRecordSetupCallable(bibRecord,
                    bibMarcRecordProcessor,
                    fieldsToTags2IncludeMap, fieldsToTags2ExcludeMap,
                    producerTemplate, oleMemorizeService));
//            Future submit = executorService.submit(new BibItemRecordSetupCallable(bibRecord, solrTemplate, bibliographicDetailsRepository, holdingsDetailsRepository, producerTemplate));
            futures.add(submit);
        }

        logger.info("Num futures to prepare Bib and Associated data : " + futures.size());

        int count= 0;
        List<SolrInputDocument> solrInputDocumentsToIndex = new ArrayList<>();
        for (Iterator<Future> futureIterator = futures.iterator(); futureIterator.hasNext(); ) {
            try {
                Future future = futureIterator.next();
                Object object = future.get();
                if(null != object) {
                    count++;
                    solrInputDocumentsToIndex.addAll((List<SolrInputDocument>) object);
                }
            } catch (Exception e) {
                logger.error("Exception : " + e.getMessage());
            }
        }

        executorService.shutdown();

        if (!CollectionUtils.isEmpty(solrInputDocumentsToIndex)) {
            SolrTemplate solrTemplate = new SolrTemplate(new HttpSolrClient(solrURL + File.separator + coreName));
            solrTemplate.setSolrCore(coreName);
            solrTemplate.saveDocuments(solrInputDocumentsToIndex);
            solrTemplate.commit();
        }

        String message = "Total solr input document for page : " + pageNum + "  docPerPage " + docsPerPage + "  = " + count;

        processStopWatch.stop();
        logger.info(message + "    time taken for process : " + processStopWatch.getTotalTimeSeconds() + " Secs");

        return solrInputDocumentsToIndex.size();
    }
}
