package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.Modelo;
import com.zenit.saturno.domain.PrecioServicio;
import com.zenit.saturno.domain.PlanMantenimiento;
import com.zenit.saturno.domain.Vehiculo;
import com.zenit.saturno.repository.ModeloRepository;
import com.zenit.saturno.service.ModeloService;
import com.zenit.saturno.service.dto.ModeloDTO;
import com.zenit.saturno.service.mapper.ModeloMapper;
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

import com.zenit.saturno.domain.enumeration.Marca;
/**
 * Test class for the ModeloResource REST controller.
 *
 * @see ModeloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class ModeloResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAA";
    private static final String UPDATED_CODIGO = "BBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANIO_INICIO_PRODUCCION = 1950;
    private static final Integer UPDATED_ANIO_INICIO_PRODUCCION = 1951;

    private static final Integer DEFAULT_ANIO_FIN_PRODUCCION = 1950;
    private static final Integer UPDATED_ANIO_FIN_PRODUCCION = 1951;

    private static final Marca DEFAULT_MARCA = Marca.TOYOTA;
    private static final Marca UPDATED_MARCA = Marca.LEXUS;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private ModeloMapper modeloMapper;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restModeloMockMvc;

    private Modelo modelo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModeloResource modeloResource = new ModeloResource(modeloService);
        this.restModeloMockMvc = MockMvcBuilders.standaloneSetup(modeloResource)
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
    public static Modelo createEntity(EntityManager em) {
        Modelo modelo = new Modelo()
            .codigo(DEFAULT_CODIGO)
            .nombre(DEFAULT_NOMBRE)
            .anioInicioProduccion(DEFAULT_ANIO_INICIO_PRODUCCION)
            .anioFinProduccion(DEFAULT_ANIO_FIN_PRODUCCION)
            .marca(DEFAULT_MARCA);
        // Add required entity
        PrecioServicio precioServicio = PrecioServicioResourceIntTest.createEntity(em);
        em.persist(precioServicio);
        em.flush();
        modelo.getPrecios().add(precioServicio);
        // Add required entity
        PlanMantenimiento planMantenimiento = PlanMantenimientoResourceIntTest.createEntity(em);
        em.persist(planMantenimiento);
        em.flush();
        modelo.setPlan(planMantenimiento);
        // Add required entity
        Vehiculo vehiculo = VehiculoResourceIntTest.createEntity(em);
        em.persist(vehiculo);
        em.flush();
        modelo.getVehiculos().add(vehiculo);
        return modelo;
    }

    @Before
    public void initTest() {
        modelo = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelo() throws Exception {
        int databaseSizeBeforeCreate = modeloRepository.findAll().size();

        // Create the Modelo
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);
        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isCreated());

        // Validate the Modelo in the database
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeCreate + 1);
        Modelo testModelo = modeloList.get(modeloList.size() - 1);
        assertThat(testModelo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testModelo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testModelo.getAnioInicioProduccion()).isEqualTo(DEFAULT_ANIO_INICIO_PRODUCCION);
        assertThat(testModelo.getAnioFinProduccion()).isEqualTo(DEFAULT_ANIO_FIN_PRODUCCION);
        assertThat(testModelo.getMarca()).isEqualTo(DEFAULT_MARCA);
    }

    @Test
    @Transactional
    public void createModeloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeloRepository.findAll().size();

        // Create the Modelo with an existing ID
        modelo.setId(1L);
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Modelo in the database
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloRepository.findAll().size();
        // set the field null
        modelo.setCodigo(null);

        // Create the Modelo, which fails.
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isBadRequest());

        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloRepository.findAll().size();
        // set the field null
        modelo.setNombre(null);

        // Create the Modelo, which fails.
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isBadRequest());

        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnioInicioProduccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloRepository.findAll().size();
        // set the field null
        modelo.setAnioInicioProduccion(null);

        // Create the Modelo, which fails.
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isBadRequest());

        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = modeloRepository.findAll().size();
        // set the field null
        modelo.setMarca(null);

        // Create the Modelo, which fails.
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        restModeloMockMvc.perform(post("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isBadRequest());

        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModelos() throws Exception {
        // Initialize the database
        modeloRepository.saveAndFlush(modelo);

        // Get all the modeloList
        restModeloMockMvc.perform(get("/api/modelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].anioInicioProduccion").value(hasItem(DEFAULT_ANIO_INICIO_PRODUCCION)))
            .andExpect(jsonPath("$.[*].anioFinProduccion").value(hasItem(DEFAULT_ANIO_FIN_PRODUCCION)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA.toString())));
    }
    
    @Test
    @Transactional
    public void getModelo() throws Exception {
        // Initialize the database
        modeloRepository.saveAndFlush(modelo);

        // Get the modelo
        restModeloMockMvc.perform(get("/api/modelos/{id}", modelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelo.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.anioInicioProduccion").value(DEFAULT_ANIO_INICIO_PRODUCCION))
            .andExpect(jsonPath("$.anioFinProduccion").value(DEFAULT_ANIO_FIN_PRODUCCION))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModelo() throws Exception {
        // Get the modelo
        restModeloMockMvc.perform(get("/api/modelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelo() throws Exception {
        // Initialize the database
        modeloRepository.saveAndFlush(modelo);

        int databaseSizeBeforeUpdate = modeloRepository.findAll().size();

        // Update the modelo
        Modelo updatedModelo = modeloRepository.findById(modelo.getId()).get();
        // Disconnect from session so that the updates on updatedModelo are not directly saved in db
        em.detach(updatedModelo);
        updatedModelo
            .codigo(UPDATED_CODIGO)
            .nombre(UPDATED_NOMBRE)
            .anioInicioProduccion(UPDATED_ANIO_INICIO_PRODUCCION)
            .anioFinProduccion(UPDATED_ANIO_FIN_PRODUCCION)
            .marca(UPDATED_MARCA);
        ModeloDTO modeloDTO = modeloMapper.toDto(updatedModelo);

        restModeloMockMvc.perform(put("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isOk());

        // Validate the Modelo in the database
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeUpdate);
        Modelo testModelo = modeloList.get(modeloList.size() - 1);
        assertThat(testModelo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testModelo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testModelo.getAnioInicioProduccion()).isEqualTo(UPDATED_ANIO_INICIO_PRODUCCION);
        assertThat(testModelo.getAnioFinProduccion()).isEqualTo(UPDATED_ANIO_FIN_PRODUCCION);
        assertThat(testModelo.getMarca()).isEqualTo(UPDATED_MARCA);
    }

    @Test
    @Transactional
    public void updateNonExistingModelo() throws Exception {
        int databaseSizeBeforeUpdate = modeloRepository.findAll().size();

        // Create the Modelo
        ModeloDTO modeloDTO = modeloMapper.toDto(modelo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeloMockMvc.perform(put("/api/modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modeloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Modelo in the database
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModelo() throws Exception {
        // Initialize the database
        modeloRepository.saveAndFlush(modelo);

        int databaseSizeBeforeDelete = modeloRepository.findAll().size();

        // Get the modelo
        restModeloMockMvc.perform(delete("/api/modelos/{id}", modelo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Modelo> modeloList = modeloRepository.findAll();
        assertThat(modeloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modelo.class);
        Modelo modelo1 = new Modelo();
        modelo1.setId(1L);
        Modelo modelo2 = new Modelo();
        modelo2.setId(modelo1.getId());
        assertThat(modelo1).isEqualTo(modelo2);
        modelo2.setId(2L);
        assertThat(modelo1).isNotEqualTo(modelo2);
        modelo1.setId(null);
        assertThat(modelo1).isNotEqualTo(modelo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeloDTO.class);
        ModeloDTO modeloDTO1 = new ModeloDTO();
        modeloDTO1.setId(1L);
        ModeloDTO modeloDTO2 = new ModeloDTO();
        assertThat(modeloDTO1).isNotEqualTo(modeloDTO2);
        modeloDTO2.setId(modeloDTO1.getId());
        assertThat(modeloDTO1).isEqualTo(modeloDTO2);
        modeloDTO2.setId(2L);
        assertThat(modeloDTO1).isNotEqualTo(modeloDTO2);
        modeloDTO1.setId(null);
        assertThat(modeloDTO1).isNotEqualTo(modeloDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(modeloMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(modeloMapper.fromId(null)).isNull();
    }
}
