package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.VisitorCard;

import com.mycompany.myapp.repository.VisitorCardRepository;
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
 * REST controller for managing VisitorCard.
 */
@RestController
@RequestMapping("/api")
public class VisitorCardResource {

    private final Logger log = LoggerFactory.getLogger(VisitorCardResource.class);

    private static final String ENTITY_NAME = "visitorCard";

    private final VisitorCardRepository visitorCardRepository;

    public VisitorCardResource(VisitorCardRepository visitorCardRepository) {
        this.visitorCardRepository = visitorCardRepository;
    }

    /**
     * POST  /visitor-cards : Create a new visitorCard.
     *
     * @param visitorCard the visitorCard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new visitorCard, or with status 400 (Bad Request) if the visitorCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/visitor-cards")
    @Timed
    public ResponseEntity<VisitorCard> createVisitorCard(@Valid @RequestBody VisitorCard visitorCard) throws URISyntaxException {
        log.debug("REST request to save VisitorCard : {}", visitorCard);
        if (visitorCard.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new visitorCard cannot already have an ID")).body(null);
        }
        VisitorCard result = visitorCardRepository.save(visitorCard);
        return ResponseEntity.created(new URI("/api/visitor-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /visitor-cards : Updates an existing visitorCard.
     *
     * @param visitorCard the visitorCard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated visitorCard,
     * or with status 400 (Bad Request) if the visitorCard is not valid,
     * or with status 500 (Internal Server Error) if the visitorCard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/visitor-cards")
    @Timed
    public ResponseEntity<VisitorCard> updateVisitorCard(@Valid @RequestBody VisitorCard visitorCard) throws URISyntaxException {
        log.debug("REST request to update VisitorCard : {}", visitorCard);
        if (visitorCard.getId() == null) {
            return createVisitorCard(visitorCard);
        }
        VisitorCard result = visitorCardRepository.save(visitorCard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, visitorCard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /visitor-cards : get all the visitorCards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of visitorCards in body
     */
    @GetMapping("/visitor-cards")
    @Timed
    public List<VisitorCard> getAllVisitorCards() {
        log.debug("REST request to get all VisitorCards");
        return visitorCardRepository.findAll();
        }

    /**
     * GET  /visitor-cards/:id : get the "id" visitorCard.
     *
     * @param id the id of the visitorCard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the visitorCard, or with status 404 (Not Found)
     */
    @GetMapping("/visitor-cards/{id}")
    @Timed
    public ResponseEntity<VisitorCard> getVisitorCard(@PathVariable Long id) {
        log.debug("REST request to get VisitorCard : {}", id);
        VisitorCard visitorCard = visitorCardRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(visitorCard));
    }

    /**
     * DELETE  /visitor-cards/:id : delete the "id" visitorCard.
     *
     * @param id the id of the visitorCard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/visitor-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteVisitorCard(@PathVariable Long id) {
        log.debug("REST request to delete VisitorCard : {}", id);
        visitorCardRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
