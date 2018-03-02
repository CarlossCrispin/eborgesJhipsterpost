package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.Sinodal;

import org.jhipster.cinvestav.repository.SinodalRepository;
import org.jhipster.cinvestav.repository.search.SinodalSearchRepository;
import org.jhipster.cinvestav.web.rest.errors.BadRequestAlertException;
import org.jhipster.cinvestav.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Sinodal.
 */
@RestController
@RequestMapping("/api")
public class SinodalResource {

    private final Logger log = LoggerFactory.getLogger(SinodalResource.class);

    private static final String ENTITY_NAME = "sinodal";

    private final SinodalRepository sinodalRepository;

    private final SinodalSearchRepository sinodalSearchRepository;

    public SinodalResource(SinodalRepository sinodalRepository, SinodalSearchRepository sinodalSearchRepository) {
        this.sinodalRepository = sinodalRepository;
        this.sinodalSearchRepository = sinodalSearchRepository;
    }

    /**
     * POST  /sinodals : Create a new sinodal.
     *
     * @param sinodal the sinodal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sinodal, or with status 400 (Bad Request) if the sinodal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sinodals")
    @Timed
    public ResponseEntity<Sinodal> createSinodal(@RequestBody Sinodal sinodal) throws URISyntaxException {
        log.debug("REST request to save Sinodal : {}", sinodal);
        if (sinodal.getId() != null) {
            throw new BadRequestAlertException("A new sinodal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sinodal result = sinodalRepository.save(sinodal);
        sinodalSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sinodals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sinodals : Updates an existing sinodal.
     *
     * @param sinodal the sinodal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sinodal,
     * or with status 400 (Bad Request) if the sinodal is not valid,
     * or with status 500 (Internal Server Error) if the sinodal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sinodals")
    @Timed
    public ResponseEntity<Sinodal> updateSinodal(@RequestBody Sinodal sinodal) throws URISyntaxException {
        log.debug("REST request to update Sinodal : {}", sinodal);
        if (sinodal.getId() == null) {
            return createSinodal(sinodal);
        }
        Sinodal result = sinodalRepository.save(sinodal);
        sinodalSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sinodal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sinodals : get all the sinodals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sinodals in body
     */
    @GetMapping("/sinodals")
    @Timed
    public List<Sinodal> getAllSinodals() {
        log.debug("REST request to get all Sinodals");
        return sinodalRepository.findAll();
        }

    /**
     * GET  /sinodals/:id : get the "id" sinodal.
     *
     * @param id the id of the sinodal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sinodal, or with status 404 (Not Found)
     */
    @GetMapping("/sinodals/{id}")
    @Timed
    public ResponseEntity<Sinodal> getSinodal(@PathVariable Long id) {
        log.debug("REST request to get Sinodal : {}", id);
        Sinodal sinodal = sinodalRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sinodal));
    }

    /**
     * DELETE  /sinodals/:id : delete the "id" sinodal.
     *
     * @param id the id of the sinodal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sinodals/{id}")
    @Timed
    public ResponseEntity<Void> deleteSinodal(@PathVariable Long id) {
        log.debug("REST request to delete Sinodal : {}", id);
        sinodalRepository.delete(id);
        sinodalSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sinodals?query=:query : search for the sinodal corresponding
     * to the query.
     *
     * @param query the query of the sinodal search
     * @return the result of the search
     */
    @GetMapping("/_search/sinodals")
    @Timed
    public List<Sinodal> searchSinodals(@RequestParam String query) {
        log.debug("REST request to search Sinodals for query {}", query);
        return StreamSupport
            .stream(sinodalSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
