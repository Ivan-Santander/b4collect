package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.WeightSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WeightSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeightSummaryRepository extends JpaRepository<WeightSummary, UUID>, JpaSpecificationExecutor<WeightSummary> {}
