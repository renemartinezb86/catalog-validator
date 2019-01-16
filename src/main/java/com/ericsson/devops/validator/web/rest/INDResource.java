package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.IND;
import com.ericsson.devops.validator.service.INDService;
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
 * REST controller for managing IND.
 */
@RestController
@RequestMapping("/api")
public class INDResource {

    private final Logger log = LoggerFactory.getLogger(INDResource.class);

    private static final String ENTITY_NAME = "iND";

    private final INDService iNDService;

    public INDResource(INDService iNDService) {
        this.iNDService = iNDService;
    }

    /**
     * POST  /inds : Create a new iND.
     *
     * @param iND the iND to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iND, or with status 400 (Bad Request) if the iND has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inds")
    @Timed
    public ResponseEntity<IND> createIND(@RequestBody IND iND) throws URISyntaxException {
        log.debug("REST request to save IND : {}", iND);
        if (iND.getId() != null) {
            throw new BadRequestAlertException("A new iND cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IND result = iNDService.save(iND);
        return ResponseEntity.created(new URI("/api/inds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inds : Updates an existing iND.
     *
     * @param iND the iND to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iND,
     * or with status 400 (Bad Request) if the iND is not valid,
     * or with status 500 (Internal Server Error) if the iND couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inds")
    @Timed
    public ResponseEntity<IND> updateIND(@RequestBody IND iND) throws URISyntaxException {
        log.debug("REST request to update IND : {}", iND);
        if (iND.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IND result = iNDService.save(iND);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iND.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inds : get all the iNDS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of iNDS in body
     */
    @GetMapping("/inds")
    @Timed
    public ResponseEntity<List<IND>> getAllINDS(Pageable pageable) {
        log.debug("REST request to get a page of INDS");
        Page<IND> page = iNDService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inds");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /inds/:id : get the "id" iND.
     *
     * @param id the id of the iND to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iND, or with status 404 (Not Found)
     */
    @GetMapping("/inds/{id}")
    @Timed
    public ResponseEntity<IND> getIND(@PathVariable String id) {
        log.debug("REST request to get IND : {}", id);
        Optional<IND> iND = iNDService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iND);
    }

    /**
     * DELETE  /inds/:id : delete the "id" iND.
     *
     * @param id the id of the iND to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inds/{id}")
    @Timed
    public ResponseEntity<Void> deleteIND(@PathVariable String id) {
        log.debug("REST request to delete IND : {}", id);
        iNDService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/inds?query=:query : search for the iND corresponding
     * to the query.
     *
     * @param query the query of the iND search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/inds")
    @Timed
    public ResponseEntity<List<IND>> searchINDS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of INDS for query {}", query);
        Page<IND> page = iNDService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/inds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
