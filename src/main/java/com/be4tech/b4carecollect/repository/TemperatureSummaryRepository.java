package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.TemperatureSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TemperatureSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemperatureSummaryRepository
    extends JpaRepository<TemperatureSummary, UUID>, JpaSpecificationExecutor<TemperatureSummary> {}
