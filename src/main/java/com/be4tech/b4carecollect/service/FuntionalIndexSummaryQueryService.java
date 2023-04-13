package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.FuntionalIndexSummary;
import com.be4tech.b4carecollect.repository.FuntionalIndexSummaryRepository;
import com.be4tech.b4carecollect.service.criteria.FuntionalIndexSummaryCriteria;
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
 * Service for executing complex queries for {@link FuntionalIndexSummary} entities in the database.
 * The main input is a {@link FuntionalIndexSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuntionalIndexSummary} or a {@link Page} of {@link FuntionalIndexSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuntionalIndexSummaryQueryService extends QueryService<FuntionalIndexSummary> {

    private final Logger log = LoggerFactory.getLogger(FuntionalIndexSummaryQueryService.class);

    private final FuntionalIndexSummaryRepository funtionalIndexSummaryRepository;

    public FuntionalIndexSummaryQueryService(FuntionalIndexSummaryRepository funtionalIndexSummaryRepository) {
        this.funtionalIndexSummaryRepository = funtionalIndexSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link FuntionalIndexSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuntionalIndexSummary> findByCriteria(FuntionalIndexSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FuntionalIndexSummary> specification = createSpecification(criteria);
        return funtionalIndexSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FuntionalIndexSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuntionalIndexSummary> findByCriteria(FuntionalIndexSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FuntionalIndexSummary> specification = createSpecification(criteria);
        return funtionalIndexSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuntionalIndexSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FuntionalIndexSummary> specification = createSpecification(criteria);
        return funtionalIndexSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link FuntionalIndexSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FuntionalIndexSummary> createSpecification(FuntionalIndexSummaryCriteria criteria) {
        Specification<FuntionalIndexSummary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FuntionalIndexSummary_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), FuntionalIndexSummary_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), FuntionalIndexSummary_.empresaId));
            }
            if (criteria.getFieldFuntionalIndexAverage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldFuntionalIndexAverage(), FuntionalIndexSummary_.fieldFuntionalIndexAverage)
                    );
            }
            if (criteria.getFieldFuntionalIndexMax() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldFuntionalIndexMax(), FuntionalIndexSummary_.fieldFuntionalIndexMax)
                    );
            }
            if (criteria.getFieldFuntionalIndexMin() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFieldFuntionalIndexMin(), FuntionalIndexSummary_.fieldFuntionalIndexMin)
                    );
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), FuntionalIndexSummary_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), FuntionalIndexSummary_.endTime));
            }
        }
        return specification;
    }
}
