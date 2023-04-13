package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.LocationSample;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LocationSample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationSampleRepository extends JpaRepository<LocationSample, UUID>, JpaSpecificationExecutor<LocationSample> {}
