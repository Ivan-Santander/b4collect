package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.UserMedicalInfo;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserMedicalInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserMedicalInfoRepository extends JpaRepository<UserMedicalInfo, UUID>, JpaSpecificationExecutor<UserMedicalInfo> {}
