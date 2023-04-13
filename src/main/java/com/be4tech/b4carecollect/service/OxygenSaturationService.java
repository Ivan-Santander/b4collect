package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.OxygenSaturation;
import com.be4tech.b4carecollect.repository.OxygenSaturationRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OxygenSaturation}.
 */
@Service
@Transactional
public class OxygenSaturationService {

    private final Logger log = LoggerFactory.getLogger(OxygenSaturationService.class);

    private final OxygenSaturationRepository oxygenSaturationRepository;

    public OxygenSaturationService(OxygenSaturationRepository oxygenSaturationRepository) {
        this.oxygenSaturationRepository = oxygenSaturationRepository;
    }

    /**
     * Save a oxygenSaturation.
     *
     * @param oxygenSaturation the entity to save.
     * @return the persisted entity.
     */
    public OxygenSaturation save(OxygenSaturation oxygenSaturation) {
        log.debug("Request to save OxygenSaturation : {}", oxygenSaturation);
        return oxygenSaturationRepository.save(oxygenSaturation);
    }

    /**
     * Update a oxygenSaturation.
     *
     * @param oxygenSaturation the entity to save.
     * @return the persisted entity.
     */
    public OxygenSaturation update(OxygenSaturation oxygenSaturation) {
        log.debug("Request to update OxygenSaturation : {}", oxygenSaturation);
        return oxygenSaturationRepository.save(oxygenSaturation);
    }

    /**
     * Partially update a oxygenSaturation.
     *
     * @param oxygenSaturation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OxygenSaturation> partialUpdate(OxygenSaturation oxygenSaturation) {
        log.debug("Request to partially update OxygenSaturation : {}", oxygenSaturation);

        return oxygenSaturationRepository
            .findById(oxygenSaturation.getId())
            .map(existingOxygenSaturation -> {
                if (oxygenSaturation.getUsuarioId() != null) {
                    existingOxygenSaturation.setUsuarioId(oxygenSaturation.getUsuarioId());
                }
                if (oxygenSaturation.getEmpresaId() != null) {
                    existingOxygenSaturation.setEmpresaId(oxygenSaturation.getEmpresaId());
                }
                if (oxygenSaturation.getFieldOxigenSaturation() != null) {
                    existingOxygenSaturation.setFieldOxigenSaturation(oxygenSaturation.getFieldOxigenSaturation());
                }
                if (oxygenSaturation.getFieldSuplementalOxigenFlowRate() != null) {
                    existingOxygenSaturation.setFieldSuplementalOxigenFlowRate(oxygenSaturation.getFieldSuplementalOxigenFlowRate());
                }
                if (oxygenSaturation.getFieldOxigenTherapyAdministrationMode() != null) {
                    existingOxygenSaturation.setFieldOxigenTherapyAdministrationMode(
                        oxygenSaturation.getFieldOxigenTherapyAdministrationMode()
                    );
                }
                if (oxygenSaturation.getFieldOxigenSaturationMode() != null) {
                    existingOxygenSaturation.setFieldOxigenSaturationMode(oxygenSaturation.getFieldOxigenSaturationMode());
                }
                if (oxygenSaturation.getFieldOxigenSaturationMeasurementMethod() != null) {
                    existingOxygenSaturation.setFieldOxigenSaturationMeasurementMethod(
                        oxygenSaturation.getFieldOxigenSaturationMeasurementMethod()
                    );
                }
                if (oxygenSaturation.getEndTime() != null) {
                    existingOxygenSaturation.setEndTime(oxygenSaturation.getEndTime());
                }

                return existingOxygenSaturation;
            })
            .map(oxygenSaturationRepository::save);
    }

    /**
     * Get all the oxygenSaturations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OxygenSaturation> findAll(Pageable pageable) {
        log.debug("Request to get all OxygenSaturations");
        return oxygenSaturationRepository.findAll(pageable);
    }

    /**
     * Get one oxygenSaturation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OxygenSaturation> findOne(UUID id) {
        log.debug("Request to get OxygenSaturation : {}", id);
        return oxygenSaturationRepository.findById(id);
    }

    /**
     * Delete the oxygenSaturation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete OxygenSaturation : {}", id);
        oxygenSaturationRepository.deleteById(id);
    }
}
