package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.UserMedicalInfo;
import com.be4tech.b4carecollect.repository.UserMedicalInfoRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserMedicalInfo}.
 */
@Service
@Transactional
public class UserMedicalInfoService {

    private final Logger log = LoggerFactory.getLogger(UserMedicalInfoService.class);

    private final UserMedicalInfoRepository userMedicalInfoRepository;

    public UserMedicalInfoService(UserMedicalInfoRepository userMedicalInfoRepository) {
        this.userMedicalInfoRepository = userMedicalInfoRepository;
    }

    /**
     * Save a userMedicalInfo.
     *
     * @param userMedicalInfo the entity to save.
     * @return the persisted entity.
     */
    public UserMedicalInfo save(UserMedicalInfo userMedicalInfo) {
        log.debug("Request to save UserMedicalInfo : {}", userMedicalInfo);
        return userMedicalInfoRepository.save(userMedicalInfo);
    }

    /**
     * Update a userMedicalInfo.
     *
     * @param userMedicalInfo the entity to save.
     * @return the persisted entity.
     */
    public UserMedicalInfo update(UserMedicalInfo userMedicalInfo) {
        log.debug("Request to update UserMedicalInfo : {}", userMedicalInfo);
        return userMedicalInfoRepository.save(userMedicalInfo);
    }

    /**
     * Partially update a userMedicalInfo.
     *
     * @param userMedicalInfo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserMedicalInfo> partialUpdate(UserMedicalInfo userMedicalInfo) {
        log.debug("Request to partially update UserMedicalInfo : {}", userMedicalInfo);

        return userMedicalInfoRepository
            .findById(userMedicalInfo.getId())
            .map(existingUserMedicalInfo -> {
                if (userMedicalInfo.getUsuarioId() != null) {
                    existingUserMedicalInfo.setUsuarioId(userMedicalInfo.getUsuarioId());
                }
                if (userMedicalInfo.getEmpresaId() != null) {
                    existingUserMedicalInfo.setEmpresaId(userMedicalInfo.getEmpresaId());
                }
                if (userMedicalInfo.getHypertension() != null) {
                    existingUserMedicalInfo.setHypertension(userMedicalInfo.getHypertension());
                }
                if (userMedicalInfo.getHighGlucose() != null) {
                    existingUserMedicalInfo.setHighGlucose(userMedicalInfo.getHighGlucose());
                }
                if (userMedicalInfo.getHiabetes() != null) {
                    existingUserMedicalInfo.setHiabetes(userMedicalInfo.getHiabetes());
                }
                if (userMedicalInfo.getTotalCholesterol() != null) {
                    existingUserMedicalInfo.setTotalCholesterol(userMedicalInfo.getTotalCholesterol());
                }
                if (userMedicalInfo.getHdlCholesterol() != null) {
                    existingUserMedicalInfo.setHdlCholesterol(userMedicalInfo.getHdlCholesterol());
                }
                if (userMedicalInfo.getMedicalMainCondition() != null) {
                    existingUserMedicalInfo.setMedicalMainCondition(userMedicalInfo.getMedicalMainCondition());
                }
                if (userMedicalInfo.getMedicalSecundaryCondition() != null) {
                    existingUserMedicalInfo.setMedicalSecundaryCondition(userMedicalInfo.getMedicalSecundaryCondition());
                }
                if (userMedicalInfo.getMedicalMainMedication() != null) {
                    existingUserMedicalInfo.setMedicalMainMedication(userMedicalInfo.getMedicalMainMedication());
                }
                if (userMedicalInfo.getMedicalSecundaryMedication() != null) {
                    existingUserMedicalInfo.setMedicalSecundaryMedication(userMedicalInfo.getMedicalSecundaryMedication());
                }
                if (userMedicalInfo.getMedicalScore() != null) {
                    existingUserMedicalInfo.setMedicalScore(userMedicalInfo.getMedicalScore());
                }
                if (userMedicalInfo.getEndTime() != null) {
                    existingUserMedicalInfo.setEndTime(userMedicalInfo.getEndTime());
                }

                return existingUserMedicalInfo;
            })
            .map(userMedicalInfoRepository::save);
    }

    /**
     * Get all the userMedicalInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserMedicalInfo> findAll(Pageable pageable) {
        log.debug("Request to get all UserMedicalInfos");
        return userMedicalInfoRepository.findAll(pageable);
    }

    /**
     * Get one userMedicalInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserMedicalInfo> findOne(UUID id) {
        log.debug("Request to get UserMedicalInfo : {}", id);
        return userMedicalInfoRepository.findById(id);
    }

    /**
     * Delete the userMedicalInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete UserMedicalInfo : {}", id);
        userMedicalInfoRepository.deleteById(id);
    }
}
