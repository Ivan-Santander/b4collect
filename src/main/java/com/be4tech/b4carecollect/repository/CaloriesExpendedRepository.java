package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.CaloriesExpended;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CaloriesExpended entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaloriesExpendedRepository extends JpaRepository<CaloriesExpended, UUID>, JpaSpecificationExecutor<CaloriesExpended> {}
