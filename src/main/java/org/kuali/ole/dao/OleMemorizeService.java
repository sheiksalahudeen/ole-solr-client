package org.kuali.ole.dao;

import org.kuali.ole.model.jpa.CallNumberTypeRecord;

/**
 * Created by sheiks on 10/11/16.
 */
public interface OleMemorizeService {

    public CallNumberTypeRecord getCallNumberTypeRecordById(long id);
}
