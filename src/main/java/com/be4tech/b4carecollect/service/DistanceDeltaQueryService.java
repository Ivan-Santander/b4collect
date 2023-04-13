package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.DistanceDelta;
import com.be4tech.b4carecollect.repository.DistanceDeltaRepository;
import com.be4tech.b4carecollect.service.criteria.DistanceDeltaCriteria;
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
 * Service for executing complex queries for {@link DistanceDelta} entities in the database.
 * The main input is a {@link DistanceDeltaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DistanceDelta} or a {@link Page} of {@link DistanceDelta} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DistanceDeltaQueryService extends QueryService<DistanceDelta> {

    private final Logger log = LoggerFactory.getLogger(DistanceDeltaQueryService.class);

    private final DistanceDeltaRepository distanceDeltaRepository;

    public DistanceDeltaQueryService(DistanceDeltaRepository distanceDeltaRepository) {
        this.distanceDeltaRepository = distanceDeltaRepository;
    }

    /**
     * Return a {@link List} of {@link DistanceDelta} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DistanceDelta> findByCriteria(DistanceDeltaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DistanceDelta> specification = createSpecification(criteria);
        return distanceDeltaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DistanceDelta} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DistanceDelta> findByCriteria(DistanceDeltaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DistanceDelta> specification = createSpecification(criteria);
        return distanceDeltaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DistanceDeltaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DistanceDelta> specification = createSpecification(criteria);
        return distanceDeltaRepository.count(specification);
    }

    /**
     * Function to convert {@link DistanceDeltaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DistanceDelta> createSpecification(DistanceDeltaCriteria criteria) {
        Specification<DistanceDelta> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DistanceDelta_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), DistanceDelta_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), DistanceDelta_.empresaId));
            }
            if (criteria.getDistance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistance(), DistanceDelta_.distance));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), DistanceDelta_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), DistanceDelta_.endTime));
            }
        }
        return specification;
    }
}
