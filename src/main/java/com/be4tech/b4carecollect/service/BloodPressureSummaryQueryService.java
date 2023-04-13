package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.BloodPressureSummary;
import com.be4tech.b4carecollect.repository.BloodPressureSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.BloodPressureSummaryCriteria;
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
 * Service for executing complex queries for {@link BloodPressureSummary} entities in the database.
 * The main input is a {@link BloodPressureSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BloodPressureSummary} or a {@link Page} of {@link BloodPressureSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BloodPressureSummaryQueryService extends QueryService<BloodPressureSummary> {

    private final Logger log = LoggerFactory.getLogger(BloodPressureSummaryQueryService.class);

    private final BloodPressureSummaryRepository bloodPressureSummaryRepository;

    public BloodPressureSummaryQueryService(BloodPressureSummaryRepository bloodPressureSummaryRepository) {
        this.bloodPressureSummaryRepository = bloodPressureSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link BloodPressureSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BloodPressureSummary> findByCriteria(BloodPressureSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BloodPressureSummary> specification = createSpecification(criteria);
        return bloodPressureSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BloodPressureSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodPressureSummary> findByCriteria(BloodPressureSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BloodPressureSummary> specification = createSpecification(criteria);
        return bloodPressureSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BloodPressureSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BloodPressureSummary> specification = createSpecification(criteria);
        return bloodPressureSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link BloodPressureSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BloodPressureSummary> createSpecification(BloodPressureSummaryCriteria criteria) {
        Specification<BloodPressureSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BloodPressureSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), BloodPressureSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), BloodPressureSummary_.empresaId));
            }
            if (criteria.getFieldSistolicAverage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldSistolicAverage(), BloodPressureSummary_.fieldSistolicAverage)
                    );
            }
            if (criteria.getFieldSistolicMax() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldSistolicMax(), BloodPressureSummary_.fieldSistolicMax));
            }
            if (criteria.getFieldSistolicMin() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldSistolicMin(), BloodPressureSummary_.fieldSistolicMin));
            }
            if (criteria.getFieldDiasatolicAverage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldDiasatolicAverage(), BloodPressureSummary_.fieldDiasatolicAverage)
                    );
            }
            if (criteria.getFieldDiastolicMax() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldDiastolicMax(), BloodPressureSummary_.fieldDiastolicMax));
            }
            if (criteria.getFieldDiastolicMin() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldDiastolicMin(), BloodPressureSummary_.fieldDiastolicMin));
            }
            if (criteria.getBodyPosition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodyPosition(), BloodPressureSummary_.bodyPosition));
            }
            if (criteria.getMeasurementLocation() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getMeasurementLocation(), BloodPressureSummary_.measurementLocation)
                    );
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), BloodPressureSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), BloodPressureSummary_.endTime));
            }
        }
        return specification;
    }
}
