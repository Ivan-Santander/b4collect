package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.Weight;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Weight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeightRepository extends JpaRepository<Weight, UUID>, JpaSpecificationExecutor<Weight> {}
