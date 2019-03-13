package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.PlanMantenimiento;
import com.zenit.saturno.domain.Servicio;
import com.zenit.saturno.domain.Modelo;
import com.zenit.saturno.repository.PlanMantenimientoRepository;
import com.zenit.saturno.service.PlanMantenimientoService;
import com.zenit.saturno.service.dto.PlanMantenimientoDTO;
import com.zenit.saturno.service.mapper.PlanMantenimientoMapper;
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

/**
 * Test class for the PlanMantenimientoResource REST controller.
 *
 * @see PlanMantenimientoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class PlanMantenimientoResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAA";
    private static final String UPDATED_CODIGO = "BBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private PlanMantenimientoRepository planMantenimientoRepository;

    @Autowired
    private PlanMantenimientoMapper planMantenimientoMapper;

    @Autowired
    private PlanMantenimientoService planMantenimientoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanMantenimientoMockMvc;

    private PlanMantenimiento planMantenimiento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanMantenimientoResource planMantenimientoResource = new PlanMantenimientoResource(planMantenimientoService);
        this.restPlanMantenimientoMockMvc = MockMvcBuilders.standaloneSetup(planMantenimientoResource)
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
    public static PlanMantenimiento createEntity(EntityManager em) {
        PlanMantenimiento planMantenimiento = new PlanMantenimiento()
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE);
        // Add required entity
        Servicio servicio = ServicioResourceIntTest.createEntity(em);
        em.persist(servicio);
        em.flush();
        planMantenimiento.getServicios().add(servicio);
        // Add required entity
        Modelo modelo = ModeloResourceIntTest.createEntity(em);
        em.persist(modelo);
        em.flush();
        planMantenimiento.getModelos().add(modelo);
        return planMantenimiento;
    }

    @Before
    public void initTest() {
        planMantenimiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanMantenimiento() throws Exception {
        int databaseSizeBeforeCreate = planMantenimientoRepository.findAll().size();

        // Create the PlanMantenimiento
        PlanMantenimientoDTO planMantenimientoDTO = planMantenimientoMapper.toDto(planMantenimiento);
        restPlanMantenimientoMockMvc.perform(post("/api/plan-mantenimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planMantenimientoDTO)))
            .andExpect(status().isCreated());

        // Validate the PlanMantenimiento in the database
        List<PlanMantenimiento> planMantenimientoList = planMantenimientoRepository.findAll();
        assertThat(planMantenimientoList).hasSize(databaseSizeBeforeCreate + 1);
        PlanMantenimiento testPlanMantenimiento = planMantenimientoList.get(planMantenimientoList.size() - 1);
        assertThat(testPlanMantenimiento.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testPlanMantenimiento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createPlanMantenimientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planMantenimientoRepository.findAll().size();

        // Create the PlanMantenimiento with an existing ID
        planMantenimiento.setId(1L);
        PlanMantenimientoDTO planMantenimientoDTO = planMantenimientoMapper.toDto(planMantenimiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanMantenimientoMockMvc.perform(post("/api/plan-mantenimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planMantenimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanMantenimiento in the database
        List<PlanMantenimiento> planMantenimientoList = planMantenimientoRepository.findAll();
        assertThat(planMantenimientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planMantenimientoRepository.findAll().size();
        // set the field null
        planMantenimiento.setCodigo(null);

        // Create the PlanMantenimiento, which fails.
        PlanMantenimientoDTO planMantenimientoDTO = planMantenimientoMapper.toDto(planMantenimiento);

        restPlanMantenimientoMockMvc.perform(post("/api/plan-mantenimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planMantenimientoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanMantenimiento> planMantenimientoList = planMantenimientoRepository.findAll();
        assertThat(planMantenimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = planMantenimientoRepository.findAll().size();
        // set the field null
        planMantenimiento.setNombre(null);

        // Create the PlanMantenimiento, which fails.
        PlanMantenimientoDTO planMantenimientoDTO = planMantenimientoMapper.toDto(planMantenimiento);

        restPlanMantenimientoMockMvc.perform(post("/api/plan-mantenimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planMantenimientoDTO)))
            .andExpect(status().isBadRequest());

        List<PlanMantenimiento> planMantenimientoList = planMantenimientoRepository.findAll();
        assertThat(planMantenimientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanMantenimientos() throws Exception {
        // Initialize the database
        planMantenimientoRepository.saveAndFlush(planMantenimiento);

        // Get all the planMantenimientoList
        restPlanMantenimientoMockMvc.perform(get("/api/plan-mantenimientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planMantenimiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanMantenimiento() throws Exception {
        // Initialize the database
        planMantenimientoRepository.saveAndFlush(planMantenimiento);

        // Get the planMantenimiento
        restPlanMantenimientoMockMvc.perform(get("/api/plan-mantenimientos/{id}", planMantenimiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planMantenimiento.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanMantenimiento() throws Exception {
        // Get the planMantenimiento
        restPlanMantenimientoMockMvc.perform(get("/api/plan-mantenimientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanMantenimiento() throws Exception {
        // Initialize the database
        planMantenimientoRepository.saveAndFlush(planMantenimiento);

        int databaseSizeBeforeUpdate = planMantenimientoRepository.findAll().size();

        // Update the planMantenimiento
        PlanMantenimiento updatedPlanMantenimiento = planMantenimientoRepository.findById(planMantenimiento.getId()).get();
        // Disconnect from session so that the updates on updatedPlanMantenimiento are not directly saved in db
        em.detach(updatedPlanMantenimiento);
        updatedPlanMantenimiento
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE);
        PlanMantenimientoDTO planMantenimientoDTO = planMantenimientoMapper.toDto(updatedPlanMantenimiento);

        restPlanMantenimientoMockMvc.perform(put("/api/plan-mantenimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planMantenimientoDTO)))
            .andExpect(status().isOk());

        // Validate the PlanMantenimiento in the database
        List<PlanMantenimiento> planMantenimientoList = planMantenimientoRepository.findAll();
        assertThat(planMantenimientoList).hasSize(databaseSizeBeforeUpdate);
        PlanMantenimiento testPlanMantenimiento = planMantenimientoList.get(planMantenimientoList.size() - 1);
        assertThat(testPlanMantenimiento.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testPlanMantenimiento.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanMantenimiento() throws Exception {
        int databaseSizeBeforeUpdate = planMantenimientoRepository.findAll().size();

        // Create the PlanMantenimiento
        PlanMantenimientoDTO planMantenimientoDTO = planMantenimientoMapper.toDto(planMantenimiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanMantenimientoMockMvc.perform(put("/api/plan-mantenimientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planMantenimientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanMantenimiento in the database
        List<PlanMantenimiento> planMantenimientoList = planMantenimientoRepository.findAll();
        assertThat(planMantenimientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanMantenimiento() throws Exception {
        // Initialize the database
        planMantenimientoRepository.saveAndFlush(planMantenimiento);

        int databaseSizeBeforeDelete = planMantenimientoRepository.findAll().size();

        // Get the planMantenimiento
        restPlanMantenimientoMockMvc.perform(delete("/api/plan-mantenimientos/{id}", planMantenimiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanMantenimiento> planMantenimientoList = planMantenimientoRepository.findAll();
        assertThat(planMantenimientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanMantenimiento.class);
        PlanMantenimiento planMantenimiento1 = new PlanMantenimiento();
        planMantenimiento1.setId(1L);
        PlanMantenimiento planMantenimiento2 = new PlanMantenimiento();
        planMantenimiento2.setId(planMantenimiento1.getId());
        assertThat(planMantenimiento1).isEqualTo(planMantenimiento2);
        planMantenimiento2.setId(2L);
        assertThat(planMantenimiento1).isNotEqualTo(planMantenimiento2);
        planMantenimiento1.setId(null);
        assertThat(planMantenimiento1).isNotEqualTo(planMantenimiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanMantenimientoDTO.class);
        PlanMantenimientoDTO planMantenimientoDTO1 = new PlanMantenimientoDTO();
        planMantenimientoDTO1.setId(1L);
        PlanMantenimientoDTO planMantenimientoDTO2 = new PlanMantenimientoDTO();
        assertThat(planMantenimientoDTO1).isNotEqualTo(planMantenimientoDTO2);
        planMantenimientoDTO2.setId(planMantenimientoDTO1.getId());
        assertThat(planMantenimientoDTO1).isEqualTo(planMantenimientoDTO2);
        planMantenimientoDTO2.setId(2L);
        assertThat(planMantenimientoDTO1).isNotEqualTo(planMantenimientoDTO2);
        planMantenimientoDTO1.setId(null);
        assertThat(planMantenimientoDTO1).isNotEqualTo(planMantenimientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(planMantenimientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(planMantenimientoMapper.fromId(null)).isNull();
    }
}
