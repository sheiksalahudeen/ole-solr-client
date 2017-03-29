package org.kuali.ole.response;

/**
 * Created by sheiks on 27/03/17.
 */
public class PartialIndexStatus extends IndexStatus {
    private static PartialIndexStatus ourInstance = new PartialIndexStatus();

    public static PartialIndexStatus getInstance() {
        return ourInstance;
    }

    private PartialIndexStatus() {
    }
}
