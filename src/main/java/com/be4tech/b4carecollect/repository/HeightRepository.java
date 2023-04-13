package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.Height;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Height entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeightRepository extends JpaRepository<Height, UUID>, JpaSpecificationExecutor<Height> {}
