package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.Height;
import com.be4tech.b4carecollect.repository.HeightRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Height}.
 */
@Service
@Transactional
public class HeightService {

    private final Logger log = LoggerFactory.getLogger(HeightService.class);

    private final HeightRepository heightRepository;

    public HeightService(HeightRepository heightRepository) {
        this.heightRepository = heightRepository;
    }

    /**
     * Save a height.
     *
     * @param height the entity to save.
     * @return the persisted entity.
     */
    public Height save(Height height) {
        log.debug("Request to save Height : {}", height);
        return heightRepository.save(height);
    }

    /**
     * Update a height.
     *
     * @param height the entity to save.
     * @return the persisted entity.
     */
    public Height update(Height height) {
        log.debug("Request to update Height : {}", height);
        return heightRepository.save(height);
    }

    /**
     * Partially update a height.
     *
     * @param height the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Height> partialUpdate(Height height) {
        log.debug("Request to partially update Height : {}", height);

        return heightRepository
            .findById(height.getId())
            .map(existingHeight -> {
                if (height.getUsuarioId() != null) {
                    existingHeight.setUsuarioId(height.getUsuarioId());
                }
                if (height.getEmpresaId() != null) {
                    existingHeight.setEmpresaId(height.getEmpresaId());
                }
                if (height.getFieldHeight() != null) {
                    existingHeight.setFieldHeight(height.getFieldHeight());
                }
                if (height.getEndTime() != null) {
                    existingHeight.setEndTime(height.getEndTime());
                }

                return existingHeight;
            })
            .map(heightRepository::save);
    }

    /**
     * Get all the heights.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Height> findAll(Pageable pageable) {
        log.debug("Request to get all Heights");
        return heightRepository.findAll(pageable);
    }

    /**
     * Get one height by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Height> findOne(UUID id) {
        log.debug("Request to get Height : {}", id);
        return heightRepository.findById(id);
    }

    /**
     * Delete the height by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Height : {}", id);
        heightRepository.deleteById(id);
    }
}
