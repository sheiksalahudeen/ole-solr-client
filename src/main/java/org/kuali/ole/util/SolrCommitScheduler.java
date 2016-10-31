package org.kuali.ole.util;

import org.kuali.ole.service.SolrAdmin;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sheiks on 28/10/16.
 */
public class SolrCommitScheduler {
    public SolrCommitScheduler() {
        System.out.println("Scheduler Triggered");
        Timer timer = new Timer();
        timer.schedule(new CommitSolr(), 0, 300000);
    }

    class CommitSolr extends TimerTask {
        public void run() {
            try {
                HelperUtil.getBean(SolrAdmin.class).commit();
                System.out.println("Commit Completed");
            } catch (Exception e) {
                System.out.println("Commit failed : " + e);
                e.printStackTrace();
            }
        }
    }
}
