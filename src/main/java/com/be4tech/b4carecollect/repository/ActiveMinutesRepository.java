package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.ActiveMinutes;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ActiveMinutes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActiveMinutesRepository extends JpaRepository<ActiveMinutes, UUID>, JpaSpecificationExecutor<ActiveMinutes> {}
