package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.CaloriesBMR;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CaloriesBMR entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaloriesBMRRepository extends JpaRepository<CaloriesBMR, UUID>, JpaSpecificationExecutor<CaloriesBMR> {}
