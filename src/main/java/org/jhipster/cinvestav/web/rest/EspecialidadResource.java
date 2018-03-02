package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.Especialidad;

import org.jhipster.cinvestav.repository.EspecialidadRepository;
import org.jhipster.cinvestav.repository.search.EspecialidadSearchRepository;
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
 * REST controller for managing Especialidad.
 */
@RestController
@RequestMapping("/api")
public class EspecialidadResource {

    private final Logger log = LoggerFactory.getLogger(EspecialidadResource.class);

    private static final String ENTITY_NAME = "especialidad";

    private final EspecialidadRepository especialidadRepository;

    private final EspecialidadSearchRepository especialidadSearchRepository;

    public EspecialidadResource(EspecialidadRepository especialidadRepository, EspecialidadSearchRepository especialidadSearchRepository) {
        this.especialidadRepository = especialidadRepository;
        this.especialidadSearchRepository = especialidadSearchRepository;
    }

    /**
     * POST  /especialidads : Create a new especialidad.
     *
     * @param especialidad the especialidad to create
     * @return the ResponseEntity with status 201 (Created) and with body the new especialidad, or with status 400 (Bad Request) if the especialidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/especialidads")
    @Timed
    public ResponseEntity<Especialidad> createEspecialidad(@RequestBody Especialidad especialidad) throws URISyntaxException {
        log.debug("REST request to save Especialidad : {}", especialidad);
        if (especialidad.getId() != null) {
            throw new BadRequestAlertException("A new especialidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Especialidad result = especialidadRepository.save(especialidad);
        especialidadSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/especialidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /especialidads : Updates an existing especialidad.
     *
     * @param especialidad the especialidad to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated especialidad,
     * or with status 400 (Bad Request) if the especialidad is not valid,
     * or with status 500 (Internal Server Error) if the especialidad couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/especialidads")
    @Timed
    public ResponseEntity<Especialidad> updateEspecialidad(@RequestBody Especialidad especialidad) throws URISyntaxException {
        log.debug("REST request to update Especialidad : {}", especialidad);
        if (especialidad.getId() == null) {
            return createEspecialidad(especialidad);
        }
        Especialidad result = especialidadRepository.save(especialidad);
        especialidadSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, especialidad.getId().toString()))
            .body(result);
    }

    /**
     * GET  /especialidads : get all the especialidads.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of especialidads in body
     */
    @GetMapping("/especialidads")
    @Timed
    public List<Especialidad> getAllEspecialidads() {
        log.debug("REST request to get all Especialidads");
        return especialidadRepository.findAll();
        }

    /**
     * GET  /especialidads/:id : get the "id" especialidad.
     *
     * @param id the id of the especialidad to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the especialidad, or with status 404 (Not Found)
     */
    @GetMapping("/especialidads/{id}")
    @Timed
    public ResponseEntity<Especialidad> getEspecialidad(@PathVariable Long id) {
        log.debug("REST request to get Especialidad : {}", id);
        Especialidad especialidad = especialidadRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(especialidad));
    }

    /**
     * DELETE  /especialidads/:id : delete the "id" especialidad.
     *
     * @param id the id of the especialidad to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/especialidads/{id}")
    @Timed
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable Long id) {
        log.debug("REST request to delete Especialidad : {}", id);
        especialidadRepository.delete(id);
        especialidadSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/especialidads?query=:query : search for the especialidad corresponding
     * to the query.
     *
     * @param query the query of the especialidad search
     * @return the result of the search
     */
    @GetMapping("/_search/especialidads")
    @Timed
    public List<Especialidad> searchEspecialidads(@RequestParam String query) {
        log.debug("REST request to search Especialidads for query {}", query);
        return StreamSupport
            .stream(especialidadSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
