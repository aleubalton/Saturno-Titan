package com.zenit.saturno.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenit.saturno.service.TipoDeServicioService;
import com.zenit.saturno.web.rest.errors.BadRequestAlertException;
import com.zenit.saturno.web.rest.util.HeaderUtil;
import com.zenit.saturno.web.rest.util.PaginationUtil;
import com.zenit.saturno.service.dto.TipoDeServicioDTO;
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
 * REST controller for managing TipoDeServicio.
 */
@RestController
@RequestMapping("/api")
public class TipoDeServicioResource {

    private final Logger log = LoggerFactory.getLogger(TipoDeServicioResource.class);

    private static final String ENTITY_NAME = "tipoDeServicio";

    private final TipoDeServicioService tipoDeServicioService;

    public TipoDeServicioResource(TipoDeServicioService tipoDeServicioService) {
        this.tipoDeServicioService = tipoDeServicioService;
    }

    /**
     * POST  /tipo-de-servicios : Create a new tipoDeServicio.
     *
     * @param tipoDeServicioDTO the tipoDeServicioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDeServicioDTO, or with status 400 (Bad Request) if the tipoDeServicio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-de-servicios")
    @Timed
    public ResponseEntity<TipoDeServicioDTO> createTipoDeServicio(@Valid @RequestBody TipoDeServicioDTO tipoDeServicioDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDeServicio : {}", tipoDeServicioDTO);
        if (tipoDeServicioDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeServicio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeServicioDTO result = tipoDeServicioService.save(tipoDeServicioDTO);
        return ResponseEntity.created(new URI("/api/tipo-de-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-de-servicios : Updates an existing tipoDeServicio.
     *
     * @param tipoDeServicioDTO the tipoDeServicioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDeServicioDTO,
     * or with status 400 (Bad Request) if the tipoDeServicioDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDeServicioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-de-servicios")
    @Timed
    public ResponseEntity<TipoDeServicioDTO> updateTipoDeServicio(@Valid @RequestBody TipoDeServicioDTO tipoDeServicioDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDeServicio : {}", tipoDeServicioDTO);
        if (tipoDeServicioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDeServicioDTO result = tipoDeServicioService.save(tipoDeServicioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoDeServicioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-de-servicios : get all the tipoDeServicios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoDeServicios in body
     */
    @GetMapping("/tipo-de-servicios")
    @Timed
    public ResponseEntity<List<TipoDeServicioDTO>> getAllTipoDeServicios(Pageable pageable) {
        log.debug("REST request to get a page of TipoDeServicios");
        Page<TipoDeServicioDTO> page = tipoDeServicioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-de-servicios");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tipo-de-servicios/:id : get the "id" tipoDeServicio.
     *
     * @param id the id of the tipoDeServicioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDeServicioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-de-servicios/{id}")
    @Timed
    public ResponseEntity<TipoDeServicioDTO> getTipoDeServicio(@PathVariable Long id) {
        log.debug("REST request to get TipoDeServicio : {}", id);
        Optional<TipoDeServicioDTO> tipoDeServicioDTO = tipoDeServicioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeServicioDTO);
    }

    /**
     * DELETE  /tipo-de-servicios/:id : delete the "id" tipoDeServicio.
     *
     * @param id the id of the tipoDeServicioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-de-servicios/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoDeServicio(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeServicio : {}", id);
        tipoDeServicioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
