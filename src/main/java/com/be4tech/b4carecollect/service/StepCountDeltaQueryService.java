package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.StepCountDelta;
import com.be4tech.b4carecollect.repository.StepCountDeltaRepository;
import com.be4tech.b4carecollect.service.criteria.StepCountDeltaCriteria;
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
 * Service for executing complex queries for {@link StepCountDelta} entities in the database.
 * The main input is a {@link StepCountDeltaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StepCountDelta} or a {@link Page} of {@link StepCountDelta} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StepCountDeltaQueryService extends QueryService<StepCountDelta> {

    private final Logger log = LoggerFactory.getLogger(StepCountDeltaQueryService.class);

    private final StepCountDeltaRepository stepCountDeltaRepository;

    public StepCountDeltaQueryService(StepCountDeltaRepository stepCountDeltaRepository) {
        this.stepCountDeltaRepository = stepCountDeltaRepository;
    }

    /**
     * Return a {@link List} of {@link StepCountDelta} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StepCountDelta> findByCriteria(StepCountDeltaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StepCountDelta> specification = createSpecification(criteria);
        return stepCountDeltaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StepCountDelta} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StepCountDelta> findByCriteria(StepCountDeltaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StepCountDelta> specification = createSpecification(criteria);
        return stepCountDeltaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StepCountDeltaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StepCountDelta> specification = createSpecification(criteria);
        return stepCountDeltaRepository.count(specification);
    }

    /**
     * Function to convert {@link StepCountDeltaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StepCountDelta> createSpecification(StepCountDeltaCriteria criteria) {
        Specification<StepCountDelta> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StepCountDelta_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), StepCountDelta_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), StepCountDelta_.empresaId));
            }
            if (criteria.getSteps() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSteps(), StepCountDelta_.steps));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), StepCountDelta_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), StepCountDelta_.endTime));
            }
        }
        return specification;
    }
}
