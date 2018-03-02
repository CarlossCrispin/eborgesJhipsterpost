package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.Sinodal;
import org.jhipster.cinvestav.repository.SinodalRepository;
import org.jhipster.cinvestav.repository.search.SinodalSearchRepository;
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
 * Test class for the SinodalResource REST controller.
 *
 * @see SinodalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class SinodalResourceIntTest {

    @Autowired
    private SinodalRepository sinodalRepository;

    @Autowired
    private SinodalSearchRepository sinodalSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSinodalMockMvc;

    private Sinodal sinodal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SinodalResource sinodalResource = new SinodalResource(sinodalRepository, sinodalSearchRepository);
        this.restSinodalMockMvc = MockMvcBuilders.standaloneSetup(sinodalResource)
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
    public static Sinodal createEntity(EntityManager em) {
        Sinodal sinodal = new Sinodal();
        return sinodal;
    }

    @Before
    public void initTest() {
        sinodalSearchRepository.deleteAll();
        sinodal = createEntity(em);
    }

    @Test
    @Transactional
    public void createSinodal() throws Exception {
        int databaseSizeBeforeCreate = sinodalRepository.findAll().size();

        // Create the Sinodal
        restSinodalMockMvc.perform(post("/api/sinodals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinodal)))
            .andExpect(status().isCreated());

        // Validate the Sinodal in the database
        List<Sinodal> sinodalList = sinodalRepository.findAll();
        assertThat(sinodalList).hasSize(databaseSizeBeforeCreate + 1);
        Sinodal testSinodal = sinodalList.get(sinodalList.size() - 1);

        // Validate the Sinodal in Elasticsearch
        Sinodal sinodalEs = sinodalSearchRepository.findOne(testSinodal.getId());
        assertThat(sinodalEs).isEqualToIgnoringGivenFields(testSinodal);
    }

    @Test
    @Transactional
    public void createSinodalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sinodalRepository.findAll().size();

        // Create the Sinodal with an existing ID
        sinodal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSinodalMockMvc.perform(post("/api/sinodals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinodal)))
            .andExpect(status().isBadRequest());

        // Validate the Sinodal in the database
        List<Sinodal> sinodalList = sinodalRepository.findAll();
        assertThat(sinodalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSinodals() throws Exception {
        // Initialize the database
        sinodalRepository.saveAndFlush(sinodal);

        // Get all the sinodalList
        restSinodalMockMvc.perform(get("/api/sinodals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinodal.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSinodal() throws Exception {
        // Initialize the database
        sinodalRepository.saveAndFlush(sinodal);

        // Get the sinodal
        restSinodalMockMvc.perform(get("/api/sinodals/{id}", sinodal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sinodal.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSinodal() throws Exception {
        // Get the sinodal
        restSinodalMockMvc.perform(get("/api/sinodals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSinodal() throws Exception {
        // Initialize the database
        sinodalRepository.saveAndFlush(sinodal);
        sinodalSearchRepository.save(sinodal);
        int databaseSizeBeforeUpdate = sinodalRepository.findAll().size();

        // Update the sinodal
        Sinodal updatedSinodal = sinodalRepository.findOne(sinodal.getId());
        // Disconnect from session so that the updates on updatedSinodal are not directly saved in db
        em.detach(updatedSinodal);

        restSinodalMockMvc.perform(put("/api/sinodals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSinodal)))
            .andExpect(status().isOk());

        // Validate the Sinodal in the database
        List<Sinodal> sinodalList = sinodalRepository.findAll();
        assertThat(sinodalList).hasSize(databaseSizeBeforeUpdate);
        Sinodal testSinodal = sinodalList.get(sinodalList.size() - 1);

        // Validate the Sinodal in Elasticsearch
        Sinodal sinodalEs = sinodalSearchRepository.findOne(testSinodal.getId());
        assertThat(sinodalEs).isEqualToIgnoringGivenFields(testSinodal);
    }

    @Test
    @Transactional
    public void updateNonExistingSinodal() throws Exception {
        int databaseSizeBeforeUpdate = sinodalRepository.findAll().size();

        // Create the Sinodal

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSinodalMockMvc.perform(put("/api/sinodals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sinodal)))
            .andExpect(status().isCreated());

        // Validate the Sinodal in the database
        List<Sinodal> sinodalList = sinodalRepository.findAll();
        assertThat(sinodalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSinodal() throws Exception {
        // Initialize the database
        sinodalRepository.saveAndFlush(sinodal);
        sinodalSearchRepository.save(sinodal);
        int databaseSizeBeforeDelete = sinodalRepository.findAll().size();

        // Get the sinodal
        restSinodalMockMvc.perform(delete("/api/sinodals/{id}", sinodal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sinodalExistsInEs = sinodalSearchRepository.exists(sinodal.getId());
        assertThat(sinodalExistsInEs).isFalse();

        // Validate the database is empty
        List<Sinodal> sinodalList = sinodalRepository.findAll();
        assertThat(sinodalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSinodal() throws Exception {
        // Initialize the database
        sinodalRepository.saveAndFlush(sinodal);
        sinodalSearchRepository.save(sinodal);

        // Search the sinodal
        restSinodalMockMvc.perform(get("/api/_search/sinodals?query=id:" + sinodal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sinodal.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sinodal.class);
        Sinodal sinodal1 = new Sinodal();
        sinodal1.setId(1L);
        Sinodal sinodal2 = new Sinodal();
        sinodal2.setId(sinodal1.getId());
        assertThat(sinodal1).isEqualTo(sinodal2);
        sinodal2.setId(2L);
        assertThat(sinodal1).isNotEqualTo(sinodal2);
        sinodal1.setId(null);
        assertThat(sinodal1).isNotEqualTo(sinodal2);
    }
}
