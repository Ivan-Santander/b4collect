package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.Speed;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Speed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeedRepository extends JpaRepository<Speed, UUID>, JpaSpecificationExecutor<Speed> {}
