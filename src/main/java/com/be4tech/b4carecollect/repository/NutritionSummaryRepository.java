package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.NutritionSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NutritionSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NutritionSummaryRepository extends JpaRepository<NutritionSummary, UUID>, JpaSpecificationExecutor<NutritionSummary> {}
