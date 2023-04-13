package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.MentalHealth;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MentalHealth entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MentalHealthRepository extends JpaRepository<MentalHealth, UUID>, JpaSpecificationExecutor<MentalHealth> {}
