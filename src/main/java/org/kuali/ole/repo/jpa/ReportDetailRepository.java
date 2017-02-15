package org.kuali.ole.repo.jpa;

import org.kuali.ole.model.jpa.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sheiks on 14/02/17.
 */
public interface ReportDetailRepository extends JpaRepository<ReportEntity, Integer> {
}
