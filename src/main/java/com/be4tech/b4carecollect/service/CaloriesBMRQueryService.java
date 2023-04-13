package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.CaloriesBMR;
import com.be4tech.b4carecollect.repository.CaloriesBMRRepository;
import com.be4tech.b4carecollect.service.criteria.CaloriesBMRCriteria;
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
 * Service for executing complex queries for {@link CaloriesBMR} entities in the database.
 * The main input is a {@link CaloriesBMRCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CaloriesBMR} or a {@link Page} of {@link CaloriesBMR} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CaloriesBMRQueryService extends QueryService<CaloriesBMR> {

    private final Logger log = LoggerFactory.getLogger(CaloriesBMRQueryService.class);

    private final CaloriesBMRRepository caloriesBMRRepository;

    public CaloriesBMRQueryService(CaloriesBMRRepository caloriesBMRRepository) {
        this.caloriesBMRRepository = caloriesBMRRepository;
    }

    /**
     * Return a {@link List} of {@link CaloriesBMR} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CaloriesBMR> findByCriteria(CaloriesBMRCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CaloriesBMR> specification = createSpecification(criteria);
        return caloriesBMRRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CaloriesBMR} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CaloriesBMR> findByCriteria(CaloriesBMRCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CaloriesBMR> specification = createSpecification(criteria);
        return caloriesBMRRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CaloriesBMRCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CaloriesBMR> specification = createSpecification(criteria);
        return caloriesBMRRepository.count(specification);
    }

    /**
     * Function to convert {@link CaloriesBMRCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CaloriesBMR> createSpecification(CaloriesBMRCriteria criteria) {
        Specification<CaloriesBMR> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CaloriesBMR_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), CaloriesBMR_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), CaloriesBMR_.empresaId));
            }
            if (criteria.getCalorias() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalorias(), CaloriesBMR_.calorias));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), CaloriesBMR_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), CaloriesBMR_.endTime));
            }
        }
        return specification;
    }
}
