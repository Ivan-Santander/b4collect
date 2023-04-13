package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.BloodGlucoseSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BloodGlucoseSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloodGlucoseSummaryRepository
    extends JpaRepository<BloodGlucoseSummary, UUID>, JpaSpecificationExecutor<BloodGlucoseSummary> {}
