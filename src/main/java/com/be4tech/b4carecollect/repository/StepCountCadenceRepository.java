package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.StepCountCadence;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StepCountCadence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StepCountCadenceRepository extends JpaRepository<StepCountCadence, UUID>, JpaSpecificationExecutor<StepCountCadence> {}
