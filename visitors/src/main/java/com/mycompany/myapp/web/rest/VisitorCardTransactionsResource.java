package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VisitorCardTransactions;

import com.mycompany.myapp.repository.VisitorCardTransactionsRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
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

/**
 * REST controller for managing VisitorCardTransactions.
 */
@RestController
@RequestMapping("/api")
public class VisitorCardTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(VisitorCardTransactionsResource.class);

    private static final String ENTITY_NAME = "visitorCardTransactions";

    private final VisitorCardTransactionsRepository visitorCardTransactionsRepository;

    public VisitorCardTransactionsResource(VisitorCardTransactionsRepository visitorCardTransactionsRepository) {
        this.visitorCardTransactionsRepository = visitorCardTransactionsRepository;
    }

    /**
     * POST  /visitor-card-transactions : Create a new visitorCardTransactions.
     *
     * @param visitorCardTransactions the visitorCardTransactions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitorCardTransactions, or with status 400 (Bad Request) if the visitorCardTransactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visitor-card-transactions")
    @Timed
    public ResponseEntity<VisitorCardTransactions> createVisitorCardTransactions(@RequestBody VisitorCardTransactions visitorCardTransactions) throws URISyntaxException {
        log.debug("REST request to save VisitorCardTransactions : {}", visitorCardTransactions);
        if (visitorCardTransactions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new visitorCardTransactions cannot already have an ID")).body(null);
        }
        VisitorCardTransactions result = visitorCardTransactionsRepository.save(visitorCardTransactions);
        return ResponseEntity.created(new URI("/api/visitor-card-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visitor-card-transactions : Updates an existing visitorCardTransactions.
     *
     * @param visitorCardTransactions the visitorCardTransactions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visitorCardTransactions,
     * or with status 400 (Bad Request) if the visitorCardTransactions is not valid,
     * or with status 500 (Internal Server Error) if the visitorCardTransactions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visitor-card-transactions")
    @Timed
    public ResponseEntity<VisitorCardTransactions> updateVisitorCardTransactions(@RequestBody VisitorCardTransactions visitorCardTransactions) throws URISyntaxException {
        log.debug("REST request to update VisitorCardTransactions : {}", visitorCardTransactions);
        if (visitorCardTransactions.getId() == null) {
            return createVisitorCardTransactions(visitorCardTransactions);
        }
        VisitorCardTransactions result = visitorCardTransactionsRepository.save(visitorCardTransactions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitorCardTransactions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visitor-card-transactions : get all the visitorCardTransactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of visitorCardTransactions in body
     */
    @GetMapping("/visitor-card-transactions")
    @Timed
    public ResponseEntity<List<VisitorCardTransactions>> getAllVisitorCardTransactions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of VisitorCardTransactions");
        Page<VisitorCardTransactions> page = visitorCardTransactionsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/visitor-card-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /visitor-card-transactions/:id : get the "id" visitorCardTransactions.
     *
     * @param id the id of the visitorCardTransactions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitorCardTransactions, or with status 404 (Not Found)
     */
    @GetMapping("/visitor-card-transactions/{id}")
    @Timed
    public ResponseEntity<VisitorCardTransactions> getVisitorCardTransactions(@PathVariable Long id) {
        log.debug("REST request to get VisitorCardTransactions : {}", id);
        VisitorCardTransactions visitorCardTransactions = visitorCardTransactionsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visitorCardTransactions));
    }

    /**
     * DELETE  /visitor-card-transactions/:id : delete the "id" visitorCardTransactions.
     *
     * @param id the id of the visitorCardTransactions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visitor-card-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisitorCardTransactions(@PathVariable Long id) {
        log.debug("REST request to delete VisitorCardTransactions : {}", id);
        visitorCardTransactionsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
