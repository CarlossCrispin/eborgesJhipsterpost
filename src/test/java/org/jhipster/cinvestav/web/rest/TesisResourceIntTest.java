package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.Tesis;
import org.jhipster.cinvestav.repository.TesisRepository;
import org.jhipster.cinvestav.repository.search.TesisSearchRepository;
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
 * Test class for the TesisResource REST controller.
 *
 * @see TesisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class TesisResourceIntTest {

    private static final String DEFAULT_TITULODETESIS = "AAAAAAAAAA";
    private static final String UPDATED_TITULODETESIS = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHADEPUBLICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHADEPUBLICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RESUMEN = "AAAAAAAAAA";
    private static final String UPDATED_RESUMEN = "BBBBBBBBBB";

    private static final String DEFAULT_CLASIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_CLASIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_CLASIFICACION_1 = "AAAAAAAAAA";
    private static final String UPDATED_CLASIFICACION_1 = "BBBBBBBBBB";

    private static final Long DEFAULT_ANIO = 1L;
    private static final Long UPDATED_ANIO = 2L;

    private static final Long DEFAULT_MES = 1L;
    private static final Long UPDATED_MES = 2L;

    @Autowired
    private TesisRepository tesisRepository;

    @Autowired
    private TesisSearchRepository tesisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTesisMockMvc;

    private Tesis tesis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TesisResource tesisResource = new TesisResource(tesisRepository, tesisSearchRepository);
        this.restTesisMockMvc = MockMvcBuilders.standaloneSetup(tesisResource)
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
    public static Tesis createEntity(EntityManager em) {
        Tesis tesis = new Tesis()
            .titulodetesis(DEFAULT_TITULODETESIS)
            .fechadepublicacion(DEFAULT_FECHADEPUBLICACION)
            .resumen(DEFAULT_RESUMEN)
            .clasificacion(DEFAULT_CLASIFICACION)
            .clasificacion1(DEFAULT_CLASIFICACION_1)
            .anio(DEFAULT_ANIO)
            .mes(DEFAULT_MES);
        return tesis;
    }

    @Before
    public void initTest() {
        tesisSearchRepository.deleteAll();
        tesis = createEntity(em);
    }

    @Test
    @Transactional
    public void createTesis() throws Exception {
        int databaseSizeBeforeCreate = tesisRepository.findAll().size();

        // Create the Tesis
        restTesisMockMvc.perform(post("/api/teses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesis)))
            .andExpect(status().isCreated());

        // Validate the Tesis in the database
        List<Tesis> tesisList = tesisRepository.findAll();
        assertThat(tesisList).hasSize(databaseSizeBeforeCreate + 1);
        Tesis testTesis = tesisList.get(tesisList.size() - 1);
        assertThat(testTesis.getTitulodetesis()).isEqualTo(DEFAULT_TITULODETESIS);
        assertThat(testTesis.getFechadepublicacion()).isEqualTo(DEFAULT_FECHADEPUBLICACION);
        assertThat(testTesis.getResumen()).isEqualTo(DEFAULT_RESUMEN);
        assertThat(testTesis.getClasificacion()).isEqualTo(DEFAULT_CLASIFICACION);
        assertThat(testTesis.getClasificacion1()).isEqualTo(DEFAULT_CLASIFICACION_1);
        assertThat(testTesis.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testTesis.getMes()).isEqualTo(DEFAULT_MES);

        // Validate the Tesis in Elasticsearch
        Tesis tesisEs = tesisSearchRepository.findOne(testTesis.getId());
        assertThat(tesisEs).isEqualToIgnoringGivenFields(testTesis);
    }

    @Test
    @Transactional
    public void createTesisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tesisRepository.findAll().size();

        // Create the Tesis with an existing ID
        tesis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTesisMockMvc.perform(post("/api/teses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesis)))
            .andExpect(status().isBadRequest());

        // Validate the Tesis in the database
        List<Tesis> tesisList = tesisRepository.findAll();
        assertThat(tesisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeses() throws Exception {
        // Initialize the database
        tesisRepository.saveAndFlush(tesis);

        // Get all the tesisList
        restTesisMockMvc.perform(get("/api/teses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tesis.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulodetesis").value(hasItem(DEFAULT_TITULODETESIS.toString())))
            .andExpect(jsonPath("$.[*].fechadepublicacion").value(hasItem(DEFAULT_FECHADEPUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].resumen").value(hasItem(DEFAULT_RESUMEN.toString())))
            .andExpect(jsonPath("$.[*].clasificacion").value(hasItem(DEFAULT_CLASIFICACION.toString())))
            .andExpect(jsonPath("$.[*].clasificacion1").value(hasItem(DEFAULT_CLASIFICACION_1.toString())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO.intValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.intValue())));
    }

    @Test
    @Transactional
    public void getTesis() throws Exception {
        // Initialize the database
        tesisRepository.saveAndFlush(tesis);

        // Get the tesis
        restTesisMockMvc.perform(get("/api/teses/{id}", tesis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tesis.getId().intValue()))
            .andExpect(jsonPath("$.titulodetesis").value(DEFAULT_TITULODETESIS.toString()))
            .andExpect(jsonPath("$.fechadepublicacion").value(DEFAULT_FECHADEPUBLICACION.toString()))
            .andExpect(jsonPath("$.resumen").value(DEFAULT_RESUMEN.toString()))
            .andExpect(jsonPath("$.clasificacion").value(DEFAULT_CLASIFICACION.toString()))
            .andExpect(jsonPath("$.clasificacion1").value(DEFAULT_CLASIFICACION_1.toString()))
            .andExpect(jsonPath("$.anio").value(DEFAULT_ANIO.intValue()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTesis() throws Exception {
        // Get the tesis
        restTesisMockMvc.perform(get("/api/teses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTesis() throws Exception {
        // Initialize the database
        tesisRepository.saveAndFlush(tesis);
        tesisSearchRepository.save(tesis);
        int databaseSizeBeforeUpdate = tesisRepository.findAll().size();

        // Update the tesis
        Tesis updatedTesis = tesisRepository.findOne(tesis.getId());
        // Disconnect from session so that the updates on updatedTesis are not directly saved in db
        em.detach(updatedTesis);
        updatedTesis
            .titulodetesis(UPDATED_TITULODETESIS)
            .fechadepublicacion(UPDATED_FECHADEPUBLICACION)
            .resumen(UPDATED_RESUMEN)
            .clasificacion(UPDATED_CLASIFICACION)
            .clasificacion1(UPDATED_CLASIFICACION_1)
            .anio(UPDATED_ANIO)
            .mes(UPDATED_MES);

        restTesisMockMvc.perform(put("/api/teses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTesis)))
            .andExpect(status().isOk());

        // Validate the Tesis in the database
        List<Tesis> tesisList = tesisRepository.findAll();
        assertThat(tesisList).hasSize(databaseSizeBeforeUpdate);
        Tesis testTesis = tesisList.get(tesisList.size() - 1);
        assertThat(testTesis.getTitulodetesis()).isEqualTo(UPDATED_TITULODETESIS);
        assertThat(testTesis.getFechadepublicacion()).isEqualTo(UPDATED_FECHADEPUBLICACION);
        assertThat(testTesis.getResumen()).isEqualTo(UPDATED_RESUMEN);
        assertThat(testTesis.getClasificacion()).isEqualTo(UPDATED_CLASIFICACION);
        assertThat(testTesis.getClasificacion1()).isEqualTo(UPDATED_CLASIFICACION_1);
        assertThat(testTesis.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testTesis.getMes()).isEqualTo(UPDATED_MES);

        // Validate the Tesis in Elasticsearch
        Tesis tesisEs = tesisSearchRepository.findOne(testTesis.getId());
        assertThat(tesisEs).isEqualToIgnoringGivenFields(testTesis);
    }

    @Test
    @Transactional
    public void updateNonExistingTesis() throws Exception {
        int databaseSizeBeforeUpdate = tesisRepository.findAll().size();

        // Create the Tesis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTesisMockMvc.perform(put("/api/teses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tesis)))
            .andExpect(status().isCreated());

        // Validate the Tesis in the database
        List<Tesis> tesisList = tesisRepository.findAll();
        assertThat(tesisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTesis() throws Exception {
        // Initialize the database
        tesisRepository.saveAndFlush(tesis);
        tesisSearchRepository.save(tesis);
        int databaseSizeBeforeDelete = tesisRepository.findAll().size();

        // Get the tesis
        restTesisMockMvc.perform(delete("/api/teses/{id}", tesis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tesisExistsInEs = tesisSearchRepository.exists(tesis.getId());
        assertThat(tesisExistsInEs).isFalse();

        // Validate the database is empty
        List<Tesis> tesisList = tesisRepository.findAll();
        assertThat(tesisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTesis() throws Exception {
        // Initialize the database
        tesisRepository.saveAndFlush(tesis);
        tesisSearchRepository.save(tesis);

        // Search the tesis
        restTesisMockMvc.perform(get("/api/_search/teses?query=id:" + tesis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tesis.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulodetesis").value(hasItem(DEFAULT_TITULODETESIS.toString())))
            .andExpect(jsonPath("$.[*].fechadepublicacion").value(hasItem(DEFAULT_FECHADEPUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].resumen").value(hasItem(DEFAULT_RESUMEN.toString())))
            .andExpect(jsonPath("$.[*].clasificacion").value(hasItem(DEFAULT_CLASIFICACION.toString())))
            .andExpect(jsonPath("$.[*].clasificacion1").value(hasItem(DEFAULT_CLASIFICACION_1.toString())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO.intValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tesis.class);
        Tesis tesis1 = new Tesis();
        tesis1.setId(1L);
        Tesis tesis2 = new Tesis();
        tesis2.setId(tesis1.getId());
        assertThat(tesis1).isEqualTo(tesis2);
        tesis2.setId(2L);
        assertThat(tesis1).isNotEqualTo(tesis2);
        tesis1.setId(null);
        assertThat(tesis1).isNotEqualTo(tesis2);
    }
}
