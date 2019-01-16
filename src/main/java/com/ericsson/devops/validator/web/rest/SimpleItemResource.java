package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.SimpleItem;
import com.ericsson.devops.validator.service.SimpleItemService;
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
 * REST controller for managing SimpleItem.
 */
@RestController
@RequestMapping("/api")
public class SimpleItemResource {

    private final Logger log = LoggerFactory.getLogger(SimpleItemResource.class);

    private static final String ENTITY_NAME = "simpleItem";

    private final SimpleItemService simpleItemService;

    public SimpleItemResource(SimpleItemService simpleItemService) {
        this.simpleItemService = simpleItemService;
    }

    /**
     * POST  /simple-items : Create a new simpleItem.
     *
     * @param simpleItem the simpleItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new simpleItem, or with status 400 (Bad Request) if the simpleItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/simple-items")
    @Timed
    public ResponseEntity<SimpleItem> createSimpleItem(@RequestBody SimpleItem simpleItem) throws URISyntaxException {
        log.debug("REST request to save SimpleItem : {}", simpleItem);
        if (simpleItem.getId() != null) {
            throw new BadRequestAlertException("A new simpleItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SimpleItem result = simpleItemService.save(simpleItem);
        return ResponseEntity.created(new URI("/api/simple-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /simple-items : Updates an existing simpleItem.
     *
     * @param simpleItem the simpleItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated simpleItem,
     * or with status 400 (Bad Request) if the simpleItem is not valid,
     * or with status 500 (Internal Server Error) if the simpleItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/simple-items")
    @Timed
    public ResponseEntity<SimpleItem> updateSimpleItem(@RequestBody SimpleItem simpleItem) throws URISyntaxException {
        log.debug("REST request to update SimpleItem : {}", simpleItem);
        if (simpleItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SimpleItem result = simpleItemService.save(simpleItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, simpleItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /simple-items : get all the simpleItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of simpleItems in body
     */
    @GetMapping("/simple-items")
    @Timed
    public ResponseEntity<List<SimpleItem>> getAllSimpleItems(Pageable pageable) {
        log.debug("REST request to get a page of SimpleItems");
        Page<SimpleItem> page = simpleItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/simple-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /simple-items/:id : get the "id" simpleItem.
     *
     * @param id the id of the simpleItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the simpleItem, or with status 404 (Not Found)
     */
    @GetMapping("/simple-items/{id}")
    @Timed
    public ResponseEntity<SimpleItem> getSimpleItem(@PathVariable String id) {
        log.debug("REST request to get SimpleItem : {}", id);
        Optional<SimpleItem> simpleItem = simpleItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(simpleItem);
    }

    /**
     * DELETE  /simple-items/:id : delete the "id" simpleItem.
     *
     * @param id the id of the simpleItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/simple-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteSimpleItem(@PathVariable String id) {
        log.debug("REST request to delete SimpleItem : {}", id);
        simpleItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/simple-items?query=:query : search for the simpleItem corresponding
     * to the query.
     *
     * @param query the query of the simpleItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/simple-items")
    @Timed
    public ResponseEntity<List<SimpleItem>> searchSimpleItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SimpleItems for query {}", query);
        Page<SimpleItem> page = simpleItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/simple-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
