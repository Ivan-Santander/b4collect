package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.DistanceDelta;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DistanceDelta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistanceDeltaRepository extends JpaRepository<DistanceDelta, UUID>, JpaSpecificationExecutor<DistanceDelta> {}
