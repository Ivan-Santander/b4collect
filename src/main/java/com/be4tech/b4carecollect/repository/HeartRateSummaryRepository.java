package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.HeartRateSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HeartRateSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeartRateSummaryRepository extends JpaRepository<HeartRateSummary, UUID>, JpaSpecificationExecutor<HeartRateSummary> {}
