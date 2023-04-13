package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.TemperatureSummary;
import com.be4tech.b4carecollect.repository.TemperatureSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TemperatureSummary}.
 */
@Service
@Transactional
public class TemperatureSummaryService {

    private final Logger log = LoggerFactory.getLogger(TemperatureSummaryService.class);

    private final TemperatureSummaryRepository temperatureSummaryRepository;

    public TemperatureSummaryService(TemperatureSummaryRepository temperatureSummaryRepository) {
        this.temperatureSummaryRepository = temperatureSummaryRepository;
    }

    /**
     * Save a temperatureSummary.
     *
     * @param temperatureSummary the entity to save.
     * @return the persisted entity.
     */
    public TemperatureSummary save(TemperatureSummary temperatureSummary) {
        log.debug("Request to save TemperatureSummary : {}", temperatureSummary);
        return temperatureSummaryRepository.save(temperatureSummary);
    }

    /**
     * Update a temperatureSummary.
     *
     * @param temperatureSummary the entity to save.
     * @return the persisted entity.
     */
    public TemperatureSummary update(TemperatureSummary temperatureSummary) {
        log.debug("Request to update TemperatureSummary : {}", temperatureSummary);
        return temperatureSummaryRepository.save(temperatureSummary);
    }

    /**
     * Partially update a temperatureSummary.
     *
     * @param temperatureSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TemperatureSummary> partialUpdate(TemperatureSummary temperatureSummary) {
        log.debug("Request to partially update TemperatureSummary : {}", temperatureSummary);

        return temperatureSummaryRepository
            .findById(temperatureSummary.getId())
            .map(existingTemperatureSummary -> {
                if (temperatureSummary.getUsuarioId() != null) {
                    existingTemperatureSummary.setUsuarioId(temperatureSummary.getUsuarioId());
                }
                if (temperatureSummary.getEmpresaId() != null) {
                    existingTemperatureSummary.setEmpresaId(temperatureSummary.getEmpresaId());
                }
                if (temperatureSummary.getFieldAverage() != null) {
                    existingTemperatureSummary.setFieldAverage(temperatureSummary.getFieldAverage());
                }
                if (temperatureSummary.getFieldMax() != null) {
                    existingTemperatureSummary.setFieldMax(temperatureSummary.getFieldMax());
                }
                if (temperatureSummary.getFieldMin() != null) {
                    existingTemperatureSummary.setFieldMin(temperatureSummary.getFieldMin());
                }
                if (temperatureSummary.getMeasurementLocation() != null) {
                    existingTemperatureSummary.setMeasurementLocation(temperatureSummary.getMeasurementLocation());
                }
                if (temperatureSummary.getStartTime() != null) {
                    existingTemperatureSummary.setStartTime(temperatureSummary.getStartTime());
                }
                if (temperatureSummary.getEndTime() != null) {
                    existingTemperatureSummary.setEndTime(temperatureSummary.getEndTime());
                }

                return existingTemperatureSummary;
            })
            .map(temperatureSummaryRepository::save);
    }

    /**
     * Get all the temperatureSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TemperatureSummary> findAll(Pageable pageable) {
        log.debug("Request to get all TemperatureSummaries");
        return temperatureSummaryRepository.findAll(pageable);
    }

    /**
     * Get one temperatureSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemperatureSummary> findOne(UUID id) {
        log.debug("Request to get TemperatureSummary : {}", id);
        return temperatureSummaryRepository.findById(id);
    }

    /**
     * Delete the temperatureSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete TemperatureSummary : {}", id);
        temperatureSummaryRepository.deleteById(id);
    }
}
