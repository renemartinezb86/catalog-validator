package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.Catalog;
import com.ericsson.devops.validator.service.CatalogService;
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
 * REST controller for managing Catalog.
 */
@RestController
@RequestMapping("/api")
public class CatalogResource {

    private final Logger log = LoggerFactory.getLogger(CatalogResource.class);

    private static final String ENTITY_NAME = "catalog";

    private final CatalogService catalogService;

    public CatalogResource(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * POST  /catalogs : Create a new catalog.
     *
     * @param catalog the catalog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new catalog, or with status 400 (Bad Request) if the catalog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/catalogs")
    @Timed
    public ResponseEntity<Catalog> createCatalog(@RequestBody Catalog catalog) throws URISyntaxException {
        log.debug("REST request to save Catalog : {}", catalog);
        if (catalog.getId() != null) {
            throw new BadRequestAlertException("A new catalog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Catalog result = catalogService.save(catalog);
        return ResponseEntity.created(new URI("/api/catalogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /catalogs : Updates an existing catalog.
     *
     * @param catalog the catalog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated catalog,
     * or with status 400 (Bad Request) if the catalog is not valid,
     * or with status 500 (Internal Server Error) if the catalog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/catalogs")
    @Timed
    public ResponseEntity<Catalog> updateCatalog(@RequestBody Catalog catalog) throws URISyntaxException {
        log.debug("REST request to update Catalog : {}", catalog);
        if (catalog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Catalog result = catalogService.save(catalog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, catalog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /catalogs : get all the catalogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of catalogs in body
     */
    @GetMapping("/catalogs")
    @Timed
    public ResponseEntity<List<Catalog>> getAllCatalogs(Pageable pageable) {
        log.debug("REST request to get a page of Catalogs");
        Page<Catalog> page = catalogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/catalogs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /catalogs/:id : get the "id" catalog.
     *
     * @param id the id of the catalog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the catalog, or with status 404 (Not Found)
     */
    @GetMapping("/catalogs/{id}")
    @Timed
    public ResponseEntity<Catalog> getCatalog(@PathVariable String id) {
        log.debug("REST request to get Catalog : {}", id);
        Optional<Catalog> catalog = catalogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catalog);
    }

    /**
     * DELETE  /catalogs/:id : delete the "id" catalog.
     *
     * @param id the id of the catalog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/catalogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCatalog(@PathVariable String id) {
        log.debug("REST request to delete Catalog : {}", id);
        catalogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/catalogs?query=:query : search for the catalog corresponding
     * to the query.
     *
     * @param query the query of the catalog search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/catalogs")
    @Timed
    public ResponseEntity<List<Catalog>> searchCatalogs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Catalogs for query {}", query);
        Page<Catalog> page = catalogService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/catalogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
