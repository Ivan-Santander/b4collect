package com.be4tech.b4carecollect.repository;

import com.be4tech.b4carecollect.domain.UserBodyInfo;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UserBodyInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserBodyInfoRepository extends JpaRepository<UserBodyInfo, UUID>, JpaSpecificationExecutor<UserBodyInfo> {}
