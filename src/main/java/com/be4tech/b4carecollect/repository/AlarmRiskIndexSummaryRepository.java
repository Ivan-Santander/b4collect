package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.AlarmRiskIndexSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AlarmRiskIndexSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmRiskIndexSummaryRepository
    extends JpaRepository<AlarmRiskIndexSummary, UUID>, JpaSpecificationExecutor<AlarmRiskIndexSummary> {}
