package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.AlarmRiskIndexSummary;
import com.be4tech.b4carecollect.repository.AlarmRiskIndexSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AlarmRiskIndexSummary}.
 */
@Service
@Transactional
public class AlarmRiskIndexSummaryService {

    private final Logger log = LoggerFactory.getLogger(AlarmRiskIndexSummaryService.class);

    private final AlarmRiskIndexSummaryRepository alarmRiskIndexSummaryRepository;

    public AlarmRiskIndexSummaryService(AlarmRiskIndexSummaryRepository alarmRiskIndexSummaryRepository) {
        this.alarmRiskIndexSummaryRepository = alarmRiskIndexSummaryRepository;
    }

    /**
     * Save a alarmRiskIndexSummary.
     *
     * @param alarmRiskIndexSummary the entity to save.
     * @return the persisted entity.
     */
    public AlarmRiskIndexSummary save(AlarmRiskIndexSummary alarmRiskIndexSummary) {
        log.debug("Request to save AlarmRiskIndexSummary : {}", alarmRiskIndexSummary);
        return alarmRiskIndexSummaryRepository.save(alarmRiskIndexSummary);
    }

    /**
     * Update a alarmRiskIndexSummary.
     *
     * @param alarmRiskIndexSummary the entity to save.
     * @return the persisted entity.
     */
    public AlarmRiskIndexSummary update(AlarmRiskIndexSummary alarmRiskIndexSummary) {
        log.debug("Request to update AlarmRiskIndexSummary : {}", alarmRiskIndexSummary);
        return alarmRiskIndexSummaryRepository.save(alarmRiskIndexSummary);
    }

    /**
     * Partially update a alarmRiskIndexSummary.
     *
     * @param alarmRiskIndexSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AlarmRiskIndexSummary> partialUpdate(AlarmRiskIndexSummary alarmRiskIndexSummary) {
        log.debug("Request to partially update AlarmRiskIndexSummary : {}", alarmRiskIndexSummary);

        return alarmRiskIndexSummaryRepository
            .findById(alarmRiskIndexSummary.getId())
            .map(existingAlarmRiskIndexSummary -> {
                if (alarmRiskIndexSummary.getUsuarioId() != null) {
                    existingAlarmRiskIndexSummary.setUsuarioId(alarmRiskIndexSummary.getUsuarioId());
                }
                if (alarmRiskIndexSummary.getEmpresaId() != null) {
                    existingAlarmRiskIndexSummary.setEmpresaId(alarmRiskIndexSummary.getEmpresaId());
                }
                if (alarmRiskIndexSummary.getFieldAlarmRiskAverage() != null) {
                    existingAlarmRiskIndexSummary.setFieldAlarmRiskAverage(alarmRiskIndexSummary.getFieldAlarmRiskAverage());
                }
                if (alarmRiskIndexSummary.getFieldAlarmRiskMax() != null) {
                    existingAlarmRiskIndexSummary.setFieldAlarmRiskMax(alarmRiskIndexSummary.getFieldAlarmRiskMax());
                }
                if (alarmRiskIndexSummary.getFieldAlarmRiskMin() != null) {
                    existingAlarmRiskIndexSummary.setFieldAlarmRiskMin(alarmRiskIndexSummary.getFieldAlarmRiskMin());
                }
                if (alarmRiskIndexSummary.getStartTime() != null) {
                    existingAlarmRiskIndexSummary.setStartTime(alarmRiskIndexSummary.getStartTime());
                }
                if (alarmRiskIndexSummary.getEndTime() != null) {
                    existingAlarmRiskIndexSummary.setEndTime(alarmRiskIndexSummary.getEndTime());
                }

                return existingAlarmRiskIndexSummary;
            })
            .map(alarmRiskIndexSummaryRepository::save);
    }

    /**
     * Get all the alarmRiskIndexSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AlarmRiskIndexSummary> findAll(Pageable pageable) {
        log.debug("Request to get all AlarmRiskIndexSummaries");
        return alarmRiskIndexSummaryRepository.findAll(pageable);
    }

    /**
     * Get one alarmRiskIndexSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AlarmRiskIndexSummary> findOne(UUID id) {
        log.debug("Request to get AlarmRiskIndexSummary : {}", id);
        return alarmRiskIndexSummaryRepository.findById(id);
    }

    /**
     * Delete the alarmRiskIndexSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete AlarmRiskIndexSummary : {}", id);
        alarmRiskIndexSummaryRepository.deleteById(id);
    }
}
