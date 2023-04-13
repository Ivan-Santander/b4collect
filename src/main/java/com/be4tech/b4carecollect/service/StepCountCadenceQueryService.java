package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.StepCountCadence;
import com.be4tech.b4carecollect.repository.StepCountCadenceRepository;
import com.be4tech.b4carecollect.service.criteria.StepCountCadenceCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link StepCountCadence} entities in the database.
 * The main input is a {@link StepCountCadenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StepCountCadence} or a {@link Page} of {@link StepCountCadence} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StepCountCadenceQueryService extends QueryService<StepCountCadence> {

    private final Logger log = LoggerFactory.getLogger(StepCountCadenceQueryService.class);

    private final StepCountCadenceRepository stepCountCadenceRepository;

    public StepCountCadenceQueryService(StepCountCadenceRepository stepCountCadenceRepository) {
        this.stepCountCadenceRepository = stepCountCadenceRepository;
    }

    /**
     * Return a {@link List} of {@link StepCountCadence} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StepCountCadence> findByCriteria(StepCountCadenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StepCountCadence> specification = createSpecification(criteria);
        return stepCountCadenceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StepCountCadence} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StepCountCadence> findByCriteria(StepCountCadenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StepCountCadence> specification = createSpecification(criteria);
        return stepCountCadenceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StepCountCadenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StepCountCadence> specification = createSpecification(criteria);
        return stepCountCadenceRepository.count(specification);
    }

    /**
     * Function to convert {@link StepCountCadenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StepCountCadence> createSpecification(StepCountCadenceCriteria criteria) {
        Specification<StepCountCadence> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StepCountCadence_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), StepCountCadence_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), StepCountCadence_.empresaId));
            }
            if (criteria.getRpm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRpm(), StepCountCadence_.rpm));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), StepCountCadence_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), StepCountCadence_.endTime));
            }
        }
        return specification;
    }
}
