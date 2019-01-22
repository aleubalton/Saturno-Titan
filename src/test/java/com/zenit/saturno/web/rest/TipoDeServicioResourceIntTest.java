package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.TipoDeServicio;
import com.zenit.saturno.domain.Servicio;
import com.zenit.saturno.repository.TipoDeServicioRepository;
import com.zenit.saturno.service.TipoDeServicioService;
import com.zenit.saturno.service.dto.TipoDeServicioDTO;
import com.zenit.saturno.service.mapper.TipoDeServicioMapper;
import com.zenit.saturno.web.rest.errors.ExceptionTranslator;

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


import static com.zenit.saturno.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.zenit.saturno.domain.enumeration.TipoRecurso;
/**
 * Test class for the TipoDeServicioResource REST controller.
 *
 * @see TipoDeServicioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class TipoDeServicioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAA";
    private static final String UPDATED_CODIGO = "BBBB";

    private static final Boolean DEFAULT_INTERNO = false;
    private static final Boolean UPDATED_INTERNO = true;

    private static final TipoRecurso DEFAULT_TIPO_DE_RECURSO = TipoRecurso.BAHIA;
    private static final TipoRecurso UPDATED_TIPO_DE_RECURSO = TipoRecurso.LAVADERO;

    @Autowired
    private TipoDeServicioRepository tipoDeServicioRepository;

    @Autowired
    private TipoDeServicioMapper tipoDeServicioMapper;

    @Autowired
    private TipoDeServicioService tipoDeServicioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoDeServicioMockMvc;

    private TipoDeServicio tipoDeServicio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDeServicioResource tipoDeServicioResource = new TipoDeServicioResource(tipoDeServicioService);
        this.restTipoDeServicioMockMvc = MockMvcBuilders.standaloneSetup(tipoDeServicioResource)
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
    public static TipoDeServicio createEntity(EntityManager em) {
        TipoDeServicio tipoDeServicio = new TipoDeServicio()
            .nombre(DEFAULT_NOMBRE)
            .codigo(DEFAULT_CODIGO)
            .interno(DEFAULT_INTERNO)
            .tipoDeRecurso(DEFAULT_TIPO_DE_RECURSO);
        // Add required entity
        Servicio servicio = ServicioResourceIntTest.createEntity(em);
        em.persist(servicio);
        em.flush();
        tipoDeServicio.getServicios().add(servicio);
        return tipoDeServicio;
    }

    @Before
    public void initTest() {
        tipoDeServicio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDeServicio() throws Exception {
        int databaseSizeBeforeCreate = tipoDeServicioRepository.findAll().size();

        // Create the TipoDeServicio
        TipoDeServicioDTO tipoDeServicioDTO = tipoDeServicioMapper.toDto(tipoDeServicio);
        restTipoDeServicioMockMvc.perform(post("/api/tipo-de-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeServicioDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDeServicio in the database
        List<TipoDeServicio> tipoDeServicioList = tipoDeServicioRepository.findAll();
        assertThat(tipoDeServicioList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeServicio testTipoDeServicio = tipoDeServicioList.get(tipoDeServicioList.size() - 1);
        assertThat(testTipoDeServicio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoDeServicio.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoDeServicio.isInterno()).isEqualTo(DEFAULT_INTERNO);
        assertThat(testTipoDeServicio.getTipoDeRecurso()).isEqualTo(DEFAULT_TIPO_DE_RECURSO);
    }

    @Test
    @Transactional
    public void createTipoDeServicioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDeServicioRepository.findAll().size();

        // Create the TipoDeServicio with an existing ID
        tipoDeServicio.setId(1L);
        TipoDeServicioDTO tipoDeServicioDTO = tipoDeServicioMapper.toDto(tipoDeServicio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDeServicioMockMvc.perform(post("/api/tipo-de-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeServicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeServicio in the database
        List<TipoDeServicio> tipoDeServicioList = tipoDeServicioRepository.findAll();
        assertThat(tipoDeServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeServicioRepository.findAll().size();
        // set the field null
        tipoDeServicio.setNombre(null);

        // Create the TipoDeServicio, which fails.
        TipoDeServicioDTO tipoDeServicioDTO = tipoDeServicioMapper.toDto(tipoDeServicio);

        restTipoDeServicioMockMvc.perform(post("/api/tipo-de-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeServicioDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDeServicio> tipoDeServicioList = tipoDeServicioRepository.findAll();
        assertThat(tipoDeServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeServicioRepository.findAll().size();
        // set the field null
        tipoDeServicio.setCodigo(null);

        // Create the TipoDeServicio, which fails.
        TipoDeServicioDTO tipoDeServicioDTO = tipoDeServicioMapper.toDto(tipoDeServicio);

        restTipoDeServicioMockMvc.perform(post("/api/tipo-de-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeServicioDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDeServicio> tipoDeServicioList = tipoDeServicioRepository.findAll();
        assertThat(tipoDeServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDeServicios() throws Exception {
        // Initialize the database
        tipoDeServicioRepository.saveAndFlush(tipoDeServicio);

        // Get all the tipoDeServicioList
        restTipoDeServicioMockMvc.perform(get("/api/tipo-de-servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].interno").value(hasItem(DEFAULT_INTERNO.booleanValue())))
            .andExpect(jsonPath("$.[*].tipoDeRecurso").value(hasItem(DEFAULT_TIPO_DE_RECURSO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoDeServicio() throws Exception {
        // Initialize the database
        tipoDeServicioRepository.saveAndFlush(tipoDeServicio);

        // Get the tipoDeServicio
        restTipoDeServicioMockMvc.perform(get("/api/tipo-de-servicios/{id}", tipoDeServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeServicio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.interno").value(DEFAULT_INTERNO.booleanValue()))
            .andExpect(jsonPath("$.tipoDeRecurso").value(DEFAULT_TIPO_DE_RECURSO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDeServicio() throws Exception {
        // Get the tipoDeServicio
        restTipoDeServicioMockMvc.perform(get("/api/tipo-de-servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDeServicio() throws Exception {
        // Initialize the database
        tipoDeServicioRepository.saveAndFlush(tipoDeServicio);

        int databaseSizeBeforeUpdate = tipoDeServicioRepository.findAll().size();

        // Update the tipoDeServicio
        TipoDeServicio updatedTipoDeServicio = tipoDeServicioRepository.findById(tipoDeServicio.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeServicio are not directly saved in db
        em.detach(updatedTipoDeServicio);
        updatedTipoDeServicio
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .interno(UPDATED_INTERNO)
            .tipoDeRecurso(UPDATED_TIPO_DE_RECURSO);
        TipoDeServicioDTO tipoDeServicioDTO = tipoDeServicioMapper.toDto(updatedTipoDeServicio);

        restTipoDeServicioMockMvc.perform(put("/api/tipo-de-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeServicioDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDeServicio in the database
        List<TipoDeServicio> tipoDeServicioList = tipoDeServicioRepository.findAll();
        assertThat(tipoDeServicioList).hasSize(databaseSizeBeforeUpdate);
        TipoDeServicio testTipoDeServicio = tipoDeServicioList.get(tipoDeServicioList.size() - 1);
        assertThat(testTipoDeServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoDeServicio.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoDeServicio.isInterno()).isEqualTo(UPDATED_INTERNO);
        assertThat(testTipoDeServicio.getTipoDeRecurso()).isEqualTo(UPDATED_TIPO_DE_RECURSO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDeServicio() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeServicioRepository.findAll().size();

        // Create the TipoDeServicio
        TipoDeServicioDTO tipoDeServicioDTO = tipoDeServicioMapper.toDto(tipoDeServicio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeServicioMockMvc.perform(put("/api/tipo-de-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeServicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeServicio in the database
        List<TipoDeServicio> tipoDeServicioList = tipoDeServicioRepository.findAll();
        assertThat(tipoDeServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoDeServicio() throws Exception {
        // Initialize the database
        tipoDeServicioRepository.saveAndFlush(tipoDeServicio);

        int databaseSizeBeforeDelete = tipoDeServicioRepository.findAll().size();

        // Get the tipoDeServicio
        restTipoDeServicioMockMvc.perform(delete("/api/tipo-de-servicios/{id}", tipoDeServicio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoDeServicio> tipoDeServicioList = tipoDeServicioRepository.findAll();
        assertThat(tipoDeServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeServicio.class);
        TipoDeServicio tipoDeServicio1 = new TipoDeServicio();
        tipoDeServicio1.setId(1L);
        TipoDeServicio tipoDeServicio2 = new TipoDeServicio();
        tipoDeServicio2.setId(tipoDeServicio1.getId());
        assertThat(tipoDeServicio1).isEqualTo(tipoDeServicio2);
        tipoDeServicio2.setId(2L);
        assertThat(tipoDeServicio1).isNotEqualTo(tipoDeServicio2);
        tipoDeServicio1.setId(null);
        assertThat(tipoDeServicio1).isNotEqualTo(tipoDeServicio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeServicioDTO.class);
        TipoDeServicioDTO tipoDeServicioDTO1 = new TipoDeServicioDTO();
        tipoDeServicioDTO1.setId(1L);
        TipoDeServicioDTO tipoDeServicioDTO2 = new TipoDeServicioDTO();
        assertThat(tipoDeServicioDTO1).isNotEqualTo(tipoDeServicioDTO2);
        tipoDeServicioDTO2.setId(tipoDeServicioDTO1.getId());
        assertThat(tipoDeServicioDTO1).isEqualTo(tipoDeServicioDTO2);
        tipoDeServicioDTO2.setId(2L);
        assertThat(tipoDeServicioDTO1).isNotEqualTo(tipoDeServicioDTO2);
        tipoDeServicioDTO1.setId(null);
        assertThat(tipoDeServicioDTO1).isNotEqualTo(tipoDeServicioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDeServicioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDeServicioMapper.fromId(null)).isNull();
    }
}
