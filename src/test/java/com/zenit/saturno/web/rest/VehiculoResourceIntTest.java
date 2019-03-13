package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.Vehiculo;
import com.zenit.saturno.domain.Modelo;
import com.zenit.saturno.domain.Turno;
import com.zenit.saturno.repository.VehiculoRepository;
import com.zenit.saturno.service.VehiculoService;
import com.zenit.saturno.service.dto.VehiculoDTO;
import com.zenit.saturno.service.mapper.VehiculoMapper;
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
 * Test class for the VehiculoResource REST controller.
 *
 * @see VehiculoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class VehiculoResourceIntTest {

    private static final String DEFAULT_PATENTE = "PV799NN";
    private static final String UPDATED_PATENTE = "XT564CI";

    private static final Integer DEFAULT_ANIO = 1950;
    private static final Integer UPDATED_ANIO = 1951;

    private static final Integer DEFAULT_KILOMETRAJE = 0;
    private static final Integer UPDATED_KILOMETRAJE = 1;

    private static final String DEFAULT_VIN = "AAAAAAAAAAAAAAAAA";
    private static final String UPDATED_VIN = "BBBBBBBBBBBBBBBBB";

    private static final String DEFAULT_MOTOR = "AAAAAAAAAAAAAAAAA";
    private static final String UPDATED_MOTOR = "BBBBBBBBBBBBBBBBB";

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private VehiculoMapper vehiculoMapper;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehiculoMockMvc;

    private Vehiculo vehiculo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehiculoResource vehiculoResource = new VehiculoResource(vehiculoService);
        this.restVehiculoMockMvc = MockMvcBuilders.standaloneSetup(vehiculoResource)
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
    public static Vehiculo createEntity(EntityManager em) {
        Vehiculo vehiculo = new Vehiculo()
            .patente(DEFAULT_PATENTE)
            .anio(DEFAULT_ANIO)
            .kilometraje(DEFAULT_KILOMETRAJE)
            .vin(DEFAULT_VIN)
            .motor(DEFAULT_MOTOR);
        // Add required entity
        Modelo modelo = ModeloResourceIntTest.createEntity(em);
        em.persist(modelo);
        em.flush();
        vehiculo.setModelo(modelo);
        // Add required entity
        Turno turno = TurnoResourceIntTest.createEntity(em);
        em.persist(turno);
        em.flush();
        vehiculo.getTurnos().add(turno);
        return vehiculo;
    }

    @Before
    public void initTest() {
        vehiculo = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehiculo() throws Exception {
        int databaseSizeBeforeCreate = vehiculoRepository.findAll().size();

        // Create the Vehiculo
        VehiculoDTO vehiculoDTO = vehiculoMapper.toDto(vehiculo);
        restVehiculoMockMvc.perform(post("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculoDTO)))
            .andExpect(status().isCreated());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeCreate + 1);
        Vehiculo testVehiculo = vehiculoList.get(vehiculoList.size() - 1);
        assertThat(testVehiculo.getPatente()).isEqualTo(DEFAULT_PATENTE);
        assertThat(testVehiculo.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testVehiculo.getKilometraje()).isEqualTo(DEFAULT_KILOMETRAJE);
        assertThat(testVehiculo.getVin()).isEqualTo(DEFAULT_VIN);
        assertThat(testVehiculo.getMotor()).isEqualTo(DEFAULT_MOTOR);
    }

    @Test
    @Transactional
    public void createVehiculoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiculoRepository.findAll().size();

        // Create the Vehiculo with an existing ID
        vehiculo.setId(1L);
        VehiculoDTO vehiculoDTO = vehiculoMapper.toDto(vehiculo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiculoMockMvc.perform(post("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPatenteIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehiculoRepository.findAll().size();
        // set the field null
        vehiculo.setPatente(null);

        // Create the Vehiculo, which fails.
        VehiculoDTO vehiculoDTO = vehiculoMapper.toDto(vehiculo);

        restVehiculoMockMvc.perform(post("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnioIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehiculoRepository.findAll().size();
        // set the field null
        vehiculo.setAnio(null);

        // Create the Vehiculo, which fails.
        VehiculoDTO vehiculoDTO = vehiculoMapper.toDto(vehiculo);

        restVehiculoMockMvc.perform(post("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKilometrajeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehiculoRepository.findAll().size();
        // set the field null
        vehiculo.setKilometraje(null);

        // Create the Vehiculo, which fails.
        VehiculoDTO vehiculoDTO = vehiculoMapper.toDto(vehiculo);

        restVehiculoMockMvc.perform(post("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculoDTO)))
            .andExpect(status().isBadRequest());

        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehiculos() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        // Get all the vehiculoList
        restVehiculoMockMvc.perform(get("/api/vehiculos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehiculo.getId().intValue())))
            .andExpect(jsonPath("$.[*].patente").value(hasItem(DEFAULT_PATENTE.toString())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)))
            .andExpect(jsonPath("$.[*].kilometraje").value(hasItem(DEFAULT_KILOMETRAJE)))
            .andExpect(jsonPath("$.[*].vin").value(hasItem(DEFAULT_VIN.toString())))
            .andExpect(jsonPath("$.[*].motor").value(hasItem(DEFAULT_MOTOR.toString())));
    }
    
    @Test
    @Transactional
    public void getVehiculo() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        // Get the vehiculo
        restVehiculoMockMvc.perform(get("/api/vehiculos/{id}", vehiculo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehiculo.getId().intValue()))
            .andExpect(jsonPath("$.patente").value(DEFAULT_PATENTE.toString()))
            .andExpect(jsonPath("$.anio").value(DEFAULT_ANIO))
            .andExpect(jsonPath("$.kilometraje").value(DEFAULT_KILOMETRAJE))
            .andExpect(jsonPath("$.vin").value(DEFAULT_VIN.toString()))
            .andExpect(jsonPath("$.motor").value(DEFAULT_MOTOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehiculo() throws Exception {
        // Get the vehiculo
        restVehiculoMockMvc.perform(get("/api/vehiculos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehiculo() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();

        // Update the vehiculo
        Vehiculo updatedVehiculo = vehiculoRepository.findById(vehiculo.getId()).get();
        // Disconnect from session so that the updates on updatedVehiculo are not directly saved in db
        em.detach(updatedVehiculo);
        updatedVehiculo
            .patente(UPDATED_PATENTE)
            .anio(UPDATED_ANIO)
            .kilometraje(UPDATED_KILOMETRAJE)
            .vin(UPDATED_VIN)
            .motor(UPDATED_MOTOR);
        VehiculoDTO vehiculoDTO = vehiculoMapper.toDto(updatedVehiculo);

        restVehiculoMockMvc.perform(put("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculoDTO)))
            .andExpect(status().isOk());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
        Vehiculo testVehiculo = vehiculoList.get(vehiculoList.size() - 1);
        assertThat(testVehiculo.getPatente()).isEqualTo(UPDATED_PATENTE);
        assertThat(testVehiculo.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testVehiculo.getKilometraje()).isEqualTo(UPDATED_KILOMETRAJE);
        assertThat(testVehiculo.getVin()).isEqualTo(UPDATED_VIN);
        assertThat(testVehiculo.getMotor()).isEqualTo(UPDATED_MOTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingVehiculo() throws Exception {
        int databaseSizeBeforeUpdate = vehiculoRepository.findAll().size();

        // Create the Vehiculo
        VehiculoDTO vehiculoDTO = vehiculoMapper.toDto(vehiculo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehiculoMockMvc.perform(put("/api/vehiculos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehiculoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehiculo in the database
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehiculo() throws Exception {
        // Initialize the database
        vehiculoRepository.saveAndFlush(vehiculo);

        int databaseSizeBeforeDelete = vehiculoRepository.findAll().size();

        // Get the vehiculo
        restVehiculoMockMvc.perform(delete("/api/vehiculos/{id}", vehiculo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        assertThat(vehiculoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehiculo.class);
        Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(1L);
        Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(vehiculo1.getId());
        assertThat(vehiculo1).isEqualTo(vehiculo2);
        vehiculo2.setId(2L);
        assertThat(vehiculo1).isNotEqualTo(vehiculo2);
        vehiculo1.setId(null);
        assertThat(vehiculo1).isNotEqualTo(vehiculo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculoDTO.class);
        VehiculoDTO vehiculoDTO1 = new VehiculoDTO();
        vehiculoDTO1.setId(1L);
        VehiculoDTO vehiculoDTO2 = new VehiculoDTO();
        assertThat(vehiculoDTO1).isNotEqualTo(vehiculoDTO2);
        vehiculoDTO2.setId(vehiculoDTO1.getId());
        assertThat(vehiculoDTO1).isEqualTo(vehiculoDTO2);
        vehiculoDTO2.setId(2L);
        assertThat(vehiculoDTO1).isNotEqualTo(vehiculoDTO2);
        vehiculoDTO1.setId(null);
        assertThat(vehiculoDTO1).isNotEqualTo(vehiculoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehiculoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehiculoMapper.fromId(null)).isNull();
    }
}
