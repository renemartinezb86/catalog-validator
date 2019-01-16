package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.BaseItem;
import com.ericsson.devops.validator.service.BaseItemService;
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
 * REST controller for managing BaseItem.
 */
@RestController
@RequestMapping("/api")
public class BaseItemResource {

    private final Logger log = LoggerFactory.getLogger(BaseItemResource.class);

    private static final String ENTITY_NAME = "baseItem";

    private final BaseItemService baseItemService;

    public BaseItemResource(BaseItemService baseItemService) {
        this.baseItemService = baseItemService;
    }

    /**
     * POST  /base-items : Create a new baseItem.
     *
     * @param baseItem the baseItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new baseItem, or with status 400 (Bad Request) if the baseItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/base-items")
    @Timed
    public ResponseEntity<BaseItem> createBaseItem(@RequestBody BaseItem baseItem) throws URISyntaxException {
        log.debug("REST request to save BaseItem : {}", baseItem);
        if (baseItem.getId() != null) {
            throw new BadRequestAlertException("A new baseItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaseItem result = baseItemService.save(baseItem);
        return ResponseEntity.created(new URI("/api/base-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /base-items : Updates an existing baseItem.
     *
     * @param baseItem the baseItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated baseItem,
     * or with status 400 (Bad Request) if the baseItem is not valid,
     * or with status 500 (Internal Server Error) if the baseItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/base-items")
    @Timed
    public ResponseEntity<BaseItem> updateBaseItem(@RequestBody BaseItem baseItem) throws URISyntaxException {
        log.debug("REST request to update BaseItem : {}", baseItem);
        if (baseItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BaseItem result = baseItemService.save(baseItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, baseItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /base-items : get all the baseItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of baseItems in body
     */
    @GetMapping("/base-items")
    @Timed
    public ResponseEntity<List<BaseItem>> getAllBaseItems(Pageable pageable) {
        log.debug("REST request to get a page of BaseItems");
        Page<BaseItem> page = baseItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/base-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /base-items/:id : get the "id" baseItem.
     *
     * @param id the id of the baseItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the baseItem, or with status 404 (Not Found)
     */
    @GetMapping("/base-items/{id}")
    @Timed
    public ResponseEntity<BaseItem> getBaseItem(@PathVariable String id) {
        log.debug("REST request to get BaseItem : {}", id);
        Optional<BaseItem> baseItem = baseItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(baseItem);
    }

    /**
     * DELETE  /base-items/:id : delete the "id" baseItem.
     *
     * @param id the id of the baseItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/base-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteBaseItem(@PathVariable String id) {
        log.debug("REST request to delete BaseItem : {}", id);
        baseItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/base-items?query=:query : search for the baseItem corresponding
     * to the query.
     *
     * @param query the query of the baseItem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/base-items")
    @Timed
    public ResponseEntity<List<BaseItem>> searchBaseItems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BaseItems for query {}", query);
        Page<BaseItem> page = baseItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/base-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
