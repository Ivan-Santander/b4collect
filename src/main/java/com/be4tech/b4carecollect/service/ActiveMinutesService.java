package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.ActiveMinutes;
import com.be4tech.b4carecollect.repository.ActiveMinutesRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ActiveMinutes}.
 */
@Service
@Transactional
public class ActiveMinutesService {

    private final Logger log = LoggerFactory.getLogger(ActiveMinutesService.class);

    private final ActiveMinutesRepository activeMinutesRepository;

    public ActiveMinutesService(ActiveMinutesRepository activeMinutesRepository) {
        this.activeMinutesRepository = activeMinutesRepository;
    }

    /**
     * Save a activeMinutes.
     *
     * @param activeMinutes the entity to save.
     * @return the persisted entity.
     */
    public ActiveMinutes save(ActiveMinutes activeMinutes) {
        log.debug("Request to save ActiveMinutes : {}", activeMinutes);
        return activeMinutesRepository.save(activeMinutes);
    }

    /**
     * Update a activeMinutes.
     *
     * @param activeMinutes the entity to save.
     * @return the persisted entity.
     */
    public ActiveMinutes update(ActiveMinutes activeMinutes) {
        log.debug("Request to update ActiveMinutes : {}", activeMinutes);
        return activeMinutesRepository.save(activeMinutes);
    }

    /**
     * Partially update a activeMinutes.
     *
     * @param activeMinutes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActiveMinutes> partialUpdate(ActiveMinutes activeMinutes) {
        log.debug("Request to partially update ActiveMinutes : {}", activeMinutes);

        return activeMinutesRepository
            .findById(activeMinutes.getId())
            .map(existingActiveMinutes -> {
                if (activeMinutes.getUsuarioId() != null) {
                    existingActiveMinutes.setUsuarioId(activeMinutes.getUsuarioId());
                }
                if (activeMinutes.getEmpresaId() != null) {
                    existingActiveMinutes.setEmpresaId(activeMinutes.getEmpresaId());
                }
                if (activeMinutes.getCalorias() != null) {
                    existingActiveMinutes.setCalorias(activeMinutes.getCalorias());
                }
                if (activeMinutes.getStartTime() != null) {
                    existingActiveMinutes.setStartTime(activeMinutes.getStartTime());
                }
                if (activeMinutes.getEndTime() != null) {
                    existingActiveMinutes.setEndTime(activeMinutes.getEndTime());
                }

                return existingActiveMinutes;
            })
            .map(activeMinutesRepository::save);
    }

    /**
     * Get all the activeMinutes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActiveMinutes> findAll(Pageable pageable) {
        log.debug("Request to get all ActiveMinutes");
        return activeMinutesRepository.findAll(pageable);
    }

    /**
     * Get one activeMinutes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActiveMinutes> findOne(UUID id) {
        log.debug("Request to get ActiveMinutes : {}", id);
        return activeMinutesRepository.findById(id);
    }

    /**
     * Delete the activeMinutes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ActiveMinutes : {}", id);
        activeMinutesRepository.deleteById(id);
    }
}
