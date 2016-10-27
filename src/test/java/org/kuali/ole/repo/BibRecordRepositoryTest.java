package org.kuali.ole.repo;

import org.junit.Test;
import org.kuali.ole.BaseTestCase;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sheiks on 27/10/16.
 */
public class BibRecordRepositoryTest extends BaseTestCase{

    @Test
    public void testFetchCount() {
        assertNotNull(bibRecordRepository);
        long count = bibRecordRepository.count();
        assertTrue(count != 0);
        System.out.println("total bib count : " + count);
    }


}