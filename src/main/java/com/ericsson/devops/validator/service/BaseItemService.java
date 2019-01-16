package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.BaseItem;
import com.ericsson.devops.validator.repository.BaseItemRepository;
import com.ericsson.devops.validator.repository.search.BaseItemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BaseItem.
 */
@Service
public class BaseItemService {

    private final Logger log = LoggerFactory.getLogger(BaseItemService.class);

    private final BaseItemRepository baseItemRepository;

    private final BaseItemSearchRepository baseItemSearchRepository;

    public BaseItemService(BaseItemRepository baseItemRepository, BaseItemSearchRepository baseItemSearchRepository) {
        this.baseItemRepository = baseItemRepository;
        this.baseItemSearchRepository = baseItemSearchRepository;
    }

    /**
     * Save a baseItem.
     *
     * @param baseItem the entity to save
     * @return the persisted entity
     */
    public BaseItem save(BaseItem baseItem) {
        log.debug("Request to save BaseItem : {}", baseItem);
        BaseItem result = baseItemRepository.save(baseItem);
        baseItemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the baseItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<BaseItem> findAll(Pageable pageable) {
        log.debug("Request to get all BaseItems");
        return baseItemRepository.findAll(pageable);
    }


    /**
     * Get one baseItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<BaseItem> findOne(String id) {
        log.debug("Request to get BaseItem : {}", id);
        return baseItemRepository.findById(id);
    }

    /**
     * Delete the baseItem by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete BaseItem : {}", id);
        baseItemRepository.deleteById(id);
        baseItemSearchRepository.deleteById(id);
    }

    /**
     * Search for the baseItem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<BaseItem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BaseItems for query {}", query);
        return baseItemSearchRepository.search(queryStringQuery(query), pageable);    }
}
