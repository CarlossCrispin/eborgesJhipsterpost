package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.TipoAsesor;
import org.jhipster.cinvestav.repository.TipoAsesorRepository;
import org.jhipster.cinvestav.repository.search.TipoAsesorSearchRepository;
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
import java.util.List;

import static org.jhipster.cinvestav.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoAsesorResource REST controller.
 *
 * @see TipoAsesorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class TipoAsesorResourceIntTest {

    private static final Integer DEFAULT_NTIPO = 1;
    private static final Integer UPDATED_NTIPO = 2;

    @Autowired
    private TipoAsesorRepository tipoAsesorRepository;

    @Autowired
    private TipoAsesorSearchRepository tipoAsesorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoAsesorMockMvc;

    private TipoAsesor tipoAsesor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoAsesorResource tipoAsesorResource = new TipoAsesorResource(tipoAsesorRepository, tipoAsesorSearchRepository);
        this.restTipoAsesorMockMvc = MockMvcBuilders.standaloneSetup(tipoAsesorResource)
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
    public static TipoAsesor createEntity(EntityManager em) {
        TipoAsesor tipoAsesor = new TipoAsesor()
            .ntipo(DEFAULT_NTIPO);
        return tipoAsesor;
    }

    @Before
    public void initTest() {
        tipoAsesorSearchRepository.deleteAll();
        tipoAsesor = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoAsesor() throws Exception {
        int databaseSizeBeforeCreate = tipoAsesorRepository.findAll().size();

        // Create the TipoAsesor
        restTipoAsesorMockMvc.perform(post("/api/tipo-asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAsesor)))
            .andExpect(status().isCreated());

        // Validate the TipoAsesor in the database
        List<TipoAsesor> tipoAsesorList = tipoAsesorRepository.findAll();
        assertThat(tipoAsesorList).hasSize(databaseSizeBeforeCreate + 1);
        TipoAsesor testTipoAsesor = tipoAsesorList.get(tipoAsesorList.size() - 1);
        assertThat(testTipoAsesor.getNtipo()).isEqualTo(DEFAULT_NTIPO);

        // Validate the TipoAsesor in Elasticsearch
        TipoAsesor tipoAsesorEs = tipoAsesorSearchRepository.findOne(testTipoAsesor.getId());
        assertThat(tipoAsesorEs).isEqualToIgnoringGivenFields(testTipoAsesor);
    }

    @Test
    @Transactional
    public void createTipoAsesorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoAsesorRepository.findAll().size();

        // Create the TipoAsesor with an existing ID
        tipoAsesor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoAsesorMockMvc.perform(post("/api/tipo-asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAsesor)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAsesor in the database
        List<TipoAsesor> tipoAsesorList = tipoAsesorRepository.findAll();
        assertThat(tipoAsesorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoAsesors() throws Exception {
        // Initialize the database
        tipoAsesorRepository.saveAndFlush(tipoAsesor);

        // Get all the tipoAsesorList
        restTipoAsesorMockMvc.perform(get("/api/tipo-asesors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAsesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].ntipo").value(hasItem(DEFAULT_NTIPO)));
    }

    @Test
    @Transactional
    public void getTipoAsesor() throws Exception {
        // Initialize the database
        tipoAsesorRepository.saveAndFlush(tipoAsesor);

        // Get the tipoAsesor
        restTipoAsesorMockMvc.perform(get("/api/tipo-asesors/{id}", tipoAsesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoAsesor.getId().intValue()))
            .andExpect(jsonPath("$.ntipo").value(DEFAULT_NTIPO));
    }

    @Test
    @Transactional
    public void getNonExistingTipoAsesor() throws Exception {
        // Get the tipoAsesor
        restTipoAsesorMockMvc.perform(get("/api/tipo-asesors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoAsesor() throws Exception {
        // Initialize the database
        tipoAsesorRepository.saveAndFlush(tipoAsesor);
        tipoAsesorSearchRepository.save(tipoAsesor);
        int databaseSizeBeforeUpdate = tipoAsesorRepository.findAll().size();

        // Update the tipoAsesor
        TipoAsesor updatedTipoAsesor = tipoAsesorRepository.findOne(tipoAsesor.getId());
        // Disconnect from session so that the updates on updatedTipoAsesor are not directly saved in db
        em.detach(updatedTipoAsesor);
        updatedTipoAsesor
            .ntipo(UPDATED_NTIPO);

        restTipoAsesorMockMvc.perform(put("/api/tipo-asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoAsesor)))
            .andExpect(status().isOk());

        // Validate the TipoAsesor in the database
        List<TipoAsesor> tipoAsesorList = tipoAsesorRepository.findAll();
        assertThat(tipoAsesorList).hasSize(databaseSizeBeforeUpdate);
        TipoAsesor testTipoAsesor = tipoAsesorList.get(tipoAsesorList.size() - 1);
        assertThat(testTipoAsesor.getNtipo()).isEqualTo(UPDATED_NTIPO);

        // Validate the TipoAsesor in Elasticsearch
        TipoAsesor tipoAsesorEs = tipoAsesorSearchRepository.findOne(testTipoAsesor.getId());
        assertThat(tipoAsesorEs).isEqualToIgnoringGivenFields(testTipoAsesor);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoAsesor() throws Exception {
        int databaseSizeBeforeUpdate = tipoAsesorRepository.findAll().size();

        // Create the TipoAsesor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoAsesorMockMvc.perform(put("/api/tipo-asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAsesor)))
            .andExpect(status().isCreated());

        // Validate the TipoAsesor in the database
        List<TipoAsesor> tipoAsesorList = tipoAsesorRepository.findAll();
        assertThat(tipoAsesorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoAsesor() throws Exception {
        // Initialize the database
        tipoAsesorRepository.saveAndFlush(tipoAsesor);
        tipoAsesorSearchRepository.save(tipoAsesor);
        int databaseSizeBeforeDelete = tipoAsesorRepository.findAll().size();

        // Get the tipoAsesor
        restTipoAsesorMockMvc.perform(delete("/api/tipo-asesors/{id}", tipoAsesor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tipoAsesorExistsInEs = tipoAsesorSearchRepository.exists(tipoAsesor.getId());
        assertThat(tipoAsesorExistsInEs).isFalse();

        // Validate the database is empty
        List<TipoAsesor> tipoAsesorList = tipoAsesorRepository.findAll();
        assertThat(tipoAsesorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTipoAsesor() throws Exception {
        // Initialize the database
        tipoAsesorRepository.saveAndFlush(tipoAsesor);
        tipoAsesorSearchRepository.save(tipoAsesor);

        // Search the tipoAsesor
        restTipoAsesorMockMvc.perform(get("/api/_search/tipo-asesors?query=id:" + tipoAsesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAsesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].ntipo").value(hasItem(DEFAULT_NTIPO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAsesor.class);
        TipoAsesor tipoAsesor1 = new TipoAsesor();
        tipoAsesor1.setId(1L);
        TipoAsesor tipoAsesor2 = new TipoAsesor();
        tipoAsesor2.setId(tipoAsesor1.getId());
        assertThat(tipoAsesor1).isEqualTo(tipoAsesor2);
        tipoAsesor2.setId(2L);
        assertThat(tipoAsesor1).isNotEqualTo(tipoAsesor2);
        tipoAsesor1.setId(null);
        assertThat(tipoAsesor1).isNotEqualTo(tipoAsesor2);
    }
}
