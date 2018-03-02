package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.Departamento;

import org.jhipster.cinvestav.repository.DepartamentoRepository;
import org.jhipster.cinvestav.repository.search.DepartamentoSearchRepository;
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
 * REST controller for managing Departamento.
 */
@RestController
@RequestMapping("/api")
public class DepartamentoResource {

    private final Logger log = LoggerFactory.getLogger(DepartamentoResource.class);

    private static final String ENTITY_NAME = "departamento";

    private final DepartamentoRepository departamentoRepository;

    private final DepartamentoSearchRepository departamentoSearchRepository;

    public DepartamentoResource(DepartamentoRepository departamentoRepository, DepartamentoSearchRepository departamentoSearchRepository) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoSearchRepository = departamentoSearchRepository;
    }

    /**
     * POST  /departamentos : Create a new departamento.
     *
     * @param departamento the departamento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departamento, or with status 400 (Bad Request) if the departamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departamentos")
    @Timed
    public ResponseEntity<Departamento> createDepartamento(@RequestBody Departamento departamento) throws URISyntaxException {
        log.debug("REST request to save Departamento : {}", departamento);
        if (departamento.getId() != null) {
            throw new BadRequestAlertException("A new departamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Departamento result = departamentoRepository.save(departamento);
        departamentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/departamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departamentos : Updates an existing departamento.
     *
     * @param departamento the departamento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departamento,
     * or with status 400 (Bad Request) if the departamento is not valid,
     * or with status 500 (Internal Server Error) if the departamento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departamentos")
    @Timed
    public ResponseEntity<Departamento> updateDepartamento(@RequestBody Departamento departamento) throws URISyntaxException {
        log.debug("REST request to update Departamento : {}", departamento);
        if (departamento.getId() == null) {
            return createDepartamento(departamento);
        }
        Departamento result = departamentoRepository.save(departamento);
        departamentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, departamento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departamentos : get all the departamentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of departamentos in body
     */
    @GetMapping("/departamentos")
    @Timed
    public List<Departamento> getAllDepartamentos() {
        log.debug("REST request to get all Departamentos");
        return departamentoRepository.findAll();
        }

    /**
     * GET  /departamentos/:id : get the "id" departamento.
     *
     * @param id the id of the departamento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departamento, or with status 404 (Not Found)
     */
    @GetMapping("/departamentos/{id}")
    @Timed
    public ResponseEntity<Departamento> getDepartamento(@PathVariable Long id) {
        log.debug("REST request to get Departamento : {}", id);
        Departamento departamento = departamentoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(departamento));
    }

    /**
     * DELETE  /departamentos/:id : delete the "id" departamento.
     *
     * @param id the id of the departamento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        log.debug("REST request to delete Departamento : {}", id);
        departamentoRepository.delete(id);
        departamentoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/departamentos?query=:query : search for the departamento corresponding
     * to the query.
     *
     * @param query the query of the departamento search
     * @return the result of the search
     */
    @GetMapping("/_search/departamentos")
    @Timed
    public List<Departamento> searchDepartamentos(@RequestParam String query) {
        log.debug("REST request to search Departamentos for query {}", query);
        return StreamSupport
            .stream(departamentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
