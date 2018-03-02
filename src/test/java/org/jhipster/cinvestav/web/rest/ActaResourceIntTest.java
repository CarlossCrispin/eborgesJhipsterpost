package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.Acta;
import org.jhipster.cinvestav.repository.ActaRepository;
import org.jhipster.cinvestav.repository.search.ActaSearchRepository;
import org.jhipster.cinvestav.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.jhipster.cinvestav.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActaResource REST controller.
 *
 * @see ActaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class ActaResourceIntTest {

    private static final Long DEFAULT_FOLIO = 1L;
    private static final Long UPDATED_FOLIO = 2L;

    private static final Instant DEFAULT_FECHATOMAGRADO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHATOMAGRADO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ActaRepository actaRepository;

    @Autowired
    private ActaSearchRepository actaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActaMockMvc;

    private Acta acta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActaResource actaResource = new ActaResource(actaRepository, actaSearchRepository);
        this.restActaMockMvc = MockMvcBuilders.standaloneSetup(actaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acta createEntity(EntityManager em) {
        Acta acta = new Acta()
            .folio(DEFAULT_FOLIO)
            .fechatomagrado(DEFAULT_FECHATOMAGRADO);
        return acta;
    }

    @Before
    public void initTest() {
        actaSearchRepository.deleteAll();
        acta = createEntity(em);
    }

    @Test
    @Transactional
    public void createActa() throws Exception {
        int databaseSizeBeforeCreate = actaRepository.findAll().size();

        // Create the Acta
        restActaMockMvc.perform(post("/api/actas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acta)))
            .andExpect(status().isCreated());

        // Validate the Acta in the database
        List<Acta> actaList = actaRepository.findAll();
        assertThat(actaList).hasSize(databaseSizeBeforeCreate + 1);
        Acta testActa = actaList.get(actaList.size() - 1);
        assertThat(testActa.getFolio()).isEqualTo(DEFAULT_FOLIO);
        assertThat(testActa.getFechatomagrado()).isEqualTo(DEFAULT_FECHATOMAGRADO);

        // Validate the Acta in Elasticsearch
        Acta actaEs = actaSearchRepository.findOne(testActa.getId());
        assertThat(actaEs).isEqualToIgnoringGivenFields(testActa);
    }

    @Test
    @Transactional
    public void createActaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actaRepository.findAll().size();

        // Create the Acta with an existing ID
        acta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActaMockMvc.perform(post("/api/actas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acta)))
            .andExpect(status().isBadRequest());

        // Validate the Acta in the database
        List<Acta> actaList = actaRepository.findAll();
        assertThat(actaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActas() throws Exception {
        // Initialize the database
        actaRepository.saveAndFlush(acta);

        // Get all the actaList
        restActaMockMvc.perform(get("/api/actas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acta.getId().intValue())))
            .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO.intValue())))
            .andExpect(jsonPath("$.[*].fechatomagrado").value(hasItem(DEFAULT_FECHATOMAGRADO.toString())));
    }

    @Test
    @Transactional
    public void getActa() throws Exception {
        // Initialize the database
        actaRepository.saveAndFlush(acta);

        // Get the acta
        restActaMockMvc.perform(get("/api/actas/{id}", acta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acta.getId().intValue()))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO.intValue()))
            .andExpect(jsonPath("$.fechatomagrado").value(DEFAULT_FECHATOMAGRADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActa() throws Exception {
        // Get the acta
        restActaMockMvc.perform(get("/api/actas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActa() throws Exception {
        // Initialize the database
        actaRepository.saveAndFlush(acta);
        actaSearchRepository.save(acta);
        int databaseSizeBeforeUpdate = actaRepository.findAll().size();

        // Update the acta
        Acta updatedActa = actaRepository.findOne(acta.getId());
        // Disconnect from session so that the updates on updatedActa are not directly saved in db
        em.detach(updatedActa);
        updatedActa
            .folio(UPDATED_FOLIO)
            .fechatomagrado(UPDATED_FECHATOMAGRADO);

        restActaMockMvc.perform(put("/api/actas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActa)))
            .andExpect(status().isOk());

        // Validate the Acta in the database
        List<Acta> actaList = actaRepository.findAll();
        assertThat(actaList).hasSize(databaseSizeBeforeUpdate);
        Acta testActa = actaList.get(actaList.size() - 1);
        assertThat(testActa.getFolio()).isEqualTo(UPDATED_FOLIO);
        assertThat(testActa.getFechatomagrado()).isEqualTo(UPDATED_FECHATOMAGRADO);

        // Validate the Acta in Elasticsearch
        Acta actaEs = actaSearchRepository.findOne(testActa.getId());
        assertThat(actaEs).isEqualToIgnoringGivenFields(testActa);
    }

    @Test
    @Transactional
    public void updateNonExistingActa() throws Exception {
        int databaseSizeBeforeUpdate = actaRepository.findAll().size();

        // Create the Acta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActaMockMvc.perform(put("/api/actas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acta)))
            .andExpect(status().isCreated());

        // Validate the Acta in the database
        List<Acta> actaList = actaRepository.findAll();
        assertThat(actaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteActa() throws Exception {
        // Initialize the database
        actaRepository.saveAndFlush(acta);
        actaSearchRepository.save(acta);
        int databaseSizeBeforeDelete = actaRepository.findAll().size();

        // Get the acta
        restActaMockMvc.perform(delete("/api/actas/{id}", acta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean actaExistsInEs = actaSearchRepository.exists(acta.getId());
        assertThat(actaExistsInEs).isFalse();

        // Validate the database is empty
        List<Acta> actaList = actaRepository.findAll();
        assertThat(actaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchActa() throws Exception {
        // Initialize the database
        actaRepository.saveAndFlush(acta);
        actaSearchRepository.save(acta);

        // Search the acta
        restActaMockMvc.perform(get("/api/_search/actas?query=id:" + acta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acta.getId().intValue())))
            .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO.intValue())))
            .andExpect(jsonPath("$.[*].fechatomagrado").value(hasItem(DEFAULT_FECHATOMAGRADO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acta.class);
        Acta acta1 = new Acta();
        acta1.setId(1L);
        Acta acta2 = new Acta();
        acta2.setId(acta1.getId());
        assertThat(acta1).isEqualTo(acta2);
        acta2.setId(2L);
        assertThat(acta1).isNotEqualTo(acta2);
        acta1.setId(null);
        assertThat(acta1).isNotEqualTo(acta2);
    }
}
