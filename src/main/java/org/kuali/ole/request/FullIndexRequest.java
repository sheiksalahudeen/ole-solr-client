package org.kuali.ole.request;

/**
 * Created by sheiks on 08/11/16.
 */
public class FullIndexRequest {
    private Integer noOfDbThreads;
    private Integer noOfBibProcessThreads;
    private Integer docsPerThread;

    public Integer getNoOfDbThreads() {
        return noOfDbThreads;
    }

    public void setNoOfDbThreads(Integer noOfDbThreads) {
        this.noOfDbThreads = noOfDbThreads;
    }

    public Integer getNoOfBibProcessThreads() {
        return noOfBibProcessThreads;
    }

    public void setNoOfBibProcessThreads(Integer noOfBibProcessThreads) {
        this.noOfBibProcessThreads = noOfBibProcessThreads;
    }

    public Integer getDocsPerThread() {
        return docsPerThread;
    }

    public void setDocsPerThread(Integer docsPerThread) {
        this.docsPerThread = docsPerThread;
    }

    @Override
    public String toString() {
        return "FullIndexRequest{" +
                "noOfDbThreads=" + noOfDbThreads +
                ", noOfBibProcessThreads=" + noOfBibProcessThreads +
                ", docsPerThread=" + docsPerThread +
                '}';
    }
}
