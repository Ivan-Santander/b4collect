package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.SleepSegment;
import com.be4tech.b4carecollect.repository.SleepSegmentRepository;
import com.be4tech.b4carecollect.service.criteria.SleepSegmentCriteria;
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
 * Service for executing complex queries for {@link SleepSegment} entities in the database.
 * The main input is a {@link SleepSegmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SleepSegment} or a {@link Page} of {@link SleepSegment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SleepSegmentQueryService extends QueryService<SleepSegment> {

    private final Logger log = LoggerFactory.getLogger(SleepSegmentQueryService.class);

    private final SleepSegmentRepository sleepSegmentRepository;

    public SleepSegmentQueryService(SleepSegmentRepository sleepSegmentRepository) {
        this.sleepSegmentRepository = sleepSegmentRepository;
    }

    /**
     * Return a {@link List} of {@link SleepSegment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SleepSegment> findByCriteria(SleepSegmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SleepSegment> specification = createSpecification(criteria);
        return sleepSegmentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SleepSegment} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SleepSegment> findByCriteria(SleepSegmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SleepSegment> specification = createSpecification(criteria);
        return sleepSegmentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SleepSegmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SleepSegment> specification = createSpecification(criteria);
        return sleepSegmentRepository.count(specification);
    }

    /**
     * Function to convert {@link SleepSegmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SleepSegment> createSpecification(SleepSegmentCriteria criteria) {
        Specification<SleepSegment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SleepSegment_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), SleepSegment_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), SleepSegment_.empresaId));
            }
            if (criteria.getFieldSleepSegmentType() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFieldSleepSegmentType(), SleepSegment_.fieldSleepSegmentType));
            }
            if (criteria.getFieldBloodGlucoseSpecimenSource() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getFieldBloodGlucoseSpecimenSource(),
                            SleepSegment_.fieldBloodGlucoseSpecimenSource
                        )
                    );
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), SleepSegment_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), SleepSegment_.endTime));
            }
        }
        return specification;
    }
}
