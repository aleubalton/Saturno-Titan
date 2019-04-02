package com.zenit.saturno.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenit.saturno.service.PlanMantenimientoService;
import com.zenit.saturno.web.rest.errors.BadRequestAlertException;
import com.zenit.saturno.web.rest.util.HeaderUtil;
import com.zenit.saturno.web.rest.util.PaginationUtil;
import com.zenit.saturno.service.dto.PlanMantenimientoDTO;
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
 * REST controller for managing PlanMantenimiento.
 */
@RestController
@RequestMapping("/api")
public class PlanMantenimientoResource {

    private final Logger log = LoggerFactory.getLogger(PlanMantenimientoResource.class);

    private static final String ENTITY_NAME = "planMantenimiento";

    private final PlanMantenimientoService planMantenimientoService;

    public PlanMantenimientoResource(PlanMantenimientoService planMantenimientoService) {
        this.planMantenimientoService = planMantenimientoService;
    }

    /**
     * POST  /plan-mantenimientos : Create a new planMantenimiento.
     *
     * @param planMantenimientoDTO the planMantenimientoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planMantenimientoDTO, or with status 400 (Bad Request) if the planMantenimiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plan-mantenimientos")
    @Timed
    public ResponseEntity<PlanMantenimientoDTO> createPlanMantenimiento(@Valid @RequestBody PlanMantenimientoDTO planMantenimientoDTO) throws URISyntaxException {
        log.debug("REST request to save PlanMantenimiento : {}", planMantenimientoDTO);
        if (planMantenimientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new planMantenimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanMantenimientoDTO result = planMantenimientoService.save(planMantenimientoDTO);
        return ResponseEntity.created(new URI("/api/plan-mantenimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /plan-mantenimientos : Updates an existing planMantenimiento.
     *
     * @param planMantenimientoDTO the planMantenimientoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planMantenimientoDTO,
     * or with status 400 (Bad Request) if the planMantenimientoDTO is not valid,
     * or with status 500 (Internal Server Error) if the planMantenimientoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plan-mantenimientos")
    @Timed
    public ResponseEntity<PlanMantenimientoDTO> updatePlanMantenimiento(@Valid @RequestBody PlanMantenimientoDTO planMantenimientoDTO) throws URISyntaxException {
        log.debug("REST request to update PlanMantenimiento : {}", planMantenimientoDTO);
        if (planMantenimientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanMantenimientoDTO result = planMantenimientoService.save(planMantenimientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planMantenimientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plan-mantenimientos : get all the planMantenimientos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of planMantenimientos in body
     */
    @GetMapping("/plan-mantenimientos")
    @Timed
    public ResponseEntity<List<PlanMantenimientoDTO>> getAllPlanMantenimientos(Pageable pageable) {
        log.debug("REST request to get a page of PlanMantenimientos");
        Page<PlanMantenimientoDTO> page = planMantenimientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plan-mantenimientos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /plan-mantenimientos/:id : get the "id" planMantenimiento.
     *
     * @param id the id of the planMantenimientoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planMantenimientoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/plan-mantenimientos/{id}")
    @Timed
    public ResponseEntity<PlanMantenimientoDTO> getPlanMantenimiento(@PathVariable Long id) {
        log.debug("REST request to get PlanMantenimiento : {}", id);
        Optional<PlanMantenimientoDTO> planMantenimientoDTO = planMantenimientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planMantenimientoDTO);
    }

    /**
     * DELETE  /plan-mantenimientos/:id : delete the "id" planMantenimiento.
     *
     * @param id the id of the planMantenimientoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plan-mantenimientos/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanMantenimiento(@PathVariable Long id) {
        log.debug("REST request to delete PlanMantenimiento : {}", id);
        planMantenimientoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
