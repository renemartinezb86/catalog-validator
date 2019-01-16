package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.Attribute;
import com.ericsson.devops.validator.service.AttributeService;
import com.ericsson.devops.validator.web.rest.errors.BadRequestAlertException;
import com.ericsson.devops.validator.web.rest.util.HeaderUtil;
import com.ericsson.devops.validator.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Attribute.
 */
@RestController
@RequestMapping("/api")
public class AttributeResource {

    private final Logger log = LoggerFactory.getLogger(AttributeResource.class);

    private static final String ENTITY_NAME = "attribute";

    private final AttributeService attributeService;

    public AttributeResource(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    /**
     * POST  /attributes : Create a new attribute.
     *
     * @param attribute the attribute to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attribute, or with status 400 (Bad Request) if the attribute has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attributes")
    @Timed
    public ResponseEntity<Attribute> createAttribute(@RequestBody Attribute attribute) throws URISyntaxException {
        log.debug("REST request to save Attribute : {}", attribute);
        if (attribute.getId() != null) {
            throw new BadRequestAlertException("A new attribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attribute result = attributeService.save(attribute);
        return ResponseEntity.created(new URI("/api/attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attributes : Updates an existing attribute.
     *
     * @param attribute the attribute to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attribute,
     * or with status 400 (Bad Request) if the attribute is not valid,
     * or with status 500 (Internal Server Error) if the attribute couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attributes")
    @Timed
    public ResponseEntity<Attribute> updateAttribute(@RequestBody Attribute attribute) throws URISyntaxException {
        log.debug("REST request to update Attribute : {}", attribute);
        if (attribute.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Attribute result = attributeService.save(attribute);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attribute.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attributes : get all the attributes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of attributes in body
     */
    @GetMapping("/attributes")
    @Timed
    public ResponseEntity<List<Attribute>> getAllAttributes(Pageable pageable) {
        log.debug("REST request to get a page of Attributes");
        Page<Attribute> page = attributeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attributes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /attributes/:id : get the "id" attribute.
     *
     * @param id the id of the attribute to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attribute, or with status 404 (Not Found)
     */
    @GetMapping("/attributes/{id}")
    @Timed
    public ResponseEntity<Attribute> getAttribute(@PathVariable String id) {
        log.debug("REST request to get Attribute : {}", id);
        Optional<Attribute> attribute = attributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attribute);
    }

    /**
     * DELETE  /attributes/:id : delete the "id" attribute.
     *
     * @param id the id of the attribute to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attributes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttribute(@PathVariable String id) {
        log.debug("REST request to delete Attribute : {}", id);
        attributeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/attributes?query=:query : search for the attribute corresponding
     * to the query.
     *
     * @param query the query of the attribute search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/attributes")
    @Timed
    public ResponseEntity<List<Attribute>> searchAttributes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Attributes for query {}", query);
        Page<Attribute> page = attributeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/attributes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
