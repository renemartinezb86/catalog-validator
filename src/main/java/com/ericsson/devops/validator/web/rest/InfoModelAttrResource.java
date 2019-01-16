package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.InfoModelAttr;
import com.ericsson.devops.validator.service.InfoModelAttrService;
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
 * REST controller for managing InfoModelAttr.
 */
@RestController
@RequestMapping("/api")
public class InfoModelAttrResource {

    private final Logger log = LoggerFactory.getLogger(InfoModelAttrResource.class);

    private static final String ENTITY_NAME = "infoModelAttr";

    private final InfoModelAttrService infoModelAttrService;

    public InfoModelAttrResource(InfoModelAttrService infoModelAttrService) {
        this.infoModelAttrService = infoModelAttrService;
    }

    /**
     * POST  /info-model-attrs : Create a new infoModelAttr.
     *
     * @param infoModelAttr the infoModelAttr to create
     * @return the ResponseEntity with status 201 (Created) and with body the new infoModelAttr, or with status 400 (Bad Request) if the infoModelAttr has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/info-model-attrs")
    @Timed
    public ResponseEntity<InfoModelAttr> createInfoModelAttr(@RequestBody InfoModelAttr infoModelAttr) throws URISyntaxException {
        log.debug("REST request to save InfoModelAttr : {}", infoModelAttr);
        if (infoModelAttr.getId() != null) {
            throw new BadRequestAlertException("A new infoModelAttr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoModelAttr result = infoModelAttrService.save(infoModelAttr);
        return ResponseEntity.created(new URI("/api/info-model-attrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /info-model-attrs : Updates an existing infoModelAttr.
     *
     * @param infoModelAttr the infoModelAttr to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated infoModelAttr,
     * or with status 400 (Bad Request) if the infoModelAttr is not valid,
     * or with status 500 (Internal Server Error) if the infoModelAttr couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/info-model-attrs")
    @Timed
    public ResponseEntity<InfoModelAttr> updateInfoModelAttr(@RequestBody InfoModelAttr infoModelAttr) throws URISyntaxException {
        log.debug("REST request to update InfoModelAttr : {}", infoModelAttr);
        if (infoModelAttr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InfoModelAttr result = infoModelAttrService.save(infoModelAttr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, infoModelAttr.getId().toString()))
            .body(result);
    }

    /**
     * GET  /info-model-attrs : get all the infoModelAttrs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of infoModelAttrs in body
     */
    @GetMapping("/info-model-attrs")
    @Timed
    public ResponseEntity<List<InfoModelAttr>> getAllInfoModelAttrs(Pageable pageable) {
        log.debug("REST request to get a page of InfoModelAttrs");
        Page<InfoModelAttr> page = infoModelAttrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/info-model-attrs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /info-model-attrs/:id : get the "id" infoModelAttr.
     *
     * @param id the id of the infoModelAttr to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the infoModelAttr, or with status 404 (Not Found)
     */
    @GetMapping("/info-model-attrs/{id}")
    @Timed
    public ResponseEntity<InfoModelAttr> getInfoModelAttr(@PathVariable String id) {
        log.debug("REST request to get InfoModelAttr : {}", id);
        Optional<InfoModelAttr> infoModelAttr = infoModelAttrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infoModelAttr);
    }

    /**
     * DELETE  /info-model-attrs/:id : delete the "id" infoModelAttr.
     *
     * @param id the id of the infoModelAttr to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/info-model-attrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteInfoModelAttr(@PathVariable String id) {
        log.debug("REST request to delete InfoModelAttr : {}", id);
        infoModelAttrService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/info-model-attrs?query=:query : search for the infoModelAttr corresponding
     * to the query.
     *
     * @param query the query of the infoModelAttr search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/info-model-attrs")
    @Timed
    public ResponseEntity<List<InfoModelAttr>> searchInfoModelAttrs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InfoModelAttrs for query {}", query);
        Page<InfoModelAttr> page = infoModelAttrService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/info-model-attrs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
