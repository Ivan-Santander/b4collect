package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.SpeedSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SpeedSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeedSummaryRepository extends JpaRepository<SpeedSummary, UUID>, JpaSpecificationExecutor<SpeedSummary> {}
