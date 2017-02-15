package org.kuali.ole.repo.jpa;

import org.kuali.ole.model.jpa.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by sheiks on 14/02/17.
 */
public interface ReportDetailRepository extends JpaRepository<ReportEntity, Integer> {

    @Query(value = "select * from report_t where FILE_NAME=?1 and TYPE=?2 and CREATED_DATE >= ?3 and CREATED_DATE <= ?4", nativeQuery = true)
    List<ReportEntity> findByFileAndTypeAndDateRange(String fileName, String type, Date from, Date to);
}
