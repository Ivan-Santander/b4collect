package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.MentalHealthSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MentalHealthSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MentalHealthSummaryRepository
    extends JpaRepository<MentalHealthSummary, UUID>, JpaSpecificationExecutor<MentalHealthSummary> {}
