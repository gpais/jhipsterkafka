package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Commissions;

import com.mycompany.myapp.repository.CommissionsRepository;
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
 * REST controller for managing Commissions.
 */
@RestController
@RequestMapping("/api")
public class CommissionsResource {

    private final Logger log = LoggerFactory.getLogger(CommissionsResource.class);

    private static final String ENTITY_NAME = "commissions";

    private final CommissionsRepository commissionsRepository;

    public CommissionsResource(CommissionsRepository commissionsRepository) {
        this.commissionsRepository = commissionsRepository;
    }

    /**
     * POST  /commissions : Create a new commissions.
     *
     * @param commissions the commissions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commissions, or with status 400 (Bad Request) if the commissions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commissions")
    @Timed
    public ResponseEntity<Commissions> createCommissions(@RequestBody Commissions commissions) throws URISyntaxException {
        log.debug("REST request to save Commissions : {}", commissions);
        if (commissions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commissions cannot already have an ID")).body(null);
        }
        Commissions result = commissionsRepository.save(commissions);
        return ResponseEntity.created(new URI("/api/commissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commissions : Updates an existing commissions.
     *
     * @param commissions the commissions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commissions,
     * or with status 400 (Bad Request) if the commissions is not valid,
     * or with status 500 (Internal Server Error) if the commissions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commissions")
    @Timed
    public ResponseEntity<Commissions> updateCommissions(@RequestBody Commissions commissions) throws URISyntaxException {
        log.debug("REST request to update Commissions : {}", commissions);
        if (commissions.getId() == null) {
            return createCommissions(commissions);
        }
        Commissions result = commissionsRepository.save(commissions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commissions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commissions : get all the commissions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commissions in body
     */
    @GetMapping("/commissions")
    @Timed
    public List<Commissions> getAllCommissions() {
        log.debug("REST request to get all Commissions");
        return commissionsRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /commissions/:id : get the "id" commissions.
     *
     * @param id the id of the commissions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commissions, or with status 404 (Not Found)
     */
    @GetMapping("/commissions/{id}")
    @Timed
    public ResponseEntity<Commissions> getCommissions(@PathVariable Long id) {
        log.debug("REST request to get Commissions : {}", id);
        Commissions commissions = commissionsRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commissions));
    }

    /**
     * DELETE  /commissions/:id : delete the "id" commissions.
     *
     * @param id the id of the commissions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commissions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommissions(@PathVariable Long id) {
        log.debug("REST request to delete Commissions : {}", id);
        commissionsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
