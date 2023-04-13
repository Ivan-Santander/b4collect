package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.CyclingWheelRevolution;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CyclingWheelRevolution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CyclingWheelRevolutionRepository
    extends JpaRepository<CyclingWheelRevolution, UUID>, JpaSpecificationExecutor<CyclingWheelRevolution> {}
