package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.MentalHealthSummary;
import com.be4tech.b4carecollect.repository.MentalHealthSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.MentalHealthSummaryCriteria;
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
 * Service for executing complex queries for {@link MentalHealthSummary} entities in the database.
 * The main input is a {@link MentalHealthSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MentalHealthSummary} or a {@link Page} of {@link MentalHealthSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MentalHealthSummaryQueryService extends QueryService<MentalHealthSummary> {

    private final Logger log = LoggerFactory.getLogger(MentalHealthSummaryQueryService.class);

    private final MentalHealthSummaryRepository mentalHealthSummaryRepository;

    public MentalHealthSummaryQueryService(MentalHealthSummaryRepository mentalHealthSummaryRepository) {
        this.mentalHealthSummaryRepository = mentalHealthSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link MentalHealthSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MentalHealthSummary> findByCriteria(MentalHealthSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MentalHealthSummary> specification = createSpecification(criteria);
        return mentalHealthSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MentalHealthSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MentalHealthSummary> findByCriteria(MentalHealthSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MentalHealthSummary> specification = createSpecification(criteria);
        return mentalHealthSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MentalHealthSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MentalHealthSummary> specification = createSpecification(criteria);
        return mentalHealthSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link MentalHealthSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MentalHealthSummary> createSpecification(MentalHealthSummaryCriteria criteria) {
        Specification<MentalHealthSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MentalHealthSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), MentalHealthSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), MentalHealthSummary_.empresaId));
            }
            if (criteria.getEmotionDescripMain() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEmotionDescripMain(), MentalHealthSummary_.emotionDescripMain));
            }
            if (criteria.getEmotionDescripSecond() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getEmotionDescripSecond(), MentalHealthSummary_.emotionDescripSecond)
                    );
            }
            if (criteria.getFieldMentalHealthAverage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldMentalHealthAverage(), MentalHealthSummary_.fieldMentalHealthAverage)
                    );
            }
            if (criteria.getFieldMentalHealthMax() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldMentalHealthMax(), MentalHealthSummary_.fieldMentalHealthMax)
                    );
            }
            if (criteria.getFieldMentalHealthMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldMentalHealthMin(), MentalHealthSummary_.fieldMentalHealthMin)
                    );
            }
            if (criteria.getScoreMentalRisk() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getScoreMentalRisk(), MentalHealthSummary_.scoreMentalRisk));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), MentalHealthSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), MentalHealthSummary_.endTime));
            }
        }
        return specification;
    }
}
