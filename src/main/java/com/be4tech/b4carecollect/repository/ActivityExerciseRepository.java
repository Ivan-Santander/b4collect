package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.ActivityExercise;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ActivityExercise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityExerciseRepository extends JpaRepository<ActivityExercise, UUID>, JpaSpecificationExecutor<ActivityExercise> {}
