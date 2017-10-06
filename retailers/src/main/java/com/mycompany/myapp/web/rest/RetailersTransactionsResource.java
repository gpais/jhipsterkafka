package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.RetailersTransactions;

import com.mycompany.myapp.repository.RetailersTransactionsRepository;
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
 * REST controller for managing RetailersTransactions.
 */
@RestController
@RequestMapping("/api")
public class RetailersTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(RetailersTransactionsResource.class);

    private static final String ENTITY_NAME = "retailersTransactions";

    private final RetailersTransactionsRepository retailersTransactionsRepository;

    public RetailersTransactionsResource(RetailersTransactionsRepository retailersTransactionsRepository) {
        this.retailersTransactionsRepository = retailersTransactionsRepository;
    }

    /**
     * POST  /retailers-transactions : Create a new retailersTransactions.
     *
     * @param retailersTransactions the retailersTransactions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new retailersTransactions, or with status 400 (Bad Request) if the retailersTransactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/retailers-transactions")
    @Timed
    public ResponseEntity<RetailersTransactions> createRetailersTransactions(@RequestBody RetailersTransactions retailersTransactions) throws URISyntaxException {
        log.debug("REST request to save RetailersTransactions : {}", retailersTransactions);
        if (retailersTransactions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new retailersTransactions cannot already have an ID")).body(null);
        }
        RetailersTransactions result = retailersTransactionsRepository.save(retailersTransactions);
        return ResponseEntity.created(new URI("/api/retailers-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /retailers-transactions : Updates an existing retailersTransactions.
     *
     * @param retailersTransactions the retailersTransactions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated retailersTransactions,
     * or with status 400 (Bad Request) if the retailersTransactions is not valid,
     * or with status 500 (Internal Server Error) if the retailersTransactions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/retailers-transactions")
    @Timed
    public ResponseEntity<RetailersTransactions> updateRetailersTransactions(@RequestBody RetailersTransactions retailersTransactions) throws URISyntaxException {
        log.debug("REST request to update RetailersTransactions : {}", retailersTransactions);
        if (retailersTransactions.getId() == null) {
            return createRetailersTransactions(retailersTransactions);
        }
        RetailersTransactions result = retailersTransactionsRepository.save(retailersTransactions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, retailersTransactions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /retailers-transactions : get all the retailersTransactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of retailersTransactions in body
     */
    @GetMapping("/retailers-transactions")
    @Timed
    public ResponseEntity<List<RetailersTransactions>> getAllRetailersTransactions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RetailersTransactions");
        Page<RetailersTransactions> page = retailersTransactionsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/retailers-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /retailers-transactions/:id : get the "id" retailersTransactions.
     *
     * @param id the id of the retailersTransactions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the retailersTransactions, or with status 404 (Not Found)
     */
    @GetMapping("/retailers-transactions/{id}")
    @Timed
    public ResponseEntity<RetailersTransactions> getRetailersTransactions(@PathVariable Long id) {
        log.debug("REST request to get RetailersTransactions : {}", id);
        RetailersTransactions retailersTransactions = retailersTransactionsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(retailersTransactions));
    }

    /**
     * DELETE  /retailers-transactions/:id : delete the "id" retailersTransactions.
     *
     * @param id the id of the retailersTransactions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/retailers-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRetailersTransactions(@PathVariable Long id) {
        log.debug("REST request to delete RetailersTransactions : {}", id);
        retailersTransactionsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
