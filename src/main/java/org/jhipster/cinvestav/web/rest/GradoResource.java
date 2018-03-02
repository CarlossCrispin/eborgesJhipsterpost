package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.Grado;

import org.jhipster.cinvestav.repository.GradoRepository;
import org.jhipster.cinvestav.repository.search.GradoSearchRepository;
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
 * REST controller for managing Grado.
 */
@RestController
@RequestMapping("/api")
public class GradoResource {

    private final Logger log = LoggerFactory.getLogger(GradoResource.class);

    private static final String ENTITY_NAME = "grado";

    private final GradoRepository gradoRepository;

    private final GradoSearchRepository gradoSearchRepository;

    public GradoResource(GradoRepository gradoRepository, GradoSearchRepository gradoSearchRepository) {
        this.gradoRepository = gradoRepository;
        this.gradoSearchRepository = gradoSearchRepository;
    }

    /**
     * POST  /grados : Create a new grado.
     *
     * @param grado the grado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grado, or with status 400 (Bad Request) if the grado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grados")
    @Timed
    public ResponseEntity<Grado> createGrado(@RequestBody Grado grado) throws URISyntaxException {
        log.debug("REST request to save Grado : {}", grado);
        if (grado.getId() != null) {
            throw new BadRequestAlertException("A new grado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Grado result = gradoRepository.save(grado);
        gradoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/grados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grados : Updates an existing grado.
     *
     * @param grado the grado to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grado,
     * or with status 400 (Bad Request) if the grado is not valid,
     * or with status 500 (Internal Server Error) if the grado couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grados")
    @Timed
    public ResponseEntity<Grado> updateGrado(@RequestBody Grado grado) throws URISyntaxException {
        log.debug("REST request to update Grado : {}", grado);
        if (grado.getId() == null) {
            return createGrado(grado);
        }
        Grado result = gradoRepository.save(grado);
        gradoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grado.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grados : get all the grados.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of grados in body
     */
    @GetMapping("/grados")
    @Timed
    public List<Grado> getAllGrados() {
        log.debug("REST request to get all Grados");
        return gradoRepository.findAll();
        }

    /**
     * GET  /grados/:id : get the "id" grado.
     *
     * @param id the id of the grado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grado, or with status 404 (Not Found)
     */
    @GetMapping("/grados/{id}")
    @Timed
    public ResponseEntity<Grado> getGrado(@PathVariable Long id) {
        log.debug("REST request to get Grado : {}", id);
        Grado grado = gradoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(grado));
    }

    /**
     * DELETE  /grados/:id : delete the "id" grado.
     *
     * @param id the id of the grado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grados/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrado(@PathVariable Long id) {
        log.debug("REST request to delete Grado : {}", id);
        gradoRepository.delete(id);
        gradoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/grados?query=:query : search for the grado corresponding
     * to the query.
     *
     * @param query the query of the grado search
     * @return the result of the search
     */
    @GetMapping("/_search/grados")
    @Timed
    public List<Grado> searchGrados(@RequestParam String query) {
        log.debug("REST request to search Grados for query {}", query);
        return StreamSupport
            .stream(gradoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
