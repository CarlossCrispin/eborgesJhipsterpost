package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.Grado;
import org.jhipster.cinvestav.repository.GradoRepository;
import org.jhipster.cinvestav.repository.search.GradoSearchRepository;
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
 * Test class for the GradoResource REST controller.
 *
 * @see GradoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class GradoResourceIntTest {

    private static final String DEFAULT_NGRADO = "AAAAAAAAAA";
    private static final String UPDATED_NGRADO = "BBBBBBBBBB";

    @Autowired
    private GradoRepository gradoRepository;

    @Autowired
    private GradoSearchRepository gradoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGradoMockMvc;

    private Grado grado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GradoResource gradoResource = new GradoResource(gradoRepository, gradoSearchRepository);
        this.restGradoMockMvc = MockMvcBuilders.standaloneSetup(gradoResource)
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
    public static Grado createEntity(EntityManager em) {
        Grado grado = new Grado()
            .ngrado(DEFAULT_NGRADO);
        return grado;
    }

    @Before
    public void initTest() {
        gradoSearchRepository.deleteAll();
        grado = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrado() throws Exception {
        int databaseSizeBeforeCreate = gradoRepository.findAll().size();

        // Create the Grado
        restGradoMockMvc.perform(post("/api/grados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grado)))
            .andExpect(status().isCreated());

        // Validate the Grado in the database
        List<Grado> gradoList = gradoRepository.findAll();
        assertThat(gradoList).hasSize(databaseSizeBeforeCreate + 1);
        Grado testGrado = gradoList.get(gradoList.size() - 1);
        assertThat(testGrado.getNgrado()).isEqualTo(DEFAULT_NGRADO);

        // Validate the Grado in Elasticsearch
        Grado gradoEs = gradoSearchRepository.findOne(testGrado.getId());
        assertThat(gradoEs).isEqualToIgnoringGivenFields(testGrado);
    }

    @Test
    @Transactional
    public void createGradoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradoRepository.findAll().size();

        // Create the Grado with an existing ID
        grado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradoMockMvc.perform(post("/api/grados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grado)))
            .andExpect(status().isBadRequest());

        // Validate the Grado in the database
        List<Grado> gradoList = gradoRepository.findAll();
        assertThat(gradoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGrados() throws Exception {
        // Initialize the database
        gradoRepository.saveAndFlush(grado);

        // Get all the gradoList
        restGradoMockMvc.perform(get("/api/grados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grado.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngrado").value(hasItem(DEFAULT_NGRADO.toString())));
    }

    @Test
    @Transactional
    public void getGrado() throws Exception {
        // Initialize the database
        gradoRepository.saveAndFlush(grado);

        // Get the grado
        restGradoMockMvc.perform(get("/api/grados/{id}", grado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grado.getId().intValue()))
            .andExpect(jsonPath("$.ngrado").value(DEFAULT_NGRADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrado() throws Exception {
        // Get the grado
        restGradoMockMvc.perform(get("/api/grados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrado() throws Exception {
        // Initialize the database
        gradoRepository.saveAndFlush(grado);
        gradoSearchRepository.save(grado);
        int databaseSizeBeforeUpdate = gradoRepository.findAll().size();

        // Update the grado
        Grado updatedGrado = gradoRepository.findOne(grado.getId());
        // Disconnect from session so that the updates on updatedGrado are not directly saved in db
        em.detach(updatedGrado);
        updatedGrado
            .ngrado(UPDATED_NGRADO);

        restGradoMockMvc.perform(put("/api/grados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrado)))
            .andExpect(status().isOk());

        // Validate the Grado in the database
        List<Grado> gradoList = gradoRepository.findAll();
        assertThat(gradoList).hasSize(databaseSizeBeforeUpdate);
        Grado testGrado = gradoList.get(gradoList.size() - 1);
        assertThat(testGrado.getNgrado()).isEqualTo(UPDATED_NGRADO);

        // Validate the Grado in Elasticsearch
        Grado gradoEs = gradoSearchRepository.findOne(testGrado.getId());
        assertThat(gradoEs).isEqualToIgnoringGivenFields(testGrado);
    }

    @Test
    @Transactional
    public void updateNonExistingGrado() throws Exception {
        int databaseSizeBeforeUpdate = gradoRepository.findAll().size();

        // Create the Grado

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGradoMockMvc.perform(put("/api/grados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grado)))
            .andExpect(status().isCreated());

        // Validate the Grado in the database
        List<Grado> gradoList = gradoRepository.findAll();
        assertThat(gradoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGrado() throws Exception {
        // Initialize the database
        gradoRepository.saveAndFlush(grado);
        gradoSearchRepository.save(grado);
        int databaseSizeBeforeDelete = gradoRepository.findAll().size();

        // Get the grado
        restGradoMockMvc.perform(delete("/api/grados/{id}", grado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gradoExistsInEs = gradoSearchRepository.exists(grado.getId());
        assertThat(gradoExistsInEs).isFalse();

        // Validate the database is empty
        List<Grado> gradoList = gradoRepository.findAll();
        assertThat(gradoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGrado() throws Exception {
        // Initialize the database
        gradoRepository.saveAndFlush(grado);
        gradoSearchRepository.save(grado);

        // Search the grado
        restGradoMockMvc.perform(get("/api/_search/grados?query=id:" + grado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grado.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngrado").value(hasItem(DEFAULT_NGRADO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grado.class);
        Grado grado1 = new Grado();
        grado1.setId(1L);
        Grado grado2 = new Grado();
        grado2.setId(grado1.getId());
        assertThat(grado1).isEqualTo(grado2);
        grado2.setId(2L);
        assertThat(grado1).isNotEqualTo(grado2);
        grado1.setId(null);
        assertThat(grado1).isNotEqualTo(grado2);
    }
}
