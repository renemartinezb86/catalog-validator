package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.InfoModel;
import com.ericsson.devops.validator.repository.InfoModelRepository;
import com.ericsson.devops.validator.repository.search.InfoModelSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing InfoModel.
 */
@Service
public class InfoModelService {

    private final Logger log = LoggerFactory.getLogger(InfoModelService.class);

    private final InfoModelRepository infoModelRepository;

    private final InfoModelSearchRepository infoModelSearchRepository;

    public InfoModelService(InfoModelRepository infoModelRepository, InfoModelSearchRepository infoModelSearchRepository) {
        this.infoModelRepository = infoModelRepository;
        this.infoModelSearchRepository = infoModelSearchRepository;
    }

    /**
     * Save a infoModel.
     *
     * @param infoModel the entity to save
     * @return the persisted entity
     */
    public InfoModel save(InfoModel infoModel) {
        log.debug("Request to save InfoModel : {}", infoModel);
        InfoModel result = infoModelRepository.save(infoModel);
        infoModelSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the infoModels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<InfoModel> findAll(Pageable pageable) {
        log.debug("Request to get all InfoModels");
        return infoModelRepository.findAll(pageable);
    }


    /**
     * Get one infoModel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<InfoModel> findOne(String id) {
        log.debug("Request to get InfoModel : {}", id);
        return infoModelRepository.findById(id);
    }

    /**
     * Delete the infoModel by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete InfoModel : {}", id);
        infoModelRepository.deleteById(id);
        infoModelSearchRepository.deleteById(id);
    }

    /**
     * Search for the infoModel corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<InfoModel> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InfoModels for query {}", query);
        return infoModelSearchRepository.search(queryStringQuery(query), pageable);    }
}
