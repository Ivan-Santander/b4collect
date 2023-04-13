package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.SleepScores;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SleepScores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SleepScoresRepository extends JpaRepository<SleepScores, UUID>, JpaSpecificationExecutor<SleepScores> {}
