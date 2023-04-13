package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.BloodGlucose;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BloodGlucose entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloodGlucoseRepository extends JpaRepository<BloodGlucose, UUID>, JpaSpecificationExecutor<BloodGlucose> {}
