package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.Unidad;

import org.jhipster.cinvestav.repository.UnidadRepository;
import org.jhipster.cinvestav.repository.search.UnidadSearchRepository;
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
 * REST controller for managing Unidad.
 */
@RestController
@RequestMapping("/api")
public class UnidadResource {

    private final Logger log = LoggerFactory.getLogger(UnidadResource.class);

    private static final String ENTITY_NAME = "unidad";

    private final UnidadRepository unidadRepository;

    private final UnidadSearchRepository unidadSearchRepository;

    public UnidadResource(UnidadRepository unidadRepository, UnidadSearchRepository unidadSearchRepository) {
        this.unidadRepository = unidadRepository;
        this.unidadSearchRepository = unidadSearchRepository;
    }

    /**
     * POST  /unidads : Create a new unidad.
     *
     * @param unidad the unidad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unidad, or with status 400 (Bad Request) if the unidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unidads")
    @Timed
    public ResponseEntity<Unidad> createUnidad(@RequestBody Unidad unidad) throws URISyntaxException {
        log.debug("REST request to save Unidad : {}", unidad);
        if (unidad.getId() != null) {
            throw new BadRequestAlertException("A new unidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Unidad result = unidadRepository.save(unidad);
        unidadSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/unidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unidads : Updates an existing unidad.
     *
     * @param unidad the unidad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unidad,
     * or with status 400 (Bad Request) if the unidad is not valid,
     * or with status 500 (Internal Server Error) if the unidad couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unidads")
    @Timed
    public ResponseEntity<Unidad> updateUnidad(@RequestBody Unidad unidad) throws URISyntaxException {
        log.debug("REST request to update Unidad : {}", unidad);
        if (unidad.getId() == null) {
            return createUnidad(unidad);
        }
        Unidad result = unidadRepository.save(unidad);
        unidadSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unidad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unidads : get all the unidads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unidads in body
     */
    @GetMapping("/unidads")
    @Timed
    public List<Unidad> getAllUnidads() {
        log.debug("REST request to get all Unidads");
        return unidadRepository.findAll();
        }

    /**
     * GET  /unidads/:id : get the "id" unidad.
     *
     * @param id the id of the unidad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unidad, or with status 404 (Not Found)
     */
    @GetMapping("/unidads/{id}")
    @Timed
    public ResponseEntity<Unidad> getUnidad(@PathVariable Long id) {
        log.debug("REST request to get Unidad : {}", id);
        Unidad unidad = unidadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(unidad));
    }

    /**
     * DELETE  /unidads/:id : delete the "id" unidad.
     *
     * @param id the id of the unidad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unidads/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnidad(@PathVariable Long id) {
        log.debug("REST request to delete Unidad : {}", id);
        unidadRepository.delete(id);
        unidadSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/unidads?query=:query : search for the unidad corresponding
     * to the query.
     *
     * @param query the query of the unidad search
     * @return the result of the search
     */
    @GetMapping("/_search/unidads")
    @Timed
    public List<Unidad> searchUnidads(@RequestParam String query) {
        log.debug("REST request to search Unidads for query {}", query);
        return StreamSupport
            .stream(unidadSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
