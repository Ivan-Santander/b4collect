package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.OxygenSaturationSummary;
import com.be4tech.b4carecollect.repository.OxygenSaturationSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.OxygenSaturationSummaryCriteria;
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
 * Service for executing complex queries for {@link OxygenSaturationSummary} entities in the database.
 * The main input is a {@link OxygenSaturationSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OxygenSaturationSummary} or a {@link Page} of {@link OxygenSaturationSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OxygenSaturationSummaryQueryService extends QueryService<OxygenSaturationSummary> {

    private final Logger log = LoggerFactory.getLogger(OxygenSaturationSummaryQueryService.class);

    private final OxygenSaturationSummaryRepository oxygenSaturationSummaryRepository;

    public OxygenSaturationSummaryQueryService(OxygenSaturationSummaryRepository oxygenSaturationSummaryRepository) {
        this.oxygenSaturationSummaryRepository = oxygenSaturationSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link OxygenSaturationSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OxygenSaturationSummary> findByCriteria(OxygenSaturationSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OxygenSaturationSummary> specification = createSpecification(criteria);
        return oxygenSaturationSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OxygenSaturationSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OxygenSaturationSummary> findByCriteria(OxygenSaturationSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OxygenSaturationSummary> specification = createSpecification(criteria);
        return oxygenSaturationSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OxygenSaturationSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OxygenSaturationSummary> specification = createSpecification(criteria);
        return oxygenSaturationSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link OxygenSaturationSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OxygenSaturationSummary> createSpecification(OxygenSaturationSummaryCriteria criteria) {
        Specification<OxygenSaturationSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), OxygenSaturationSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), OxygenSaturationSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), OxygenSaturationSummary_.empresaId));
            }
            if (criteria.getFieldOxigenSaturationAverage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldOxigenSaturationAverage(),
                            OxygenSaturationSummary_.fieldOxigenSaturationAverage
                        )
                    );
            }
            if (criteria.getFieldOxigenSaturationMax() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldOxigenSaturationMax(), OxygenSaturationSummary_.fieldOxigenSaturationMax)
                    );
            }
            if (criteria.getFieldOxigenSaturationMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldOxigenSaturationMin(), OxygenSaturationSummary_.fieldOxigenSaturationMin)
                    );
            }
            if (criteria.getFieldSuplementalOxigenFlowRateAverage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldSuplementalOxigenFlowRateAverage(),
                            OxygenSaturationSummary_.fieldSuplementalOxigenFlowRateAverage
                        )
                    );
            }
            if (criteria.getFieldSuplementalOxigenFlowRateMax() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldSuplementalOxigenFlowRateMax(),
                            OxygenSaturationSummary_.fieldSuplementalOxigenFlowRateMax
                        )
                    );
            }
            if (criteria.getFieldSuplementalOxigenFlowRateMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldSuplementalOxigenFlowRateMin(),
                            OxygenSaturationSummary_.fieldSuplementalOxigenFlowRateMin
                        )
                    );
            }
            if (criteria.getFieldOxigenTherapyAdministrationMode() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldOxigenTherapyAdministrationMode(),
                            OxygenSaturationSummary_.fieldOxigenTherapyAdministrationMode
                        )
                    );
            }
            if (criteria.getFieldOxigenSaturationMode() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldOxigenSaturationMode(), OxygenSaturationSummary_.fieldOxigenSaturationMode)
                    );
            }
            if (criteria.getFieldOxigenSaturationMeasurementMethod() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldOxigenSaturationMeasurementMethod(),
                            OxygenSaturationSummary_.fieldOxigenSaturationMeasurementMethod
                        )
                    );
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), OxygenSaturationSummary_.endTime));
            }
        }
        return specification;
    }
}
