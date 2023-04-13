package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.BloodPressure;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BloodPressure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressure, UUID>, JpaSpecificationExecutor<BloodPressure> {}
