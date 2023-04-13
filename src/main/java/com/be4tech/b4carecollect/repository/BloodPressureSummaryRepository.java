package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.BloodPressureSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BloodPressureSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloodPressureSummaryRepository
    extends JpaRepository<BloodPressureSummary, UUID>, JpaSpecificationExecutor<BloodPressureSummary> {}
