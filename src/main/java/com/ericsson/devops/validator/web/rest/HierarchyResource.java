package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.Hierarchy;
import com.ericsson.devops.validator.service.HierarchyService;
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
 * REST controller for managing Hierarchy.
 */
@RestController
@RequestMapping("/api")
public class HierarchyResource {

    private final Logger log = LoggerFactory.getLogger(HierarchyResource.class);

    private static final String ENTITY_NAME = "hierarchy";

    private final HierarchyService hierarchyService;

    public HierarchyResource(HierarchyService hierarchyService) {
        this.hierarchyService = hierarchyService;
    }

    /**
     * POST  /hierarchies : Create a new hierarchy.
     *
     * @param hierarchy the hierarchy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hierarchy, or with status 400 (Bad Request) if the hierarchy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hierarchies")
    @Timed
    public ResponseEntity<Hierarchy> createHierarchy(@RequestBody Hierarchy hierarchy) throws URISyntaxException {
        log.debug("REST request to save Hierarchy : {}", hierarchy);
        if (hierarchy.getId() != null) {
            throw new BadRequestAlertException("A new hierarchy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hierarchy result = hierarchyService.save(hierarchy);
        return ResponseEntity.created(new URI("/api/hierarchies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hierarchies : Updates an existing hierarchy.
     *
     * @param hierarchy the hierarchy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hierarchy,
     * or with status 400 (Bad Request) if the hierarchy is not valid,
     * or with status 500 (Internal Server Error) if the hierarchy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hierarchies")
    @Timed
    public ResponseEntity<Hierarchy> updateHierarchy(@RequestBody Hierarchy hierarchy) throws URISyntaxException {
        log.debug("REST request to update Hierarchy : {}", hierarchy);
        if (hierarchy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hierarchy result = hierarchyService.save(hierarchy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hierarchy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hierarchies : get all the hierarchies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hierarchies in body
     */
    @GetMapping("/hierarchies")
    @Timed
    public ResponseEntity<List<Hierarchy>> getAllHierarchies(Pageable pageable) {
        log.debug("REST request to get a page of Hierarchies");
        Page<Hierarchy> page = hierarchyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hierarchies");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /hierarchies/:id : get the "id" hierarchy.
     *
     * @param id the id of the hierarchy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hierarchy, or with status 404 (Not Found)
     */
    @GetMapping("/hierarchies/{id}")
    @Timed
    public ResponseEntity<Hierarchy> getHierarchy(@PathVariable String id) {
        log.debug("REST request to get Hierarchy : {}", id);
        Optional<Hierarchy> hierarchy = hierarchyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hierarchy);
    }

    /**
     * DELETE  /hierarchies/:id : delete the "id" hierarchy.
     *
     * @param id the id of the hierarchy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hierarchies/{id}")
    @Timed
    public ResponseEntity<Void> deleteHierarchy(@PathVariable String id) {
        log.debug("REST request to delete Hierarchy : {}", id);
        hierarchyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/hierarchies?query=:query : search for the hierarchy corresponding
     * to the query.
     *
     * @param query the query of the hierarchy search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/hierarchies")
    @Timed
    public ResponseEntity<List<Hierarchy>> searchHierarchies(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Hierarchies for query {}", query);
        Page<Hierarchy> page = hierarchyService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/hierarchies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
