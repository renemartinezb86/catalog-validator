package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.IND;
import com.ericsson.devops.validator.repository.INDRepository;
import com.ericsson.devops.validator.repository.search.INDSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IND.
 */
@Service
public class INDService {

    private final Logger log = LoggerFactory.getLogger(INDService.class);

    private final INDRepository iNDRepository;

    private final INDSearchRepository iNDSearchRepository;

    public INDService(INDRepository iNDRepository, INDSearchRepository iNDSearchRepository) {
        this.iNDRepository = iNDRepository;
        this.iNDSearchRepository = iNDSearchRepository;
    }

    /**
     * Save a iND.
     *
     * @param iND the entity to save
     * @return the persisted entity
     */
    public IND save(IND iND) {
        log.debug("Request to save IND : {}", iND);
        IND result = iNDRepository.save(iND);
        iNDSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the iNDS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<IND> findAll(Pageable pageable) {
        log.debug("Request to get all INDS");
        return iNDRepository.findAll(pageable);
    }


    /**
     * Get one iND by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<IND> findOne(String id) {
        log.debug("Request to get IND : {}", id);
        return iNDRepository.findById(id);
    }

    /**
     * Delete the iND by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete IND : {}", id);
        iNDRepository.deleteById(id);
        iNDSearchRepository.deleteById(id);
    }

    /**
     * Search for the iND corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<IND> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of INDS for query {}", query);
        return iNDSearchRepository.search(queryStringQuery(query), pageable);    }
}
