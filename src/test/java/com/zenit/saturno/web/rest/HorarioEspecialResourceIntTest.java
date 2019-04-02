package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.HorarioEspecial;
import com.zenit.saturno.repository.HorarioEspecialRepository;
import com.zenit.saturno.service.HorarioEspecialService;
import com.zenit.saturno.service.dto.HorarioEspecialDTO;
import com.zenit.saturno.service.mapper.HorarioEspecialMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.zenit.saturno.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HorarioEspecialResource REST controller.
 *
 * @see HorarioEspecialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class HorarioEspecialResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_HORA_DESDE = 0;
    private static final Integer UPDATED_HORA_DESDE = 1;

    private static final Integer DEFAULT_HORA_HASTA = 0;
    private static final Integer UPDATED_HORA_HASTA = 1;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private HorarioEspecialRepository horarioEspecialRepository;

    @Autowired
    private HorarioEspecialMapper horarioEspecialMapper;

    @Autowired
    private HorarioEspecialService horarioEspecialService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHorarioEspecialMockMvc;

    private HorarioEspecial horarioEspecial;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HorarioEspecialResource horarioEspecialResource = new HorarioEspecialResource(horarioEspecialService);
        this.restHorarioEspecialMockMvc = MockMvcBuilders.standaloneSetup(horarioEspecialResource)
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
    public static HorarioEspecial createEntity(EntityManager em) {
        HorarioEspecial horarioEspecial = new HorarioEspecial()
            .descripcion(DEFAULT_DESCRIPCION)
            .horaDesde(DEFAULT_HORA_DESDE)
            .horaHasta(DEFAULT_HORA_HASTA)
            .fecha(DEFAULT_FECHA);
        return horarioEspecial;
    }

    @Before
    public void initTest() {
        horarioEspecial = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorarioEspecial() throws Exception {
        int databaseSizeBeforeCreate = horarioEspecialRepository.findAll().size();

        // Create the HorarioEspecial
        HorarioEspecialDTO horarioEspecialDTO = horarioEspecialMapper.toDto(horarioEspecial);
        restHorarioEspecialMockMvc.perform(post("/api/horario-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioEspecialDTO)))
            .andExpect(status().isCreated());

        // Validate the HorarioEspecial in the database
        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeCreate + 1);
        HorarioEspecial testHorarioEspecial = horarioEspecialList.get(horarioEspecialList.size() - 1);
        assertThat(testHorarioEspecial.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testHorarioEspecial.getHoraDesde()).isEqualTo(DEFAULT_HORA_DESDE);
        assertThat(testHorarioEspecial.getHoraHasta()).isEqualTo(DEFAULT_HORA_HASTA);
        assertThat(testHorarioEspecial.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createHorarioEspecialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioEspecialRepository.findAll().size();

        // Create the HorarioEspecial with an existing ID
        horarioEspecial.setId(1L);
        HorarioEspecialDTO horarioEspecialDTO = horarioEspecialMapper.toDto(horarioEspecial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioEspecialMockMvc.perform(post("/api/horario-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioEspecialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioEspecial in the database
        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioEspecialRepository.findAll().size();
        // set the field null
        horarioEspecial.setDescripcion(null);

        // Create the HorarioEspecial, which fails.
        HorarioEspecialDTO horarioEspecialDTO = horarioEspecialMapper.toDto(horarioEspecial);

        restHorarioEspecialMockMvc.perform(post("/api/horario-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioEspecialDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraDesdeIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioEspecialRepository.findAll().size();
        // set the field null
        horarioEspecial.setHoraDesde(null);

        // Create the HorarioEspecial, which fails.
        HorarioEspecialDTO horarioEspecialDTO = horarioEspecialMapper.toDto(horarioEspecial);

        restHorarioEspecialMockMvc.perform(post("/api/horario-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioEspecialDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraHastaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioEspecialRepository.findAll().size();
        // set the field null
        horarioEspecial.setHoraHasta(null);

        // Create the HorarioEspecial, which fails.
        HorarioEspecialDTO horarioEspecialDTO = horarioEspecialMapper.toDto(horarioEspecial);

        restHorarioEspecialMockMvc.perform(post("/api/horario-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioEspecialDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioEspecialRepository.findAll().size();
        // set the field null
        horarioEspecial.setFecha(null);

        // Create the HorarioEspecial, which fails.
        HorarioEspecialDTO horarioEspecialDTO = horarioEspecialMapper.toDto(horarioEspecial);

        restHorarioEspecialMockMvc.perform(post("/api/horario-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioEspecialDTO)))
            .andExpect(status().isBadRequest());

        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorarioEspecials() throws Exception {
        // Initialize the database
        horarioEspecialRepository.saveAndFlush(horarioEspecial);

        // Get all the horarioEspecialList
        restHorarioEspecialMockMvc.perform(get("/api/horario-especials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioEspecial.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].horaDesde").value(hasItem(DEFAULT_HORA_DESDE)))
            .andExpect(jsonPath("$.[*].horaHasta").value(hasItem(DEFAULT_HORA_HASTA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getHorarioEspecial() throws Exception {
        // Initialize the database
        horarioEspecialRepository.saveAndFlush(horarioEspecial);

        // Get the horarioEspecial
        restHorarioEspecialMockMvc.perform(get("/api/horario-especials/{id}", horarioEspecial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horarioEspecial.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.horaDesde").value(DEFAULT_HORA_DESDE))
            .andExpect(jsonPath("$.horaHasta").value(DEFAULT_HORA_HASTA))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHorarioEspecial() throws Exception {
        // Get the horarioEspecial
        restHorarioEspecialMockMvc.perform(get("/api/horario-especials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorarioEspecial() throws Exception {
        // Initialize the database
        horarioEspecialRepository.saveAndFlush(horarioEspecial);

        int databaseSizeBeforeUpdate = horarioEspecialRepository.findAll().size();

        // Update the horarioEspecial
        HorarioEspecial updatedHorarioEspecial = horarioEspecialRepository.findById(horarioEspecial.getId()).get();
        // Disconnect from session so that the updates on updatedHorarioEspecial are not directly saved in db
        em.detach(updatedHorarioEspecial);
        updatedHorarioEspecial
            .descripcion(UPDATED_DESCRIPCION)
            .horaDesde(UPDATED_HORA_DESDE)
            .horaHasta(UPDATED_HORA_HASTA)
            .fecha(UPDATED_FECHA);
        HorarioEspecialDTO horarioEspecialDTO = horarioEspecialMapper.toDto(updatedHorarioEspecial);

        restHorarioEspecialMockMvc.perform(put("/api/horario-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioEspecialDTO)))
            .andExpect(status().isOk());

        // Validate the HorarioEspecial in the database
        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeUpdate);
        HorarioEspecial testHorarioEspecial = horarioEspecialList.get(horarioEspecialList.size() - 1);
        assertThat(testHorarioEspecial.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testHorarioEspecial.getHoraDesde()).isEqualTo(UPDATED_HORA_DESDE);
        assertThat(testHorarioEspecial.getHoraHasta()).isEqualTo(UPDATED_HORA_HASTA);
        assertThat(testHorarioEspecial.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingHorarioEspecial() throws Exception {
        int databaseSizeBeforeUpdate = horarioEspecialRepository.findAll().size();

        // Create the HorarioEspecial
        HorarioEspecialDTO horarioEspecialDTO = horarioEspecialMapper.toDto(horarioEspecial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioEspecialMockMvc.perform(put("/api/horario-especials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioEspecialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioEspecial in the database
        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHorarioEspecial() throws Exception {
        // Initialize the database
        horarioEspecialRepository.saveAndFlush(horarioEspecial);

        int databaseSizeBeforeDelete = horarioEspecialRepository.findAll().size();

        // Get the horarioEspecial
        restHorarioEspecialMockMvc.perform(delete("/api/horario-especials/{id}", horarioEspecial.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HorarioEspecial> horarioEspecialList = horarioEspecialRepository.findAll();
        assertThat(horarioEspecialList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioEspecial.class);
        HorarioEspecial horarioEspecial1 = new HorarioEspecial();
        horarioEspecial1.setId(1L);
        HorarioEspecial horarioEspecial2 = new HorarioEspecial();
        horarioEspecial2.setId(horarioEspecial1.getId());
        assertThat(horarioEspecial1).isEqualTo(horarioEspecial2);
        horarioEspecial2.setId(2L);
        assertThat(horarioEspecial1).isNotEqualTo(horarioEspecial2);
        horarioEspecial1.setId(null);
        assertThat(horarioEspecial1).isNotEqualTo(horarioEspecial2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioEspecialDTO.class);
        HorarioEspecialDTO horarioEspecialDTO1 = new HorarioEspecialDTO();
        horarioEspecialDTO1.setId(1L);
        HorarioEspecialDTO horarioEspecialDTO2 = new HorarioEspecialDTO();
        assertThat(horarioEspecialDTO1).isNotEqualTo(horarioEspecialDTO2);
        horarioEspecialDTO2.setId(horarioEspecialDTO1.getId());
        assertThat(horarioEspecialDTO1).isEqualTo(horarioEspecialDTO2);
        horarioEspecialDTO2.setId(2L);
        assertThat(horarioEspecialDTO1).isNotEqualTo(horarioEspecialDTO2);
        horarioEspecialDTO1.setId(null);
        assertThat(horarioEspecialDTO1).isNotEqualTo(horarioEspecialDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(horarioEspecialMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(horarioEspecialMapper.fromId(null)).isNull();
    }
}
