package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.DiaNoLaborable;
import com.zenit.saturno.repository.DiaNoLaborableRepository;
import com.zenit.saturno.service.DiaNoLaborableService;
import com.zenit.saturno.service.dto.DiaNoLaborableDTO;
import com.zenit.saturno.service.mapper.DiaNoLaborableMapper;
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

import com.zenit.saturno.domain.enumeration.Dia;
/**
 * Test class for the DiaNoLaborableResource REST controller.
 *
 * @see DiaNoLaborableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class DiaNoLaborableResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Dia DEFAULT_DIA = Dia.LUNES;
    private static final Dia UPDATED_DIA = Dia.MARTES;

    private static final Boolean DEFAULT_REPITE = false;
    private static final Boolean UPDATED_REPITE = true;

    @Autowired
    private DiaNoLaborableRepository diaNoLaborableRepository;

    @Autowired
    private DiaNoLaborableMapper diaNoLaborableMapper;

    @Autowired
    private DiaNoLaborableService diaNoLaborableService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiaNoLaborableMockMvc;

    private DiaNoLaborable diaNoLaborable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiaNoLaborableResource diaNoLaborableResource = new DiaNoLaborableResource(diaNoLaborableService);
        this.restDiaNoLaborableMockMvc = MockMvcBuilders.standaloneSetup(diaNoLaborableResource)
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
    public static DiaNoLaborable createEntity(EntityManager em) {
        DiaNoLaborable diaNoLaborable = new DiaNoLaborable()
            .fecha(DEFAULT_FECHA)
            .dia(DEFAULT_DIA)
            .repite(DEFAULT_REPITE);
        return diaNoLaborable;
    }

    @Before
    public void initTest() {
        diaNoLaborable = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiaNoLaborable() throws Exception {
        int databaseSizeBeforeCreate = diaNoLaborableRepository.findAll().size();

        // Create the DiaNoLaborable
        DiaNoLaborableDTO diaNoLaborableDTO = diaNoLaborableMapper.toDto(diaNoLaborable);
        restDiaNoLaborableMockMvc.perform(post("/api/dia-no-laborables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaNoLaborableDTO)))
            .andExpect(status().isCreated());

        // Validate the DiaNoLaborable in the database
        List<DiaNoLaborable> diaNoLaborableList = diaNoLaborableRepository.findAll();
        assertThat(diaNoLaborableList).hasSize(databaseSizeBeforeCreate + 1);
        DiaNoLaborable testDiaNoLaborable = diaNoLaborableList.get(diaNoLaborableList.size() - 1);
        assertThat(testDiaNoLaborable.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testDiaNoLaborable.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testDiaNoLaborable.isRepite()).isEqualTo(DEFAULT_REPITE);
    }

    @Test
    @Transactional
    public void createDiaNoLaborableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diaNoLaborableRepository.findAll().size();

        // Create the DiaNoLaborable with an existing ID
        diaNoLaborable.setId(1L);
        DiaNoLaborableDTO diaNoLaborableDTO = diaNoLaborableMapper.toDto(diaNoLaborable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiaNoLaborableMockMvc.perform(post("/api/dia-no-laborables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaNoLaborableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiaNoLaborable in the database
        List<DiaNoLaborable> diaNoLaborableList = diaNoLaborableRepository.findAll();
        assertThat(diaNoLaborableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDiaNoLaborables() throws Exception {
        // Initialize the database
        diaNoLaborableRepository.saveAndFlush(diaNoLaborable);

        // Get all the diaNoLaborableList
        restDiaNoLaborableMockMvc.perform(get("/api/dia-no-laborables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diaNoLaborable.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].repite").value(hasItem(DEFAULT_REPITE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDiaNoLaborable() throws Exception {
        // Initialize the database
        diaNoLaborableRepository.saveAndFlush(diaNoLaborable);

        // Get the diaNoLaborable
        restDiaNoLaborableMockMvc.perform(get("/api/dia-no-laborables/{id}", diaNoLaborable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diaNoLaborable.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.repite").value(DEFAULT_REPITE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiaNoLaborable() throws Exception {
        // Get the diaNoLaborable
        restDiaNoLaborableMockMvc.perform(get("/api/dia-no-laborables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiaNoLaborable() throws Exception {
        // Initialize the database
        diaNoLaborableRepository.saveAndFlush(diaNoLaborable);

        int databaseSizeBeforeUpdate = diaNoLaborableRepository.findAll().size();

        // Update the diaNoLaborable
        DiaNoLaborable updatedDiaNoLaborable = diaNoLaborableRepository.findById(diaNoLaborable.getId()).get();
        // Disconnect from session so that the updates on updatedDiaNoLaborable are not directly saved in db
        em.detach(updatedDiaNoLaborable);
        updatedDiaNoLaborable
            .fecha(UPDATED_FECHA)
            .dia(UPDATED_DIA)
            .repite(UPDATED_REPITE);
        DiaNoLaborableDTO diaNoLaborableDTO = diaNoLaborableMapper.toDto(updatedDiaNoLaborable);

        restDiaNoLaborableMockMvc.perform(put("/api/dia-no-laborables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaNoLaborableDTO)))
            .andExpect(status().isOk());

        // Validate the DiaNoLaborable in the database
        List<DiaNoLaborable> diaNoLaborableList = diaNoLaborableRepository.findAll();
        assertThat(diaNoLaborableList).hasSize(databaseSizeBeforeUpdate);
        DiaNoLaborable testDiaNoLaborable = diaNoLaborableList.get(diaNoLaborableList.size() - 1);
        assertThat(testDiaNoLaborable.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testDiaNoLaborable.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testDiaNoLaborable.isRepite()).isEqualTo(UPDATED_REPITE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiaNoLaborable() throws Exception {
        int databaseSizeBeforeUpdate = diaNoLaborableRepository.findAll().size();

        // Create the DiaNoLaborable
        DiaNoLaborableDTO diaNoLaborableDTO = diaNoLaborableMapper.toDto(diaNoLaborable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiaNoLaborableMockMvc.perform(put("/api/dia-no-laborables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diaNoLaborableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiaNoLaborable in the database
        List<DiaNoLaborable> diaNoLaborableList = diaNoLaborableRepository.findAll();
        assertThat(diaNoLaborableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiaNoLaborable() throws Exception {
        // Initialize the database
        diaNoLaborableRepository.saveAndFlush(diaNoLaborable);

        int databaseSizeBeforeDelete = diaNoLaborableRepository.findAll().size();

        // Get the diaNoLaborable
        restDiaNoLaborableMockMvc.perform(delete("/api/dia-no-laborables/{id}", diaNoLaborable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DiaNoLaborable> diaNoLaborableList = diaNoLaborableRepository.findAll();
        assertThat(diaNoLaborableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaNoLaborable.class);
        DiaNoLaborable diaNoLaborable1 = new DiaNoLaborable();
        diaNoLaborable1.setId(1L);
        DiaNoLaborable diaNoLaborable2 = new DiaNoLaborable();
        diaNoLaborable2.setId(diaNoLaborable1.getId());
        assertThat(diaNoLaborable1).isEqualTo(diaNoLaborable2);
        diaNoLaborable2.setId(2L);
        assertThat(diaNoLaborable1).isNotEqualTo(diaNoLaborable2);
        diaNoLaborable1.setId(null);
        assertThat(diaNoLaborable1).isNotEqualTo(diaNoLaborable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaNoLaborableDTO.class);
        DiaNoLaborableDTO diaNoLaborableDTO1 = new DiaNoLaborableDTO();
        diaNoLaborableDTO1.setId(1L);
        DiaNoLaborableDTO diaNoLaborableDTO2 = new DiaNoLaborableDTO();
        assertThat(diaNoLaborableDTO1).isNotEqualTo(diaNoLaborableDTO2);
        diaNoLaborableDTO2.setId(diaNoLaborableDTO1.getId());
        assertThat(diaNoLaborableDTO1).isEqualTo(diaNoLaborableDTO2);
        diaNoLaborableDTO2.setId(2L);
        assertThat(diaNoLaborableDTO1).isNotEqualTo(diaNoLaborableDTO2);
        diaNoLaborableDTO1.setId(null);
        assertThat(diaNoLaborableDTO1).isNotEqualTo(diaNoLaborableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(diaNoLaborableMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(diaNoLaborableMapper.fromId(null)).isNull();
    }
}
