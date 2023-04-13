package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.UserBodyInfo;
import com.be4tech.b4carecollect.repository.UserBodyInfoRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserBodyInfo}.
 */
@Service
@Transactional
public class UserBodyInfoService {

    private final Logger log = LoggerFactory.getLogger(UserBodyInfoService.class);

    private final UserBodyInfoRepository userBodyInfoRepository;

    public UserBodyInfoService(UserBodyInfoRepository userBodyInfoRepository) {
        this.userBodyInfoRepository = userBodyInfoRepository;
    }

    /**
     * Save a userBodyInfo.
     *
     * @param userBodyInfo the entity to save.
     * @return the persisted entity.
     */
    public UserBodyInfo save(UserBodyInfo userBodyInfo) {
        log.debug("Request to save UserBodyInfo : {}", userBodyInfo);
        return userBodyInfoRepository.save(userBodyInfo);
    }

    /**
     * Update a userBodyInfo.
     *
     * @param userBodyInfo the entity to save.
     * @return the persisted entity.
     */
    public UserBodyInfo update(UserBodyInfo userBodyInfo) {
        log.debug("Request to update UserBodyInfo : {}", userBodyInfo);
        return userBodyInfoRepository.save(userBodyInfo);
    }

    /**
     * Partially update a userBodyInfo.
     *
     * @param userBodyInfo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserBodyInfo> partialUpdate(UserBodyInfo userBodyInfo) {
        log.debug("Request to partially update UserBodyInfo : {}", userBodyInfo);

        return userBodyInfoRepository
            .findById(userBodyInfo.getId())
            .map(existingUserBodyInfo -> {
                if (userBodyInfo.getUsuarioId() != null) {
                    existingUserBodyInfo.setUsuarioId(userBodyInfo.getUsuarioId());
                }
                if (userBodyInfo.getEmpresaId() != null) {
                    existingUserBodyInfo.setEmpresaId(userBodyInfo.getEmpresaId());
                }
                if (userBodyInfo.getWaistCircumference() != null) {
                    existingUserBodyInfo.setWaistCircumference(userBodyInfo.getWaistCircumference());
                }
                if (userBodyInfo.getHipCircumference() != null) {
                    existingUserBodyInfo.setHipCircumference(userBodyInfo.getHipCircumference());
                }
                if (userBodyInfo.getChestCircumference() != null) {
                    existingUserBodyInfo.setChestCircumference(userBodyInfo.getChestCircumference());
                }
                if (userBodyInfo.getBoneCompositionPercentaje() != null) {
                    existingUserBodyInfo.setBoneCompositionPercentaje(userBodyInfo.getBoneCompositionPercentaje());
                }
                if (userBodyInfo.getMuscleCompositionPercentaje() != null) {
                    existingUserBodyInfo.setMuscleCompositionPercentaje(userBodyInfo.getMuscleCompositionPercentaje());
                }
                if (userBodyInfo.getSmoker() != null) {
                    existingUserBodyInfo.setSmoker(userBodyInfo.getSmoker());
                }
                if (userBodyInfo.getWaightKg() != null) {
                    existingUserBodyInfo.setWaightKg(userBodyInfo.getWaightKg());
                }
                if (userBodyInfo.getHeightCm() != null) {
                    existingUserBodyInfo.setHeightCm(userBodyInfo.getHeightCm());
                }
                if (userBodyInfo.getBodyHealthScore() != null) {
                    existingUserBodyInfo.setBodyHealthScore(userBodyInfo.getBodyHealthScore());
                }
                if (userBodyInfo.getCardiovascularRisk() != null) {
                    existingUserBodyInfo.setCardiovascularRisk(userBodyInfo.getCardiovascularRisk());
                }

                return existingUserBodyInfo;
            })
            .map(userBodyInfoRepository::save);
    }

    /**
     * Get all the userBodyInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserBodyInfo> findAll(Pageable pageable) {
        log.debug("Request to get all UserBodyInfos");
        return userBodyInfoRepository.findAll(pageable);
    }

    /**
     * Get one userBodyInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserBodyInfo> findOne(UUID id) {
        log.debug("Request to get UserBodyInfo : {}", id);
        return userBodyInfoRepository.findById(id);
    }

    /**
     * Delete the userBodyInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete UserBodyInfo : {}", id);
        userBodyInfoRepository.deleteById(id);
    }
}
