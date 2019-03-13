package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.Tarea;
import com.zenit.saturno.domain.Servicio;
import com.zenit.saturno.repository.TareaRepository;
import com.zenit.saturno.service.TareaService;
import com.zenit.saturno.service.dto.TareaDTO;
import com.zenit.saturno.service.mapper.TareaMapper;
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

import com.zenit.saturno.domain.enumeration.TipoTarea;
/**
 * Test class for the TareaResource REST controller.
 *
 * @see TareaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class TareaResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAA";
    private static final String UPDATED_CODIGO = "BBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final TipoTarea DEFAULT_TIPO = TipoTarea.CAMBIO;
    private static final TipoTarea UPDATED_TIPO = TipoTarea.INSPECCION;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private TareaMapper tareaMapper;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTareaMockMvc;

    private Tarea tarea;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TareaResource tareaResource = new TareaResource(tareaService);
        this.restTareaMockMvc = MockMvcBuilders.standaloneSetup(tareaResource)
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
    public static Tarea createEntity(EntityManager em) {
        Tarea tarea = new Tarea()
            .codigo(DEFAULT_CODIGO)
            .descripcion(DEFAULT_DESCRIPCION)
            .tipo(DEFAULT_TIPO);
        // Add required entity
        Servicio servicio = ServicioResourceIntTest.createEntity(em);
        em.persist(servicio);
        em.flush();
        tarea.getServicios().add(servicio);
        return tarea;
    }

    @Before
    public void initTest() {
        tarea = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarea() throws Exception {
        int databaseSizeBeforeCreate = tareaRepository.findAll().size();

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);
        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTarea.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTarea.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createTareaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tareaRepository.findAll().size();

        // Create the Tarea with an existing ID
        tarea.setId(1L);
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tareaRepository.findAll().size();
        // set the field null
        tarea.setCodigo(null);

        // Create the Tarea, which fails.
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tareaRepository.findAll().size();
        // set the field null
        tarea.setDescripcion(null);

        // Create the Tarea, which fails.
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tareaRepository.findAll().size();
        // set the field null
        tarea.setTipo(null);

        // Create the Tarea, which fails.
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        restTareaMockMvc.perform(post("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTareas() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get all the tareaList
        restTareaMockMvc.perform(get("/api/tareas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarea.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }
    
    @Test
    @Transactional
    public void getTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        // Get the tarea
        restTareaMockMvc.perform(get("/api/tareas/{id}", tarea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarea.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTarea() throws Exception {
        // Get the tarea
        restTareaMockMvc.perform(get("/api/tareas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Update the tarea
        Tarea updatedTarea = tareaRepository.findById(tarea.getId()).get();
        // Disconnect from session so that the updates on updatedTarea are not directly saved in db
        em.detach(updatedTarea);
        updatedTarea
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION)
            .tipo(UPDATED_TIPO);
        TareaDTO tareaDTO = tareaMapper.toDto(updatedTarea);

        restTareaMockMvc.perform(put("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isOk());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
        Tarea testTarea = tareaList.get(tareaList.size() - 1);
        assertThat(testTarea.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTarea.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTarea.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingTarea() throws Exception {
        int databaseSizeBeforeUpdate = tareaRepository.findAll().size();

        // Create the Tarea
        TareaDTO tareaDTO = tareaMapper.toDto(tarea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTareaMockMvc.perform(put("/api/tareas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tareaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarea in the database
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarea() throws Exception {
        // Initialize the database
        tareaRepository.saveAndFlush(tarea);

        int databaseSizeBeforeDelete = tareaRepository.findAll().size();

        // Get the tarea
        restTareaMockMvc.perform(delete("/api/tareas/{id}", tarea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tarea> tareaList = tareaRepository.findAll();
        assertThat(tareaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarea.class);
        Tarea tarea1 = new Tarea();
        tarea1.setId(1L);
        Tarea tarea2 = new Tarea();
        tarea2.setId(tarea1.getId());
        assertThat(tarea1).isEqualTo(tarea2);
        tarea2.setId(2L);
        assertThat(tarea1).isNotEqualTo(tarea2);
        tarea1.setId(null);
        assertThat(tarea1).isNotEqualTo(tarea2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TareaDTO.class);
        TareaDTO tareaDTO1 = new TareaDTO();
        tareaDTO1.setId(1L);
        TareaDTO tareaDTO2 = new TareaDTO();
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
        tareaDTO2.setId(tareaDTO1.getId());
        assertThat(tareaDTO1).isEqualTo(tareaDTO2);
        tareaDTO2.setId(2L);
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
        tareaDTO1.setId(null);
        assertThat(tareaDTO1).isNotEqualTo(tareaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tareaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tareaMapper.fromId(null)).isNull();
    }
}
