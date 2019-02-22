package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.TipoServicio;
import com.zenit.saturno.domain.Servicio;
import com.zenit.saturno.repository.TipoServicioRepository;
import com.zenit.saturno.service.TipoServicioService;
import com.zenit.saturno.service.dto.TipoServicioDTO;
import com.zenit.saturno.service.mapper.TipoServicioMapper;
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
 * Test class for the TipoServicioResource REST controller.
 *
 * @see TipoServicioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class TipoServicioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAA";
    private static final String UPDATED_CODIGO = "BBBB";

    private static final Boolean DEFAULT_INTERNO = false;
    private static final Boolean UPDATED_INTERNO = true;

    private static final TipoRecurso DEFAULT_TIPO_RECURSO = TipoRecurso.BAHIA;
    private static final TipoRecurso UPDATED_TIPO_RECURSO = TipoRecurso.LAVADERO;

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Autowired
    private TipoServicioMapper tipoServicioMapper;

    @Autowired
    private TipoServicioService tipoServicioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoServicioMockMvc;

    private TipoServicio tipoServicio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoServicioResource tipoServicioResource = new TipoServicioResource(tipoServicioService);
        this.restTipoServicioMockMvc = MockMvcBuilders.standaloneSetup(tipoServicioResource)
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
    public static TipoServicio createEntity(EntityManager em) {
        TipoServicio tipoServicio = new TipoServicio()
            .nombre(DEFAULT_NOMBRE)
            .codigo(DEFAULT_CODIGO)
            .interno(DEFAULT_INTERNO)
            .tipoRecurso(DEFAULT_TIPO_RECURSO);
        // Add required entity
        Servicio servicio = ServicioResourceIntTest.createEntity(em);
        em.persist(servicio);
        em.flush();
        tipoServicio.getServicios().add(servicio);
        return tipoServicio;
    }

    @Before
    public void initTest() {
        tipoServicio = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoServicio() throws Exception {
        int databaseSizeBeforeCreate = tipoServicioRepository.findAll().size();

        // Create the TipoServicio
        TipoServicioDTO tipoServicioDTO = tipoServicioMapper.toDto(tipoServicio);
        restTipoServicioMockMvc.perform(post("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicioDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoServicio in the database
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeCreate + 1);
        TipoServicio testTipoServicio = tipoServicioList.get(tipoServicioList.size() - 1);
        assertThat(testTipoServicio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoServicio.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoServicio.isInterno()).isEqualTo(DEFAULT_INTERNO);
        assertThat(testTipoServicio.getTipoRecurso()).isEqualTo(DEFAULT_TIPO_RECURSO);
    }

    @Test
    @Transactional
    public void createTipoServicioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoServicioRepository.findAll().size();

        // Create the TipoServicio with an existing ID
        tipoServicio.setId(1L);
        TipoServicioDTO tipoServicioDTO = tipoServicioMapper.toDto(tipoServicio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoServicioMockMvc.perform(post("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoServicio in the database
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoServicioRepository.findAll().size();
        // set the field null
        tipoServicio.setNombre(null);

        // Create the TipoServicio, which fails.
        TipoServicioDTO tipoServicioDTO = tipoServicioMapper.toDto(tipoServicio);

        restTipoServicioMockMvc.perform(post("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicioDTO)))
            .andExpect(status().isBadRequest());

        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoServicioRepository.findAll().size();
        // set the field null
        tipoServicio.setCodigo(null);

        // Create the TipoServicio, which fails.
        TipoServicioDTO tipoServicioDTO = tipoServicioMapper.toDto(tipoServicio);

        restTipoServicioMockMvc.perform(post("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicioDTO)))
            .andExpect(status().isBadRequest());

        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoServicios() throws Exception {
        // Initialize the database
        tipoServicioRepository.saveAndFlush(tipoServicio);

        // Get all the tipoServicioList
        restTipoServicioMockMvc.perform(get("/api/tipo-servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].interno").value(hasItem(DEFAULT_INTERNO.booleanValue())))
            .andExpect(jsonPath("$.[*].tipoRecurso").value(hasItem(DEFAULT_TIPO_RECURSO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoServicio() throws Exception {
        // Initialize the database
        tipoServicioRepository.saveAndFlush(tipoServicio);

        // Get the tipoServicio
        restTipoServicioMockMvc.perform(get("/api/tipo-servicios/{id}", tipoServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoServicio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.interno").value(DEFAULT_INTERNO.booleanValue()))
            .andExpect(jsonPath("$.tipoRecurso").value(DEFAULT_TIPO_RECURSO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoServicio() throws Exception {
        // Get the tipoServicio
        restTipoServicioMockMvc.perform(get("/api/tipo-servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoServicio() throws Exception {
        // Initialize the database
        tipoServicioRepository.saveAndFlush(tipoServicio);

        int databaseSizeBeforeUpdate = tipoServicioRepository.findAll().size();

        // Update the tipoServicio
        TipoServicio updatedTipoServicio = tipoServicioRepository.findById(tipoServicio.getId()).get();
        // Disconnect from session so that the updates on updatedTipoServicio are not directly saved in db
        em.detach(updatedTipoServicio);
        updatedTipoServicio
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .interno(UPDATED_INTERNO)
            .tipoRecurso(UPDATED_TIPO_RECURSO);
        TipoServicioDTO tipoServicioDTO = tipoServicioMapper.toDto(updatedTipoServicio);

        restTipoServicioMockMvc.perform(put("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicioDTO)))
            .andExpect(status().isOk());

        // Validate the TipoServicio in the database
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeUpdate);
        TipoServicio testTipoServicio = tipoServicioList.get(tipoServicioList.size() - 1);
        assertThat(testTipoServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoServicio.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoServicio.isInterno()).isEqualTo(UPDATED_INTERNO);
        assertThat(testTipoServicio.getTipoRecurso()).isEqualTo(UPDATED_TIPO_RECURSO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoServicio() throws Exception {
        int databaseSizeBeforeUpdate = tipoServicioRepository.findAll().size();

        // Create the TipoServicio
        TipoServicioDTO tipoServicioDTO = tipoServicioMapper.toDto(tipoServicio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoServicioMockMvc.perform(put("/api/tipo-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoServicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoServicio in the database
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoServicio() throws Exception {
        // Initialize the database
        tipoServicioRepository.saveAndFlush(tipoServicio);

        int databaseSizeBeforeDelete = tipoServicioRepository.findAll().size();

        // Get the tipoServicio
        restTipoServicioMockMvc.perform(delete("/api/tipo-servicios/{id}", tipoServicio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoServicio> tipoServicioList = tipoServicioRepository.findAll();
        assertThat(tipoServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoServicio.class);
        TipoServicio tipoServicio1 = new TipoServicio();
        tipoServicio1.setId(1L);
        TipoServicio tipoServicio2 = new TipoServicio();
        tipoServicio2.setId(tipoServicio1.getId());
        assertThat(tipoServicio1).isEqualTo(tipoServicio2);
        tipoServicio2.setId(2L);
        assertThat(tipoServicio1).isNotEqualTo(tipoServicio2);
        tipoServicio1.setId(null);
        assertThat(tipoServicio1).isNotEqualTo(tipoServicio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoServicioDTO.class);
        TipoServicioDTO tipoServicioDTO1 = new TipoServicioDTO();
        tipoServicioDTO1.setId(1L);
        TipoServicioDTO tipoServicioDTO2 = new TipoServicioDTO();
        assertThat(tipoServicioDTO1).isNotEqualTo(tipoServicioDTO2);
        tipoServicioDTO2.setId(tipoServicioDTO1.getId());
        assertThat(tipoServicioDTO1).isEqualTo(tipoServicioDTO2);
        tipoServicioDTO2.setId(2L);
        assertThat(tipoServicioDTO1).isNotEqualTo(tipoServicioDTO2);
        tipoServicioDTO1.setId(null);
        assertThat(tipoServicioDTO1).isNotEqualTo(tipoServicioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoServicioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoServicioMapper.fromId(null)).isNull();
    }
}
