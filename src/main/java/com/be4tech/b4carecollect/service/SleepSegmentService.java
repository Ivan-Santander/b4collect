package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.SleepSegment;
import com.be4tech.b4carecollect.repository.SleepSegmentRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SleepSegment}.
 */
@Service
@Transactional
public class SleepSegmentService {

    private final Logger log = LoggerFactory.getLogger(SleepSegmentService.class);

    private final SleepSegmentRepository sleepSegmentRepository;

    public SleepSegmentService(SleepSegmentRepository sleepSegmentRepository) {
        this.sleepSegmentRepository = sleepSegmentRepository;
    }

    /**
     * Save a sleepSegment.
     *
     * @param sleepSegment the entity to save.
     * @return the persisted entity.
     */
    public SleepSegment save(SleepSegment sleepSegment) {
        log.debug("Request to save SleepSegment : {}", sleepSegment);
        return sleepSegmentRepository.save(sleepSegment);
    }

    /**
     * Update a sleepSegment.
     *
     * @param sleepSegment the entity to save.
     * @return the persisted entity.
     */
    public SleepSegment update(SleepSegment sleepSegment) {
        log.debug("Request to update SleepSegment : {}", sleepSegment);
        return sleepSegmentRepository.save(sleepSegment);
    }

    /**
     * Partially update a sleepSegment.
     *
     * @param sleepSegment the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SleepSegment> partialUpdate(SleepSegment sleepSegment) {
        log.debug("Request to partially update SleepSegment : {}", sleepSegment);

        return sleepSegmentRepository
            .findById(sleepSegment.getId())
            .map(existingSleepSegment -> {
                if (sleepSegment.getUsuarioId() != null) {
                    existingSleepSegment.setUsuarioId(sleepSegment.getUsuarioId());
                }
                if (sleepSegment.getEmpresaId() != null) {
                    existingSleepSegment.setEmpresaId(sleepSegment.getEmpresaId());
                }
                if (sleepSegment.getFieldSleepSegmentType() != null) {
                    existingSleepSegment.setFieldSleepSegmentType(sleepSegment.getFieldSleepSegmentType());
                }
                if (sleepSegment.getFieldBloodGlucoseSpecimenSource() != null) {
                    existingSleepSegment.setFieldBloodGlucoseSpecimenSource(sleepSegment.getFieldBloodGlucoseSpecimenSource());
                }
                if (sleepSegment.getStartTime() != null) {
                    existingSleepSegment.setStartTime(sleepSegment.getStartTime());
                }
                if (sleepSegment.getEndTime() != null) {
                    existingSleepSegment.setEndTime(sleepSegment.getEndTime());
                }

                return existingSleepSegment;
            })
            .map(sleepSegmentRepository::save);
    }

    /**
     * Get all the sleepSegments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SleepSegment> findAll(Pageable pageable) {
        log.debug("Request to get all SleepSegments");
        return sleepSegmentRepository.findAll(pageable);
    }

    /**
     * Get one sleepSegment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SleepSegment> findOne(UUID id) {
        log.debug("Request to get SleepSegment : {}", id);
        return sleepSegmentRepository.findById(id);
    }

    /**
     * Delete the sleepSegment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete SleepSegment : {}", id);
        sleepSegmentRepository.deleteById(id);
    }
}
