package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.CaloriesExpended;
import com.be4tech.b4carecollect.repository.CaloriesExpendedRepository;
import com.be4tech.b4carecollect.service.criteria.CaloriesExpendedCriteria;
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
 * Service for executing complex queries for {@link CaloriesExpended} entities in the database.
 * The main input is a {@link CaloriesExpendedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CaloriesExpended} or a {@link Page} of {@link CaloriesExpended} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CaloriesExpendedQueryService extends QueryService<CaloriesExpended> {

    private final Logger log = LoggerFactory.getLogger(CaloriesExpendedQueryService.class);

    private final CaloriesExpendedRepository caloriesExpendedRepository;

    public CaloriesExpendedQueryService(CaloriesExpendedRepository caloriesExpendedRepository) {
        this.caloriesExpendedRepository = caloriesExpendedRepository;
    }

    /**
     * Return a {@link List} of {@link CaloriesExpended} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CaloriesExpended> findByCriteria(CaloriesExpendedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CaloriesExpended> specification = createSpecification(criteria);
        return caloriesExpendedRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CaloriesExpended} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CaloriesExpended> findByCriteria(CaloriesExpendedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CaloriesExpended> specification = createSpecification(criteria);
        return caloriesExpendedRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CaloriesExpendedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CaloriesExpended> specification = createSpecification(criteria);
        return caloriesExpendedRepository.count(specification);
    }

    /**
     * Function to convert {@link CaloriesExpendedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CaloriesExpended> createSpecification(CaloriesExpendedCriteria criteria) {
        Specification<CaloriesExpended> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CaloriesExpended_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), CaloriesExpended_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), CaloriesExpended_.empresaId));
            }
            if (criteria.getCalorias() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalorias(), CaloriesExpended_.calorias));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), CaloriesExpended_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), CaloriesExpended_.endTime));
            }
        }
        return specification;
    }
}
