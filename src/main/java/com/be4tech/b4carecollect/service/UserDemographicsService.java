package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.UserDemographics;
import com.be4tech.b4carecollect.repository.UserDemographicsRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserDemographics}.
 */
@Service
@Transactional
public class UserDemographicsService {

    private final Logger log = LoggerFactory.getLogger(UserDemographicsService.class);

    private final UserDemographicsRepository userDemographicsRepository;

    public UserDemographicsService(UserDemographicsRepository userDemographicsRepository) {
        this.userDemographicsRepository = userDemographicsRepository;
    }

    /**
     * Save a userDemographics.
     *
     * @param userDemographics the entity to save.
     * @return the persisted entity.
     */
    public UserDemographics save(UserDemographics userDemographics) {
        log.debug("Request to save UserDemographics : {}", userDemographics);
        return userDemographicsRepository.save(userDemographics);
    }

    /**
     * Update a userDemographics.
     *
     * @param userDemographics the entity to save.
     * @return the persisted entity.
     */
    public UserDemographics update(UserDemographics userDemographics) {
        log.debug("Request to update UserDemographics : {}", userDemographics);
        return userDemographicsRepository.save(userDemographics);
    }

    /**
     * Partially update a userDemographics.
     *
     * @param userDemographics the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserDemographics> partialUpdate(UserDemographics userDemographics) {
        log.debug("Request to partially update UserDemographics : {}", userDemographics);

        return userDemographicsRepository
            .findById(userDemographics.getId())
            .map(existingUserDemographics -> {
                if (userDemographics.getUsuarioId() != null) {
                    existingUserDemographics.setUsuarioId(userDemographics.getUsuarioId());
                }
                if (userDemographics.getEmpresaId() != null) {
                    existingUserDemographics.setEmpresaId(userDemographics.getEmpresaId());
                }
                if (userDemographics.getGender() != null) {
                    existingUserDemographics.setGender(userDemographics.getGender());
                }
                if (userDemographics.getDateOfBird() != null) {
                    existingUserDemographics.setDateOfBird(userDemographics.getDateOfBird());
                }
                if (userDemographics.getAge() != null) {
                    existingUserDemographics.setAge(userDemographics.getAge());
                }
                if (userDemographics.getCountry() != null) {
                    existingUserDemographics.setCountry(userDemographics.getCountry());
                }
                if (userDemographics.getState() != null) {
                    existingUserDemographics.setState(userDemographics.getState());
                }
                if (userDemographics.getCity() != null) {
                    existingUserDemographics.setCity(userDemographics.getCity());
                }
                if (userDemographics.getEthnicity() != null) {
                    existingUserDemographics.setEthnicity(userDemographics.getEthnicity());
                }
                if (userDemographics.getIncome() != null) {
                    existingUserDemographics.setIncome(userDemographics.getIncome());
                }
                if (userDemographics.getMaritalStatus() != null) {
                    existingUserDemographics.setMaritalStatus(userDemographics.getMaritalStatus());
                }
                if (userDemographics.getEducation() != null) {
                    existingUserDemographics.setEducation(userDemographics.getEducation());
                }
                if (userDemographics.getEndTime() != null) {
                    existingUserDemographics.setEndTime(userDemographics.getEndTime());
                }

                return existingUserDemographics;
            })
            .map(userDemographicsRepository::save);
    }

    /**
     * Get all the userDemographics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserDemographics> findAll(Pageable pageable) {
        log.debug("Request to get all UserDemographics");
        return userDemographicsRepository.findAll(pageable);
    }

    /**
     * Get one userDemographics by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserDemographics> findOne(UUID id) {
        log.debug("Request to get UserDemographics : {}", id);
        return userDemographicsRepository.findById(id);
    }

    /**
     * Delete the userDemographics by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete UserDemographics : {}", id);
        userDemographicsRepository.deleteById(id);
    }
}
