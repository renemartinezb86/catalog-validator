package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.TaxModel;
import com.ericsson.devops.validator.repository.TaxModelRepository;
import com.ericsson.devops.validator.repository.search.TaxModelSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TaxModel.
 */
@Service
public class TaxModelService {

    private final Logger log = LoggerFactory.getLogger(TaxModelService.class);

    private final TaxModelRepository taxModelRepository;

    private final TaxModelSearchRepository taxModelSearchRepository;

    public TaxModelService(TaxModelRepository taxModelRepository, TaxModelSearchRepository taxModelSearchRepository) {
        this.taxModelRepository = taxModelRepository;
        this.taxModelSearchRepository = taxModelSearchRepository;
    }

    /**
     * Save a taxModel.
     *
     * @param taxModel the entity to save
     * @return the persisted entity
     */
    public TaxModel save(TaxModel taxModel) {
        log.debug("Request to save TaxModel : {}", taxModel);
        TaxModel result = taxModelRepository.save(taxModel);
        taxModelSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the taxModels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<TaxModel> findAll(Pageable pageable) {
        log.debug("Request to get all TaxModels");
        return taxModelRepository.findAll(pageable);
    }


    /**
     * Get one taxModel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<TaxModel> findOne(String id) {
        log.debug("Request to get TaxModel : {}", id);
        return taxModelRepository.findById(id);
    }

    /**
     * Delete the taxModel by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete TaxModel : {}", id);
        taxModelRepository.deleteById(id);
        taxModelSearchRepository.deleteById(id);
    }

    /**
     * Search for the taxModel corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<TaxModel> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TaxModels for query {}", query);
        return taxModelSearchRepository.search(queryStringQuery(query), pageable);    }
}
