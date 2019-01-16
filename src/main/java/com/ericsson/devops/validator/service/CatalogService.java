package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.Catalog;
import com.ericsson.devops.validator.repository.CatalogRepository;
import com.ericsson.devops.validator.repository.search.CatalogSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Catalog.
 */
@Service
public class CatalogService {

    private final Logger log = LoggerFactory.getLogger(CatalogService.class);

    private final CatalogRepository catalogRepository;

    private final CatalogSearchRepository catalogSearchRepository;

    public CatalogService(CatalogRepository catalogRepository, CatalogSearchRepository catalogSearchRepository) {
        this.catalogRepository = catalogRepository;
        this.catalogSearchRepository = catalogSearchRepository;
    }

    /**
     * Save a catalog.
     *
     * @param catalog the entity to save
     * @return the persisted entity
     */
    public Catalog save(Catalog catalog) {
        log.debug("Request to save Catalog : {}", catalog);
        Catalog result = catalogRepository.save(catalog);
        catalogSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the catalogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Catalog> findAll(Pageable pageable) {
        log.debug("Request to get all Catalogs");
        return catalogRepository.findAll(pageable);
    }


    /**
     * Get one catalog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Catalog> findOne(String id) {
        log.debug("Request to get Catalog : {}", id);
        return catalogRepository.findById(id);
    }

    /**
     * Delete the catalog by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Catalog : {}", id);
        catalogRepository.deleteById(id);
        catalogSearchRepository.deleteById(id);
    }

    /**
     * Search for the catalog corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Catalog> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Catalogs for query {}", query);
        return catalogSearchRepository.search(queryStringQuery(query), pageable);    }
}
