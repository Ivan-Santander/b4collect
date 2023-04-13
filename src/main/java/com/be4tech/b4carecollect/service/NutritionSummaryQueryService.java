package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.NutritionSummary;
import com.be4tech.b4carecollect.repository.NutritionSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.NutritionSummaryCriteria;
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
 * Service for executing complex queries for {@link NutritionSummary} entities in the database.
 * The main input is a {@link NutritionSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NutritionSummary} or a {@link Page} of {@link NutritionSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NutritionSummaryQueryService extends QueryService<NutritionSummary> {

    private final Logger log = LoggerFactory.getLogger(NutritionSummaryQueryService.class);

    private final NutritionSummaryRepository nutritionSummaryRepository;

    public NutritionSummaryQueryService(NutritionSummaryRepository nutritionSummaryRepository) {
        this.nutritionSummaryRepository = nutritionSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link NutritionSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NutritionSummary> findByCriteria(NutritionSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NutritionSummary> specification = createSpecification(criteria);
        return nutritionSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NutritionSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NutritionSummary> findByCriteria(NutritionSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NutritionSummary> specification = createSpecification(criteria);
        return nutritionSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NutritionSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NutritionSummary> specification = createSpecification(criteria);
        return nutritionSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link NutritionSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NutritionSummary> createSpecification(NutritionSummaryCriteria criteria) {
        Specification<NutritionSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NutritionSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), NutritionSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), NutritionSummary_.empresaId));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), NutritionSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), NutritionSummary_.endTime));
            }
        }
        return specification;
    }
}
