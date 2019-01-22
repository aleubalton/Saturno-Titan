package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.Intervalo;
import com.zenit.saturno.repository.IntervaloRepository;
import com.zenit.saturno.service.IntervaloService;
import com.zenit.saturno.service.dto.IntervaloDTO;
import com.zenit.saturno.service.mapper.IntervaloMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.zenit.saturno.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.zenit.saturno.domain.enumeration.Dia;
/**
 * Test class for the IntervaloResource REST controller.
 *
 * @see IntervaloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class IntervaloResourceIntTest {

    private static final Instant DEFAULT_FECHA_HORA_DESDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_HORA_DESDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DURACION = 0;
    private static final Integer UPDATED_DURACION = 1;

    private static final Dia DEFAULT_DIA = Dia.LUNES;
    private static final Dia UPDATED_DIA = Dia.MARTES;

    private static final Boolean DEFAULT_REPITE = false;
    private static final Boolean UPDATED_REPITE = true;

    @Autowired
    private IntervaloRepository intervaloRepository;

    @Autowired
    private IntervaloMapper intervaloMapper;

    @Autowired
    private IntervaloService intervaloService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIntervaloMockMvc;

    private Intervalo intervalo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntervaloResource intervaloResource = new IntervaloResource(intervaloService);
        this.restIntervaloMockMvc = MockMvcBuilders.standaloneSetup(intervaloResource)
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
    public static Intervalo createEntity(EntityManager em) {
        Intervalo intervalo = new Intervalo()
            .fechaHoraDesde(DEFAULT_FECHA_HORA_DESDE)
            .duracion(DEFAULT_DURACION)
            .dia(DEFAULT_DIA)
            .repite(DEFAULT_REPITE);
        return intervalo;
    }

    @Before
    public void initTest() {
        intervalo = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntervalo() throws Exception {
        int databaseSizeBeforeCreate = intervaloRepository.findAll().size();

        // Create the Intervalo
        IntervaloDTO intervaloDTO = intervaloMapper.toDto(intervalo);
        restIntervaloMockMvc.perform(post("/api/intervalos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervaloDTO)))
            .andExpect(status().isCreated());

        // Validate the Intervalo in the database
        List<Intervalo> intervaloList = intervaloRepository.findAll();
        assertThat(intervaloList).hasSize(databaseSizeBeforeCreate + 1);
        Intervalo testIntervalo = intervaloList.get(intervaloList.size() - 1);
        assertThat(testIntervalo.getFechaHoraDesde()).isEqualTo(DEFAULT_FECHA_HORA_DESDE);
        assertThat(testIntervalo.getDuracion()).isEqualTo(DEFAULT_DURACION);
        assertThat(testIntervalo.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testIntervalo.isRepite()).isEqualTo(DEFAULT_REPITE);
    }

    @Test
    @Transactional
    public void createIntervaloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intervaloRepository.findAll().size();

        // Create the Intervalo with an existing ID
        intervalo.setId(1L);
        IntervaloDTO intervaloDTO = intervaloMapper.toDto(intervalo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntervaloMockMvc.perform(post("/api/intervalos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervaloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intervalo in the database
        List<Intervalo> intervaloList = intervaloRepository.findAll();
        assertThat(intervaloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIntervalos() throws Exception {
        // Initialize the database
        intervaloRepository.saveAndFlush(intervalo);

        // Get all the intervaloList
        restIntervaloMockMvc.perform(get("/api/intervalos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intervalo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaHoraDesde").value(hasItem(DEFAULT_FECHA_HORA_DESDE.toString())))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].repite").value(hasItem(DEFAULT_REPITE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getIntervalo() throws Exception {
        // Initialize the database
        intervaloRepository.saveAndFlush(intervalo);

        // Get the intervalo
        restIntervaloMockMvc.perform(get("/api/intervalos/{id}", intervalo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intervalo.getId().intValue()))
            .andExpect(jsonPath("$.fechaHoraDesde").value(DEFAULT_FECHA_HORA_DESDE.toString()))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.repite").value(DEFAULT_REPITE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIntervalo() throws Exception {
        // Get the intervalo
        restIntervaloMockMvc.perform(get("/api/intervalos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntervalo() throws Exception {
        // Initialize the database
        intervaloRepository.saveAndFlush(intervalo);

        int databaseSizeBeforeUpdate = intervaloRepository.findAll().size();

        // Update the intervalo
        Intervalo updatedIntervalo = intervaloRepository.findById(intervalo.getId()).get();
        // Disconnect from session so that the updates on updatedIntervalo are not directly saved in db
        em.detach(updatedIntervalo);
        updatedIntervalo
            .fechaHoraDesde(UPDATED_FECHA_HORA_DESDE)
            .duracion(UPDATED_DURACION)
            .dia(UPDATED_DIA)
            .repite(UPDATED_REPITE);
        IntervaloDTO intervaloDTO = intervaloMapper.toDto(updatedIntervalo);

        restIntervaloMockMvc.perform(put("/api/intervalos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervaloDTO)))
            .andExpect(status().isOk());

        // Validate the Intervalo in the database
        List<Intervalo> intervaloList = intervaloRepository.findAll();
        assertThat(intervaloList).hasSize(databaseSizeBeforeUpdate);
        Intervalo testIntervalo = intervaloList.get(intervaloList.size() - 1);
        assertThat(testIntervalo.getFechaHoraDesde()).isEqualTo(UPDATED_FECHA_HORA_DESDE);
        assertThat(testIntervalo.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testIntervalo.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testIntervalo.isRepite()).isEqualTo(UPDATED_REPITE);
    }

    @Test
    @Transactional
    public void updateNonExistingIntervalo() throws Exception {
        int databaseSizeBeforeUpdate = intervaloRepository.findAll().size();

        // Create the Intervalo
        IntervaloDTO intervaloDTO = intervaloMapper.toDto(intervalo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntervaloMockMvc.perform(put("/api/intervalos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervaloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intervalo in the database
        List<Intervalo> intervaloList = intervaloRepository.findAll();
        assertThat(intervaloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntervalo() throws Exception {
        // Initialize the database
        intervaloRepository.saveAndFlush(intervalo);

        int databaseSizeBeforeDelete = intervaloRepository.findAll().size();

        // Get the intervalo
        restIntervaloMockMvc.perform(delete("/api/intervalos/{id}", intervalo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Intervalo> intervaloList = intervaloRepository.findAll();
        assertThat(intervaloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intervalo.class);
        Intervalo intervalo1 = new Intervalo();
        intervalo1.setId(1L);
        Intervalo intervalo2 = new Intervalo();
        intervalo2.setId(intervalo1.getId());
        assertThat(intervalo1).isEqualTo(intervalo2);
        intervalo2.setId(2L);
        assertThat(intervalo1).isNotEqualTo(intervalo2);
        intervalo1.setId(null);
        assertThat(intervalo1).isNotEqualTo(intervalo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntervaloDTO.class);
        IntervaloDTO intervaloDTO1 = new IntervaloDTO();
        intervaloDTO1.setId(1L);
        IntervaloDTO intervaloDTO2 = new IntervaloDTO();
        assertThat(intervaloDTO1).isNotEqualTo(intervaloDTO2);
        intervaloDTO2.setId(intervaloDTO1.getId());
        assertThat(intervaloDTO1).isEqualTo(intervaloDTO2);
        intervaloDTO2.setId(2L);
        assertThat(intervaloDTO1).isNotEqualTo(intervaloDTO2);
        intervaloDTO1.setId(null);
        assertThat(intervaloDTO1).isNotEqualTo(intervaloDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(intervaloMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(intervaloMapper.fromId(null)).isNull();
    }
}
