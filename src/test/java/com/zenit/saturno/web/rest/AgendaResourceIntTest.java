package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.Agenda;
import com.zenit.saturno.domain.Turno;
import com.zenit.saturno.repository.AgendaRepository;
import com.zenit.saturno.service.AgendaService;
import com.zenit.saturno.service.dto.AgendaDTO;
import com.zenit.saturno.service.mapper.AgendaMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static com.zenit.saturno.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.zenit.saturno.domain.enumeration.TipoRecurso;
/**
 * Test class for the AgendaResource REST controller.
 *
 * @see AgendaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class AgendaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final TipoRecurso DEFAULT_TIPO_RECURSO = TipoRecurso.BAHIA;
    private static final TipoRecurso UPDATED_TIPO_RECURSO = TipoRecurso.LAVADERO;

    private static final LocalDate DEFAULT_FECHA_DESDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DESDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_HASTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_HASTA = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVA = false;
    private static final Boolean UPDATED_ACTIVA = true;

    @Autowired
    private AgendaRepository agendaRepository;

    @Mock
    private AgendaRepository agendaRepositoryMock;

    @Autowired
    private AgendaMapper agendaMapper;

    @Mock
    private AgendaService agendaServiceMock;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgendaMockMvc;

    private Agenda agenda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendaResource agendaResource = new AgendaResource(agendaService);
        this.restAgendaMockMvc = MockMvcBuilders.standaloneSetup(agendaResource)
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
    public static Agenda createEntity(EntityManager em) {
        Agenda agenda = new Agenda()
            .nombre(DEFAULT_NOMBRE)
            .tipoRecurso(DEFAULT_TIPO_RECURSO)
            .fechaDesde(DEFAULT_FECHA_DESDE)
            .fechaHasta(DEFAULT_FECHA_HASTA)
            .activa(DEFAULT_ACTIVA);
        // Add required entity
        Turno turno = TurnoResourceIntTest.createEntity(em);
        em.persist(turno);
        em.flush();
        agenda.getTurnos().add(turno);
        return agenda;
    }

    @Before
    public void initTest() {
        agenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgenda() throws Exception {
        int databaseSizeBeforeCreate = agendaRepository.findAll().size();

        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);
        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isCreated());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeCreate + 1);
        Agenda testAgenda = agendaList.get(agendaList.size() - 1);
        assertThat(testAgenda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAgenda.getTipoRecurso()).isEqualTo(DEFAULT_TIPO_RECURSO);
        assertThat(testAgenda.getFechaDesde()).isEqualTo(DEFAULT_FECHA_DESDE);
        assertThat(testAgenda.getFechaHasta()).isEqualTo(DEFAULT_FECHA_HASTA);
        assertThat(testAgenda.isActiva()).isEqualTo(DEFAULT_ACTIVA);
    }

    @Test
    @Transactional
    public void createAgendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaRepository.findAll().size();

        // Create the Agenda with an existing ID
        agenda.setId(1L);
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendaRepository.findAll().size();
        // set the field null
        agenda.setNombre(null);

        // Create the Agenda, which fails.
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoRecursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendaRepository.findAll().size();
        // set the field null
        agenda.setTipoRecurso(null);

        // Create the Agenda, which fails.
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaDesdeIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendaRepository.findAll().size();
        // set the field null
        agenda.setFechaDesde(null);

        // Create the Agenda, which fails.
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaHastaIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendaRepository.findAll().size();
        // set the field null
        agenda.setFechaHasta(null);

        // Create the Agenda, which fails.
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivaIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendaRepository.findAll().size();
        // set the field null
        agenda.setActiva(null);

        // Create the Agenda, which fails.
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgenda() throws Exception {
        // Initialize the database
        agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList
        restAgendaMockMvc.perform(get("/api/agenda?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipoRecurso").value(hasItem(DEFAULT_TIPO_RECURSO.toString())))
            .andExpect(jsonPath("$.[*].fechaDesde").value(hasItem(DEFAULT_FECHA_DESDE.toString())))
            .andExpect(jsonPath("$.[*].fechaHasta").value(hasItem(DEFAULT_FECHA_HASTA.toString())))
            .andExpect(jsonPath("$.[*].activa").value(hasItem(DEFAULT_ACTIVA.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAgendaWithEagerRelationshipsIsEnabled() throws Exception {
        AgendaResource agendaResource = new AgendaResource(agendaServiceMock);
        when(agendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAgendaMockMvc = MockMvcBuilders.standaloneSetup(agendaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAgendaMockMvc.perform(get("/api/agenda?eagerload=true"))
        .andExpect(status().isOk());

        verify(agendaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAgendaWithEagerRelationshipsIsNotEnabled() throws Exception {
        AgendaResource agendaResource = new AgendaResource(agendaServiceMock);
            when(agendaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAgendaMockMvc = MockMvcBuilders.standaloneSetup(agendaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAgendaMockMvc.perform(get("/api/agenda?eagerload=true"))
        .andExpect(status().isOk());

            verify(agendaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAgenda() throws Exception {
        // Initialize the database
        agendaRepository.saveAndFlush(agenda);

        // Get the agenda
        restAgendaMockMvc.perform(get("/api/agenda/{id}", agenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agenda.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipoRecurso").value(DEFAULT_TIPO_RECURSO.toString()))
            .andExpect(jsonPath("$.fechaDesde").value(DEFAULT_FECHA_DESDE.toString()))
            .andExpect(jsonPath("$.fechaHasta").value(DEFAULT_FECHA_HASTA.toString()))
            .andExpect(jsonPath("$.activa").value(DEFAULT_ACTIVA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAgenda() throws Exception {
        // Get the agenda
        restAgendaMockMvc.perform(get("/api/agenda/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgenda() throws Exception {
        // Initialize the database
        agendaRepository.saveAndFlush(agenda);

        int databaseSizeBeforeUpdate = agendaRepository.findAll().size();

        // Update the agenda
        Agenda updatedAgenda = agendaRepository.findById(agenda.getId()).get();
        // Disconnect from session so that the updates on updatedAgenda are not directly saved in db
        em.detach(updatedAgenda);
        updatedAgenda
            .nombre(UPDATED_NOMBRE)
            .tipoRecurso(UPDATED_TIPO_RECURSO)
            .fechaDesde(UPDATED_FECHA_DESDE)
            .fechaHasta(UPDATED_FECHA_HASTA)
            .activa(UPDATED_ACTIVA);
        AgendaDTO agendaDTO = agendaMapper.toDto(updatedAgenda);

        restAgendaMockMvc.perform(put("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isOk());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeUpdate);
        Agenda testAgenda = agendaList.get(agendaList.size() - 1);
        assertThat(testAgenda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAgenda.getTipoRecurso()).isEqualTo(UPDATED_TIPO_RECURSO);
        assertThat(testAgenda.getFechaDesde()).isEqualTo(UPDATED_FECHA_DESDE);
        assertThat(testAgenda.getFechaHasta()).isEqualTo(UPDATED_FECHA_HASTA);
        assertThat(testAgenda.isActiva()).isEqualTo(UPDATED_ACTIVA);
    }

    @Test
    @Transactional
    public void updateNonExistingAgenda() throws Exception {
        int databaseSizeBeforeUpdate = agendaRepository.findAll().size();

        // Create the Agenda
        AgendaDTO agendaDTO = agendaMapper.toDto(agenda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaMockMvc.perform(put("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgenda() throws Exception {
        // Initialize the database
        agendaRepository.saveAndFlush(agenda);

        int databaseSizeBeforeDelete = agendaRepository.findAll().size();

        // Get the agenda
        restAgendaMockMvc.perform(delete("/api/agenda/{id}", agenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agenda.class);
        Agenda agenda1 = new Agenda();
        agenda1.setId(1L);
        Agenda agenda2 = new Agenda();
        agenda2.setId(agenda1.getId());
        assertThat(agenda1).isEqualTo(agenda2);
        agenda2.setId(2L);
        assertThat(agenda1).isNotEqualTo(agenda2);
        agenda1.setId(null);
        assertThat(agenda1).isNotEqualTo(agenda2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendaDTO.class);
        AgendaDTO agendaDTO1 = new AgendaDTO();
        agendaDTO1.setId(1L);
        AgendaDTO agendaDTO2 = new AgendaDTO();
        assertThat(agendaDTO1).isNotEqualTo(agendaDTO2);
        agendaDTO2.setId(agendaDTO1.getId());
        assertThat(agendaDTO1).isEqualTo(agendaDTO2);
        agendaDTO2.setId(2L);
        assertThat(agendaDTO1).isNotEqualTo(agendaDTO2);
        agendaDTO1.setId(null);
        assertThat(agendaDTO1).isNotEqualTo(agendaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agendaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agendaMapper.fromId(null)).isNull();
    }
}
