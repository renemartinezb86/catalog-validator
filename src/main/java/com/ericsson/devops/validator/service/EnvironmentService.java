package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.Environment;
import com.ericsson.devops.validator.repository.EnvironmentRepository;
import com.ericsson.devops.validator.repository.search.EnvironmentSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Environment.
 */
@Service
public class EnvironmentService {

    private final Logger log = LoggerFactory.getLogger(EnvironmentService.class);

    private final EnvironmentRepository environmentRepository;

    private final EnvironmentSearchRepository environmentSearchRepository;

    public EnvironmentService(EnvironmentRepository environmentRepository, EnvironmentSearchRepository environmentSearchRepository) {
        this.environmentRepository = environmentRepository;
        this.environmentSearchRepository = environmentSearchRepository;
    }

    /**
     * Save a environment.
     *
     * @param environment the entity to save
     * @return the persisted entity
     */
    public Environment save(Environment environment) {
        log.debug("Request to save Environment : {}", environment);
        Environment result = environmentRepository.save(environment);
        environmentSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the environments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Environment> findAll(Pageable pageable) {
        log.debug("Request to get all Environments");
        return environmentRepository.findAll(pageable);
    }


    /**
     * Get one environment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Environment> findOne(String id) {
        log.debug("Request to get Environment : {}", id);
        return environmentRepository.findById(id);
    }

    /**
     * Delete the environment by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Environment : {}", id);
        environmentRepository.deleteById(id);
        environmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the environment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Environment> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Environments for query {}", query);
        return environmentSearchRepository.search(queryStringQuery(query), pageable);    }
}
