package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.Acta;

import org.jhipster.cinvestav.repository.ActaRepository;
import org.jhipster.cinvestav.repository.search.ActaSearchRepository;
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
 * REST controller for managing Acta.
 */
@RestController
@RequestMapping("/api")
public class ActaResource {

    private final Logger log = LoggerFactory.getLogger(ActaResource.class);

    private static final String ENTITY_NAME = "acta";

    private final ActaRepository actaRepository;

    private final ActaSearchRepository actaSearchRepository;

    public ActaResource(ActaRepository actaRepository, ActaSearchRepository actaSearchRepository) {
        this.actaRepository = actaRepository;
        this.actaSearchRepository = actaSearchRepository;
    }

    /**
     * POST  /actas : Create a new acta.
     *
     * @param acta the acta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acta, or with status 400 (Bad Request) if the acta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actas")
    @Timed
    public ResponseEntity<Acta> createActa(@RequestBody Acta acta) throws URISyntaxException {
        log.debug("REST request to save Acta : {}", acta);
        if (acta.getId() != null) {
            throw new BadRequestAlertException("A new acta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acta result = actaRepository.save(acta);
        actaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/actas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actas : Updates an existing acta.
     *
     * @param acta the acta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acta,
     * or with status 400 (Bad Request) if the acta is not valid,
     * or with status 500 (Internal Server Error) if the acta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actas")
    @Timed
    public ResponseEntity<Acta> updateActa(@RequestBody Acta acta) throws URISyntaxException {
        log.debug("REST request to update Acta : {}", acta);
        if (acta.getId() == null) {
            return createActa(acta);
        }
        Acta result = actaRepository.save(acta);
        actaSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actas : get all the actas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actas in body
     */
    @GetMapping("/actas")
    @Timed
    public List<Acta> getAllActas() {
        log.debug("REST request to get all Actas");
        return actaRepository.findAll();
        }

    /**
     * GET  /actas/:id : get the "id" acta.
     *
     * @param id the id of the acta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acta, or with status 404 (Not Found)
     */
    @GetMapping("/actas/{id}")
    @Timed
    public ResponseEntity<Acta> getActa(@PathVariable Long id) {
        log.debug("REST request to get Acta : {}", id);
        Acta acta = actaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acta));
    }

    /**
     * DELETE  /actas/:id : delete the "id" acta.
     *
     * @param id the id of the acta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actas/{id}")
    @Timed
    public ResponseEntity<Void> deleteActa(@PathVariable Long id) {
        log.debug("REST request to delete Acta : {}", id);
        actaRepository.delete(id);
        actaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/actas?query=:query : search for the acta corresponding
     * to the query.
     *
     * @param query the query of the acta search
     * @return the result of the search
     */
    @GetMapping("/_search/actas")
    @Timed
    public List<Acta> searchActas(@RequestParam String query) {
        log.debug("REST request to search Actas for query {}", query);
        return StreamSupport
            .stream(actaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
