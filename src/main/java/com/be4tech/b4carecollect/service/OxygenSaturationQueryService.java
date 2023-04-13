package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.OxygenSaturation;
import com.be4tech.b4carecollect.repository.OxygenSaturationRepository;
import com.be4tech.b4carecollect.service.criteria.OxygenSaturationCriteria;
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
 * Service for executing complex queries for {@link OxygenSaturation} entities in the database.
 * The main input is a {@link OxygenSaturationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OxygenSaturation} or a {@link Page} of {@link OxygenSaturation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OxygenSaturationQueryService extends QueryService<OxygenSaturation> {

    private final Logger log = LoggerFactory.getLogger(OxygenSaturationQueryService.class);

    private final OxygenSaturationRepository oxygenSaturationRepository;

    public OxygenSaturationQueryService(OxygenSaturationRepository oxygenSaturationRepository) {
        this.oxygenSaturationRepository = oxygenSaturationRepository;
    }

    /**
     * Return a {@link List} of {@link OxygenSaturation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OxygenSaturation> findByCriteria(OxygenSaturationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OxygenSaturation> specification = createSpecification(criteria);
        return oxygenSaturationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OxygenSaturation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OxygenSaturation> findByCriteria(OxygenSaturationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OxygenSaturation> specification = createSpecification(criteria);
        return oxygenSaturationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OxygenSaturationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OxygenSaturation> specification = createSpecification(criteria);
        return oxygenSaturationRepository.count(specification);
    }

    /**
     * Function to convert {@link OxygenSaturationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OxygenSaturation> createSpecification(OxygenSaturationCriteria criteria) {
        Specification<OxygenSaturation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), OxygenSaturation_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), OxygenSaturation_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), OxygenSaturation_.empresaId));
            }
            if (criteria.getFieldOxigenSaturation() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldOxigenSaturation(), OxygenSaturation_.fieldOxigenSaturation)
                    );
            }
            if (criteria.getFieldSuplementalOxigenFlowRate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldSuplementalOxigenFlowRate(),
                            OxygenSaturation_.fieldSuplementalOxigenFlowRate
                        )
                    );
            }
            if (criteria.getFieldOxigenTherapyAdministrationMode() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldOxigenTherapyAdministrationMode(),
                            OxygenSaturation_.fieldOxigenTherapyAdministrationMode
                        )
                    );
            }
            if (criteria.getFieldOxigenSaturationMode() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldOxigenSaturationMode(), OxygenSaturation_.fieldOxigenSaturationMode)
                    );
            }
            if (criteria.getFieldOxigenSaturationMeasurementMethod() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldOxigenSaturationMeasurementMethod(),
                            OxygenSaturation_.fieldOxigenSaturationMeasurementMethod
                        )
                    );
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), OxygenSaturation_.endTime));
            }
        }
        return specification;
    }
}
