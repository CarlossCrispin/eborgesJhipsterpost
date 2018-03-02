package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.Alumno;
import org.jhipster.cinvestav.repository.AlumnoRepository;
import org.jhipster.cinvestav.repository.search.AlumnoSearchRepository;
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
 * Test class for the AlumnoResource REST controller.
 *
 * @see AlumnoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class AlumnoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_1 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_2 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_MATRICULA = 1;
    private static final Integer UPDATED_MATRICULA = 2;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AlumnoSearchRepository alumnoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlumnoMockMvc;

    private Alumno alumno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlumnoResource alumnoResource = new AlumnoResource(alumnoRepository, alumnoSearchRepository);
        this.restAlumnoMockMvc = MockMvcBuilders.standaloneSetup(alumnoResource)
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
    public static Alumno createEntity(EntityManager em) {
        Alumno alumno = new Alumno()
            .nombre(DEFAULT_NOMBRE)
            .apellido1(DEFAULT_APELLIDO_1)
            .apellido2(DEFAULT_APELLIDO_2)
            .matricula(DEFAULT_MATRICULA);
        return alumno;
    }

    @Before
    public void initTest() {
        alumnoSearchRepository.deleteAll();
        alumno = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlumno() throws Exception {
        int databaseSizeBeforeCreate = alumnoRepository.findAll().size();

        // Create the Alumno
        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isCreated());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeCreate + 1);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAlumno.getApellido1()).isEqualTo(DEFAULT_APELLIDO_1);
        assertThat(testAlumno.getApellido2()).isEqualTo(DEFAULT_APELLIDO_2);
        assertThat(testAlumno.getMatricula()).isEqualTo(DEFAULT_MATRICULA);

        // Validate the Alumno in Elasticsearch
        Alumno alumnoEs = alumnoSearchRepository.findOne(testAlumno.getId());
        assertThat(alumnoEs).isEqualToIgnoringGivenFields(testAlumno);
    }

    @Test
    @Transactional
    public void createAlumnoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alumnoRepository.findAll().size();

        // Create the Alumno with an existing ID
        alumno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlumnoMockMvc.perform(post("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isBadRequest());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlumnos() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get all the alumnoList
        restAlumnoMockMvc.perform(get("/api/alumnos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1.toString())))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2.toString())))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)));
    }

    @Test
    @Transactional
    public void getAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);

        // Get the alumno
        restAlumnoMockMvc.perform(get("/api/alumnos/{id}", alumno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alumno.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido1").value(DEFAULT_APELLIDO_1.toString()))
            .andExpect(jsonPath("$.apellido2").value(DEFAULT_APELLIDO_2.toString()))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA));
    }

    @Test
    @Transactional
    public void getNonExistingAlumno() throws Exception {
        // Get the alumno
        restAlumnoMockMvc.perform(get("/api/alumnos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);
        alumnoSearchRepository.save(alumno);
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Update the alumno
        Alumno updatedAlumno = alumnoRepository.findOne(alumno.getId());
        // Disconnect from session so that the updates on updatedAlumno are not directly saved in db
        em.detach(updatedAlumno);
        updatedAlumno
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .matricula(UPDATED_MATRICULA);

        restAlumnoMockMvc.perform(put("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlumno)))
            .andExpect(status().isOk());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate);
        Alumno testAlumno = alumnoList.get(alumnoList.size() - 1);
        assertThat(testAlumno.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAlumno.getApellido1()).isEqualTo(UPDATED_APELLIDO_1);
        assertThat(testAlumno.getApellido2()).isEqualTo(UPDATED_APELLIDO_2);
        assertThat(testAlumno.getMatricula()).isEqualTo(UPDATED_MATRICULA);

        // Validate the Alumno in Elasticsearch
        Alumno alumnoEs = alumnoSearchRepository.findOne(testAlumno.getId());
        assertThat(alumnoEs).isEqualToIgnoringGivenFields(testAlumno);
    }

    @Test
    @Transactional
    public void updateNonExistingAlumno() throws Exception {
        int databaseSizeBeforeUpdate = alumnoRepository.findAll().size();

        // Create the Alumno

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlumnoMockMvc.perform(put("/api/alumnos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alumno)))
            .andExpect(status().isCreated());

        // Validate the Alumno in the database
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);
        alumnoSearchRepository.save(alumno);
        int databaseSizeBeforeDelete = alumnoRepository.findAll().size();

        // Get the alumno
        restAlumnoMockMvc.perform(delete("/api/alumnos/{id}", alumno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean alumnoExistsInEs = alumnoSearchRepository.exists(alumno.getId());
        assertThat(alumnoExistsInEs).isFalse();

        // Validate the database is empty
        List<Alumno> alumnoList = alumnoRepository.findAll();
        assertThat(alumnoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAlumno() throws Exception {
        // Initialize the database
        alumnoRepository.saveAndFlush(alumno);
        alumnoSearchRepository.save(alumno);

        // Search the alumno
        restAlumnoMockMvc.perform(get("/api/_search/alumnos?query=id:" + alumno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumno.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1.toString())))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2.toString())))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alumno.class);
        Alumno alumno1 = new Alumno();
        alumno1.setId(1L);
        Alumno alumno2 = new Alumno();
        alumno2.setId(alumno1.getId());
        assertThat(alumno1).isEqualTo(alumno2);
        alumno2.setId(2L);
        assertThat(alumno1).isNotEqualTo(alumno2);
        alumno1.setId(null);
        assertThat(alumno1).isNotEqualTo(alumno2);
    }
}
