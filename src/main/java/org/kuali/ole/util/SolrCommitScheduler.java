package org.kuali.ole.util;

import org.kuali.ole.executor.BibIndexCallable;
import org.kuali.ole.service.SolrAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sheiks on 28/10/16.
 */
public class SolrCommitScheduler {

    Logger logger = LoggerFactory.getLogger(BibIndexCallable.class);

    public SolrCommitScheduler() {
        logger.info("Scheduler Triggered");
        Timer timer = new Timer();
        timer.schedule(new CommitSolr(), 0, 300000);
    }

    class CommitSolr extends TimerTask {
        public void run() {
            try {
                HelperUtil.getBean(SolrAdmin.class).commit();
                logger.info("-------------------------- Commit Completed ----------------------------");
            } catch (Exception e) {
                System.out.println("Commit failed : " + e);
                e.printStackTrace();
            }
        }
    }
}
