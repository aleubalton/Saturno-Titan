package com.zenit.saturno.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenit.saturno.service.DiaNoLaborableService;
import com.zenit.saturno.web.rest.errors.BadRequestAlertException;
import com.zenit.saturno.web.rest.util.HeaderUtil;
import com.zenit.saturno.web.rest.util.PaginationUtil;
import com.zenit.saturno.service.dto.DiaNoLaborableDTO;
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
 * REST controller for managing DiaNoLaborable.
 */
@RestController
@RequestMapping("/api")
public class DiaNoLaborableResource {

    private final Logger log = LoggerFactory.getLogger(DiaNoLaborableResource.class);

    private static final String ENTITY_NAME = "diaNoLaborable";

    private final DiaNoLaborableService diaNoLaborableService;

    public DiaNoLaborableResource(DiaNoLaborableService diaNoLaborableService) {
        this.diaNoLaborableService = diaNoLaborableService;
    }

    /**
     * POST  /dia-no-laborables : Create a new diaNoLaborable.
     *
     * @param diaNoLaborableDTO the diaNoLaborableDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diaNoLaborableDTO, or with status 400 (Bad Request) if the diaNoLaborable has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dia-no-laborables")
    @Timed
    public ResponseEntity<DiaNoLaborableDTO> createDiaNoLaborable(@Valid @RequestBody DiaNoLaborableDTO diaNoLaborableDTO) throws URISyntaxException {
        log.debug("REST request to save DiaNoLaborable : {}", diaNoLaborableDTO);
        if (diaNoLaborableDTO.getId() != null) {
            throw new BadRequestAlertException("A new diaNoLaborable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiaNoLaborableDTO result = diaNoLaborableService.save(diaNoLaborableDTO);
        return ResponseEntity.created(new URI("/api/dia-no-laborables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dia-no-laborables : Updates an existing diaNoLaborable.
     *
     * @param diaNoLaborableDTO the diaNoLaborableDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diaNoLaborableDTO,
     * or with status 400 (Bad Request) if the diaNoLaborableDTO is not valid,
     * or with status 500 (Internal Server Error) if the diaNoLaborableDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dia-no-laborables")
    @Timed
    public ResponseEntity<DiaNoLaborableDTO> updateDiaNoLaborable(@Valid @RequestBody DiaNoLaborableDTO diaNoLaborableDTO) throws URISyntaxException {
        log.debug("REST request to update DiaNoLaborable : {}", diaNoLaborableDTO);
        if (diaNoLaborableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiaNoLaborableDTO result = diaNoLaborableService.save(diaNoLaborableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diaNoLaborableDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dia-no-laborables : get all the diaNoLaborables.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of diaNoLaborables in body
     */
    @GetMapping("/dia-no-laborables")
    @Timed
    public ResponseEntity<List<DiaNoLaborableDTO>> getAllDiaNoLaborables(Pageable pageable) {
        log.debug("REST request to get a page of DiaNoLaborables");
        Page<DiaNoLaborableDTO> page = diaNoLaborableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dia-no-laborables");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dia-no-laborables/:id : get the "id" diaNoLaborable.
     *
     * @param id the id of the diaNoLaborableDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diaNoLaborableDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dia-no-laborables/{id}")
    @Timed
    public ResponseEntity<DiaNoLaborableDTO> getDiaNoLaborable(@PathVariable Long id) {
        log.debug("REST request to get DiaNoLaborable : {}", id);
        Optional<DiaNoLaborableDTO> diaNoLaborableDTO = diaNoLaborableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diaNoLaborableDTO);
    }

    /**
     * DELETE  /dia-no-laborables/:id : delete the "id" diaNoLaborable.
     *
     * @param id the id of the diaNoLaborableDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dia-no-laborables/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiaNoLaborable(@PathVariable Long id) {
        log.debug("REST request to delete DiaNoLaborable : {}", id);
        diaNoLaborableService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
