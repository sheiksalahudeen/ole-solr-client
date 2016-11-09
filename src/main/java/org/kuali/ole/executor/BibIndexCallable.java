package org.kuali.ole.executor;

import org.apache.camel.ProducerTemplate;
import org.kuali.ole.common.marc.xstream.BibMarcRecordProcessor;
import org.kuali.ole.indexer.BibIndexingTxObject;
import org.kuali.ole.model.jpa.BibRecord;
import org.kuali.ole.repo.BibRecordRepository;
import org.kuali.ole.util.HelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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
    private int noOfBibProcessThreads;

    public BibIndexCallable(int pageNum, int docsPerPage, String coreName, String solrURL,
                            BibMarcRecordProcessor bibMarcRecordProcessor,
                            Map<String, String> fieldsToTags2IncludeMap,
                            Map<String, String> fieldsToTags2ExcludeMap,
                            ProducerTemplate producerTemplate,
                            int noOfBibProcessThreads) {
        this.pageNum = pageNum;
        this.docsPerPage = docsPerPage;
        this.coreName = coreName;
        this.solrURL = solrURL;
        this.bibMarcRecordProcessor = bibMarcRecordProcessor;
        this.fieldsToTags2IncludeMap.putAll(fieldsToTags2IncludeMap);
        this.fieldsToTags2ExcludeMap.putAll(fieldsToTags2ExcludeMap);
        this.producerTemplate = producerTemplate;
        this.noOfBibProcessThreads = noOfBibProcessThreads;
    }

    @Override
    public Object call() throws Exception {
        StopWatch mainStopWatch = new StopWatch();
        mainStopWatch.start();
        Page<BibRecord> pageableBibRecords = HelperUtil.getRepository(BibRecordRepository.class).findAll(new PageRequest(pageNum, docsPerPage));
        mainStopWatch.stop();
        logger.info("Page Num : " + pageNum + "    Num Bibs Fetched : " + pageableBibRecords.getNumberOfElements() + "   and time taken to fetch : " + mainStopWatch.getTotalTimeSeconds() + " Secs");

        BibIndexingTxObject bibIndexingTxObject = new BibIndexingTxObject();
        List<BibRecord> content = pageableBibRecords.getContent();
        bibIndexingTxObject.setBibRecords(content);
        bibIndexingTxObject.setFIELDS_TO_TAGS_2_INCLUDE_MAP(fieldsToTags2IncludeMap);
        bibIndexingTxObject.setFIELDS_TO_TAGS_2_EXCLUDE_MAP(fieldsToTags2ExcludeMap);
        bibIndexingTxObject.setPageNum(pageNum);
        bibIndexingTxObject.setDocsPerPage(docsPerPage);
        bibIndexingTxObject.setNoOfBibProcessThreads(noOfBibProcessThreads);
        bibIndexingTxObject.setBibMarcRecordProcessor(bibMarcRecordProcessor);
        producerTemplate.sendBody("oleactivemq:queue:bibQ", bibIndexingTxObject);
        producerTemplate.sendBody("oleactivemq:queue:bibFetchedQ", content.size());
        return 0;
    }
}
