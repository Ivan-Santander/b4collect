package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.DistanceDelta;
import com.be4tech.b4carecollect.repository.DistanceDeltaRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DistanceDelta}.
 */
@Service
@Transactional
public class DistanceDeltaService {

    private final Logger log = LoggerFactory.getLogger(DistanceDeltaService.class);

    private final DistanceDeltaRepository distanceDeltaRepository;

    public DistanceDeltaService(DistanceDeltaRepository distanceDeltaRepository) {
        this.distanceDeltaRepository = distanceDeltaRepository;
    }

    /**
     * Save a distanceDelta.
     *
     * @param distanceDelta the entity to save.
     * @return the persisted entity.
     */
    public DistanceDelta save(DistanceDelta distanceDelta) {
        log.debug("Request to save DistanceDelta : {}", distanceDelta);
        return distanceDeltaRepository.save(distanceDelta);
    }

    /**
     * Update a distanceDelta.
     *
     * @param distanceDelta the entity to save.
     * @return the persisted entity.
     */
    public DistanceDelta update(DistanceDelta distanceDelta) {
        log.debug("Request to update DistanceDelta : {}", distanceDelta);
        return distanceDeltaRepository.save(distanceDelta);
    }

    /**
     * Partially update a distanceDelta.
     *
     * @param distanceDelta the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DistanceDelta> partialUpdate(DistanceDelta distanceDelta) {
        log.debug("Request to partially update DistanceDelta : {}", distanceDelta);

        return distanceDeltaRepository
            .findById(distanceDelta.getId())
            .map(existingDistanceDelta -> {
                if (distanceDelta.getUsuarioId() != null) {
                    existingDistanceDelta.setUsuarioId(distanceDelta.getUsuarioId());
                }
                if (distanceDelta.getEmpresaId() != null) {
                    existingDistanceDelta.setEmpresaId(distanceDelta.getEmpresaId());
                }
                if (distanceDelta.getDistance() != null) {
                    existingDistanceDelta.setDistance(distanceDelta.getDistance());
                }
                if (distanceDelta.getStartTime() != null) {
                    existingDistanceDelta.setStartTime(distanceDelta.getStartTime());
                }
                if (distanceDelta.getEndTime() != null) {
                    existingDistanceDelta.setEndTime(distanceDelta.getEndTime());
                }

                return existingDistanceDelta;
            })
            .map(distanceDeltaRepository::save);
    }

    /**
     * Get all the distanceDeltas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DistanceDelta> findAll(Pageable pageable) {
        log.debug("Request to get all DistanceDeltas");
        return distanceDeltaRepository.findAll(pageable);
    }

    /**
     * Get one distanceDelta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DistanceDelta> findOne(UUID id) {
        log.debug("Request to get DistanceDelta : {}", id);
        return distanceDeltaRepository.findById(id);
    }

    /**
     * Delete the distanceDelta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete DistanceDelta : {}", id);
        distanceDeltaRepository.deleteById(id);
    }
}
