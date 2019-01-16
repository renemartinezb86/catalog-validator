package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.TaxModel;
import com.ericsson.devops.validator.service.TaxModelService;
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
 * REST controller for managing TaxModel.
 */
@RestController
@RequestMapping("/api")
public class TaxModelResource {

    private final Logger log = LoggerFactory.getLogger(TaxModelResource.class);

    private static final String ENTITY_NAME = "taxModel";

    private final TaxModelService taxModelService;

    public TaxModelResource(TaxModelService taxModelService) {
        this.taxModelService = taxModelService;
    }

    /**
     * POST  /tax-models : Create a new taxModel.
     *
     * @param taxModel the taxModel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxModel, or with status 400 (Bad Request) if the taxModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tax-models")
    @Timed
    public ResponseEntity<TaxModel> createTaxModel(@RequestBody TaxModel taxModel) throws URISyntaxException {
        log.debug("REST request to save TaxModel : {}", taxModel);
        if (taxModel.getId() != null) {
            throw new BadRequestAlertException("A new taxModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaxModel result = taxModelService.save(taxModel);
        return ResponseEntity.created(new URI("/api/tax-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-models : Updates an existing taxModel.
     *
     * @param taxModel the taxModel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxModel,
     * or with status 400 (Bad Request) if the taxModel is not valid,
     * or with status 500 (Internal Server Error) if the taxModel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tax-models")
    @Timed
    public ResponseEntity<TaxModel> updateTaxModel(@RequestBody TaxModel taxModel) throws URISyntaxException {
        log.debug("REST request to update TaxModel : {}", taxModel);
        if (taxModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaxModel result = taxModelService.save(taxModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taxModel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-models : get all the taxModels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxModels in body
     */
    @GetMapping("/tax-models")
    @Timed
    public ResponseEntity<List<TaxModel>> getAllTaxModels(Pageable pageable) {
        log.debug("REST request to get a page of TaxModels");
        Page<TaxModel> page = taxModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-models");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tax-models/:id : get the "id" taxModel.
     *
     * @param id the id of the taxModel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxModel, or with status 404 (Not Found)
     */
    @GetMapping("/tax-models/{id}")
    @Timed
    public ResponseEntity<TaxModel> getTaxModel(@PathVariable String id) {
        log.debug("REST request to get TaxModel : {}", id);
        Optional<TaxModel> taxModel = taxModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taxModel);
    }

    /**
     * DELETE  /tax-models/:id : delete the "id" taxModel.
     *
     * @param id the id of the taxModel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tax-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaxModel(@PathVariable String id) {
        log.debug("REST request to delete TaxModel : {}", id);
        taxModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/tax-models?query=:query : search for the taxModel corresponding
     * to the query.
     *
     * @param query the query of the taxModel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tax-models")
    @Timed
    public ResponseEntity<List<TaxModel>> searchTaxModels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TaxModels for query {}", query);
        Page<TaxModel> page = taxModelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tax-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
