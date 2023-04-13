package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.HeartMinutes;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HeartMinutes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeartMinutesRepository extends JpaRepository<HeartMinutes, UUID>, JpaSpecificationExecutor<HeartMinutes> {}
