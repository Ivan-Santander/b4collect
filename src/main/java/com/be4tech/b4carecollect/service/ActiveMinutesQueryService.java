package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.ActiveMinutes;
import com.be4tech.b4carecollect.repository.ActiveMinutesRepository;
import com.be4tech.b4carecollect.service.criteria.ActiveMinutesCriteria;
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
 * Service for executing complex queries for {@link ActiveMinutes} entities in the database.
 * The main input is a {@link ActiveMinutesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActiveMinutes} or a {@link Page} of {@link ActiveMinutes} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActiveMinutesQueryService extends QueryService<ActiveMinutes> {

    private final Logger log = LoggerFactory.getLogger(ActiveMinutesQueryService.class);

    private final ActiveMinutesRepository activeMinutesRepository;

    public ActiveMinutesQueryService(ActiveMinutesRepository activeMinutesRepository) {
        this.activeMinutesRepository = activeMinutesRepository;
    }

    /**
     * Return a {@link List} of {@link ActiveMinutes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActiveMinutes> findByCriteria(ActiveMinutesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ActiveMinutes> specification = createSpecification(criteria);
        return activeMinutesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ActiveMinutes} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActiveMinutes> findByCriteria(ActiveMinutesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ActiveMinutes> specification = createSpecification(criteria);
        return activeMinutesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActiveMinutesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ActiveMinutes> specification = createSpecification(criteria);
        return activeMinutesRepository.count(specification);
    }

    /**
     * Function to convert {@link ActiveMinutesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ActiveMinutes> createSpecification(ActiveMinutesCriteria criteria) {
        Specification<ActiveMinutes> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ActiveMinutes_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), ActiveMinutes_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), ActiveMinutes_.empresaId));
            }
            if (criteria.getCalorias() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalorias(), ActiveMinutes_.calorias));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), ActiveMinutes_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), ActiveMinutes_.endTime));
            }
        }
        return specification;
    }
}
