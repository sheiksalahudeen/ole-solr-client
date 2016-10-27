package org.kuali.ole.repo;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.kuali.ole.BaseTestCase;
import org.kuali.ole.model.BibRecord;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sheiks on 27/10/16.
 */
public class BibRecordRepositoryTest extends BaseTestCase{

    @Test
    public void testFetchAllBib() {
        assertNotNull(bibRecordRepository);
        List<BibRecord> allBibs = bibRecordRepository.findAll();
        assertTrue(CollectionUtils.isNotEmpty(allBibs));
    }


}