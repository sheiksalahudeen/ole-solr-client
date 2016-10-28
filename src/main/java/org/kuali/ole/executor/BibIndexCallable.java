package org.kuali.ole.executor;

import org.apache.camel.ProducerTemplate;
import org.kuali.ole.model.jpa.BibRecord;
import org.kuali.ole.repo.BibRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    private Integer owningInstitutionId;
    private BibRecordRepository bibRecordRepository;
    private ProducerTemplate producerTemplate;

    public BibIndexCallable(int pageNum, int docsPerPage, String coreName, String solrURL, Integer owningInstitutionId,
                            BibRecordRepository bibRecordRepository, ProducerTemplate producerTemplate) {
        this.pageNum = pageNum;
        this.docsPerPage = docsPerPage;
        this.coreName = coreName;
        this.solrURL = solrURL;
        this.owningInstitutionId = owningInstitutionId;
        this.bibRecordRepository = bibRecordRepository;
        this.producerTemplate = producerTemplate;
    }

    @Override
    public Object call() throws Exception {
        Page<BibRecord> bibRecords = bibRecordRepository.findAll(new PageRequest(pageNum, docsPerPage));
        logger.info("Num Bibs Fetched : " + bibRecords.getNumberOfElements());

        return null;
    }
}
