package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VisitorQueries;

import com.mycompany.myapp.repository.VisitorQueriesRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VisitorQueries.
 */
@RestController
@RequestMapping("/api")
public class VisitorQueriesResource {

    private final Logger log = LoggerFactory.getLogger(VisitorQueriesResource.class);

    private static final String ENTITY_NAME = "visitorQueries";

    private final VisitorQueriesRepository visitorQueriesRepository;

    public VisitorQueriesResource(VisitorQueriesRepository visitorQueriesRepository) {
        this.visitorQueriesRepository = visitorQueriesRepository;
    }

    /**
     * POST  /visitor-queries : Create a new visitorQueries.
     *
     * @param visitorQueries the visitorQueries to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitorQueries, or with status 400 (Bad Request) if the visitorQueries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visitor-queries")
    @Timed
    public ResponseEntity<VisitorQueries> createVisitorQueries(@RequestBody VisitorQueries visitorQueries) throws URISyntaxException {
        log.debug("REST request to save VisitorQueries : {}", visitorQueries);
        if (visitorQueries.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new visitorQueries cannot already have an ID")).body(null);
        }
        VisitorQueries result = visitorQueriesRepository.save(visitorQueries);
        return ResponseEntity.created(new URI("/api/visitor-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visitor-queries : Updates an existing visitorQueries.
     *
     * @param visitorQueries the visitorQueries to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visitorQueries,
     * or with status 400 (Bad Request) if the visitorQueries is not valid,
     * or with status 500 (Internal Server Error) if the visitorQueries couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visitor-queries")
    @Timed
    public ResponseEntity<VisitorQueries> updateVisitorQueries(@RequestBody VisitorQueries visitorQueries) throws URISyntaxException {
        log.debug("REST request to update VisitorQueries : {}", visitorQueries);
        if (visitorQueries.getId() == null) {
            return createVisitorQueries(visitorQueries);
        }
        VisitorQueries result = visitorQueriesRepository.save(visitorQueries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitorQueries.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visitor-queries : get all the visitorQueries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of visitorQueries in body
     */
    @GetMapping("/visitor-queries")
    @Timed
    public List<VisitorQueries> getAllVisitorQueries() {
        log.debug("REST request to get all VisitorQueries");
        return visitorQueriesRepository.findAll();
        }

    /**
     * GET  /visitor-queries/:id : get the "id" visitorQueries.
     *
     * @param id the id of the visitorQueries to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitorQueries, or with status 404 (Not Found)
     */
    @GetMapping("/visitor-queries/{id}")
    @Timed
    public ResponseEntity<VisitorQueries> getVisitorQueries(@PathVariable Long id) {
        log.debug("REST request to get VisitorQueries : {}", id);
        VisitorQueries visitorQueries = visitorQueriesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visitorQueries));
    }

    /**
     * DELETE  /visitor-queries/:id : delete the "id" visitorQueries.
     *
     * @param id the id of the visitorQueries to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visitor-queries/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisitorQueries(@PathVariable Long id) {
        log.debug("REST request to delete VisitorQueries : {}", id);
        visitorQueriesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
