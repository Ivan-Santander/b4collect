package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.UserDemographics;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserDemographics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDemographicsRepository extends JpaRepository<UserDemographics, UUID>, JpaSpecificationExecutor<UserDemographics> {}
