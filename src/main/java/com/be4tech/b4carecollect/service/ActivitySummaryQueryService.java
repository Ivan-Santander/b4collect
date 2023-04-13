package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.ActivitySummary;
import com.be4tech.b4carecollect.repository.ActivitySummaryRepository;
import com.be4tech.b4carecollect.service.criteria.ActivitySummaryCriteria;
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
 * Service for executing complex queries for {@link ActivitySummary} entities in the database.
 * The main input is a {@link ActivitySummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActivitySummary} or a {@link Page} of {@link ActivitySummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivitySummaryQueryService extends QueryService<ActivitySummary> {

    private final Logger log = LoggerFactory.getLogger(ActivitySummaryQueryService.class);

    private final ActivitySummaryRepository activitySummaryRepository;

    public ActivitySummaryQueryService(ActivitySummaryRepository activitySummaryRepository) {
        this.activitySummaryRepository = activitySummaryRepository;
    }

    /**
     * Return a {@link List} of {@link ActivitySummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActivitySummary> findByCriteria(ActivitySummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ActivitySummary> specification = createSpecification(criteria);
        return activitySummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ActivitySummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivitySummary> findByCriteria(ActivitySummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ActivitySummary> specification = createSpecification(criteria);
        return activitySummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActivitySummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ActivitySummary> specification = createSpecification(criteria);
        return activitySummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link ActivitySummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ActivitySummary> createSpecification(ActivitySummaryCriteria criteria) {
        Specification<ActivitySummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ActivitySummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), ActivitySummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), ActivitySummary_.empresaId));
            }
            if (criteria.getFieldActivity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldActivity(), ActivitySummary_.fieldActivity));
            }
            if (criteria.getFieldDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldDuration(), ActivitySummary_.fieldDuration));
            }
            if (criteria.getFieldNumSegments() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldNumSegments(), ActivitySummary_.fieldNumSegments));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), ActivitySummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), ActivitySummary_.endTime));
            }
        }
        return specification;
    }
}
