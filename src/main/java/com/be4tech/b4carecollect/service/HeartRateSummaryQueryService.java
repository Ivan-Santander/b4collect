package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.HeartRateSummary;
import com.be4tech.b4carecollect.repository.HeartRateSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.HeartRateSummaryCriteria;
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
 * Service for executing complex queries for {@link HeartRateSummary} entities in the database.
 * The main input is a {@link HeartRateSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HeartRateSummary} or a {@link Page} of {@link HeartRateSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HeartRateSummaryQueryService extends QueryService<HeartRateSummary> {

    private final Logger log = LoggerFactory.getLogger(HeartRateSummaryQueryService.class);

    private final HeartRateSummaryRepository heartRateSummaryRepository;

    public HeartRateSummaryQueryService(HeartRateSummaryRepository heartRateSummaryRepository) {
        this.heartRateSummaryRepository = heartRateSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link HeartRateSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HeartRateSummary> findByCriteria(HeartRateSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HeartRateSummary> specification = createSpecification(criteria);
        return heartRateSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HeartRateSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HeartRateSummary> findByCriteria(HeartRateSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HeartRateSummary> specification = createSpecification(criteria);
        return heartRateSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HeartRateSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HeartRateSummary> specification = createSpecification(criteria);
        return heartRateSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link HeartRateSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HeartRateSummary> createSpecification(HeartRateSummaryCriteria criteria) {
        Specification<HeartRateSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), HeartRateSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), HeartRateSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), HeartRateSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldAverage(), HeartRateSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), HeartRateSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), HeartRateSummary_.fieldMin));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), HeartRateSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), HeartRateSummary_.endTime));
            }
        }
        return specification;
    }
}
