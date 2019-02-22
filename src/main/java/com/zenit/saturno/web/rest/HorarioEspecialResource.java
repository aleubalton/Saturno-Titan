package com.zenit.saturno.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenit.saturno.service.HorarioEspecialService;
import com.zenit.saturno.web.rest.errors.BadRequestAlertException;
import com.zenit.saturno.web.rest.util.HeaderUtil;
import com.zenit.saturno.web.rest.util.PaginationUtil;
import com.zenit.saturno.service.dto.HorarioEspecialDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HorarioEspecial.
 */
@RestController
@RequestMapping("/api")
public class HorarioEspecialResource {

    private final Logger log = LoggerFactory.getLogger(HorarioEspecialResource.class);

    private static final String ENTITY_NAME = "horarioEspecial";

    private final HorarioEspecialService horarioEspecialService;

    public HorarioEspecialResource(HorarioEspecialService horarioEspecialService) {
        this.horarioEspecialService = horarioEspecialService;
    }

    /**
     * POST  /horario-especials : Create a new horarioEspecial.
     *
     * @param horarioEspecialDTO the horarioEspecialDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new horarioEspecialDTO, or with status 400 (Bad Request) if the horarioEspecial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/horario-especials")
    @Timed
    public ResponseEntity<HorarioEspecialDTO> createHorarioEspecial(@Valid @RequestBody HorarioEspecialDTO horarioEspecialDTO) throws URISyntaxException {
        log.debug("REST request to save HorarioEspecial : {}", horarioEspecialDTO);
        if (horarioEspecialDTO.getId() != null) {
            throw new BadRequestAlertException("A new horarioEspecial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HorarioEspecialDTO result = horarioEspecialService.save(horarioEspecialDTO);
        return ResponseEntity.created(new URI("/api/horario-especials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /horario-especials : Updates an existing horarioEspecial.
     *
     * @param horarioEspecialDTO the horarioEspecialDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated horarioEspecialDTO,
     * or with status 400 (Bad Request) if the horarioEspecialDTO is not valid,
     * or with status 500 (Internal Server Error) if the horarioEspecialDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/horario-especials")
    @Timed
    public ResponseEntity<HorarioEspecialDTO> updateHorarioEspecial(@Valid @RequestBody HorarioEspecialDTO horarioEspecialDTO) throws URISyntaxException {
        log.debug("REST request to update HorarioEspecial : {}", horarioEspecialDTO);
        if (horarioEspecialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HorarioEspecialDTO result = horarioEspecialService.save(horarioEspecialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, horarioEspecialDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /horario-especials : get all the horarioEspecials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of horarioEspecials in body
     */
    @GetMapping("/horario-especials")
    @Timed
    public ResponseEntity<List<HorarioEspecialDTO>> getAllHorarioEspecials(Pageable pageable) {
        log.debug("REST request to get a page of HorarioEspecials");
        Page<HorarioEspecialDTO> page = horarioEspecialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/horario-especials");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /horario-especials/:id : get the "id" horarioEspecial.
     *
     * @param id the id of the horarioEspecialDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the horarioEspecialDTO, or with status 404 (Not Found)
     */
    @GetMapping("/horario-especials/{id}")
    @Timed
    public ResponseEntity<HorarioEspecialDTO> getHorarioEspecial(@PathVariable Long id) {
        log.debug("REST request to get HorarioEspecial : {}", id);
        Optional<HorarioEspecialDTO> horarioEspecialDTO = horarioEspecialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horarioEspecialDTO);
    }

    /**
     * DELETE  /horario-especials/:id : delete the "id" horarioEspecial.
     *
     * @param id the id of the horarioEspecialDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/horario-especials/{id}")
    @Timed
    public ResponseEntity<Void> deleteHorarioEspecial(@PathVariable Long id) {
        log.debug("REST request to delete HorarioEspecial : {}", id);
        horarioEspecialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
