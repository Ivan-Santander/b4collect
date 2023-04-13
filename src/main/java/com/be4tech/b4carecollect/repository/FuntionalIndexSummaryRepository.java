package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.FuntionalIndexSummary;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuntionalIndexSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuntionalIndexSummaryRepository
    extends JpaRepository<FuntionalIndexSummary, UUID>, JpaSpecificationExecutor<FuntionalIndexSummary> {}
