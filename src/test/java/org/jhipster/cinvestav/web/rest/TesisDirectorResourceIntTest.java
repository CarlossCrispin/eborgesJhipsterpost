package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.TesisDirector;
import org.jhipster.cinvestav.repository.TesisDirectorRepository;
import org.jhipster.cinvestav.repository.search.TesisDirectorSearchRepository;
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
 * Test class for the TesisDirectorResource REST controller.
 *
 * @see TesisDirectorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class TesisDirectorResourceIntTest {

    @Autowired
    private TesisDirectorRepository tesisDirectorRepository;

    @Autowired
    private TesisDirectorSearchRepository tesisDirectorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTesisDirectorMockMvc;

    private TesisDirector tesisDirector;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TesisDirectorResource tesisDirectorResource = new TesisDirectorResource(tesisDirectorRepository, tesisDirectorSearchRepository);
        this.restTesisDirectorMockMvc = MockMvcBuilders.standaloneSetup(tesisDirectorResource)
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
    public static TesisDirector createEntity(EntityManager em) {
        TesisDirector tesisDirector = new TesisDirector();
        return tesisDirector;
    }

    @Before
    public void initTest() {
        tesisDirectorSearchRepository.deleteAll();
        tesisDirector = createEntity(em);
    }

    @Test
    @Transactional
    public void createTesisDirector() throws Exception {
        int databaseSizeBeforeCreate = tesisDirectorRepository.findAll().size();

        // Create the TesisDirector
        restTesisDirectorMockMvc.perform(post("/api/tesis-directors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisDirector)))
            .andExpect(status().isCreated());

        // Validate the TesisDirector in the database
        List<TesisDirector> tesisDirectorList = tesisDirectorRepository.findAll();
        assertThat(tesisDirectorList).hasSize(databaseSizeBeforeCreate + 1);
        TesisDirector testTesisDirector = tesisDirectorList.get(tesisDirectorList.size() - 1);

        // Validate the TesisDirector in Elasticsearch
        TesisDirector tesisDirectorEs = tesisDirectorSearchRepository.findOne(testTesisDirector.getId());
        assertThat(tesisDirectorEs).isEqualToIgnoringGivenFields(testTesisDirector);
    }

    @Test
    @Transactional
    public void createTesisDirectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tesisDirectorRepository.findAll().size();

        // Create the TesisDirector with an existing ID
        tesisDirector.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTesisDirectorMockMvc.perform(post("/api/tesis-directors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisDirector)))
            .andExpect(status().isBadRequest());

        // Validate the TesisDirector in the database
        List<TesisDirector> tesisDirectorList = tesisDirectorRepository.findAll();
        assertThat(tesisDirectorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTesisDirectors() throws Exception {
        // Initialize the database
        tesisDirectorRepository.saveAndFlush(tesisDirector);

        // Get all the tesisDirectorList
        restTesisDirectorMockMvc.perform(get("/api/tesis-directors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tesisDirector.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTesisDirector() throws Exception {
        // Initialize the database
        tesisDirectorRepository.saveAndFlush(tesisDirector);

        // Get the tesisDirector
        restTesisDirectorMockMvc.perform(get("/api/tesis-directors/{id}", tesisDirector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tesisDirector.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTesisDirector() throws Exception {
        // Get the tesisDirector
        restTesisDirectorMockMvc.perform(get("/api/tesis-directors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTesisDirector() throws Exception {
        // Initialize the database
        tesisDirectorRepository.saveAndFlush(tesisDirector);
        tesisDirectorSearchRepository.save(tesisDirector);
        int databaseSizeBeforeUpdate = tesisDirectorRepository.findAll().size();

        // Update the tesisDirector
        TesisDirector updatedTesisDirector = tesisDirectorRepository.findOne(tesisDirector.getId());
        // Disconnect from session so that the updates on updatedTesisDirector are not directly saved in db
        em.detach(updatedTesisDirector);

        restTesisDirectorMockMvc.perform(put("/api/tesis-directors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTesisDirector)))
            .andExpect(status().isOk());

        // Validate the TesisDirector in the database
        List<TesisDirector> tesisDirectorList = tesisDirectorRepository.findAll();
        assertThat(tesisDirectorList).hasSize(databaseSizeBeforeUpdate);
        TesisDirector testTesisDirector = tesisDirectorList.get(tesisDirectorList.size() - 1);

        // Validate the TesisDirector in Elasticsearch
        TesisDirector tesisDirectorEs = tesisDirectorSearchRepository.findOne(testTesisDirector.getId());
        assertThat(tesisDirectorEs).isEqualToIgnoringGivenFields(testTesisDirector);
    }

    @Test
    @Transactional
    public void updateNonExistingTesisDirector() throws Exception {
        int databaseSizeBeforeUpdate = tesisDirectorRepository.findAll().size();

        // Create the TesisDirector

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTesisDirectorMockMvc.perform(put("/api/tesis-directors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesisDirector)))
            .andExpect(status().isCreated());

        // Validate the TesisDirector in the database
        List<TesisDirector> tesisDirectorList = tesisDirectorRepository.findAll();
        assertThat(tesisDirectorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTesisDirector() throws Exception {
        // Initialize the database
        tesisDirectorRepository.saveAndFlush(tesisDirector);
        tesisDirectorSearchRepository.save(tesisDirector);
        int databaseSizeBeforeDelete = tesisDirectorRepository.findAll().size();

        // Get the tesisDirector
        restTesisDirectorMockMvc.perform(delete("/api/tesis-directors/{id}", tesisDirector.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tesisDirectorExistsInEs = tesisDirectorSearchRepository.exists(tesisDirector.getId());
        assertThat(tesisDirectorExistsInEs).isFalse();

        // Validate the database is empty
        List<TesisDirector> tesisDirectorList = tesisDirectorRepository.findAll();
        assertThat(tesisDirectorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTesisDirector() throws Exception {
        // Initialize the database
        tesisDirectorRepository.saveAndFlush(tesisDirector);
        tesisDirectorSearchRepository.save(tesisDirector);

        // Search the tesisDirector
        restTesisDirectorMockMvc.perform(get("/api/_search/tesis-directors?query=id:" + tesisDirector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tesisDirector.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TesisDirector.class);
        TesisDirector tesisDirector1 = new TesisDirector();
        tesisDirector1.setId(1L);
        TesisDirector tesisDirector2 = new TesisDirector();
        tesisDirector2.setId(tesisDirector1.getId());
        assertThat(tesisDirector1).isEqualTo(tesisDirector2);
        tesisDirector2.setId(2L);
        assertThat(tesisDirector1).isNotEqualTo(tesisDirector2);
        tesisDirector1.setId(null);
        assertThat(tesisDirector1).isNotEqualTo(tesisDirector2);
    }
}
