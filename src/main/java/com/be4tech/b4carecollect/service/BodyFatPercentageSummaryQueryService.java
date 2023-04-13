package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.BodyFatPercentageSummary;
import com.be4tech.b4carecollect.repository.BodyFatPercentageSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.BodyFatPercentageSummaryCriteria;
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
 * Service for executing complex queries for {@link BodyFatPercentageSummary} entities in the database.
 * The main input is a {@link BodyFatPercentageSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BodyFatPercentageSummary} or a {@link Page} of {@link BodyFatPercentageSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BodyFatPercentageSummaryQueryService extends QueryService<BodyFatPercentageSummary> {

    private final Logger log = LoggerFactory.getLogger(BodyFatPercentageSummaryQueryService.class);

    private final BodyFatPercentageSummaryRepository bodyFatPercentageSummaryRepository;

    public BodyFatPercentageSummaryQueryService(BodyFatPercentageSummaryRepository bodyFatPercentageSummaryRepository) {
        this.bodyFatPercentageSummaryRepository = bodyFatPercentageSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link BodyFatPercentageSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BodyFatPercentageSummary> findByCriteria(BodyFatPercentageSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BodyFatPercentageSummary> specification = createSpecification(criteria);
        return bodyFatPercentageSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BodyFatPercentageSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BodyFatPercentageSummary> findByCriteria(BodyFatPercentageSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BodyFatPercentageSummary> specification = createSpecification(criteria);
        return bodyFatPercentageSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BodyFatPercentageSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BodyFatPercentageSummary> specification = createSpecification(criteria);
        return bodyFatPercentageSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link BodyFatPercentageSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BodyFatPercentageSummary> createSpecification(BodyFatPercentageSummaryCriteria criteria) {
        Specification<BodyFatPercentageSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BodyFatPercentageSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), BodyFatPercentageSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), BodyFatPercentageSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldAverage(), BodyFatPercentageSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), BodyFatPercentageSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), BodyFatPercentageSummary_.fieldMin));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), BodyFatPercentageSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), BodyFatPercentageSummary_.endTime));
            }
        }
        return specification;
    }
}
