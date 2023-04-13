package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.SleepScores;
import com.be4tech.b4carecollect.repository.SleepScoresRepository;
import com.be4tech.b4carecollect.service.criteria.SleepScoresCriteria;
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
 * Service for executing complex queries for {@link SleepScores} entities in the database.
 * The main input is a {@link SleepScoresCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SleepScores} or a {@link Page} of {@link SleepScores} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SleepScoresQueryService extends QueryService<SleepScores> {

    private final Logger log = LoggerFactory.getLogger(SleepScoresQueryService.class);

    private final SleepScoresRepository sleepScoresRepository;

    public SleepScoresQueryService(SleepScoresRepository sleepScoresRepository) {
        this.sleepScoresRepository = sleepScoresRepository;
    }

    /**
     * Return a {@link List} of {@link SleepScores} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SleepScores> findByCriteria(SleepScoresCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SleepScores> specification = createSpecification(criteria);
        return sleepScoresRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SleepScores} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SleepScores> findByCriteria(SleepScoresCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SleepScores> specification = createSpecification(criteria);
        return sleepScoresRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SleepScoresCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SleepScores> specification = createSpecification(criteria);
        return sleepScoresRepository.count(specification);
    }

    /**
     * Function to convert {@link SleepScoresCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SleepScores> createSpecification(SleepScoresCriteria criteria) {
        Specification<SleepScores> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SleepScores_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), SleepScores_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), SleepScores_.empresaId));
            }
            if (criteria.getSleepQualityRatingScore() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSleepQualityRatingScore(), SleepScores_.sleepQualityRatingScore));
            }
            if (criteria.getSleepEfficiencyScore() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSleepEfficiencyScore(), SleepScores_.sleepEfficiencyScore));
            }
            if (criteria.getSleepGooalSecondsScore() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSleepGooalSecondsScore(), SleepScores_.sleepGooalSecondsScore));
            }
            if (criteria.getSleepContinuityScore() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSleepContinuityScore(), SleepScores_.sleepContinuityScore));
            }
            if (criteria.getSleepContinuityRating() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSleepContinuityRating(), SleepScores_.sleepContinuityRating));
            }
        }
        return specification;
    }
}
