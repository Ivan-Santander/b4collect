package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.HeartRateBpm;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HeartRateBpm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeartRateBpmRepository extends JpaRepository<HeartRateBpm, UUID>, JpaSpecificationExecutor<HeartRateBpm> {}
