package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.OxygenSaturationSummary;
import com.be4tech.b4carecollect.repository.OxygenSaturationSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OxygenSaturationSummary}.
 */
@Service
@Transactional
public class OxygenSaturationSummaryService {

    private final Logger log = LoggerFactory.getLogger(OxygenSaturationSummaryService.class);

    private final OxygenSaturationSummaryRepository oxygenSaturationSummaryRepository;

    public OxygenSaturationSummaryService(OxygenSaturationSummaryRepository oxygenSaturationSummaryRepository) {
        this.oxygenSaturationSummaryRepository = oxygenSaturationSummaryRepository;
    }

    /**
     * Save a oxygenSaturationSummary.
     *
     * @param oxygenSaturationSummary the entity to save.
     * @return the persisted entity.
     */
    public OxygenSaturationSummary save(OxygenSaturationSummary oxygenSaturationSummary) {
        log.debug("Request to save OxygenSaturationSummary : {}", oxygenSaturationSummary);
        return oxygenSaturationSummaryRepository.save(oxygenSaturationSummary);
    }

    /**
     * Update a oxygenSaturationSummary.
     *
     * @param oxygenSaturationSummary the entity to save.
     * @return the persisted entity.
     */
    public OxygenSaturationSummary update(OxygenSaturationSummary oxygenSaturationSummary) {
        log.debug("Request to update OxygenSaturationSummary : {}", oxygenSaturationSummary);
        return oxygenSaturationSummaryRepository.save(oxygenSaturationSummary);
    }

    /**
     * Partially update a oxygenSaturationSummary.
     *
     * @param oxygenSaturationSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OxygenSaturationSummary> partialUpdate(OxygenSaturationSummary oxygenSaturationSummary) {
        log.debug("Request to partially update OxygenSaturationSummary : {}", oxygenSaturationSummary);

        return oxygenSaturationSummaryRepository
            .findById(oxygenSaturationSummary.getId())
            .map(existingOxygenSaturationSummary -> {
                if (oxygenSaturationSummary.getUsuarioId() != null) {
                    existingOxygenSaturationSummary.setUsuarioId(oxygenSaturationSummary.getUsuarioId());
                }
                if (oxygenSaturationSummary.getEmpresaId() != null) {
                    existingOxygenSaturationSummary.setEmpresaId(oxygenSaturationSummary.getEmpresaId());
                }
                if (oxygenSaturationSummary.getFieldOxigenSaturationAverage() != null) {
                    existingOxygenSaturationSummary.setFieldOxigenSaturationAverage(
                        oxygenSaturationSummary.getFieldOxigenSaturationAverage()
                    );
                }
                if (oxygenSaturationSummary.getFieldOxigenSaturationMax() != null) {
                    existingOxygenSaturationSummary.setFieldOxigenSaturationMax(oxygenSaturationSummary.getFieldOxigenSaturationMax());
                }
                if (oxygenSaturationSummary.getFieldOxigenSaturationMin() != null) {
                    existingOxygenSaturationSummary.setFieldOxigenSaturationMin(oxygenSaturationSummary.getFieldOxigenSaturationMin());
                }
                if (oxygenSaturationSummary.getFieldSuplementalOxigenFlowRateAverage() != null) {
                    existingOxygenSaturationSummary.setFieldSuplementalOxigenFlowRateAverage(
                        oxygenSaturationSummary.getFieldSuplementalOxigenFlowRateAverage()
                    );
                }
                if (oxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMax() != null) {
                    existingOxygenSaturationSummary.setFieldSuplementalOxigenFlowRateMax(
                        oxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMax()
                    );
                }
                if (oxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMin() != null) {
                    existingOxygenSaturationSummary.setFieldSuplementalOxigenFlowRateMin(
                        oxygenSaturationSummary.getFieldSuplementalOxigenFlowRateMin()
                    );
                }
                if (oxygenSaturationSummary.getFieldOxigenTherapyAdministrationMode() != null) {
                    existingOxygenSaturationSummary.setFieldOxigenTherapyAdministrationMode(
                        oxygenSaturationSummary.getFieldOxigenTherapyAdministrationMode()
                    );
                }
                if (oxygenSaturationSummary.getFieldOxigenSaturationMode() != null) {
                    existingOxygenSaturationSummary.setFieldOxigenSaturationMode(oxygenSaturationSummary.getFieldOxigenSaturationMode());
                }
                if (oxygenSaturationSummary.getFieldOxigenSaturationMeasurementMethod() != null) {
                    existingOxygenSaturationSummary.setFieldOxigenSaturationMeasurementMethod(
                        oxygenSaturationSummary.getFieldOxigenSaturationMeasurementMethod()
                    );
                }
                if (oxygenSaturationSummary.getEndTime() != null) {
                    existingOxygenSaturationSummary.setEndTime(oxygenSaturationSummary.getEndTime());
                }

                return existingOxygenSaturationSummary;
            })
            .map(oxygenSaturationSummaryRepository::save);
    }

    /**
     * Get all the oxygenSaturationSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OxygenSaturationSummary> findAll(Pageable pageable) {
        log.debug("Request to get all OxygenSaturationSummaries");
        return oxygenSaturationSummaryRepository.findAll(pageable);
    }

    /**
     * Get one oxygenSaturationSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OxygenSaturationSummary> findOne(UUID id) {
        log.debug("Request to get OxygenSaturationSummary : {}", id);
        return oxygenSaturationSummaryRepository.findById(id);
    }

    /**
     * Delete the oxygenSaturationSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete OxygenSaturationSummary : {}", id);
        oxygenSaturationSummaryRepository.deleteById(id);
    }
}
