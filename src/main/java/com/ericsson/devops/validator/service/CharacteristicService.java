package com.ericsson.devops.validator.service;

import com.ericsson.devops.validator.domain.Characteristic;
import com.ericsson.devops.validator.repository.CharacteristicRepository;
import com.ericsson.devops.validator.repository.search.CharacteristicSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Characteristic.
 */
@Service
public class CharacteristicService {

    private final Logger log = LoggerFactory.getLogger(CharacteristicService.class);

    private final CharacteristicRepository characteristicRepository;

    private final CharacteristicSearchRepository characteristicSearchRepository;

    public CharacteristicService(CharacteristicRepository characteristicRepository, CharacteristicSearchRepository characteristicSearchRepository) {
        this.characteristicRepository = characteristicRepository;
        this.characteristicSearchRepository = characteristicSearchRepository;
    }

    /**
     * Save a characteristic.
     *
     * @param characteristic the entity to save
     * @return the persisted entity
     */
    public Characteristic save(Characteristic characteristic) {
        log.debug("Request to save Characteristic : {}", characteristic);
        Characteristic result = characteristicRepository.save(characteristic);
        characteristicSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the characteristics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Characteristic> findAll(Pageable pageable) {
        log.debug("Request to get all Characteristics");
        return characteristicRepository.findAll(pageable);
    }


    /**
     * Get one characteristic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Characteristic> findOne(String id) {
        log.debug("Request to get Characteristic : {}", id);
        return characteristicRepository.findById(id);
    }

    /**
     * Delete the characteristic by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Characteristic : {}", id);
        characteristicRepository.deleteById(id);
        characteristicSearchRepository.deleteById(id);
    }

    /**
     * Search for the characteristic corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    public Page<Characteristic> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Characteristics for query {}", query);
        return characteristicSearchRepository.search(queryStringQuery(query), pageable);    }
}
