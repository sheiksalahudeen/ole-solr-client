package org.kuali.ole.dao.impl;

import org.kuali.ole.dao.OleMemorizeService;
import org.kuali.ole.model.jpa.CallNumberTypeRecord;
import org.kuali.ole.repo.CallNumberTypeRecordRepository;
import org.kuali.ole.spring.cache.Memoize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sheiks on 10/11/16.
 */
@Component
public class OleMemorizeServiceImpl implements OleMemorizeService {

    @Autowired
    CallNumberTypeRecordRepository callNumberTypeRecordRepository;

    @Memoize
    public CallNumberTypeRecord getCallNumberTypeRecordById(long id) {
        return callNumberTypeRecordRepository.findOne(id);
    }
}
