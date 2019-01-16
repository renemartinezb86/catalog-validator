package com.ericsson.devops.validator.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ericsson.devops.validator.domain.InfoModel;
import com.ericsson.devops.validator.service.InfoModelService;
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
 * REST controller for managing InfoModel.
 */
@RestController
@RequestMapping("/api")
public class InfoModelResource {

    private final Logger log = LoggerFactory.getLogger(InfoModelResource.class);

    private static final String ENTITY_NAME = "infoModel";

    private final InfoModelService infoModelService;

    public InfoModelResource(InfoModelService infoModelService) {
        this.infoModelService = infoModelService;
    }

    /**
     * POST  /info-models : Create a new infoModel.
     *
     * @param infoModel the infoModel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new infoModel, or with status 400 (Bad Request) if the infoModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/info-models")
    @Timed
    public ResponseEntity<InfoModel> createInfoModel(@RequestBody InfoModel infoModel) throws URISyntaxException {
        log.debug("REST request to save InfoModel : {}", infoModel);
        if (infoModel.getId() != null) {
            throw new BadRequestAlertException("A new infoModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfoModel result = infoModelService.save(infoModel);
        return ResponseEntity.created(new URI("/api/info-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /info-models : Updates an existing infoModel.
     *
     * @param infoModel the infoModel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated infoModel,
     * or with status 400 (Bad Request) if the infoModel is not valid,
     * or with status 500 (Internal Server Error) if the infoModel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/info-models")
    @Timed
    public ResponseEntity<InfoModel> updateInfoModel(@RequestBody InfoModel infoModel) throws URISyntaxException {
        log.debug("REST request to update InfoModel : {}", infoModel);
        if (infoModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InfoModel result = infoModelService.save(infoModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, infoModel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /info-models : get all the infoModels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of infoModels in body
     */
    @GetMapping("/info-models")
    @Timed
    public ResponseEntity<List<InfoModel>> getAllInfoModels(Pageable pageable) {
        log.debug("REST request to get a page of InfoModels");
        Page<InfoModel> page = infoModelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/info-models");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /info-models/:id : get the "id" infoModel.
     *
     * @param id the id of the infoModel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the infoModel, or with status 404 (Not Found)
     */
    @GetMapping("/info-models/{id}")
    @Timed
    public ResponseEntity<InfoModel> getInfoModel(@PathVariable String id) {
        log.debug("REST request to get InfoModel : {}", id);
        Optional<InfoModel> infoModel = infoModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infoModel);
    }

    /**
     * DELETE  /info-models/:id : delete the "id" infoModel.
     *
     * @param id the id of the infoModel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/info-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteInfoModel(@PathVariable String id) {
        log.debug("REST request to delete InfoModel : {}", id);
        infoModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/info-models?query=:query : search for the infoModel corresponding
     * to the query.
     *
     * @param query the query of the infoModel search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/info-models")
    @Timed
    public ResponseEntity<List<InfoModel>> searchInfoModels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InfoModels for query {}", query);
        Page<InfoModel> page = infoModelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/info-models");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
