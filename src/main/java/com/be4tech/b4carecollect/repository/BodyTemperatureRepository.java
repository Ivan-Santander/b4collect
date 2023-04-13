package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.BodyTemperature;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BodyTemperature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BodyTemperatureRepository extends JpaRepository<BodyTemperature, UUID>, JpaSpecificationExecutor<BodyTemperature> {}
