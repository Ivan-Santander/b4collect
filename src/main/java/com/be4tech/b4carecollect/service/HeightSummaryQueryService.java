package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.HeightSummary;
import com.be4tech.b4carecollect.repository.HeightSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.HeightSummaryCriteria;
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
 * Service for executing complex queries for {@link HeightSummary} entities in the database.
 * The main input is a {@link HeightSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HeightSummary} or a {@link Page} of {@link HeightSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HeightSummaryQueryService extends QueryService<HeightSummary> {

    private final Logger log = LoggerFactory.getLogger(HeightSummaryQueryService.class);

    private final HeightSummaryRepository heightSummaryRepository;

    public HeightSummaryQueryService(HeightSummaryRepository heightSummaryRepository) {
        this.heightSummaryRepository = heightSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link HeightSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HeightSummary> findByCriteria(HeightSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HeightSummary> specification = createSpecification(criteria);
        return heightSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HeightSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HeightSummary> findByCriteria(HeightSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HeightSummary> specification = createSpecification(criteria);
        return heightSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HeightSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HeightSummary> specification = createSpecification(criteria);
        return heightSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link HeightSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HeightSummary> createSpecification(HeightSummaryCriteria criteria) {
        Specification<HeightSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), HeightSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), HeightSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), HeightSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldAverage(), HeightSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), HeightSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), HeightSummary_.fieldMin));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), HeightSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), HeightSummary_.endTime));
            }
        }
        return specification;
    }
}
