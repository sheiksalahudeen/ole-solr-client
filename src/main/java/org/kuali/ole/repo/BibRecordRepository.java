package org.kuali.ole.repo;

import org.kuali.ole.model.jpa.BibRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sheiks on 27/10/16.
 */
public interface BibRecordRepository extends JpaRepository<BibRecord, Integer> {
}
