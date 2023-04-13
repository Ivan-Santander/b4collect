package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.BodyFatPercentage;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BodyFatPercentage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BodyFatPercentageRepository extends JpaRepository<BodyFatPercentage, UUID>, JpaSpecificationExecutor<BodyFatPercentage> {}
