package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.PowerSummary;
import com.be4tech.b4carecollect.repository.PowerSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.PowerSummaryCriteria;
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
 * Service for executing complex queries for {@link PowerSummary} entities in the database.
 * The main input is a {@link PowerSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PowerSummary} or a {@link Page} of {@link PowerSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PowerSummaryQueryService extends QueryService<PowerSummary> {

    private final Logger log = LoggerFactory.getLogger(PowerSummaryQueryService.class);

    private final PowerSummaryRepository powerSummaryRepository;

    public PowerSummaryQueryService(PowerSummaryRepository powerSummaryRepository) {
        this.powerSummaryRepository = powerSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link PowerSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PowerSummary> findByCriteria(PowerSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PowerSummary> specification = createSpecification(criteria);
        return powerSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PowerSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PowerSummary> findByCriteria(PowerSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PowerSummary> specification = createSpecification(criteria);
        return powerSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PowerSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PowerSummary> specification = createSpecification(criteria);
        return powerSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link PowerSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PowerSummary> createSpecification(PowerSummaryCriteria criteria) {
        Specification<PowerSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PowerSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), PowerSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), PowerSummary_.empresaId));
            }
            if (criteria.getFieldAverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldAverage(), PowerSummary_.fieldAverage));
            }
            if (criteria.getFieldMax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMax(), PowerSummary_.fieldMax));
            }
            if (criteria.getFieldMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMin(), PowerSummary_.fieldMin));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), PowerSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), PowerSummary_.endTime));
            }
        }
        return specification;
    }
}
