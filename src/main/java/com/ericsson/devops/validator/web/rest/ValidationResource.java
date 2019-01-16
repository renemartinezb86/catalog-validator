package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.Validation;
import com.ericsson.devops.validator.service.ValidationService;
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
 * REST controller for managing Validation.
 */
@RestController
@RequestMapping("/api")
public class ValidationResource {

    private final Logger log = LoggerFactory.getLogger(ValidationResource.class);

    private static final String ENTITY_NAME = "validation";

    private final ValidationService validationService;

    public ValidationResource(ValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * POST  /validations : Create a new validation.
     *
     * @param validation the validation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new validation, or with status 400 (Bad Request) if the validation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/validations")
    @Timed
    public ResponseEntity<Validation> createValidation(@RequestBody Validation validation) throws URISyntaxException {
        log.debug("REST request to save Validation : {}", validation);
        if (validation.getId() != null) {
            throw new BadRequestAlertException("A new validation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Validation result = validationService.save(validation);
        return ResponseEntity.created(new URI("/api/validations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /validations : Updates an existing validation.
     *
     * @param validation the validation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated validation,
     * or with status 400 (Bad Request) if the validation is not valid,
     * or with status 500 (Internal Server Error) if the validation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/validations")
    @Timed
    public ResponseEntity<Validation> updateValidation(@RequestBody Validation validation) throws URISyntaxException {
        log.debug("REST request to update Validation : {}", validation);
        if (validation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Validation result = validationService.save(validation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, validation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /validations : get all the validations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of validations in body
     */
    @GetMapping("/validations")
    @Timed
    public ResponseEntity<List<Validation>> getAllValidations(Pageable pageable) {
        log.debug("REST request to get a page of Validations");
        Page<Validation> page = validationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/validations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /validations/:id : get the "id" validation.
     *
     * @param id the id of the validation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the validation, or with status 404 (Not Found)
     */
    @GetMapping("/validations/{id}")
    @Timed
    public ResponseEntity<Validation> getValidation(@PathVariable String id) {
        log.debug("REST request to get Validation : {}", id);
        Optional<Validation> validation = validationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(validation);
    }

    /**
     * DELETE  /validations/:id : delete the "id" validation.
     *
     * @param id the id of the validation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/validations/{id}")
    @Timed
    public ResponseEntity<Void> deleteValidation(@PathVariable String id) {
        log.debug("REST request to delete Validation : {}", id);
        validationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/validations?query=:query : search for the validation corresponding
     * to the query.
     *
     * @param query the query of the validation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/validations")
    @Timed
    public ResponseEntity<List<Validation>> searchValidations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Validations for query {}", query);
        Page<Validation> page = validationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/validations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
