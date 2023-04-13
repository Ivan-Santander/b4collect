package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.AlarmRiskIndexSummary;
import com.be4tech.b4carecollect.repository.AlarmRiskIndexSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.AlarmRiskIndexSummaryCriteria;
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
 * Service for executing complex queries for {@link AlarmRiskIndexSummary} entities in the database.
 * The main input is a {@link AlarmRiskIndexSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlarmRiskIndexSummary} or a {@link Page} of {@link AlarmRiskIndexSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlarmRiskIndexSummaryQueryService extends QueryService<AlarmRiskIndexSummary> {

    private final Logger log = LoggerFactory.getLogger(AlarmRiskIndexSummaryQueryService.class);

    private final AlarmRiskIndexSummaryRepository alarmRiskIndexSummaryRepository;

    public AlarmRiskIndexSummaryQueryService(AlarmRiskIndexSummaryRepository alarmRiskIndexSummaryRepository) {
        this.alarmRiskIndexSummaryRepository = alarmRiskIndexSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link AlarmRiskIndexSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlarmRiskIndexSummary> findByCriteria(AlarmRiskIndexSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AlarmRiskIndexSummary> specification = createSpecification(criteria);
        return alarmRiskIndexSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AlarmRiskIndexSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlarmRiskIndexSummary> findByCriteria(AlarmRiskIndexSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlarmRiskIndexSummary> specification = createSpecification(criteria);
        return alarmRiskIndexSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlarmRiskIndexSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AlarmRiskIndexSummary> specification = createSpecification(criteria);
        return alarmRiskIndexSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link AlarmRiskIndexSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AlarmRiskIndexSummary> createSpecification(AlarmRiskIndexSummaryCriteria criteria) {
        Specification<AlarmRiskIndexSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AlarmRiskIndexSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), AlarmRiskIndexSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), AlarmRiskIndexSummary_.empresaId));
            }
            if (criteria.getFieldAlarmRiskAverage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldAlarmRiskAverage(), AlarmRiskIndexSummary_.fieldAlarmRiskAverage)
                    );
            }
            if (criteria.getFieldAlarmRiskMax() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldAlarmRiskMax(), AlarmRiskIndexSummary_.fieldAlarmRiskMax));
            }
            if (criteria.getFieldAlarmRiskMin() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldAlarmRiskMin(), AlarmRiskIndexSummary_.fieldAlarmRiskMin));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), AlarmRiskIndexSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), AlarmRiskIndexSummary_.endTime));
            }
        }
        return specification;
    }
}
