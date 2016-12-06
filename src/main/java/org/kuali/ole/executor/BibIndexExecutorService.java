package org.kuali.ole.executor;

import com.google.common.collect.Lists;
import org.apache.camel.ProducerTemplate;
import org.apache.solr.client.solrj.SolrServerException;
import org.kuali.ole.common.DocumentSearchConfig;
import org.kuali.ole.common.marc.xstream.BibMarcRecordProcessor;
import org.kuali.ole.repo.BibRecordRepository;
import org.kuali.ole.repo.solr.BibCrudRepositoryMultiCoreSupport;
import org.kuali.ole.request.FullIndexRequest;
import org.kuali.ole.response.FullIndexStatus;
import org.kuali.ole.service.SolrAdmin;
import org.kuali.ole.util.HelperUtil;
import org.kuali.ole.util.SolrCommitScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by sheiks on 28/10/16.
 */
@Service
public class BibIndexExecutorService {
    Logger logger = LoggerFactory.getLogger(BibIndexExecutorService.class);

    @Value("${solr.url}")
    String solrUrl;

    @Value("${solr.parent.core}")
    String solrCore;

    @Value("${solr.server.protocol}")
    String solrServerProtocol;

    @Autowired
    SolrAdmin solrAdmin;

    @Autowired
    BibRecordRepository bibRecordRepository;

    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    SolrTemplate solrTemplate;
    private BibMarcRecordProcessor bibMarcRecordProcessor;

    @Async
    public Integer indexDocument(FullIndexRequest fullIndexRequest) {
        DocumentSearchConfig documentSearchConfig = DocumentSearchConfig.getDocumentSearchConfig();
        Integer numThreads = fullIndexRequest.getNoOfDbThreads();
        Integer docsPerThread = fullIndexRequest.getDocsPerThread();
        Integer commitIndexesInterval = 100000;

        StopWatch mainStopWatch = new StopWatch();
        mainStopWatch.start();

        Integer totalBibsProcessed = 0;
        SolrCommitScheduler solrCommitScheduler = null;

        try {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);

            Integer totalDocCount = ((Long)bibRecordRepository.count()).intValue();
            logger.info("Total Document Count From DB : " + totalDocCount);

            if (totalDocCount > 0) {
                int quotient = totalDocCount / (docsPerThread);
                int remainder = totalDocCount % (docsPerThread);
                Integer loopCount = remainder == 0 ? quotient : quotient + 1;
                logger.info("Loop Count Value : " + loopCount);
                logger.info("Commit Indexes Interval : " + commitIndexesInterval);

                Integer callableCountByCommitInterval = commitIndexesInterval / (docsPerThread);
                if (callableCountByCommitInterval == 0) {
                    callableCountByCommitInterval = 1;
                }
                logger.info("Number of callables to execute to commit indexes : " + callableCountByCommitInterval);

                List<String> coreNames = new ArrayList<>();
                setupCoreNames(numThreads, coreNames);
                solrAdmin.createSolrCores(coreNames);

                StopWatch stopWatch = new StopWatch();
                stopWatch.start();

                int coreNum = 0;
                List<Callable<Integer>> callables = new ArrayList<>();
                for (int pageNum = 0; pageNum < loopCount; pageNum++) {

                    Callable callable = new BibIndexCallable("http://" + solrUrl, coreNames.get(coreNum), pageNum, docsPerThread,
                            getBibMarcRecordProcessor(),
                            documentSearchConfig.FIELDS_TO_TAGS_2_INCLUDE_MAP,
                            documentSearchConfig.FIELDS_TO_TAGS_2_EXCLUDE_MAP,
                            producerTemplate,solrTemplate);
                    callables.add(callable);
                    coreNum = coreNum < numThreads - 1 ? coreNum + 1 : 0;
                }

                int futureCount = 0;
                List<List<Callable<Integer>>> partitions = Lists.partition(new ArrayList<Callable<Integer>>(callables), callableCountByCommitInterval);
                for (List<Callable<Integer>> partitionCallables : partitions) {
                    List<Future<Integer>> futures = threadPoolExecutor.invokeAll(partitionCallables);
                    futures
                            .stream()
                            .map(future -> {
                                try {
                                    return future.get();
                                } catch (Exception e) {
                                    throw new IllegalStateException(e);
                                }
                            });
                    int numOfBibsProcessed = 0;
                    for (Iterator<Future<Integer>> iterator = futures.iterator(); iterator.hasNext(); ) {
                        Future future = iterator.next();
                        try {
                            Integer entitiesCount = (Integer) future.get();
                            numOfBibsProcessed += entitiesCount;
                            totalBibsProcessed += entitiesCount;
                            //logger.info("Num of bibs fetched by thread : " + entitiesCount);
                            futureCount++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }

                    solrAdmin.mergeCores(coreNames);
                    logger.info("Solr core status : " + solrAdmin.getCoresStatus());
                    while (solrAdmin.getCoresStatus() != 0) {
                        logger.info("Solr core status : " + solrAdmin.getCoresStatus());
                    }
                    deleteTempIndexes(coreNames, solrServerProtocol + solrUrl);
                    logger.info("Num of Bibs Processed and indexed to core " + solrCore + " on commit interval : " + numOfBibsProcessed);
                    logger.info("Total Num of Bibs Processed and indexed to core " + solrCore + " : " + totalBibsProcessed);
//                    Long solrBibCount = bibSolrCrudRepository.countByDocType(RecapConstants.BIB);
//                    logger.info("Total number of Bibs in Solr : " + solrBibCount);

                    /*SedaEndpoint solrQSedaEndPoint = (SedaEndpoint) producerTemplate.getCamelContext().getEndpoint(RecapConstants.SOLR_QUEUE);
                    Integer solrQSize = solrQSedaEndPoint.getExchanges().size();
                    logger.info("Solr Queue size : " + solrQSize);
                    while (solrQSize != 0) {
                        solrQSize = solrQSedaEndPoint.getExchanges().size();
                    }
                    Future<Object> future = producerTemplate.asyncRequestBodyAndHeader(solrRouterURI + "://" + solrUri + "/" + coreName, "", SolrConstants.OPERATION, SolrConstants.OPERATION_COMMIT);
                    while (!future.isDone()) {
                        //NoOp.
                    }
                    logger.info("Commit future done : " + future.isDone());
                    logger.info("Num of Bibs Processed and indexed to core " + coreName + " on commit interval : " + numOfBibsProcessed);
                    logger.info("Total Num of Bibs Processed and indexed to core " + coreName + " : " + totalBibsProcessed);*/
                }
                logger.info("Total futures executed: " + futureCount);
                stopWatch.stop();
                logger.info("Time taken to fetch " + totalBibsProcessed + " Bib Records and index to core " + solrCore + " : " + stopWatch.getTotalTimeSeconds() + " seconds");
                solrAdmin.unLoadCores(coreNames);
                threadPoolExecutor.shutdown();

                //Final commit
//                producerTemplate.asyncRequestBodyAndHeader(solrRouterURI + "://" + solrUri + "/" + solrCore, "", SolrConstants.OPERATION, SolrConstants.OPERATION_COMMIT);
            } else {
                logger.info("No records found to index for the criteria");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FullIndexStatus instance = FullIndexStatus.getInstance();
            instance.setEndTime(new Date());
        }
        mainStopWatch.stop();
        logger.info("Total time taken:" + mainStopWatch.getTotalTimeSeconds() + " secs");
        return totalBibsProcessed;
    }

    public BibMarcRecordProcessor getBibMarcRecordProcessor() {
        if(null == bibMarcRecordProcessor) {
            bibMarcRecordProcessor = new BibMarcRecordProcessor();
        }
        return bibMarcRecordProcessor;
    }

    public void setBibMarcRecordProcessor(BibMarcRecordProcessor bibMarcRecordProcessor) {
        this.bibMarcRecordProcessor = bibMarcRecordProcessor;
    }

    private void setupCoreNames(Integer numThreads, List<String> coreNames) {
        for (int i = 0; i < numThreads; i++) {
            coreNames.add("temp" + i);
        }
    }

    private void deleteTempIndexes(List<String> coreNames, String solrUrl) {
        for (Iterator<String> iterator = coreNames.iterator(); iterator.hasNext(); ) {
            String coreName = iterator.next();
            BibCrudRepositoryMultiCoreSupport bibCrudRepositoryMultiCoreSupport = new BibCrudRepositoryMultiCoreSupport(coreName, solrUrl);
            bibCrudRepositoryMultiCoreSupport.deleteAll();
        }
    }
}
