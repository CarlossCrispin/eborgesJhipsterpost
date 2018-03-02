package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.Tesis;

import org.jhipster.cinvestav.repository.TesisRepository;
import org.jhipster.cinvestav.repository.search.TesisSearchRepository;
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
 * REST controller for managing Tesis.
 */
@RestController
@RequestMapping("/api")
public class TesisResource {

    private final Logger log = LoggerFactory.getLogger(TesisResource.class);

    private static final String ENTITY_NAME = "tesis";

    private final TesisRepository tesisRepository;

    private final TesisSearchRepository tesisSearchRepository;

    public TesisResource(TesisRepository tesisRepository, TesisSearchRepository tesisSearchRepository) {
        this.tesisRepository = tesisRepository;
        this.tesisSearchRepository = tesisSearchRepository;
    }

    /**
     * POST  /teses : Create a new tesis.
     *
     * @param tesis the tesis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tesis, or with status 400 (Bad Request) if the tesis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teses")
    @Timed
    public ResponseEntity<Tesis> createTesis(@RequestBody Tesis tesis) throws URISyntaxException {
        log.debug("REST request to save Tesis : {}", tesis);
        if (tesis.getId() != null) {
            throw new BadRequestAlertException("A new tesis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tesis result = tesisRepository.save(tesis);
        tesisSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/teses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teses : Updates an existing tesis.
     *
     * @param tesis the tesis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tesis,
     * or with status 400 (Bad Request) if the tesis is not valid,
     * or with status 500 (Internal Server Error) if the tesis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teses")
    @Timed
    public ResponseEntity<Tesis> updateTesis(@RequestBody Tesis tesis) throws URISyntaxException {
        log.debug("REST request to update Tesis : {}", tesis);
        if (tesis.getId() == null) {
            return createTesis(tesis);
        }
        Tesis result = tesisRepository.save(tesis);
        tesisSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tesis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teses : get all the teses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of teses in body
     */
    @GetMapping("/teses")
    @Timed
    public List<Tesis> getAllTeses() {
        log.debug("REST request to get all Teses");
        return tesisRepository.findAll();
        }

    /**
     * GET  /teses/:id : get the "id" tesis.
     *
     * @param id the id of the tesis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tesis, or with status 404 (Not Found)
     */
    @GetMapping("/teses/{id}")
    @Timed
    public ResponseEntity<Tesis> getTesis(@PathVariable Long id) {
        log.debug("REST request to get Tesis : {}", id);
        Tesis tesis = tesisRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tesis));
    }

    /**
     * DELETE  /teses/:id : delete the "id" tesis.
     *
     * @param id the id of the tesis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTesis(@PathVariable Long id) {
        log.debug("REST request to delete Tesis : {}", id);
        tesisRepository.delete(id);
        tesisSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/teses?query=:query : search for the tesis corresponding
     * to the query.
     *
     * @param query the query of the tesis search
     * @return the result of the search
     */
    @GetMapping("/_search/teses")
    @Timed
    public List<Tesis> searchTeses(@RequestParam String query) {
        log.debug("REST request to search Teses for query {}", query);
        return StreamSupport
            .stream(tesisSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
