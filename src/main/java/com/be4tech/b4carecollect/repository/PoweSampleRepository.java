package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.PoweSample;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PoweSample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoweSampleRepository extends JpaRepository<PoweSample, UUID>, JpaSpecificationExecutor<PoweSample> {}
