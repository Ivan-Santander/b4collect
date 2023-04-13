package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.Speed;
import com.be4tech.b4carecollect.repository.SpeedRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Speed}.
 */
@Service
@Transactional
public class SpeedService {

    private final Logger log = LoggerFactory.getLogger(SpeedService.class);

    private final SpeedRepository speedRepository;

    public SpeedService(SpeedRepository speedRepository) {
        this.speedRepository = speedRepository;
    }

    /**
     * Save a speed.
     *
     * @param speed the entity to save.
     * @return the persisted entity.
     */
    public Speed save(Speed speed) {
        log.debug("Request to save Speed : {}", speed);
        return speedRepository.save(speed);
    }

    /**
     * Update a speed.
     *
     * @param speed the entity to save.
     * @return the persisted entity.
     */
    public Speed update(Speed speed) {
        log.debug("Request to update Speed : {}", speed);
        return speedRepository.save(speed);
    }

    /**
     * Partially update a speed.
     *
     * @param speed the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Speed> partialUpdate(Speed speed) {
        log.debug("Request to partially update Speed : {}", speed);

        return speedRepository
            .findById(speed.getId())
            .map(existingSpeed -> {
                if (speed.getUsuarioId() != null) {
                    existingSpeed.setUsuarioId(speed.getUsuarioId());
                }
                if (speed.getEmpresaId() != null) {
                    existingSpeed.setEmpresaId(speed.getEmpresaId());
                }
                if (speed.getSpeed() != null) {
                    existingSpeed.setSpeed(speed.getSpeed());
                }
                if (speed.getEndTime() != null) {
                    existingSpeed.setEndTime(speed.getEndTime());
                }

                return existingSpeed;
            })
            .map(speedRepository::save);
    }

    /**
     * Get all the speeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Speed> findAll(Pageable pageable) {
        log.debug("Request to get all Speeds");
        return speedRepository.findAll(pageable);
    }

    /**
     * Get one speed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Speed> findOne(UUID id) {
        log.debug("Request to get Speed : {}", id);
        return speedRepository.findById(id);
    }

    /**
     * Delete the speed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Speed : {}", id);
        speedRepository.deleteById(id);
    }
}
