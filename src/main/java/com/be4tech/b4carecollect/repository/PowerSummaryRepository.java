package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.PowerSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PowerSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PowerSummaryRepository extends JpaRepository<PowerSummary, UUID>, JpaSpecificationExecutor<PowerSummary> {}
