package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.WeightSummary;
import com.be4tech.b4carecollect.repository.WeightSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.WeightSummaryCriteria;
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
 * Service for executing complex queries for {@link WeightSummary} entities in the database.
 * The main input is a {@link WeightSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WeightSummary} or a {@link Page} of {@link WeightSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WeightSummaryQueryService extends QueryService<WeightSummary> {

    private final Logger log = LoggerFactory.getLogger(WeightSummaryQueryService.class);

    private final WeightSummaryRepository weightSummaryRepository;

    public WeightSummaryQueryService(WeightSummaryRepository weightSummaryRepository) {
        this.weightSummaryRepository = weightSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link WeightSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WeightSummary> findByCriteria(WeightSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WeightSummary> specification = createSpecification(criteria);
        return weightSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link WeightSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WeightSummary> findByCriteria(WeightSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WeightSummary> specification = createSpecification(criteria);
        return weightSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WeightSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WeightSummary> specification = createSpecification(criteria);
        return weightSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link WeightSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WeightSummary> createSpecification(WeightSummaryCriteria criteria) {
        Specification<WeightSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), WeightSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), WeightSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), WeightSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldAverage(), WeightSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), WeightSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), WeightSummary_.fieldMin));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), WeightSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), WeightSummary_.endTime));
            }
        }
        return specification;
    }
}
