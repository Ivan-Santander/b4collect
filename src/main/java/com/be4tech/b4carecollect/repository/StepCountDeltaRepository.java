package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.StepCountDelta;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StepCountDelta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StepCountDeltaRepository extends JpaRepository<StepCountDelta, UUID>, JpaSpecificationExecutor<StepCountDelta> {}
