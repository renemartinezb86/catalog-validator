package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.TaxObject;
import com.ericsson.devops.validator.service.TaxObjectService;
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
 * REST controller for managing TaxObject.
 */
@RestController
@RequestMapping("/api")
public class TaxObjectResource {

    private final Logger log = LoggerFactory.getLogger(TaxObjectResource.class);

    private static final String ENTITY_NAME = "taxObject";

    private final TaxObjectService taxObjectService;

    public TaxObjectResource(TaxObjectService taxObjectService) {
        this.taxObjectService = taxObjectService;
    }

    /**
     * POST  /tax-objects : Create a new taxObject.
     *
     * @param taxObject the taxObject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxObject, or with status 400 (Bad Request) if the taxObject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tax-objects")
    @Timed
    public ResponseEntity<TaxObject> createTaxObject(@RequestBody TaxObject taxObject) throws URISyntaxException {
        log.debug("REST request to save TaxObject : {}", taxObject);
        if (taxObject.getId() != null) {
            throw new BadRequestAlertException("A new taxObject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxObject result = taxObjectService.save(taxObject);
        return ResponseEntity.created(new URI("/api/tax-objects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-objects : Updates an existing taxObject.
     *
     * @param taxObject the taxObject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxObject,
     * or with status 400 (Bad Request) if the taxObject is not valid,
     * or with status 500 (Internal Server Error) if the taxObject couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tax-objects")
    @Timed
    public ResponseEntity<TaxObject> updateTaxObject(@RequestBody TaxObject taxObject) throws URISyntaxException {
        log.debug("REST request to update TaxObject : {}", taxObject);
        if (taxObject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxObject result = taxObjectService.save(taxObject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taxObject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-objects : get all the taxObjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxObjects in body
     */
    @GetMapping("/tax-objects")
    @Timed
    public ResponseEntity<List<TaxObject>> getAllTaxObjects(Pageable pageable) {
        log.debug("REST request to get a page of TaxObjects");
        Page<TaxObject> page = taxObjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-objects");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tax-objects/:id : get the "id" taxObject.
     *
     * @param id the id of the taxObject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxObject, or with status 404 (Not Found)
     */
    @GetMapping("/tax-objects/{id}")
    @Timed
    public ResponseEntity<TaxObject> getTaxObject(@PathVariable String id) {
        log.debug("REST request to get TaxObject : {}", id);
        Optional<TaxObject> taxObject = taxObjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxObject);
    }

    /**
     * DELETE  /tax-objects/:id : delete the "id" taxObject.
     *
     * @param id the id of the taxObject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tax-objects/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaxObject(@PathVariable String id) {
        log.debug("REST request to delete TaxObject : {}", id);
        taxObjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/tax-objects?query=:query : search for the taxObject corresponding
     * to the query.
     *
     * @param query the query of the taxObject search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tax-objects")
    @Timed
    public ResponseEntity<List<TaxObject>> searchTaxObjects(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaxObjects for query {}", query);
        Page<TaxObject> page = taxObjectService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tax-objects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
