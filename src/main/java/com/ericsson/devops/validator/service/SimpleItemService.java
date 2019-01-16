package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.SimpleItem;
import com.ericsson.devops.validator.repository.SimpleItemRepository;
import com.ericsson.devops.validator.repository.search.SimpleItemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SimpleItem.
 */
@Service
public class SimpleItemService {

    private final Logger log = LoggerFactory.getLogger(SimpleItemService.class);

    private final SimpleItemRepository simpleItemRepository;

    private final SimpleItemSearchRepository simpleItemSearchRepository;

    public SimpleItemService(SimpleItemRepository simpleItemRepository, SimpleItemSearchRepository simpleItemSearchRepository) {
        this.simpleItemRepository = simpleItemRepository;
        this.simpleItemSearchRepository = simpleItemSearchRepository;
    }

    /**
     * Save a simpleItem.
     *
     * @param simpleItem the entity to save
     * @return the persisted entity
     */
    public SimpleItem save(SimpleItem simpleItem) {
        log.debug("Request to save SimpleItem : {}", simpleItem);
        SimpleItem result = simpleItemRepository.save(simpleItem);
        simpleItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the simpleItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<SimpleItem> findAll(Pageable pageable) {
        log.debug("Request to get all SimpleItems");
        return simpleItemRepository.findAll(pageable);
    }


    /**
     * Get one simpleItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<SimpleItem> findOne(String id) {
        log.debug("Request to get SimpleItem : {}", id);
        return simpleItemRepository.findById(id);
    }

    /**
     * Delete the simpleItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SimpleItem : {}", id);
        simpleItemRepository.deleteById(id);
        simpleItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the simpleItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<SimpleItem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SimpleItems for query {}", query);
        return simpleItemSearchRepository.search(queryStringQuery(query), pageable);    }
}
