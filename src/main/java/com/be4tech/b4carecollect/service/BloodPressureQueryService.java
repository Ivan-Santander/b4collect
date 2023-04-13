package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.BloodPressure;
import com.be4tech.b4carecollect.repository.BloodPressureRepository;
import com.be4tech.b4carecollect.service.criteria.BloodPressureCriteria;
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
 * Service for executing complex queries for {@link BloodPressure} entities in the database.
 * The main input is a {@link BloodPressureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BloodPressure} or a {@link Page} of {@link BloodPressure} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BloodPressureQueryService extends QueryService<BloodPressure> {

    private final Logger log = LoggerFactory.getLogger(BloodPressureQueryService.class);

    private final BloodPressureRepository bloodPressureRepository;

    public BloodPressureQueryService(BloodPressureRepository bloodPressureRepository) {
        this.bloodPressureRepository = bloodPressureRepository;
    }

    /**
     * Return a {@link List} of {@link BloodPressure} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BloodPressure> findByCriteria(BloodPressureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BloodPressure> specification = createSpecification(criteria);
        return bloodPressureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BloodPressure} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodPressure> findByCriteria(BloodPressureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BloodPressure> specification = createSpecification(criteria);
        return bloodPressureRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BloodPressureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BloodPressure> specification = createSpecification(criteria);
        return bloodPressureRepository.count(specification);
    }

    /**
     * Function to convert {@link BloodPressureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BloodPressure> createSpecification(BloodPressureCriteria criteria) {
        Specification<BloodPressure> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BloodPressure_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), BloodPressure_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), BloodPressure_.empresaId));
            }
            if (criteria.getFieldBloodPressureSystolic() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldBloodPressureSystolic(), BloodPressure_.fieldBloodPressureSystolic)
                    );
            }
            if (criteria.getFieldBloodPressureDiastolic() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldBloodPressureDiastolic(), BloodPressure_.fieldBloodPressureDiastolic)
                    );
            }
            if (criteria.getFieldBodyPosition() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFieldBodyPosition(), BloodPressure_.fieldBodyPosition));
            }
            if (criteria.getFieldBloodPressureMeasureLocation() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldBloodPressureMeasureLocation(),
                            BloodPressure_.fieldBloodPressureMeasureLocation
                        )
                    );
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), BloodPressure_.endTime));
            }
        }
        return specification;
    }
}
