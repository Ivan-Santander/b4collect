package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.OxygenSaturationSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OxygenSaturationSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OxygenSaturationSummaryRepository
    extends JpaRepository<OxygenSaturationSummary, UUID>, JpaSpecificationExecutor<OxygenSaturationSummary> {}
