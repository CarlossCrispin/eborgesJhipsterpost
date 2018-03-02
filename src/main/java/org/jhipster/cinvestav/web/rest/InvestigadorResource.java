package org.jhipster.cinvestav.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.cinvestav.domain.Investigador;

import org.jhipster.cinvestav.repository.InvestigadorRepository;
import org.jhipster.cinvestav.repository.search.InvestigadorSearchRepository;
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
 * REST controller for managing Investigador.
 */
@RestController
@RequestMapping("/api")
public class InvestigadorResource {

    private final Logger log = LoggerFactory.getLogger(InvestigadorResource.class);

    private static final String ENTITY_NAME = "investigador";

    private final InvestigadorRepository investigadorRepository;

    private final InvestigadorSearchRepository investigadorSearchRepository;

    public InvestigadorResource(InvestigadorRepository investigadorRepository, InvestigadorSearchRepository investigadorSearchRepository) {
        this.investigadorRepository = investigadorRepository;
        this.investigadorSearchRepository = investigadorSearchRepository;
    }

    /**
     * POST  /investigadors : Create a new investigador.
     *
     * @param investigador the investigador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new investigador, or with status 400 (Bad Request) if the investigador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/investigadors")
    @Timed
    public ResponseEntity<Investigador> createInvestigador(@RequestBody Investigador investigador) throws URISyntaxException {
        log.debug("REST request to save Investigador : {}", investigador);
        if (investigador.getId() != null) {
            throw new BadRequestAlertException("A new investigador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Investigador result = investigadorRepository.save(investigador);
        investigadorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/investigadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /investigadors : Updates an existing investigador.
     *
     * @param investigador the investigador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated investigador,
     * or with status 400 (Bad Request) if the investigador is not valid,
     * or with status 500 (Internal Server Error) if the investigador couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/investigadors")
    @Timed
    public ResponseEntity<Investigador> updateInvestigador(@RequestBody Investigador investigador) throws URISyntaxException {
        log.debug("REST request to update Investigador : {}", investigador);
        if (investigador.getId() == null) {
            return createInvestigador(investigador);
        }
        Investigador result = investigadorRepository.save(investigador);
        investigadorSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, investigador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /investigadors : get all the investigadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of investigadors in body
     */
    @GetMapping("/investigadors")
    @Timed
    public List<Investigador> getAllInvestigadors() {
        log.debug("REST request to get all Investigadors");
        return investigadorRepository.findAll();
        }

    /**
     * GET  /investigadors/:id : get the "id" investigador.
     *
     * @param id the id of the investigador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the investigador, or with status 404 (Not Found)
     */
    @GetMapping("/investigadors/{id}")
    @Timed
    public ResponseEntity<Investigador> getInvestigador(@PathVariable Long id) {
        log.debug("REST request to get Investigador : {}", id);
        Investigador investigador = investigadorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(investigador));
    }

    /**
     * DELETE  /investigadors/:id : delete the "id" investigador.
     *
     * @param id the id of the investigador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/investigadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvestigador(@PathVariable Long id) {
        log.debug("REST request to delete Investigador : {}", id);
        investigadorRepository.delete(id);
        investigadorSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/investigadors?query=:query : search for the investigador corresponding
     * to the query.
     *
     * @param query the query of the investigador search
     * @return the result of the search
     */
    @GetMapping("/_search/investigadors")
    @Timed
    public List<Investigador> searchInvestigadors(@RequestParam String query) {
        log.debug("REST request to search Investigadors for query {}", query);
        return StreamSupport
            .stream(investigadorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
