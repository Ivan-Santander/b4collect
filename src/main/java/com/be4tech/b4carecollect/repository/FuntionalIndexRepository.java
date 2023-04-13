package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.FuntionalIndex;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuntionalIndex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuntionalIndexRepository extends JpaRepository<FuntionalIndex, UUID>, JpaSpecificationExecutor<FuntionalIndex> {}
