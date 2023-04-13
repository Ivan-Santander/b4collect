package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.HeartRateBpm;
import com.be4tech.b4carecollect.repository.HeartRateBpmRepository;
import com.be4tech.b4carecollect.service.criteria.HeartRateBpmCriteria;
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
 * Service for executing complex queries for {@link HeartRateBpm} entities in the database.
 * The main input is a {@link HeartRateBpmCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HeartRateBpm} or a {@link Page} of {@link HeartRateBpm} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HeartRateBpmQueryService extends QueryService<HeartRateBpm> {

    private final Logger log = LoggerFactory.getLogger(HeartRateBpmQueryService.class);

    private final HeartRateBpmRepository heartRateBpmRepository;

    public HeartRateBpmQueryService(HeartRateBpmRepository heartRateBpmRepository) {
        this.heartRateBpmRepository = heartRateBpmRepository;
    }

    /**
     * Return a {@link List} of {@link HeartRateBpm} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HeartRateBpm> findByCriteria(HeartRateBpmCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HeartRateBpm> specification = createSpecification(criteria);
        return heartRateBpmRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HeartRateBpm} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HeartRateBpm> findByCriteria(HeartRateBpmCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HeartRateBpm> specification = createSpecification(criteria);
        return heartRateBpmRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HeartRateBpmCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HeartRateBpm> specification = createSpecification(criteria);
        return heartRateBpmRepository.count(specification);
    }

    /**
     * Function to convert {@link HeartRateBpmCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HeartRateBpm> createSpecification(HeartRateBpmCriteria criteria) {
        Specification<HeartRateBpm> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), HeartRateBpm_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), HeartRateBpm_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), HeartRateBpm_.empresaId));
            }
            if (criteria.getPpm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPpm(), HeartRateBpm_.ppm));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), HeartRateBpm_.endTime));
            }
        }
        return specification;
    }
}
