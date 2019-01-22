package com.zenit.saturno.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenit.saturno.service.AgendaService;
import com.zenit.saturno.web.rest.errors.BadRequestAlertException;
import com.zenit.saturno.web.rest.util.HeaderUtil;
import com.zenit.saturno.web.rest.util.PaginationUtil;
import com.zenit.saturno.service.dto.AgendaDTO;
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
 * REST controller for managing Agenda.
 */
@RestController
@RequestMapping("/api")
public class AgendaResource {

    private final Logger log = LoggerFactory.getLogger(AgendaResource.class);

    private static final String ENTITY_NAME = "agenda";

    private final AgendaService agendaService;

    public AgendaResource(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * POST  /agenda : Create a new agenda.
     *
     * @param agendaDTO the agendaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agendaDTO, or with status 400 (Bad Request) if the agenda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agenda")
    @Timed
    public ResponseEntity<AgendaDTO> createAgenda(@Valid @RequestBody AgendaDTO agendaDTO) throws URISyntaxException {
        log.debug("REST request to save Agenda : {}", agendaDTO);
        if (agendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new agenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgendaDTO result = agendaService.save(agendaDTO);
        return ResponseEntity.created(new URI("/api/agenda/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agenda : Updates an existing agenda.
     *
     * @param agendaDTO the agendaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agendaDTO,
     * or with status 400 (Bad Request) if the agendaDTO is not valid,
     * or with status 500 (Internal Server Error) if the agendaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agenda")
    @Timed
    public ResponseEntity<AgendaDTO> updateAgenda(@Valid @RequestBody AgendaDTO agendaDTO) throws URISyntaxException {
        log.debug("REST request to update Agenda : {}", agendaDTO);
        if (agendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AgendaDTO result = agendaService.save(agendaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agenda : get all the agenda.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of agenda in body
     */
    @GetMapping("/agenda")
    @Timed
    public ResponseEntity<List<AgendaDTO>> getAllAgenda(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Agenda");
        Page<AgendaDTO> page;
        if (eagerload) {
            page = agendaService.findAllWithEagerRelationships(pageable);
        } else {
            page = agendaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/agenda?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /agenda/:id : get the "id" agenda.
     *
     * @param id the id of the agendaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agendaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agenda/{id}")
    @Timed
    public ResponseEntity<AgendaDTO> getAgenda(@PathVariable Long id) {
        log.debug("REST request to get Agenda : {}", id);
        Optional<AgendaDTO> agendaDTO = agendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendaDTO);
    }

    /**
     * DELETE  /agenda/:id : delete the "id" agenda.
     *
     * @param id the id of the agendaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agenda/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgenda(@PathVariable Long id) {
        log.debug("REST request to delete Agenda : {}", id);
        agendaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
