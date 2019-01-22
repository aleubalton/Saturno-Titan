package com.zenit.saturno.web.rest;

import com.zenit.saturno.SaturnoApp;

import com.zenit.saturno.domain.PrecioServicio;
import com.zenit.saturno.domain.Modelo;
import com.zenit.saturno.domain.Servicio;
import com.zenit.saturno.repository.PrecioServicioRepository;
import com.zenit.saturno.service.PrecioServicioService;
import com.zenit.saturno.service.dto.PrecioServicioDTO;
import com.zenit.saturno.service.mapper.PrecioServicioMapper;
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
 * Test class for the PrecioServicioResource REST controller.
 *
 * @see PrecioServicioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaturnoApp.class)
public class PrecioServicioResourceIntTest {

    private static final Integer DEFAULT_PRECIO = 0;
    private static final Integer UPDATED_PRECIO = 1;

    @Autowired
    private PrecioServicioRepository precioServicioRepository;

    @Autowired
    private PrecioServicioMapper precioServicioMapper;

    @Autowired
    private PrecioServicioService precioServicioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrecioServicioMockMvc;

    private PrecioServicio precioServicio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrecioServicioResource precioServicioResource = new PrecioServicioResource(precioServicioService);
        this.restPrecioServicioMockMvc = MockMvcBuilders.standaloneSetup(precioServicioResource)
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
    public static PrecioServicio createEntity(EntityManager em) {
        PrecioServicio precioServicio = new PrecioServicio()
            .precio(DEFAULT_PRECIO);
        // Add required entity
        Modelo modelo = ModeloResourceIntTest.createEntity(em);
        em.persist(modelo);
        em.flush();
        precioServicio.setModelo(modelo);
        // Add required entity
        Servicio servicio = ServicioResourceIntTest.createEntity(em);
        em.persist(servicio);
        em.flush();
        precioServicio.setServicio(servicio);
        return precioServicio;
    }

    @Before
    public void initTest() {
        precioServicio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrecioServicio() throws Exception {
        int databaseSizeBeforeCreate = precioServicioRepository.findAll().size();

        // Create the PrecioServicio
        PrecioServicioDTO precioServicioDTO = precioServicioMapper.toDto(precioServicio);
        restPrecioServicioMockMvc.perform(post("/api/precio-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioServicioDTO)))
            .andExpect(status().isCreated());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeCreate + 1);
        PrecioServicio testPrecioServicio = precioServicioList.get(precioServicioList.size() - 1);
        assertThat(testPrecioServicio.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createPrecioServicioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = precioServicioRepository.findAll().size();

        // Create the PrecioServicio with an existing ID
        precioServicio.setId(1L);
        PrecioServicioDTO precioServicioDTO = precioServicioMapper.toDto(precioServicio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrecioServicioMockMvc.perform(post("/api/precio-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioServicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = precioServicioRepository.findAll().size();
        // set the field null
        precioServicio.setPrecio(null);

        // Create the PrecioServicio, which fails.
        PrecioServicioDTO precioServicioDTO = precioServicioMapper.toDto(precioServicio);

        restPrecioServicioMockMvc.perform(post("/api/precio-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioServicioDTO)))
            .andExpect(status().isBadRequest());

        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrecioServicios() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        // Get all the precioServicioList
        restPrecioServicioMockMvc.perform(get("/api/precio-servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precioServicio.getId().intValue())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO)));
    }
    
    @Test
    @Transactional
    public void getPrecioServicio() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        // Get the precioServicio
        restPrecioServicioMockMvc.perform(get("/api/precio-servicios/{id}", precioServicio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(precioServicio.getId().intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO));
    }

    @Test
    @Transactional
    public void getNonExistingPrecioServicio() throws Exception {
        // Get the precioServicio
        restPrecioServicioMockMvc.perform(get("/api/precio-servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrecioServicio() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();

        // Update the precioServicio
        PrecioServicio updatedPrecioServicio = precioServicioRepository.findById(precioServicio.getId()).get();
        // Disconnect from session so that the updates on updatedPrecioServicio are not directly saved in db
        em.detach(updatedPrecioServicio);
        updatedPrecioServicio
            .precio(UPDATED_PRECIO);
        PrecioServicioDTO precioServicioDTO = precioServicioMapper.toDto(updatedPrecioServicio);

        restPrecioServicioMockMvc.perform(put("/api/precio-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioServicioDTO)))
            .andExpect(status().isOk());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
        PrecioServicio testPrecioServicio = precioServicioList.get(precioServicioList.size() - 1);
        assertThat(testPrecioServicio.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingPrecioServicio() throws Exception {
        int databaseSizeBeforeUpdate = precioServicioRepository.findAll().size();

        // Create the PrecioServicio
        PrecioServicioDTO precioServicioDTO = precioServicioMapper.toDto(precioServicio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrecioServicioMockMvc.perform(put("/api/precio-servicios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precioServicioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrecioServicio in the database
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrecioServicio() throws Exception {
        // Initialize the database
        precioServicioRepository.saveAndFlush(precioServicio);

        int databaseSizeBeforeDelete = precioServicioRepository.findAll().size();

        // Get the precioServicio
        restPrecioServicioMockMvc.perform(delete("/api/precio-servicios/{id}", precioServicio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrecioServicio> precioServicioList = precioServicioRepository.findAll();
        assertThat(precioServicioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecioServicio.class);
        PrecioServicio precioServicio1 = new PrecioServicio();
        precioServicio1.setId(1L);
        PrecioServicio precioServicio2 = new PrecioServicio();
        precioServicio2.setId(precioServicio1.getId());
        assertThat(precioServicio1).isEqualTo(precioServicio2);
        precioServicio2.setId(2L);
        assertThat(precioServicio1).isNotEqualTo(precioServicio2);
        precioServicio1.setId(null);
        assertThat(precioServicio1).isNotEqualTo(precioServicio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecioServicioDTO.class);
        PrecioServicioDTO precioServicioDTO1 = new PrecioServicioDTO();
        precioServicioDTO1.setId(1L);
        PrecioServicioDTO precioServicioDTO2 = new PrecioServicioDTO();
        assertThat(precioServicioDTO1).isNotEqualTo(precioServicioDTO2);
        precioServicioDTO2.setId(precioServicioDTO1.getId());
        assertThat(precioServicioDTO1).isEqualTo(precioServicioDTO2);
        precioServicioDTO2.setId(2L);
        assertThat(precioServicioDTO1).isNotEqualTo(precioServicioDTO2);
        precioServicioDTO1.setId(null);
        assertThat(precioServicioDTO1).isNotEqualTo(precioServicioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(precioServicioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(precioServicioMapper.fromId(null)).isNull();
    }
}
