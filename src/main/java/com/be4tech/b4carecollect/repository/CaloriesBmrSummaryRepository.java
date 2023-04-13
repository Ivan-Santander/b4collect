package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.CaloriesBmrSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CaloriesBmrSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaloriesBmrSummaryRepository
    extends JpaRepository<CaloriesBmrSummary, UUID>, JpaSpecificationExecutor<CaloriesBmrSummary> {}
