package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.SleepSegment;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SleepSegment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SleepSegmentRepository extends JpaRepository<SleepSegment, UUID>, JpaSpecificationExecutor<SleepSegment> {}
