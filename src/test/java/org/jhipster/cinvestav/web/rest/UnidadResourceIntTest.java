package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.Unidad;
import org.jhipster.cinvestav.repository.UnidadRepository;
import org.jhipster.cinvestav.repository.search.UnidadSearchRepository;
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
 * Test class for the UnidadResource REST controller.
 *
 * @see UnidadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class UnidadResourceIntTest {

    private static final String DEFAULT_NUNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NUNIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION = "BBBBBBBBBB";

    @Autowired
    private UnidadRepository unidadRepository;

    @Autowired
    private UnidadSearchRepository unidadSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnidadMockMvc;

    private Unidad unidad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadResource unidadResource = new UnidadResource(unidadRepository, unidadSearchRepository);
        this.restUnidadMockMvc = MockMvcBuilders.standaloneSetup(unidadResource)
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
    public static Unidad createEntity(EntityManager em) {
        Unidad unidad = new Unidad()
            .nunidad(DEFAULT_NUNIDAD)
            .ubicacion(DEFAULT_UBICACION);
        return unidad;
    }

    @Before
    public void initTest() {
        unidadSearchRepository.deleteAll();
        unidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidad() throws Exception {
        int databaseSizeBeforeCreate = unidadRepository.findAll().size();

        // Create the Unidad
        restUnidadMockMvc.perform(post("/api/unidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isCreated());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeCreate + 1);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getNunidad()).isEqualTo(DEFAULT_NUNIDAD);
        assertThat(testUnidad.getUbicacion()).isEqualTo(DEFAULT_UBICACION);

        // Validate the Unidad in Elasticsearch
        Unidad unidadEs = unidadSearchRepository.findOne(testUnidad.getId());
        assertThat(unidadEs).isEqualToIgnoringGivenFields(testUnidad);
    }

    @Test
    @Transactional
    public void createUnidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadRepository.findAll().size();

        // Create the Unidad with an existing ID
        unidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadMockMvc.perform(post("/api/unidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isBadRequest());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUnidads() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);

        // Get all the unidadList
        restUnidadMockMvc.perform(get("/api/unidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nunidad").value(hasItem(DEFAULT_NUNIDAD.toString())))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION.toString())));
    }

    @Test
    @Transactional
    public void getUnidad() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);

        // Get the unidad
        restUnidadMockMvc.perform(get("/api/unidads/{id}", unidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidad.getId().intValue()))
            .andExpect(jsonPath("$.nunidad").value(DEFAULT_NUNIDAD.toString()))
            .andExpect(jsonPath("$.ubicacion").value(DEFAULT_UBICACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidad() throws Exception {
        // Get the unidad
        restUnidadMockMvc.perform(get("/api/unidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidad() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);
        unidadSearchRepository.save(unidad);
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();

        // Update the unidad
        Unidad updatedUnidad = unidadRepository.findOne(unidad.getId());
        // Disconnect from session so that the updates on updatedUnidad are not directly saved in db
        em.detach(updatedUnidad);
        updatedUnidad
            .nunidad(UPDATED_NUNIDAD)
            .ubicacion(UPDATED_UBICACION);

        restUnidadMockMvc.perform(put("/api/unidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnidad)))
            .andExpect(status().isOk());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate);
        Unidad testUnidad = unidadList.get(unidadList.size() - 1);
        assertThat(testUnidad.getNunidad()).isEqualTo(UPDATED_NUNIDAD);
        assertThat(testUnidad.getUbicacion()).isEqualTo(UPDATED_UBICACION);

        // Validate the Unidad in Elasticsearch
        Unidad unidadEs = unidadSearchRepository.findOne(testUnidad.getId());
        assertThat(unidadEs).isEqualToIgnoringGivenFields(testUnidad);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidad() throws Exception {
        int databaseSizeBeforeUpdate = unidadRepository.findAll().size();

        // Create the Unidad

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUnidadMockMvc.perform(put("/api/unidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidad)))
            .andExpect(status().isCreated());

        // Validate the Unidad in the database
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUnidad() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);
        unidadSearchRepository.save(unidad);
        int databaseSizeBeforeDelete = unidadRepository.findAll().size();

        // Get the unidad
        restUnidadMockMvc.perform(delete("/api/unidads/{id}", unidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean unidadExistsInEs = unidadSearchRepository.exists(unidad.getId());
        assertThat(unidadExistsInEs).isFalse();

        // Validate the database is empty
        List<Unidad> unidadList = unidadRepository.findAll();
        assertThat(unidadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUnidad() throws Exception {
        // Initialize the database
        unidadRepository.saveAndFlush(unidad);
        unidadSearchRepository.save(unidad);

        // Search the unidad
        restUnidadMockMvc.perform(get("/api/_search/unidads?query=id:" + unidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nunidad").value(hasItem(DEFAULT_NUNIDAD.toString())))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unidad.class);
        Unidad unidad1 = new Unidad();
        unidad1.setId(1L);
        Unidad unidad2 = new Unidad();
        unidad2.setId(unidad1.getId());
        assertThat(unidad1).isEqualTo(unidad2);
        unidad2.setId(2L);
        assertThat(unidad1).isNotEqualTo(unidad2);
        unidad1.setId(null);
        assertThat(unidad1).isNotEqualTo(unidad2);
    }
}
