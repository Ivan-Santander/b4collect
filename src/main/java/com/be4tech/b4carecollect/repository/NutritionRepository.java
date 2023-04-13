package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.Nutrition;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nutrition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, UUID>, JpaSpecificationExecutor<Nutrition> {}
