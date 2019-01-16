package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.InfoModelAttr;
import com.ericsson.devops.validator.repository.InfoModelAttrRepository;
import com.ericsson.devops.validator.repository.search.InfoModelAttrSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing InfoModelAttr.
 */
@Service
public class InfoModelAttrService {

    private final Logger log = LoggerFactory.getLogger(InfoModelAttrService.class);

    private final InfoModelAttrRepository infoModelAttrRepository;

    private final InfoModelAttrSearchRepository infoModelAttrSearchRepository;

    public InfoModelAttrService(InfoModelAttrRepository infoModelAttrRepository, InfoModelAttrSearchRepository infoModelAttrSearchRepository) {
        this.infoModelAttrRepository = infoModelAttrRepository;
        this.infoModelAttrSearchRepository = infoModelAttrSearchRepository;
    }

    /**
     * Save a infoModelAttr.
     *
     * @param infoModelAttr the entity to save
     * @return the persisted entity
     */
    public InfoModelAttr save(InfoModelAttr infoModelAttr) {
        log.debug("Request to save InfoModelAttr : {}", infoModelAttr);
        InfoModelAttr result = infoModelAttrRepository.save(infoModelAttr);
        infoModelAttrSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the infoModelAttrs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<InfoModelAttr> findAll(Pageable pageable) {
        log.debug("Request to get all InfoModelAttrs");
        return infoModelAttrRepository.findAll(pageable);
    }


    /**
     * Get one infoModelAttr by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<InfoModelAttr> findOne(String id) {
        log.debug("Request to get InfoModelAttr : {}", id);
        return infoModelAttrRepository.findById(id);
    }

    /**
     * Delete the infoModelAttr by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete InfoModelAttr : {}", id);
        infoModelAttrRepository.deleteById(id);
        infoModelAttrSearchRepository.deleteById(id);
    }

    /**
     * Search for the infoModelAttr corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<InfoModelAttr> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InfoModelAttrs for query {}", query);
        return infoModelAttrSearchRepository.search(queryStringQuery(query), pageable);    }
}
