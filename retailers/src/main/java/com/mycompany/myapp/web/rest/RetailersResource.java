package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Retailers;

import com.mycompany.myapp.repository.RetailersRepository;
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
 * REST controller for managing Retailers.
 */
@RestController
@RequestMapping("/api")
public class RetailersResource {

    private final Logger log = LoggerFactory.getLogger(RetailersResource.class);

    private static final String ENTITY_NAME = "retailers";

    private final RetailersRepository retailersRepository;

    public RetailersResource(RetailersRepository retailersRepository) {
        this.retailersRepository = retailersRepository;
    }

    /**
     * POST  /retailers : Create a new retailers.
     *
     * @param retailers the retailers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new retailers, or with status 400 (Bad Request) if the retailers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/retailers")
    @Timed
    public ResponseEntity<Retailers> createRetailers(@Valid @RequestBody Retailers retailers) throws URISyntaxException {
        log.debug("REST request to save Retailers : {}", retailers);
        if (retailers.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new retailers cannot already have an ID")).body(null);
        }
        Retailers result = retailersRepository.save(retailers);
        return ResponseEntity.created(new URI("/api/retailers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /retailers : Updates an existing retailers.
     *
     * @param retailers the retailers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated retailers,
     * or with status 400 (Bad Request) if the retailers is not valid,
     * or with status 500 (Internal Server Error) if the retailers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/retailers")
    @Timed
    public ResponseEntity<Retailers> updateRetailers(@Valid @RequestBody Retailers retailers) throws URISyntaxException {
        log.debug("REST request to update Retailers : {}", retailers);
        if (retailers.getId() == null) {
            return createRetailers(retailers);
        }
        Retailers result = retailersRepository.save(retailers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, retailers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /retailers : get all the retailers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of retailers in body
     */
    @GetMapping("/retailers")
    @Timed
    public List<Retailers> getAllRetailers() {
        log.debug("REST request to get all Retailers");
        return retailersRepository.findAll();
        }

    /**
     * GET  /retailers/:id : get the "id" retailers.
     *
     * @param id the id of the retailers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the retailers, or with status 404 (Not Found)
     */
    @GetMapping("/retailers/{id}")
    @Timed
    public ResponseEntity<Retailers> getRetailers(@PathVariable Long id) {
        log.debug("REST request to get Retailers : {}", id);
        Retailers retailers = retailersRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(retailers));
    }

    /**
     * DELETE  /retailers/:id : delete the "id" retailers.
     *
     * @param id the id of the retailers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/retailers/{id}")
    @Timed
    public ResponseEntity<Void> deleteRetailers(@PathVariable Long id) {
        log.debug("REST request to delete Retailers : {}", id);
        retailersRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
