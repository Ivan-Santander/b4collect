package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.CiclingPedalingCadence;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CiclingPedalingCadence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CiclingPedalingCadenceRepository
    extends JpaRepository<CiclingPedalingCadence, UUID>, JpaSpecificationExecutor<CiclingPedalingCadence> {}
