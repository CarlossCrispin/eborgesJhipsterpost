package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.Especialidad;
import org.jhipster.cinvestav.repository.EspecialidadRepository;
import org.jhipster.cinvestav.repository.search.EspecialidadSearchRepository;
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
 * Test class for the EspecialidadResource REST controller.
 *
 * @see EspecialidadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class EspecialidadResourceIntTest {

    private static final String DEFAULT_NESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_NESPECIALIDAD = "BBBBBBBBBB";

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private EspecialidadSearchRepository especialidadSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEspecialidadMockMvc;

    private Especialidad especialidad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspecialidadResource especialidadResource = new EspecialidadResource(especialidadRepository, especialidadSearchRepository);
        this.restEspecialidadMockMvc = MockMvcBuilders.standaloneSetup(especialidadResource)
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
    public static Especialidad createEntity(EntityManager em) {
        Especialidad especialidad = new Especialidad()
            .nespecialidad(DEFAULT_NESPECIALIDAD);
        return especialidad;
    }

    @Before
    public void initTest() {
        especialidadSearchRepository.deleteAll();
        especialidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspecialidad() throws Exception {
        int databaseSizeBeforeCreate = especialidadRepository.findAll().size();

        // Create the Especialidad
        restEspecialidadMockMvc.perform(post("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidad)))
            .andExpect(status().isCreated());

        // Validate the Especialidad in the database
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeCreate + 1);
        Especialidad testEspecialidad = especialidadList.get(especialidadList.size() - 1);
        assertThat(testEspecialidad.getNespecialidad()).isEqualTo(DEFAULT_NESPECIALIDAD);

        // Validate the Especialidad in Elasticsearch
        Especialidad especialidadEs = especialidadSearchRepository.findOne(testEspecialidad.getId());
        assertThat(especialidadEs).isEqualToIgnoringGivenFields(testEspecialidad);
    }

    @Test
    @Transactional
    public void createEspecialidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = especialidadRepository.findAll().size();

        // Create the Especialidad with an existing ID
        especialidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecialidadMockMvc.perform(post("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidad)))
            .andExpect(status().isBadRequest());

        // Validate the Especialidad in the database
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEspecialidads() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);

        // Get all the especialidadList
        restEspecialidadMockMvc.perform(get("/api/especialidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nespecialidad").value(hasItem(DEFAULT_NESPECIALIDAD.toString())));
    }

    @Test
    @Transactional
    public void getEspecialidad() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);

        // Get the especialidad
        restEspecialidadMockMvc.perform(get("/api/especialidads/{id}", especialidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(especialidad.getId().intValue()))
            .andExpect(jsonPath("$.nespecialidad").value(DEFAULT_NESPECIALIDAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEspecialidad() throws Exception {
        // Get the especialidad
        restEspecialidadMockMvc.perform(get("/api/especialidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspecialidad() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);
        especialidadSearchRepository.save(especialidad);
        int databaseSizeBeforeUpdate = especialidadRepository.findAll().size();

        // Update the especialidad
        Especialidad updatedEspecialidad = especialidadRepository.findOne(especialidad.getId());
        // Disconnect from session so that the updates on updatedEspecialidad are not directly saved in db
        em.detach(updatedEspecialidad);
        updatedEspecialidad
            .nespecialidad(UPDATED_NESPECIALIDAD);

        restEspecialidadMockMvc.perform(put("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEspecialidad)))
            .andExpect(status().isOk());

        // Validate the Especialidad in the database
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeUpdate);
        Especialidad testEspecialidad = especialidadList.get(especialidadList.size() - 1);
        assertThat(testEspecialidad.getNespecialidad()).isEqualTo(UPDATED_NESPECIALIDAD);

        // Validate the Especialidad in Elasticsearch
        Especialidad especialidadEs = especialidadSearchRepository.findOne(testEspecialidad.getId());
        assertThat(especialidadEs).isEqualToIgnoringGivenFields(testEspecialidad);
    }

    @Test
    @Transactional
    public void updateNonExistingEspecialidad() throws Exception {
        int databaseSizeBeforeUpdate = especialidadRepository.findAll().size();

        // Create the Especialidad

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEspecialidadMockMvc.perform(put("/api/especialidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especialidad)))
            .andExpect(status().isCreated());

        // Validate the Especialidad in the database
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEspecialidad() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);
        especialidadSearchRepository.save(especialidad);
        int databaseSizeBeforeDelete = especialidadRepository.findAll().size();

        // Get the especialidad
        restEspecialidadMockMvc.perform(delete("/api/especialidads/{id}", especialidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean especialidadExistsInEs = especialidadSearchRepository.exists(especialidad.getId());
        assertThat(especialidadExistsInEs).isFalse();

        // Validate the database is empty
        List<Especialidad> especialidadList = especialidadRepository.findAll();
        assertThat(especialidadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEspecialidad() throws Exception {
        // Initialize the database
        especialidadRepository.saveAndFlush(especialidad);
        especialidadSearchRepository.save(especialidad);

        // Search the especialidad
        restEspecialidadMockMvc.perform(get("/api/_search/especialidads?query=id:" + especialidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nespecialidad").value(hasItem(DEFAULT_NESPECIALIDAD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especialidad.class);
        Especialidad especialidad1 = new Especialidad();
        especialidad1.setId(1L);
        Especialidad especialidad2 = new Especialidad();
        especialidad2.setId(especialidad1.getId());
        assertThat(especialidad1).isEqualTo(especialidad2);
        especialidad2.setId(2L);
        assertThat(especialidad1).isNotEqualTo(especialidad2);
        especialidad1.setId(null);
        assertThat(especialidad1).isNotEqualTo(especialidad2);
    }
}
