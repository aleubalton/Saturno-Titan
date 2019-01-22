package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.Servicio;
import com.zenit.saturno.domain.PrecioServicio;
import com.zenit.saturno.domain.TipoDeServicio;
import com.zenit.saturno.domain.Tarea;
import com.zenit.saturno.domain.Turno;
import com.zenit.saturno.repository.ServicioRepository;
import com.zenit.saturno.service.ServicioService;
import com.zenit.saturno.service.dto.ServicioDTO;
import com.zenit.saturno.service.mapper.ServicioMapper;
import com.zenit.saturno.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.zenit.saturno.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.zenit.saturno.domain.enumeration.Categoria;
/**
 * Test class for the ServicioResource REST controller.
 *
 * @see ServicioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class ServicioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAA";
    private static final String UPDATED_CODIGO = "BBBB";

    private static final Integer DEFAULT_KILOMETRAJE = 0;
    private static final Integer UPDATED_KILOMETRAJE = 1;

    private static final Integer DEFAULT_DURACION = 0;
    private static final Integer UPDATED_DURACION = 1;

    private static final Categoria DEFAULT_CATEGORIA = Categoria.AUTOMOVIL;
    private static final Categoria UPDATED_CATEGORIA = Categoria.CARGA;

    @Autowired
    private ServicioRepository servicioRepository;

    @Mock
    private ServicioRepository servicioRepositoryMock;

    @Autowired
    private ServicioMapper servicioMapper;

    @Mock
    private ServicioService servicioServiceMock;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServicioMockMvc;

    private Servicio servicio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServicioResource servicioResource = new ServicioResource(servicioService);
        this.restServicioMockMvc = MockMvcBuilders.standaloneSetup(servicioResource)
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
    public static Servicio createEntity(EntityManager em) {
        Servicio servicio = new Servicio()
            .nombre(DEFAULT_NOMBRE)
            .codigo(DEFAULT_CODIGO)
            .kilometraje(DEFAULT_KILOMETRAJE)
            .duracion(DEFAULT_DURACION)
            .categoria(DEFAULT_CATEGORIA);
        // Add required entity
        PrecioServicio precioServicio = PrecioServicioResourceIntTest.createEntity(em);
        em.persist(precioServicio);
        em.flush();
        servicio.getPrecios().add(precioServicio);
        // Add required entity
        TipoDeServicio tipoDeServicio = TipoDeServicioResourceIntTest.createEntity(em);
        em.persist(tipoDeServicio);
        em.flush();
        servicio.setTipo(tipoDeServicio);
        // Add required entity
        Tarea tarea = TareaResourceIntTest.createEntity(em);
        em.persist(tarea);
        em.flush();
        servicio.getTareas().add(tarea);
        // Add required entity
        Turno turno = TurnoResourceIntTest.createEntity(em);
        em.persist(turno);
        em.flush();
        servicio.getTurnos().add(turno);
        return servicio;
    }

    @Before
    public void initTest() {
        servicio = createEntity(em);
    }

    @Test
    @Transactional
    public void createServicio() throws Exception {
        int databaseSizeBeforeCreate = servicioRepository.findAll().size();

        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);
        restServicioMockMvc.perform(post("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isCreated());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeCreate + 1);
        Servicio testServicio = servicioList.get(servicioList.size() - 1);
        assertThat(testServicio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testServicio.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testServicio.getKilometraje()).isEqualTo(DEFAULT_KILOMETRAJE);
        assertThat(testServicio.getDuracion()).isEqualTo(DEFAULT_DURACION);
        assertThat(testServicio.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
    }

    @Test
    @Transactional
    public void createServicioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servicioRepository.findAll().size();

        // Create the Servicio with an existing ID
        servicio.setId(1L);
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicioMockMvc.perform(post("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicioRepository.findAll().size();
        // set the field null
        servicio.setNombre(null);

        // Create the Servicio, which fails.
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        restServicioMockMvc.perform(post("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicioRepository.findAll().size();
        // set the field null
        servicio.setCodigo(null);

        // Create the Servicio, which fails.
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        restServicioMockMvc.perform(post("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKilometrajeIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicioRepository.findAll().size();
        // set the field null
        servicio.setKilometraje(null);

        // Create the Servicio, which fails.
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        restServicioMockMvc.perform(post("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDuracionIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicioRepository.findAll().size();
        // set the field null
        servicio.setDuracion(null);

        // Create the Servicio, which fails.
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        restServicioMockMvc.perform(post("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicioRepository.findAll().size();
        // set the field null
        servicio.setCategoria(null);

        // Create the Servicio, which fails.
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        restServicioMockMvc.perform(post("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServicios() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        // Get all the servicioList
        restServicioMockMvc.perform(get("/api/servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].kilometraje").value(hasItem(DEFAULT_KILOMETRAJE)))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllServiciosWithEagerRelationshipsIsEnabled() throws Exception {
        ServicioResource servicioResource = new ServicioResource(servicioServiceMock);
        when(servicioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restServicioMockMvc = MockMvcBuilders.standaloneSetup(servicioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restServicioMockMvc.perform(get("/api/servicios?eagerload=true"))
        .andExpect(status().isOk());

        verify(servicioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllServiciosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ServicioResource servicioResource = new ServicioResource(servicioServiceMock);
            when(servicioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restServicioMockMvc = MockMvcBuilders.standaloneSetup(servicioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restServicioMockMvc.perform(get("/api/servicios?eagerload=true"))
        .andExpect(status().isOk());

            verify(servicioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getServicio() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        // Get the servicio
        restServicioMockMvc.perform(get("/api/servicios/{id}", servicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servicio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.kilometraje").value(DEFAULT_KILOMETRAJE))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServicio() throws Exception {
        // Get the servicio
        restServicioMockMvc.perform(get("/api/servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServicio() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();

        // Update the servicio
        Servicio updatedServicio = servicioRepository.findById(servicio.getId()).get();
        // Disconnect from session so that the updates on updatedServicio are not directly saved in db
        em.detach(updatedServicio);
        updatedServicio
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .kilometraje(UPDATED_KILOMETRAJE)
            .duracion(UPDATED_DURACION)
            .categoria(UPDATED_CATEGORIA);
        ServicioDTO servicioDTO = servicioMapper.toDto(updatedServicio);

        restServicioMockMvc.perform(put("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isOk());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
        Servicio testServicio = servicioList.get(servicioList.size() - 1);
        assertThat(testServicio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testServicio.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testServicio.getKilometraje()).isEqualTo(UPDATED_KILOMETRAJE);
        assertThat(testServicio.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testServicio.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void updateNonExistingServicio() throws Exception {
        int databaseSizeBeforeUpdate = servicioRepository.findAll().size();

        // Create the Servicio
        ServicioDTO servicioDTO = servicioMapper.toDto(servicio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicioMockMvc.perform(put("/api/servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servicio in the database
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServicio() throws Exception {
        // Initialize the database
        servicioRepository.saveAndFlush(servicio);

        int databaseSizeBeforeDelete = servicioRepository.findAll().size();

        // Get the servicio
        restServicioMockMvc.perform(delete("/api/servicios/{id}", servicio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Servicio> servicioList = servicioRepository.findAll();
        assertThat(servicioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servicio.class);
        Servicio servicio1 = new Servicio();
        servicio1.setId(1L);
        Servicio servicio2 = new Servicio();
        servicio2.setId(servicio1.getId());
        assertThat(servicio1).isEqualTo(servicio2);
        servicio2.setId(2L);
        assertThat(servicio1).isNotEqualTo(servicio2);
        servicio1.setId(null);
        assertThat(servicio1).isNotEqualTo(servicio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicioDTO.class);
        ServicioDTO servicioDTO1 = new ServicioDTO();
        servicioDTO1.setId(1L);
        ServicioDTO servicioDTO2 = new ServicioDTO();
        assertThat(servicioDTO1).isNotEqualTo(servicioDTO2);
        servicioDTO2.setId(servicioDTO1.getId());
        assertThat(servicioDTO1).isEqualTo(servicioDTO2);
        servicioDTO2.setId(2L);
        assertThat(servicioDTO1).isNotEqualTo(servicioDTO2);
        servicioDTO1.setId(null);
        assertThat(servicioDTO1).isNotEqualTo(servicioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(servicioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(servicioMapper.fromId(null)).isNull();
    }
}
