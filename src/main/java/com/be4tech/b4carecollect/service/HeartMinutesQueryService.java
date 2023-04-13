package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.HeartMinutes;
import com.be4tech.b4carecollect.repository.HeartMinutesRepository;
import com.be4tech.b4carecollect.service.criteria.HeartMinutesCriteria;
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
 * Service for executing complex queries for {@link HeartMinutes} entities in the database.
 * The main input is a {@link HeartMinutesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HeartMinutes} or a {@link Page} of {@link HeartMinutes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HeartMinutesQueryService extends QueryService<HeartMinutes> {

    private final Logger log = LoggerFactory.getLogger(HeartMinutesQueryService.class);

    private final HeartMinutesRepository heartMinutesRepository;

    public HeartMinutesQueryService(HeartMinutesRepository heartMinutesRepository) {
        this.heartMinutesRepository = heartMinutesRepository;
    }

    /**
     * Return a {@link List} of {@link HeartMinutes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HeartMinutes> findByCriteria(HeartMinutesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HeartMinutes> specification = createSpecification(criteria);
        return heartMinutesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HeartMinutes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HeartMinutes> findByCriteria(HeartMinutesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HeartMinutes> specification = createSpecification(criteria);
        return heartMinutesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HeartMinutesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HeartMinutes> specification = createSpecification(criteria);
        return heartMinutesRepository.count(specification);
    }

    /**
     * Function to convert {@link HeartMinutesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HeartMinutes> createSpecification(HeartMinutesCriteria criteria) {
        Specification<HeartMinutes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), HeartMinutes_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), HeartMinutes_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), HeartMinutes_.empresaId));
            }
            if (criteria.getSeverity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeverity(), HeartMinutes_.severity));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), HeartMinutes_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), HeartMinutes_.endTime));
            }
        }
        return specification;
    }
}
