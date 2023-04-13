package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.Weight;
import com.be4tech.b4carecollect.repository.WeightRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Weight}.
 */
@Service
@Transactional
public class WeightService {

    private final Logger log = LoggerFactory.getLogger(WeightService.class);

    private final WeightRepository weightRepository;

    public WeightService(WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }

    /**
     * Save a weight.
     *
     * @param weight the entity to save.
     * @return the persisted entity.
     */
    public Weight save(Weight weight) {
        log.debug("Request to save Weight : {}", weight);
        return weightRepository.save(weight);
    }

    /**
     * Update a weight.
     *
     * @param weight the entity to save.
     * @return the persisted entity.
     */
    public Weight update(Weight weight) {
        log.debug("Request to update Weight : {}", weight);
        return weightRepository.save(weight);
    }

    /**
     * Partially update a weight.
     *
     * @param weight the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Weight> partialUpdate(Weight weight) {
        log.debug("Request to partially update Weight : {}", weight);

        return weightRepository
            .findById(weight.getId())
            .map(existingWeight -> {
                if (weight.getUsuarioId() != null) {
                    existingWeight.setUsuarioId(weight.getUsuarioId());
                }
                if (weight.getEmpresaId() != null) {
                    existingWeight.setEmpresaId(weight.getEmpresaId());
                }
                if (weight.getFieldWeight() != null) {
                    existingWeight.setFieldWeight(weight.getFieldWeight());
                }
                if (weight.getEndTime() != null) {
                    existingWeight.setEndTime(weight.getEndTime());
                }

                return existingWeight;
            })
            .map(weightRepository::save);
    }

    /**
     * Get all the weights.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Weight> findAll(Pageable pageable) {
        log.debug("Request to get all Weights");
        return weightRepository.findAll(pageable);
    }

    /**
     * Get one weight by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Weight> findOne(UUID id) {
        log.debug("Request to get Weight : {}", id);
        return weightRepository.findById(id);
    }

    /**
     * Delete the weight by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Weight : {}", id);
        weightRepository.deleteById(id);
    }
}
