package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.BloodGlucose;
import com.be4tech.b4carecollect.repository.BloodGlucoseRepository;
import com.be4tech.b4carecollect.service.criteria.BloodGlucoseCriteria;
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
 * Service for executing complex queries for {@link BloodGlucose} entities in the database.
 * The main input is a {@link BloodGlucoseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BloodGlucose} or a {@link Page} of {@link BloodGlucose} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BloodGlucoseQueryService extends QueryService<BloodGlucose> {

    private final Logger log = LoggerFactory.getLogger(BloodGlucoseQueryService.class);

    private final BloodGlucoseRepository bloodGlucoseRepository;

    public BloodGlucoseQueryService(BloodGlucoseRepository bloodGlucoseRepository) {
        this.bloodGlucoseRepository = bloodGlucoseRepository;
    }

    /**
     * Return a {@link List} of {@link BloodGlucose} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BloodGlucose> findByCriteria(BloodGlucoseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BloodGlucose> specification = createSpecification(criteria);
        return bloodGlucoseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BloodGlucose} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodGlucose> findByCriteria(BloodGlucoseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BloodGlucose> specification = createSpecification(criteria);
        return bloodGlucoseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BloodGlucoseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BloodGlucose> specification = createSpecification(criteria);
        return bloodGlucoseRepository.count(specification);
    }

    /**
     * Function to convert {@link BloodGlucoseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BloodGlucose> createSpecification(BloodGlucoseCriteria criteria) {
        Specification<BloodGlucose> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BloodGlucose_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), BloodGlucose_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), BloodGlucose_.empresaId));
            }
            if (criteria.getFieldBloodGlucoseLevel() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldBloodGlucoseLevel(), BloodGlucose_.fieldBloodGlucoseLevel));
            }
            if (criteria.getFieldTemporalRelationToMeal() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldTemporalRelationToMeal(), BloodGlucose_.fieldTemporalRelationToMeal)
                    );
            }
            if (criteria.getFieldMealType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldMealType(), BloodGlucose_.fieldMealType));
            }
            if (criteria.getFieldTemporalRelationToSleep() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldTemporalRelationToSleep(), BloodGlucose_.fieldTemporalRelationToSleep)
                    );
            }
            if (criteria.getFieldBloodGlucoseSpecimenSource() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldBloodGlucoseSpecimenSource(),
                            BloodGlucose_.fieldBloodGlucoseSpecimenSource
                        )
                    );
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), BloodGlucose_.endTime));
            }
        }
        return specification;
    }
}
