package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.CaloriesBmrSummary;
import com.be4tech.b4carecollect.repository.CaloriesBmrSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.CaloriesBmrSummaryCriteria;
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
 * Service for executing complex queries for {@link CaloriesBmrSummary} entities in the database.
 * The main input is a {@link CaloriesBmrSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CaloriesBmrSummary} or a {@link Page} of {@link CaloriesBmrSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CaloriesBmrSummaryQueryService extends QueryService<CaloriesBmrSummary> {

    private final Logger log = LoggerFactory.getLogger(CaloriesBmrSummaryQueryService.class);

    private final CaloriesBmrSummaryRepository caloriesBmrSummaryRepository;

    public CaloriesBmrSummaryQueryService(CaloriesBmrSummaryRepository caloriesBmrSummaryRepository) {
        this.caloriesBmrSummaryRepository = caloriesBmrSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link CaloriesBmrSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CaloriesBmrSummary> findByCriteria(CaloriesBmrSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CaloriesBmrSummary> specification = createSpecification(criteria);
        return caloriesBmrSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CaloriesBmrSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CaloriesBmrSummary> findByCriteria(CaloriesBmrSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CaloriesBmrSummary> specification = createSpecification(criteria);
        return caloriesBmrSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CaloriesBmrSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CaloriesBmrSummary> specification = createSpecification(criteria);
        return caloriesBmrSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link CaloriesBmrSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CaloriesBmrSummary> createSpecification(CaloriesBmrSummaryCriteria criteria) {
        Specification<CaloriesBmrSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CaloriesBmrSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), CaloriesBmrSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), CaloriesBmrSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldAverage(), CaloriesBmrSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), CaloriesBmrSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), CaloriesBmrSummary_.fieldMin));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), CaloriesBmrSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), CaloriesBmrSummary_.endTime));
            }
        }
        return specification;
    }
}
