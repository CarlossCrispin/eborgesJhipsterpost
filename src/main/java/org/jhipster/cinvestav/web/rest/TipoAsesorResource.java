package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.TipoAsesor;

import org.jhipster.cinvestav.repository.TipoAsesorRepository;
import org.jhipster.cinvestav.repository.search.TipoAsesorSearchRepository;
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
 * REST controller for managing TipoAsesor.
 */
@RestController
@RequestMapping("/api")
public class TipoAsesorResource {

    private final Logger log = LoggerFactory.getLogger(TipoAsesorResource.class);

    private static final String ENTITY_NAME = "tipoAsesor";

    private final TipoAsesorRepository tipoAsesorRepository;

    private final TipoAsesorSearchRepository tipoAsesorSearchRepository;

    public TipoAsesorResource(TipoAsesorRepository tipoAsesorRepository, TipoAsesorSearchRepository tipoAsesorSearchRepository) {
        this.tipoAsesorRepository = tipoAsesorRepository;
        this.tipoAsesorSearchRepository = tipoAsesorSearchRepository;
    }

    /**
     * POST  /tipo-asesors : Create a new tipoAsesor.
     *
     * @param tipoAsesor the tipoAsesor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoAsesor, or with status 400 (Bad Request) if the tipoAsesor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-asesors")
    @Timed
    public ResponseEntity<TipoAsesor> createTipoAsesor(@RequestBody TipoAsesor tipoAsesor) throws URISyntaxException {
        log.debug("REST request to save TipoAsesor : {}", tipoAsesor);
        if (tipoAsesor.getId() != null) {
            throw new BadRequestAlertException("A new tipoAsesor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoAsesor result = tipoAsesorRepository.save(tipoAsesor);
        tipoAsesorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tipo-asesors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-asesors : Updates an existing tipoAsesor.
     *
     * @param tipoAsesor the tipoAsesor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoAsesor,
     * or with status 400 (Bad Request) if the tipoAsesor is not valid,
     * or with status 500 (Internal Server Error) if the tipoAsesor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-asesors")
    @Timed
    public ResponseEntity<TipoAsesor> updateTipoAsesor(@RequestBody TipoAsesor tipoAsesor) throws URISyntaxException {
        log.debug("REST request to update TipoAsesor : {}", tipoAsesor);
        if (tipoAsesor.getId() == null) {
            return createTipoAsesor(tipoAsesor);
        }
        TipoAsesor result = tipoAsesorRepository.save(tipoAsesor);
        tipoAsesorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoAsesor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-asesors : get all the tipoAsesors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoAsesors in body
     */
    @GetMapping("/tipo-asesors")
    @Timed
    public List<TipoAsesor> getAllTipoAsesors() {
        log.debug("REST request to get all TipoAsesors");
        return tipoAsesorRepository.findAll();
        }

    /**
     * GET  /tipo-asesors/:id : get the "id" tipoAsesor.
     *
     * @param id the id of the tipoAsesor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoAsesor, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-asesors/{id}")
    @Timed
    public ResponseEntity<TipoAsesor> getTipoAsesor(@PathVariable Long id) {
        log.debug("REST request to get TipoAsesor : {}", id);
        TipoAsesor tipoAsesor = tipoAsesorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoAsesor));
    }

    /**
     * DELETE  /tipo-asesors/:id : delete the "id" tipoAsesor.
     *
     * @param id the id of the tipoAsesor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-asesors/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoAsesor(@PathVariable Long id) {
        log.debug("REST request to delete TipoAsesor : {}", id);
        tipoAsesorRepository.delete(id);
        tipoAsesorSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-asesors?query=:query : search for the tipoAsesor corresponding
     * to the query.
     *
     * @param query the query of the tipoAsesor search
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-asesors")
    @Timed
    public List<TipoAsesor> searchTipoAsesors(@RequestParam String query) {
        log.debug("REST request to search TipoAsesors for query {}", query);
        return StreamSupport
            .stream(tipoAsesorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
