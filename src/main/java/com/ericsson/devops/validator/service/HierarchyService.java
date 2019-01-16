package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.Hierarchy;
import com.ericsson.devops.validator.repository.HierarchyRepository;
import com.ericsson.devops.validator.repository.search.HierarchySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Hierarchy.
 */
@Service
public class HierarchyService {

    private final Logger log = LoggerFactory.getLogger(HierarchyService.class);

    private final HierarchyRepository hierarchyRepository;

    private final HierarchySearchRepository hierarchySearchRepository;

    public HierarchyService(HierarchyRepository hierarchyRepository, HierarchySearchRepository hierarchySearchRepository) {
        this.hierarchyRepository = hierarchyRepository;
        this.hierarchySearchRepository = hierarchySearchRepository;
    }

    /**
     * Save a hierarchy.
     *
     * @param hierarchy the entity to save
     * @return the persisted entity
     */
    public Hierarchy save(Hierarchy hierarchy) {
        log.debug("Request to save Hierarchy : {}", hierarchy);
        Hierarchy result = hierarchyRepository.save(hierarchy);
        hierarchySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the hierarchies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Hierarchy> findAll(Pageable pageable) {
        log.debug("Request to get all Hierarchies");
        return hierarchyRepository.findAll(pageable);
    }


    /**
     * Get one hierarchy by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Hierarchy> findOne(String id) {
        log.debug("Request to get Hierarchy : {}", id);
        return hierarchyRepository.findById(id);
    }

    /**
     * Delete the hierarchy by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Hierarchy : {}", id);
        hierarchyRepository.deleteById(id);
        hierarchySearchRepository.deleteById(id);
    }

    /**
     * Search for the hierarchy corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Hierarchy> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Hierarchies for query {}", query);
        return hierarchySearchRepository.search(queryStringQuery(query), pageable);    }
}
