package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.ActivitySummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ActivitySummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivitySummaryRepository extends JpaRepository<ActivitySummary, UUID>, JpaSpecificationExecutor<ActivitySummary> {}
