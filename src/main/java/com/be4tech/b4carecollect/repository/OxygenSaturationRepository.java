package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.OxygenSaturation;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OxygenSaturation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OxygenSaturationRepository extends JpaRepository<OxygenSaturation, UUID>, JpaSpecificationExecutor<OxygenSaturation> {}
