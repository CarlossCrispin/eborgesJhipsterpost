package org.jhipster.cinvestav.web.rest;

import org.jhipster.cinvestav.EBorgesApp;

import org.jhipster.cinvestav.domain.Investigador;
import org.jhipster.cinvestav.repository.InvestigadorRepository;
import org.jhipster.cinvestav.repository.search.InvestigadorSearchRepository;
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
 * Test class for the InvestigadorResource REST controller.
 *
 * @see InvestigadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EBorgesApp.class)
public class InvestigadorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_1 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_2 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_2 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESEXTERNO = false;
    private static final Boolean UPDATED_ESEXTERNO = true;

    @Autowired
    private InvestigadorRepository investigadorRepository;

    @Autowired
    private InvestigadorSearchRepository investigadorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInvestigadorMockMvc;

    private Investigador investigador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvestigadorResource investigadorResource = new InvestigadorResource(investigadorRepository, investigadorSearchRepository);
        this.restInvestigadorMockMvc = MockMvcBuilders.standaloneSetup(investigadorResource)
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
    public static Investigador createEntity(EntityManager em) {
        Investigador investigador = new Investigador()
            .nombre(DEFAULT_NOMBRE)
            .apellido1(DEFAULT_APELLIDO_1)
            .apellido2(DEFAULT_APELLIDO_2)
            .esexterno(DEFAULT_ESEXTERNO);
        return investigador;
    }

    @Before
    public void initTest() {
        investigadorSearchRepository.deleteAll();
        investigador = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvestigador() throws Exception {
        int databaseSizeBeforeCreate = investigadorRepository.findAll().size();

        // Create the Investigador
        restInvestigadorMockMvc.perform(post("/api/investigadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigador)))
            .andExpect(status().isCreated());

        // Validate the Investigador in the database
        List<Investigador> investigadorList = investigadorRepository.findAll();
        assertThat(investigadorList).hasSize(databaseSizeBeforeCreate + 1);
        Investigador testInvestigador = investigadorList.get(investigadorList.size() - 1);
        assertThat(testInvestigador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInvestigador.getApellido1()).isEqualTo(DEFAULT_APELLIDO_1);
        assertThat(testInvestigador.getApellido2()).isEqualTo(DEFAULT_APELLIDO_2);
        assertThat(testInvestigador.isEsexterno()).isEqualTo(DEFAULT_ESEXTERNO);

        // Validate the Investigador in Elasticsearch
        Investigador investigadorEs = investigadorSearchRepository.findOne(testInvestigador.getId());
        assertThat(investigadorEs).isEqualToIgnoringGivenFields(testInvestigador);
    }

    @Test
    @Transactional
    public void createInvestigadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = investigadorRepository.findAll().size();

        // Create the Investigador with an existing ID
        investigador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvestigadorMockMvc.perform(post("/api/investigadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigador)))
            .andExpect(status().isBadRequest());

        // Validate the Investigador in the database
        List<Investigador> investigadorList = investigadorRepository.findAll();
        assertThat(investigadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInvestigadors() throws Exception {
        // Initialize the database
        investigadorRepository.saveAndFlush(investigador);

        // Get all the investigadorList
        restInvestigadorMockMvc.perform(get("/api/investigadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investigador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1.toString())))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2.toString())))
            .andExpect(jsonPath("$.[*].esexterno").value(hasItem(DEFAULT_ESEXTERNO.booleanValue())));
    }

    @Test
    @Transactional
    public void getInvestigador() throws Exception {
        // Initialize the database
        investigadorRepository.saveAndFlush(investigador);

        // Get the investigador
        restInvestigadorMockMvc.perform(get("/api/investigadors/{id}", investigador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(investigador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido1").value(DEFAULT_APELLIDO_1.toString()))
            .andExpect(jsonPath("$.apellido2").value(DEFAULT_APELLIDO_2.toString()))
            .andExpect(jsonPath("$.esexterno").value(DEFAULT_ESEXTERNO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvestigador() throws Exception {
        // Get the investigador
        restInvestigadorMockMvc.perform(get("/api/investigadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestigador() throws Exception {
        // Initialize the database
        investigadorRepository.saveAndFlush(investigador);
        investigadorSearchRepository.save(investigador);
        int databaseSizeBeforeUpdate = investigadorRepository.findAll().size();

        // Update the investigador
        Investigador updatedInvestigador = investigadorRepository.findOne(investigador.getId());
        // Disconnect from session so that the updates on updatedInvestigador are not directly saved in db
        em.detach(updatedInvestigador);
        updatedInvestigador
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .esexterno(UPDATED_ESEXTERNO);

        restInvestigadorMockMvc.perform(put("/api/investigadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvestigador)))
            .andExpect(status().isOk());

        // Validate the Investigador in the database
        List<Investigador> investigadorList = investigadorRepository.findAll();
        assertThat(investigadorList).hasSize(databaseSizeBeforeUpdate);
        Investigador testInvestigador = investigadorList.get(investigadorList.size() - 1);
        assertThat(testInvestigador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInvestigador.getApellido1()).isEqualTo(UPDATED_APELLIDO_1);
        assertThat(testInvestigador.getApellido2()).isEqualTo(UPDATED_APELLIDO_2);
        assertThat(testInvestigador.isEsexterno()).isEqualTo(UPDATED_ESEXTERNO);

        // Validate the Investigador in Elasticsearch
        Investigador investigadorEs = investigadorSearchRepository.findOne(testInvestigador.getId());
        assertThat(investigadorEs).isEqualToIgnoringGivenFields(testInvestigador);
    }

    @Test
    @Transactional
    public void updateNonExistingInvestigador() throws Exception {
        int databaseSizeBeforeUpdate = investigadorRepository.findAll().size();

        // Create the Investigador

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInvestigadorMockMvc.perform(put("/api/investigadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investigador)))
            .andExpect(status().isCreated());

        // Validate the Investigador in the database
        List<Investigador> investigadorList = investigadorRepository.findAll();
        assertThat(investigadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInvestigador() throws Exception {
        // Initialize the database
        investigadorRepository.saveAndFlush(investigador);
        investigadorSearchRepository.save(investigador);
        int databaseSizeBeforeDelete = investigadorRepository.findAll().size();

        // Get the investigador
        restInvestigadorMockMvc.perform(delete("/api/investigadors/{id}", investigador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean investigadorExistsInEs = investigadorSearchRepository.exists(investigador.getId());
        assertThat(investigadorExistsInEs).isFalse();

        // Validate the database is empty
        List<Investigador> investigadorList = investigadorRepository.findAll();
        assertThat(investigadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInvestigador() throws Exception {
        // Initialize the database
        investigadorRepository.saveAndFlush(investigador);
        investigadorSearchRepository.save(investigador);

        // Search the investigador
        restInvestigadorMockMvc.perform(get("/api/_search/investigadors?query=id:" + investigador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investigador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1.toString())))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2.toString())))
            .andExpect(jsonPath("$.[*].esexterno").value(hasItem(DEFAULT_ESEXTERNO.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Investigador.class);
        Investigador investigador1 = new Investigador();
        investigador1.setId(1L);
        Investigador investigador2 = new Investigador();
        investigador2.setId(investigador1.getId());
        assertThat(investigador1).isEqualTo(investigador2);
        investigador2.setId(2L);
        assertThat(investigador1).isNotEqualTo(investigador2);
        investigador1.setId(null);
        assertThat(investigador1).isNotEqualTo(investigador2);
    }
}
