package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.CyclingWheelRevolution;
import com.be4tech.b4carecollect.repository.CyclingWheelRevolutionRepository;
import com.be4tech.b4carecollect.service.criteria.CyclingWheelRevolutionCriteria;
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
 * Service for executing complex queries for {@link CyclingWheelRevolution} entities in the database.
 * The main input is a {@link CyclingWheelRevolutionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CyclingWheelRevolution} or a {@link Page} of {@link CyclingWheelRevolution} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CyclingWheelRevolutionQueryService extends QueryService<CyclingWheelRevolution> {

    private final Logger log = LoggerFactory.getLogger(CyclingWheelRevolutionQueryService.class);

    private final CyclingWheelRevolutionRepository cyclingWheelRevolutionRepository;

    public CyclingWheelRevolutionQueryService(CyclingWheelRevolutionRepository cyclingWheelRevolutionRepository) {
        this.cyclingWheelRevolutionRepository = cyclingWheelRevolutionRepository;
    }

    /**
     * Return a {@link List} of {@link CyclingWheelRevolution} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CyclingWheelRevolution> findByCriteria(CyclingWheelRevolutionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CyclingWheelRevolution> specification = createSpecification(criteria);
        return cyclingWheelRevolutionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CyclingWheelRevolution} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CyclingWheelRevolution> findByCriteria(CyclingWheelRevolutionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CyclingWheelRevolution> specification = createSpecification(criteria);
        return cyclingWheelRevolutionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CyclingWheelRevolutionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CyclingWheelRevolution> specification = createSpecification(criteria);
        return cyclingWheelRevolutionRepository.count(specification);
    }

    /**
     * Function to convert {@link CyclingWheelRevolutionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CyclingWheelRevolution> createSpecification(CyclingWheelRevolutionCriteria criteria) {
        Specification<CyclingWheelRevolution> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CyclingWheelRevolution_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), CyclingWheelRevolution_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), CyclingWheelRevolution_.empresaId));
            }
            if (criteria.getRevolutions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRevolutions(), CyclingWheelRevolution_.revolutions));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), CyclingWheelRevolution_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), CyclingWheelRevolution_.endTime));
            }
        }
        return specification;
    }
}
