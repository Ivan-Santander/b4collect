package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.*; // for static metamodels
import com.be4tech.b4carecollect.domain.UserBodyInfo;
import com.be4tech.b4carecollect.repository.UserBodyInfoRepository;
import com.be4tech.b4carecollect.service.criteria.UserBodyInfoCriteria;
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
 * Service for executing complex queries for {@link UserBodyInfo} entities in the database.
 * The main input is a {@link UserBodyInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserBodyInfo} or a {@link Page} of {@link UserBodyInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserBodyInfoQueryService extends QueryService<UserBodyInfo> {

    private final Logger log = LoggerFactory.getLogger(UserBodyInfoQueryService.class);

    private final UserBodyInfoRepository userBodyInfoRepository;

    public UserBodyInfoQueryService(UserBodyInfoRepository userBodyInfoRepository) {
        this.userBodyInfoRepository = userBodyInfoRepository;
    }

    /**
     * Return a {@link List} of {@link UserBodyInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserBodyInfo> findByCriteria(UserBodyInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserBodyInfo> specification = createSpecification(criteria);
        return userBodyInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserBodyInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserBodyInfo> findByCriteria(UserBodyInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserBodyInfo> specification = createSpecification(criteria);
        return userBodyInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserBodyInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserBodyInfo> specification = createSpecification(criteria);
        return userBodyInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link UserBodyInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserBodyInfo> createSpecification(UserBodyInfoCriteria criteria) {
        Specification<UserBodyInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserBodyInfo_.id));
            }
            if (criteria.getUsuarioId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuarioId(), UserBodyInfo_.usuarioId));
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresaId(), UserBodyInfo_.empresaId));
            }
            if (criteria.getWaistCircumference() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getWaistCircumference(), UserBodyInfo_.waistCircumference));
            }
            if (criteria.getHipCircumference() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHipCircumference(), UserBodyInfo_.hipCircumference));
            }
            if (criteria.getChestCircumference() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getChestCircumference(), UserBodyInfo_.chestCircumference));
            }
            if (criteria.getBoneCompositionPercentaje() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getBoneCompositionPercentaje(), UserBodyInfo_.boneCompositionPercentaje)
                    );
            }
            if (criteria.getMuscleCompositionPercentaje() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getMuscleCompositionPercentaje(), UserBodyInfo_.muscleCompositionPercentaje)
                    );
            }
            if (criteria.getSmoker() != null) {
                specification = specification.and(buildSpecification(criteria.getSmoker(), UserBodyInfo_.smoker));
            }
            if (criteria.getWaightKg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaightKg(), UserBodyInfo_.waightKg));
            }
            if (criteria.getHeightCm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeightCm(), UserBodyInfo_.heightCm));
            }
            if (criteria.getBodyHealthScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBodyHealthScore(), UserBodyInfo_.bodyHealthScore));
            }
            if (criteria.getCardiovascularRisk() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCardiovascularRisk(), UserBodyInfo_.cardiovascularRisk));
            }
        }
        return specification;
    }
}
