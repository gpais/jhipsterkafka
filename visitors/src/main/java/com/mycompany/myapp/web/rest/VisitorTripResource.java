package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VisitorTrip;

import com.mycompany.myapp.repository.VisitorTripRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VisitorTrip.
 */
@RestController
@RequestMapping("/api")
public class VisitorTripResource {

    private final Logger log = LoggerFactory.getLogger(VisitorTripResource.class);

    private static final String ENTITY_NAME = "visitorTrip";

    private final VisitorTripRepository visitorTripRepository;

    public VisitorTripResource(VisitorTripRepository visitorTripRepository) {
        this.visitorTripRepository = visitorTripRepository;
    }

    /**
     * POST  /visitor-trips : Create a new visitorTrip.
     *
     * @param visitorTrip the visitorTrip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitorTrip, or with status 400 (Bad Request) if the visitorTrip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visitor-trips")
    @Timed
    public ResponseEntity<VisitorTrip> createVisitorTrip(@Valid @RequestBody VisitorTrip visitorTrip) throws URISyntaxException {
        log.debug("REST request to save VisitorTrip : {}", visitorTrip);
        if (visitorTrip.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new visitorTrip cannot already have an ID")).body(null);
        }
        VisitorTrip result = visitorTripRepository.save(visitorTrip);
        return ResponseEntity.created(new URI("/api/visitor-trips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visitor-trips : Updates an existing visitorTrip.
     *
     * @param visitorTrip the visitorTrip to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visitorTrip,
     * or with status 400 (Bad Request) if the visitorTrip is not valid,
     * or with status 500 (Internal Server Error) if the visitorTrip couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visitor-trips")
    @Timed
    public ResponseEntity<VisitorTrip> updateVisitorTrip(@Valid @RequestBody VisitorTrip visitorTrip) throws URISyntaxException {
        log.debug("REST request to update VisitorTrip : {}", visitorTrip);
        if (visitorTrip.getId() == null) {
            return createVisitorTrip(visitorTrip);
        }
        VisitorTrip result = visitorTripRepository.save(visitorTrip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitorTrip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visitor-trips : get all the visitorTrips.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of visitorTrips in body
     */
    @GetMapping("/visitor-trips")
    @Timed
    public List<VisitorTrip> getAllVisitorTrips() {
        log.debug("REST request to get all VisitorTrips");
        return visitorTripRepository.findAll();
        }

    /**
     * GET  /visitor-trips/:id : get the "id" visitorTrip.
     *
     * @param id the id of the visitorTrip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitorTrip, or with status 404 (Not Found)
     */
    @GetMapping("/visitor-trips/{id}")
    @Timed
    public ResponseEntity<VisitorTrip> getVisitorTrip(@PathVariable Long id) {
        log.debug("REST request to get VisitorTrip : {}", id);
        VisitorTrip visitorTrip = visitorTripRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visitorTrip));
    }

    /**
     * DELETE  /visitor-trips/:id : delete the "id" visitorTrip.
     *
     * @param id the id of the visitorTrip to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visitor-trips/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisitorTrip(@PathVariable Long id) {
        log.debug("REST request to delete VisitorTrip : {}", id);
        visitorTripRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
