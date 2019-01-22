package com.zenit.saturno.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenit.saturno.service.IntervaloService;
import com.zenit.saturno.web.rest.errors.BadRequestAlertException;
import com.zenit.saturno.web.rest.util.HeaderUtil;
import com.zenit.saturno.web.rest.util.PaginationUtil;
import com.zenit.saturno.service.dto.IntervaloDTO;
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
 * REST controller for managing Intervalo.
 */
@RestController
@RequestMapping("/api")
public class IntervaloResource {

    private final Logger log = LoggerFactory.getLogger(IntervaloResource.class);

    private static final String ENTITY_NAME = "intervalo";

    private final IntervaloService intervaloService;

    public IntervaloResource(IntervaloService intervaloService) {
        this.intervaloService = intervaloService;
    }

    /**
     * POST  /intervalos : Create a new intervalo.
     *
     * @param intervaloDTO the intervaloDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new intervaloDTO, or with status 400 (Bad Request) if the intervalo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/intervalos")
    @Timed
    public ResponseEntity<IntervaloDTO> createIntervalo(@Valid @RequestBody IntervaloDTO intervaloDTO) throws URISyntaxException {
        log.debug("REST request to save Intervalo : {}", intervaloDTO);
        if (intervaloDTO.getId() != null) {
            throw new BadRequestAlertException("A new intervalo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntervaloDTO result = intervaloService.save(intervaloDTO);
        return ResponseEntity.created(new URI("/api/intervalos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /intervalos : Updates an existing intervalo.
     *
     * @param intervaloDTO the intervaloDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated intervaloDTO,
     * or with status 400 (Bad Request) if the intervaloDTO is not valid,
     * or with status 500 (Internal Server Error) if the intervaloDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/intervalos")
    @Timed
    public ResponseEntity<IntervaloDTO> updateIntervalo(@Valid @RequestBody IntervaloDTO intervaloDTO) throws URISyntaxException {
        log.debug("REST request to update Intervalo : {}", intervaloDTO);
        if (intervaloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IntervaloDTO result = intervaloService.save(intervaloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, intervaloDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /intervalos : get all the intervalos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of intervalos in body
     */
    @GetMapping("/intervalos")
    @Timed
    public ResponseEntity<List<IntervaloDTO>> getAllIntervalos(Pageable pageable) {
        log.debug("REST request to get a page of Intervalos");
        Page<IntervaloDTO> page = intervaloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/intervalos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /intervalos/:id : get the "id" intervalo.
     *
     * @param id the id of the intervaloDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the intervaloDTO, or with status 404 (Not Found)
     */
    @GetMapping("/intervalos/{id}")
    @Timed
    public ResponseEntity<IntervaloDTO> getIntervalo(@PathVariable Long id) {
        log.debug("REST request to get Intervalo : {}", id);
        Optional<IntervaloDTO> intervaloDTO = intervaloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intervaloDTO);
    }

    /**
     * DELETE  /intervalos/:id : delete the "id" intervalo.
     *
     * @param id the id of the intervaloDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/intervalos/{id}")
    @Timed
    public ResponseEntity<Void> deleteIntervalo(@PathVariable Long id) {
        log.debug("REST request to delete Intervalo : {}", id);
        intervaloService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
