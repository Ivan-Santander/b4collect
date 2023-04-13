package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.SpeedSummary;
import com.be4tech.b4carecollect.repository.SpeedSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.SpeedSummaryCriteria;
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
 * Service for executing complex queries for {@link SpeedSummary} entities in the database.
 * The main input is a {@link SpeedSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpeedSummary} or a {@link Page} of {@link SpeedSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpeedSummaryQueryService extends QueryService<SpeedSummary> {

    private final Logger log = LoggerFactory.getLogger(SpeedSummaryQueryService.class);

    private final SpeedSummaryRepository speedSummaryRepository;

    public SpeedSummaryQueryService(SpeedSummaryRepository speedSummaryRepository) {
        this.speedSummaryRepository = speedSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link SpeedSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpeedSummary> findByCriteria(SpeedSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SpeedSummary> specification = createSpecification(criteria);
        return speedSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SpeedSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpeedSummary> findByCriteria(SpeedSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SpeedSummary> specification = createSpecification(criteria);
        return speedSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpeedSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SpeedSummary> specification = createSpecification(criteria);
        return speedSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link SpeedSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SpeedSummary> createSpecification(SpeedSummaryCriteria criteria) {
        Specification<SpeedSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SpeedSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), SpeedSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), SpeedSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldAverage(), SpeedSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), SpeedSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), SpeedSummary_.fieldMin));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), SpeedSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), SpeedSummary_.endTime));
            }
        }
        return specification;
    }
}
