package org.kuali.ole.response;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by sheiks on 08/11/16.
 */
public class FullIndexStatus extends IndexStatus {
    private Integer noOfDbThreads;
    private Integer docsPerThread;

    private static FullIndexStatus ourInstance = new FullIndexStatus();

    public static FullIndexStatus getInstance() {
        return ourInstance;
    }

    private FullIndexStatus() {
    }

    public Integer getNoOfDbThreads() {
        return noOfDbThreads;
    }

    public void setNoOfDbThreads(Integer noOfDbThreads) {
        this.noOfDbThreads = noOfDbThreads;
    }

    public Integer getDocsPerThread() {
        return docsPerThread;
    }

    public void setDocsPerThread(Integer docsPerThread) {
        this.docsPerThread = docsPerThread;
    }
}
