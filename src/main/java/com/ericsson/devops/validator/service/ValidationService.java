package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.Validation;
import com.ericsson.devops.validator.repository.ValidationRepository;
import com.ericsson.devops.validator.repository.search.ValidationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Validation.
 */
@Service
public class ValidationService {

    private final Logger log = LoggerFactory.getLogger(ValidationService.class);

    private final ValidationRepository validationRepository;

    private final ValidationSearchRepository validationSearchRepository;

    public ValidationService(ValidationRepository validationRepository, ValidationSearchRepository validationSearchRepository) {
        this.validationRepository = validationRepository;
        this.validationSearchRepository = validationSearchRepository;
    }

    /**
     * Save a validation.
     *
     * @param validation the entity to save
     * @return the persisted entity
     */
    public Validation save(Validation validation) {
        log.debug("Request to save Validation : {}", validation);
        Validation result = validationRepository.save(validation);
        validationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the validations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Validation> findAll(Pageable pageable) {
        log.debug("Request to get all Validations");
        return validationRepository.findAll(pageable);
    }


    /**
     * Get one validation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Validation> findOne(String id) {
        log.debug("Request to get Validation : {}", id);
        return validationRepository.findById(id);
    }

    /**
     * Delete the validation by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Validation : {}", id);
        validationRepository.deleteById(id);
        validationSearchRepository.deleteById(id);
    }

    /**
     * Search for the validation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Validation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Validations for query {}", query);
        return validationSearchRepository.search(queryStringQuery(query), pageable);    }
}
