package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.PoweSample;
import com.be4tech.b4carecollect.repository.PoweSampleRepository;
import com.be4tech.b4carecollect.service.criteria.PoweSampleCriteria;
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
 * Service for executing complex queries for {@link PoweSample} entities in the database.
 * The main input is a {@link PoweSampleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PoweSample} or a {@link Page} of {@link PoweSample} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PoweSampleQueryService extends QueryService<PoweSample> {

    private final Logger log = LoggerFactory.getLogger(PoweSampleQueryService.class);

    private final PoweSampleRepository poweSampleRepository;

    public PoweSampleQueryService(PoweSampleRepository poweSampleRepository) {
        this.poweSampleRepository = poweSampleRepository;
    }

    /**
     * Return a {@link List} of {@link PoweSample} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PoweSample> findByCriteria(PoweSampleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PoweSample> specification = createSpecification(criteria);
        return poweSampleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PoweSample} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PoweSample> findByCriteria(PoweSampleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PoweSample> specification = createSpecification(criteria);
        return poweSampleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PoweSampleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PoweSample> specification = createSpecification(criteria);
        return poweSampleRepository.count(specification);
    }

    /**
     * Function to convert {@link PoweSampleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PoweSample> createSpecification(PoweSampleCriteria criteria) {
        Specification<PoweSample> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PoweSample_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), PoweSample_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), PoweSample_.empresaId));
            }
            if (criteria.getVatios() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVatios(), PoweSample_.vatios));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), PoweSample_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), PoweSample_.endTime));
            }
        }
        return specification;
    }
}
