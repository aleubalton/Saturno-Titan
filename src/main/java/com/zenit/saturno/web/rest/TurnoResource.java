package com.zenit.saturno.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenit.saturno.service.MailService;
import com.zenit.saturno.service.TurnoService;
import com.zenit.saturno.service.ClienteService;
import com.zenit.saturno.service.VehiculoService;
import com.zenit.saturno.web.rest.errors.BadRequestAlertException;
import com.zenit.saturno.web.rest.util.HeaderUtil;
import com.zenit.saturno.web.rest.util.PaginationUtil;
import com.zenit.saturno.service.dto.TurnoDTO;
import com.zenit.saturno.service.dto.ClienteDTO;
import com.zenit.saturno.service.dto.VehiculoDTO;
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
 * REST controller for managing Turno.
 */
@RestController
@RequestMapping("/api")
public class TurnoResource {

    private final Logger log = LoggerFactory.getLogger(TurnoResource.class);

    private static final String ENTITY_NAME = "turno";

    private final TurnoService turnoService;

    private final ClienteService clienteService;

    private final VehiculoService vehiculoService;

    private final MailService mailService;

    public TurnoResource(TurnoService turnoService, ClienteService clienteService, VehiculoService vehiculoService, MailService mailService) {
        this.turnoService = turnoService;
        this.clienteService = clienteService;
        this.vehiculoService = vehiculoService;
        this.mailService = mailService;
    }

    /**
     * POST  /turnos : Create a new turno.
     *
     * @param turnoDTO the turnoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new turnoDTO, or with status 400 (Bad Request) if the turno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/turnos")
    @Timed
    public ResponseEntity<TurnoDTO> createTurno(@Valid @RequestBody TurnoDTO turnoDTO) throws URISyntaxException {
        log.debug("REST request to save Turno : {}", turnoDTO);
        if (turnoDTO.getId() != null) {
            throw new BadRequestAlertException("A new turno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TurnoDTO result = turnoService.save(turnoDTO);
        Optional<ClienteDTO> cliente = clienteService.findOne(result.getClienteId());
        Optional<VehiculoDTO> vehiculo = vehiculoService.findOne(result.getVehiculoId());
        mailService.sendTurnoEmail(result, cliente.get(), vehiculo.get());
        return ResponseEntity.created(new URI("/api/turnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /turnos : Updates an existing turno.
     *
     * @param turnoDTO the turnoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated turnoDTO,
     * or with status 400 (Bad Request) if the turnoDTO is not valid,
     * or with status 500 (Internal Server Error) if the turnoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/turnos")
    @Timed
    public ResponseEntity<TurnoDTO> updateTurno(@Valid @RequestBody TurnoDTO turnoDTO) throws URISyntaxException {
        log.debug("REST request to update Turno : {}", turnoDTO);
        if (turnoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TurnoDTO result = turnoService.save(turnoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, turnoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /turnos : get all the turnos.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of turnos in body
     */
    @GetMapping("/turnos")
    @Timed
    public ResponseEntity<List<TurnoDTO>> getAllTurnos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Turnos");
        Page<TurnoDTO> page;
        if (eagerload) {
            page = turnoService.findAllWithEagerRelationships(pageable);
        } else {
            page = turnoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/turnos?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /turnos/:id : get the "id" turno.
     *
     * @param id the id of the turnoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the turnoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/turnos/{id}")
    @Timed
    public ResponseEntity<TurnoDTO> getTurno(@PathVariable Long id) {
        log.debug("REST request to get Turno : {}", id);
        Optional<TurnoDTO> turnoDTO = turnoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(turnoDTO);
    }

    /**
     * GET  /turnos/:codigoReserva : get the "codigoReserva" turno.
     *
     * @param codigoReserva the codigoReserva of the turnoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the turnoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/turnosByCodigoReserva/{codigoReserva}")
    @Timed
    public ResponseEntity<TurnoDTO> getTurnoByCodigoReserva(@PathVariable String codigoReserva) {
        log.debug("REST request to get Turno : {}", codigoReserva);
        Optional<TurnoDTO> turnoDTO = turnoService.findByCodigoReserva(codigoReserva);
        return ResponseUtil.wrapOrNotFound(turnoDTO);
    }

    /**
     * DELETE  /turnos/:id : delete the "id" turno.
     *
     * @param id the id of the turnoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/turnos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        log.debug("REST request to delete Turno : {}", id);
        turnoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
