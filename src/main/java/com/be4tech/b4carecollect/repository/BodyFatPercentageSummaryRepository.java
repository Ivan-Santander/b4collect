package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.BodyFatPercentageSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BodyFatPercentageSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BodyFatPercentageSummaryRepository
    extends JpaRepository<BodyFatPercentageSummary, UUID>, JpaSpecificationExecutor<BodyFatPercentageSummary> {}
