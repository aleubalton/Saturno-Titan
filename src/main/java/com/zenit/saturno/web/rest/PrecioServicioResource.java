package com.zenit.saturno.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenit.saturno.service.PrecioServicioService;
import com.zenit.saturno.web.rest.errors.BadRequestAlertException;
import com.zenit.saturno.web.rest.util.HeaderUtil;
import com.zenit.saturno.web.rest.util.PaginationUtil;
import com.zenit.saturno.service.dto.PrecioServicioDTO;
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
 * REST controller for managing PrecioServicio.
 */
@RestController
@RequestMapping("/api")
public class PrecioServicioResource {

    private final Logger log = LoggerFactory.getLogger(PrecioServicioResource.class);

    private static final String ENTITY_NAME = "precioServicio";

    private final PrecioServicioService precioServicioService;

    public PrecioServicioResource(PrecioServicioService precioServicioService) {
        this.precioServicioService = precioServicioService;
    }

    /**
     * POST  /precio-servicios : Create a new precioServicio.
     *
     * @param precioServicioDTO the precioServicioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new precioServicioDTO, or with status 400 (Bad Request) if the precioServicio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/precio-servicios")
    @Timed
    public ResponseEntity<PrecioServicioDTO> createPrecioServicio(@Valid @RequestBody PrecioServicioDTO precioServicioDTO) throws URISyntaxException {
        log.debug("REST request to save PrecioServicio : {}", precioServicioDTO);
        if (precioServicioDTO.getId() != null) {
            throw new BadRequestAlertException("A new precioServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrecioServicioDTO result = precioServicioService.save(precioServicioDTO);
        return ResponseEntity.created(new URI("/api/precio-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /precio-servicios : Updates an existing precioServicio.
     *
     * @param precioServicioDTO the precioServicioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated precioServicioDTO,
     * or with status 400 (Bad Request) if the precioServicioDTO is not valid,
     * or with status 500 (Internal Server Error) if the precioServicioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/precio-servicios")
    @Timed
    public ResponseEntity<PrecioServicioDTO> updatePrecioServicio(@Valid @RequestBody PrecioServicioDTO precioServicioDTO) throws URISyntaxException {
        log.debug("REST request to update PrecioServicio : {}", precioServicioDTO);
        if (precioServicioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrecioServicioDTO result = precioServicioService.save(precioServicioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, precioServicioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /precio-servicios : get all the precioServicios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of precioServicios in body
     */
    @GetMapping("/precio-servicios")
    @Timed
    public ResponseEntity<List<PrecioServicioDTO>> getAllPrecioServicios(Pageable pageable) {
        log.debug("REST request to get a page of PrecioServicios");
        Page<PrecioServicioDTO> page = precioServicioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/precio-servicios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /precio-servicios/:id : get the "id" precioServicio.
     *
     * @param id the id of the precioServicioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the precioServicioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/precio-servicios/{id}")
    @Timed
    public ResponseEntity<PrecioServicioDTO> getPrecioServicio(@PathVariable Long id) {
        log.debug("REST request to get PrecioServicio : {}", id);
        Optional<PrecioServicioDTO> precioServicioDTO = precioServicioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(precioServicioDTO);
    }

    /**
     * DELETE  /precio-servicios/:id : delete the "id" precioServicio.
     *
     * @param id the id of the precioServicioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/precio-servicios/{id}")
    @Timed
    public ResponseEntity<Void> deletePrecioServicio(@PathVariable Long id) {
        log.debug("REST request to delete PrecioServicio : {}", id);
        precioServicioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
