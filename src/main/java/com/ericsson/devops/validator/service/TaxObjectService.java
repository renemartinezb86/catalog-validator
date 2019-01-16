package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.TaxObject;
import com.ericsson.devops.validator.repository.TaxObjectRepository;
import com.ericsson.devops.validator.repository.search.TaxObjectSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TaxObject.
 */
@Service
public class TaxObjectService {

    private final Logger log = LoggerFactory.getLogger(TaxObjectService.class);

    private final TaxObjectRepository taxObjectRepository;

    private final TaxObjectSearchRepository taxObjectSearchRepository;

    public TaxObjectService(TaxObjectRepository taxObjectRepository, TaxObjectSearchRepository taxObjectSearchRepository) {
        this.taxObjectRepository = taxObjectRepository;
        this.taxObjectSearchRepository = taxObjectSearchRepository;
    }

    /**
     * Save a taxObject.
     *
     * @param taxObject the entity to save
     * @return the persisted entity
     */
    public TaxObject save(TaxObject taxObject) {
        log.debug("Request to save TaxObject : {}", taxObject);
        TaxObject result = taxObjectRepository.save(taxObject);
        taxObjectSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the taxObjects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<TaxObject> findAll(Pageable pageable) {
        log.debug("Request to get all TaxObjects");
        return taxObjectRepository.findAll(pageable);
    }


    /**
     * Get one taxObject by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<TaxObject> findOne(String id) {
        log.debug("Request to get TaxObject : {}", id);
        return taxObjectRepository.findById(id);
    }

    /**
     * Delete the taxObject by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete TaxObject : {}", id);
        taxObjectRepository.deleteById(id);
        taxObjectSearchRepository.deleteById(id);
    }

    /**
     * Search for the taxObject corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<TaxObject> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaxObjects for query {}", query);
        return taxObjectSearchRepository.search(queryStringQuery(query), pageable);    }
}
