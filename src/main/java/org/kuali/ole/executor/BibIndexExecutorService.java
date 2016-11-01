package org.kuali.ole.executor;

import com.google.common.collect.Lists;
import org.apache.camel.component.seda.SedaEndpoint;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.kuali.ole.common.DocumentSearchConfig;
import org.kuali.ole.common.marc.xstream.BibMarcRecordProcessor;
import org.kuali.ole.repo.BibRecordRepository;
import org.kuali.ole.service.SolrAdmin;
import org.kuali.ole.util.HelperUtil;
import org.kuali.ole.util.SolrCommitScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by sheiks on 28/10/16.
 */
@Component
public class BibIndexExecutorService {
    Logger logger = LoggerFactory.getLogger(BibIndexExecutorService.class);

    @Value("${solr.url}")
    String solrUrl;

    @Value("${solr.parent.core}")
    String solrCore;

    @Autowired
    BibRecordRepository bibRecordRepository;
    private BibMarcRecordProcessor bibMarcRecordProcessor;

    public Integer indexDocument() {
        DocumentSearchConfig documentSearchConfig = DocumentSearchConfig.getDocumentSearchConfig();
        Integer numThreads = 75;
        Integer docsPerThread = 1000;
        Integer commitIndexesInterval = 100000;


        StopWatch mainStopWatch = new StopWatch();
        mainStopWatch.start();

        Integer totalBibsProcessed = 0;

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

            Integer totalDocCount = ((Long)bibRecordRepository.count()).intValue();
            logger.info("Total Document Count From DB : " + totalDocCount);

            new SolrCommitScheduler(); // Todo : Need to find proper place to trigger.

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
                callableCountByCommitInterval = 1000;
                logger.info("Number of callables to execute to commit indexes : " + callableCountByCommitInterval);

                StopWatch stopWatch = new StopWatch();
                stopWatch.start();

                List<Callable<Integer>> callables = new ArrayList<>();
                for (int pageNum = 0; pageNum < loopCount; pageNum++) {
                    Callable callable = new BibIndexCallable(pageNum, docsPerThread,solrCore,  solrUrl,
                            getBibMarcRecordProcessor(),
                            documentSearchConfig.FIELDS_TO_TAGS_2_INCLUDE_MAP,
                            documentSearchConfig.FIELDS_TO_TAGS_2_EXCLUDE_MAP);
                    callables.add(callable);
                }

                int futureCount = 0;
                List<List<Callable<Integer>>> partitions = Lists.partition(new ArrayList<Callable<Integer>>(callables), callableCountByCommitInterval);
                for (List<Callable<Integer>> partitionCallables : partitions) {
                    List<Future<Integer>> futures = executorService.invokeAll(partitionCallables);
                    futures
                            .stream()
                            .map(future -> {
                                try {
                                    return future.get();
                                } catch (Exception e) {
                                    throw new IllegalStateException(e);
                                }
                            });
                    logger.info("No of Futures Added : " + futures.size());

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
                executorService.shutdown();

                //Final commit
//                producerTemplate.asyncRequestBodyAndHeader(solrRouterURI + "://" + solrUri + "/" + solrCore, "", SolrConstants.OPERATION, SolrConstants.OPERATION_COMMIT);
            } else {
                logger.info("No records found to index for the criteria");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainStopWatch.stop();
        try {
            HelperUtil.getBean(SolrAdmin.class).commit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
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
}
