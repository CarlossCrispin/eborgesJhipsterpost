package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.TesisDirector;

import org.jhipster.cinvestav.repository.TesisDirectorRepository;
import org.jhipster.cinvestav.repository.search.TesisDirectorSearchRepository;
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
 * REST controller for managing TesisDirector.
 */
@RestController
@RequestMapping("/api")
public class TesisDirectorResource {

    private final Logger log = LoggerFactory.getLogger(TesisDirectorResource.class);

    private static final String ENTITY_NAME = "tesisDirector";

    private final TesisDirectorRepository tesisDirectorRepository;

    private final TesisDirectorSearchRepository tesisDirectorSearchRepository;

    public TesisDirectorResource(TesisDirectorRepository tesisDirectorRepository, TesisDirectorSearchRepository tesisDirectorSearchRepository) {
        this.tesisDirectorRepository = tesisDirectorRepository;
        this.tesisDirectorSearchRepository = tesisDirectorSearchRepository;
    }

    /**
     * POST  /tesis-directors : Create a new tesisDirector.
     *
     * @param tesisDirector the tesisDirector to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tesisDirector, or with status 400 (Bad Request) if the tesisDirector has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tesis-directors")
    @Timed
    public ResponseEntity<TesisDirector> createTesisDirector(@RequestBody TesisDirector tesisDirector) throws URISyntaxException {
        log.debug("REST request to save TesisDirector : {}", tesisDirector);
        if (tesisDirector.getId() != null) {
            throw new BadRequestAlertException("A new tesisDirector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TesisDirector result = tesisDirectorRepository.save(tesisDirector);
        tesisDirectorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tesis-directors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tesis-directors : Updates an existing tesisDirector.
     *
     * @param tesisDirector the tesisDirector to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tesisDirector,
     * or with status 400 (Bad Request) if the tesisDirector is not valid,
     * or with status 500 (Internal Server Error) if the tesisDirector couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tesis-directors")
    @Timed
    public ResponseEntity<TesisDirector> updateTesisDirector(@RequestBody TesisDirector tesisDirector) throws URISyntaxException {
        log.debug("REST request to update TesisDirector : {}", tesisDirector);
        if (tesisDirector.getId() == null) {
            return createTesisDirector(tesisDirector);
        }
        TesisDirector result = tesisDirectorRepository.save(tesisDirector);
        tesisDirectorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tesisDirector.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tesis-directors : get all the tesisDirectors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tesisDirectors in body
     */
    @GetMapping("/tesis-directors")
    @Timed
    public List<TesisDirector> getAllTesisDirectors() {
        log.debug("REST request to get all TesisDirectors");
        return tesisDirectorRepository.findAll();
        }

    /**
     * GET  /tesis-directors/:id : get the "id" tesisDirector.
     *
     * @param id the id of the tesisDirector to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tesisDirector, or with status 404 (Not Found)
     */
    @GetMapping("/tesis-directors/{id}")
    @Timed
    public ResponseEntity<TesisDirector> getTesisDirector(@PathVariable Long id) {
        log.debug("REST request to get TesisDirector : {}", id);
        TesisDirector tesisDirector = tesisDirectorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tesisDirector));
    }

    /**
     * DELETE  /tesis-directors/:id : delete the "id" tesisDirector.
     *
     * @param id the id of the tesisDirector to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tesis-directors/{id}")
    @Timed
    public ResponseEntity<Void> deleteTesisDirector(@PathVariable Long id) {
        log.debug("REST request to delete TesisDirector : {}", id);
        tesisDirectorRepository.delete(id);
        tesisDirectorSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tesis-directors?query=:query : search for the tesisDirector corresponding
     * to the query.
     *
     * @param query the query of the tesisDirector search
     * @return the result of the search
     */
    @GetMapping("/_search/tesis-directors")
    @Timed
    public List<TesisDirector> searchTesisDirectors(@RequestParam String query) {
        log.debug("REST request to search TesisDirectors for query {}", query);
        return StreamSupport
            .stream(tesisDirectorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
