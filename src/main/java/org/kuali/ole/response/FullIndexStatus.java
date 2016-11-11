package org.kuali.ole.response;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by sheiks on 08/11/16.
 */
public class FullIndexStatus {
    private String status;
    private Date startTime;
    private Date endTime;
    private int numberOfBibFetched;
    private int numberOfBibProcessed;
    private int numberOfHoldingsFetched;
    private int numberOfHoldingsProcessed;
    private int numberOfItemsFetched;
    private int numberOfItemsProcessed;
    private boolean running;
    private Integer noOfDbThreads;
    private Integer noOfBibProcessThreads;
    private Integer docsPerThread;

    private static FullIndexStatus ourInstance = new FullIndexStatus();

    public static FullIndexStatus getInstance() {
        return ourInstance;
    }

    private FullIndexStatus() {
    }

    public String getStatus() {
        if(running) {
            return "Running";
        } else {
            if(null != startTime) {
                return "Finished";
            } else {
                return "";
            }
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getNumberOfBibFetched() {
        return numberOfBibFetched;
    }

    public void setNumberOfBibFetched(int numberOfBibFetched) {
        this.numberOfBibFetched = numberOfBibFetched;
    }

    public int getNumberOfBibProcessed() {
        return numberOfBibProcessed;
    }

    public void setNumberOfBibProcessed(int numberOfBibProcessed) {
        this.numberOfBibProcessed = numberOfBibProcessed;
    }

    public int getNumberOfHoldingsFetched() {
        return numberOfHoldingsFetched;
    }

    public void setNumberOfHoldingsFetched(int numberOfHoldingsFetched) {
        this.numberOfHoldingsFetched = numberOfHoldingsFetched;
    }

    public int getNumberOfHoldingsProcessed() {
        return numberOfHoldingsProcessed;
    }

    public void setNumberOfHoldingsProcessed(int numberOfHoldingsProcessed) {
        this.numberOfHoldingsProcessed = numberOfHoldingsProcessed;
    }

    public int getNumberOfItemsFetched() {
        return numberOfItemsFetched;
    }

    public void setNumberOfItemsFetched(int numberOfItemsFetched) {
        this.numberOfItemsFetched = numberOfItemsFetched;
    }

    public int getNumberOfItemsProcessed() {
        return numberOfItemsProcessed;
    }

    public void setNumberOfItemsProcessed(int numberOfItemsProcessed) {
        this.numberOfItemsProcessed = numberOfItemsProcessed;
    }

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

    public void addBibFetched(int size) {
        synchronized (this) {
            this.numberOfBibFetched += size;
        }
    }

    public void addBibProcessed(int count) {
        synchronized (this) {
            this.numberOfBibProcessed += count;
        }
    }

    public void addHoldingsFetched(int size) {
        synchronized (this) {
            this.numberOfHoldingsFetched += size;
        }
    }

    public void addHoldingsProcessed(int count) {
        synchronized (this) {
            this.numberOfHoldingsProcessed += count;
        }
    }

    public void resetStatus() {
        this.running = true;
        startTime = new Date();
        endTime = null;
        numberOfBibFetched = 0;
        numberOfBibProcessed = 0;
        numberOfHoldingsFetched = 0;
        numberOfHoldingsProcessed = 0;
        numberOfItemsFetched = 0;
        numberOfItemsProcessed = 0;
    }
}
