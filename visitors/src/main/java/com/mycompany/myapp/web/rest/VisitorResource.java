package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Visitor;

import com.mycompany.myapp.repository.VisitorRepository;
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
 * REST controller for managing Visitor.
 */
@RestController
@RequestMapping("/api")
public class VisitorResource {

    private final Logger log = LoggerFactory.getLogger(VisitorResource.class);

    private static final String ENTITY_NAME = "visitor";

    private final VisitorRepository visitorRepository;

    public VisitorResource(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    /**
     * POST  /visitors : Create a new visitor.
     *
     * @param visitor the visitor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitor, or with status 400 (Bad Request) if the visitor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visitors")
    @Timed
    public ResponseEntity<Visitor> createVisitor(@Valid @RequestBody Visitor visitor) throws URISyntaxException {
        log.debug("REST request to save Visitor : {}", visitor);
        if (visitor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new visitor cannot already have an ID")).body(null);
        }
        Visitor result = visitorRepository.save(visitor);
        return ResponseEntity.created(new URI("/api/visitors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visitors : Updates an existing visitor.
     *
     * @param visitor the visitor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visitor,
     * or with status 400 (Bad Request) if the visitor is not valid,
     * or with status 500 (Internal Server Error) if the visitor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visitors")
    @Timed
    public ResponseEntity<Visitor> updateVisitor(@Valid @RequestBody Visitor visitor) throws URISyntaxException {
        log.debug("REST request to update Visitor : {}", visitor);
        if (visitor.getId() == null) {
            return createVisitor(visitor);
        }
        Visitor result = visitorRepository.save(visitor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visitors : get all the visitors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of visitors in body
     */
    @GetMapping("/visitors")
    @Timed
    public List<Visitor> getAllVisitors() {
        log.debug("REST request to get all Visitors");
        return visitorRepository.findAll();
        }

    /**
     * GET  /visitors/:id : get the "id" visitor.
     *
     * @param id the id of the visitor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitor, or with status 404 (Not Found)
     */
    @GetMapping("/visitors/{id}")
    @Timed
    public ResponseEntity<Visitor> getVisitor(@PathVariable Long id) {
        log.debug("REST request to get Visitor : {}", id);
        Visitor visitor = visitorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visitor));
    }

    /**
     * DELETE  /visitors/:id : delete the "id" visitor.
     *
     * @param id the id of the visitor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visitors/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisitor(@PathVariable Long id) {
        log.debug("REST request to delete Visitor : {}", id);
        visitorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
