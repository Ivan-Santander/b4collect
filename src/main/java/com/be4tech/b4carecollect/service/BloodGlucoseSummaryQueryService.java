package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.BloodGlucoseSummary;
import com.be4tech.b4carecollect.repository.BloodGlucoseSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.BloodGlucoseSummaryCriteria;
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
 * Service for executing complex queries for {@link BloodGlucoseSummary} entities in the database.
 * The main input is a {@link BloodGlucoseSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BloodGlucoseSummary} or a {@link Page} of {@link BloodGlucoseSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BloodGlucoseSummaryQueryService extends QueryService<BloodGlucoseSummary> {

    private final Logger log = LoggerFactory.getLogger(BloodGlucoseSummaryQueryService.class);

    private final BloodGlucoseSummaryRepository bloodGlucoseSummaryRepository;

    public BloodGlucoseSummaryQueryService(BloodGlucoseSummaryRepository bloodGlucoseSummaryRepository) {
        this.bloodGlucoseSummaryRepository = bloodGlucoseSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link BloodGlucoseSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BloodGlucoseSummary> findByCriteria(BloodGlucoseSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BloodGlucoseSummary> specification = createSpecification(criteria);
        return bloodGlucoseSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BloodGlucoseSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodGlucoseSummary> findByCriteria(BloodGlucoseSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BloodGlucoseSummary> specification = createSpecification(criteria);
        return bloodGlucoseSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BloodGlucoseSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BloodGlucoseSummary> specification = createSpecification(criteria);
        return bloodGlucoseSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link BloodGlucoseSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BloodGlucoseSummary> createSpecification(BloodGlucoseSummaryCriteria criteria) {
        Specification<BloodGlucoseSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BloodGlucoseSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), BloodGlucoseSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), BloodGlucoseSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldAverage(), BloodGlucoseSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), BloodGlucoseSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), BloodGlucoseSummary_.fieldMin));
            }
            if (criteria.getIntervalFood() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIntervalFood(), BloodGlucoseSummary_.intervalFood));
            }
            if (criteria.getRelationTemporalSleep() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getRelationTemporalSleep(), BloodGlucoseSummary_.relationTemporalSleep)
                    );
            }
            if (criteria.getSampleSource() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSampleSource(), BloodGlucoseSummary_.sampleSource));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), BloodGlucoseSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), BloodGlucoseSummary_.endTime));
            }
        }
        return specification;
    }
}
