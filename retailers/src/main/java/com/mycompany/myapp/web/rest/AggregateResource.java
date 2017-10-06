package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Aggregate;

import com.mycompany.myapp.repository.AggregateRepository;
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
 * REST controller for managing Aggregate.
 */
@RestController
@RequestMapping("/api")
public class AggregateResource {

    private final Logger log = LoggerFactory.getLogger(AggregateResource.class);

    private static final String ENTITY_NAME = "aggregate";

    private final AggregateRepository aggregateRepository;

    public AggregateResource(AggregateRepository aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }

    /**
     * POST  /aggregates : Create a new aggregate.
     *
     * @param aggregate the aggregate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aggregate, or with status 400 (Bad Request) if the aggregate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aggregates")
    @Timed
    public ResponseEntity<Aggregate> createAggregate(@RequestBody Aggregate aggregate) throws URISyntaxException {
        log.debug("REST request to save Aggregate : {}", aggregate);
        if (aggregate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new aggregate cannot already have an ID")).body(null);
        }
        Aggregate result = aggregateRepository.save(aggregate);
        return ResponseEntity.created(new URI("/api/aggregates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aggregates : Updates an existing aggregate.
     *
     * @param aggregate the aggregate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aggregate,
     * or with status 400 (Bad Request) if the aggregate is not valid,
     * or with status 500 (Internal Server Error) if the aggregate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aggregates")
    @Timed
    public ResponseEntity<Aggregate> updateAggregate(@RequestBody Aggregate aggregate) throws URISyntaxException {
        log.debug("REST request to update Aggregate : {}", aggregate);
        if (aggregate.getId() == null) {
            return createAggregate(aggregate);
        }
        Aggregate result = aggregateRepository.save(aggregate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aggregate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aggregates : get all the aggregates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aggregates in body
     */
    @GetMapping("/aggregates")
    @Timed
    public List<Aggregate> getAllAggregates() {
        log.debug("REST request to get all Aggregates");
        return aggregateRepository.findAll();
        }

    /**
     * GET  /aggregates/:id : get the "id" aggregate.
     *
     * @param id the id of the aggregate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aggregate, or with status 404 (Not Found)
     */
    @GetMapping("/aggregates/{id}")
    @Timed
    public ResponseEntity<Aggregate> getAggregate(@PathVariable Long id) {
        log.debug("REST request to get Aggregate : {}", id);
        Aggregate aggregate = aggregateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(aggregate));
    }

    /**
     * DELETE  /aggregates/:id : delete the "id" aggregate.
     *
     * @param id the id of the aggregate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aggregates/{id}")
    @Timed
    public ResponseEntity<Void> deleteAggregate(@PathVariable Long id) {
        log.debug("REST request to delete Aggregate : {}", id);
        aggregateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
