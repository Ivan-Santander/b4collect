package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.ActivityExercise;
import com.be4tech.b4carecollect.repository.ActivityExerciseRepository;
import com.be4tech.b4carecollect.service.criteria.ActivityExerciseCriteria;
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
 * Service for executing complex queries for {@link ActivityExercise} entities in the database.
 * The main input is a {@link ActivityExerciseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActivityExercise} or a {@link Page} of {@link ActivityExercise} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivityExerciseQueryService extends QueryService<ActivityExercise> {

    private final Logger log = LoggerFactory.getLogger(ActivityExerciseQueryService.class);

    private final ActivityExerciseRepository activityExerciseRepository;

    public ActivityExerciseQueryService(ActivityExerciseRepository activityExerciseRepository) {
        this.activityExerciseRepository = activityExerciseRepository;
    }

    /**
     * Return a {@link List} of {@link ActivityExercise} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActivityExercise> findByCriteria(ActivityExerciseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ActivityExercise> specification = createSpecification(criteria);
        return activityExerciseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ActivityExercise} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityExercise> findByCriteria(ActivityExerciseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ActivityExercise> specification = createSpecification(criteria);
        return activityExerciseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActivityExerciseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ActivityExercise> specification = createSpecification(criteria);
        return activityExerciseRepository.count(specification);
    }

    /**
     * Function to convert {@link ActivityExerciseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ActivityExercise> createSpecification(ActivityExerciseCriteria criteria) {
        Specification<ActivityExercise> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ActivityExercise_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), ActivityExercise_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), ActivityExercise_.empresaId));
            }
            if (criteria.getExercise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExercise(), ActivityExercise_.exercise));
            }
            if (criteria.getRepetitions() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRepetitions(), ActivityExercise_.repetitions));
            }
            if (criteria.getTypeResistence() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeResistence(), ActivityExercise_.typeResistence));
            }
            if (criteria.getResistenceKg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResistenceKg(), ActivityExercise_.resistenceKg));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), ActivityExercise_.duration));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), ActivityExercise_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), ActivityExercise_.endTime));
            }
        }
        return specification;
    }
}
