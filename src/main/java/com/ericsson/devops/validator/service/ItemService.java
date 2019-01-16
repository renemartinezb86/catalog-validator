package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.Item;
import com.ericsson.devops.validator.repository.ItemRepository;
import com.ericsson.devops.validator.repository.search.ItemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Item.
 */
@Service
public class ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    private final ItemSearchRepository itemSearchRepository;

    public ItemService(ItemRepository itemRepository, ItemSearchRepository itemSearchRepository) {
        this.itemRepository = itemRepository;
        this.itemSearchRepository = itemSearchRepository;
    }

    /**
     * Save a item.
     *
     * @param item the entity to save
     * @return the persisted entity
     */
    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        Item result = itemRepository.save(item);
        itemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the items.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Item> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        return itemRepository.findAll(pageable);
    }


    /**
     * Get one item by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Item> findOne(String id) {
        log.debug("Request to get Item : {}", id);
        return itemRepository.findById(id);
    }

    /**
     * Delete the item by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
        itemSearchRepository.deleteById(id);
    }

    /**
     * Search for the item corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Item> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Items for query {}", query);
        return itemSearchRepository.search(queryStringQuery(query), pageable);    }
}
