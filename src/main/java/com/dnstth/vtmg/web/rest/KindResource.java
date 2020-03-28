package com.dnstth.vtmg.web.rest;

import com.dnstth.vtmg.domain.Kind;
import com.dnstth.vtmg.repository.KindRepository;
import com.dnstth.vtmg.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dnstth.vtmg.domain.Kind}.
 */
@RestController
@RequestMapping("/api")
public class KindResource {

    private final Logger log = LoggerFactory.getLogger(KindResource.class);

    private static final String ENTITY_NAME = "kind";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KindRepository kindRepository;

    public KindResource(KindRepository kindRepository) {
        this.kindRepository = kindRepository;
    }

    /**
     * {@code POST  /kinds} : Create a new kind.
     *
     * @param kind the kind to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kind, or with status {@code 400 (Bad Request)} if the kind has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kinds")
    public ResponseEntity<Kind> createKind(@Valid @RequestBody Kind kind) throws URISyntaxException {
        log.debug("REST request to save Kind : {}", kind);
        if (kind.getId() != null) {
            throw new BadRequestAlertException("A new kind cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kind result = kindRepository.save(kind);
        return ResponseEntity.created(new URI("/api/kinds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kinds} : Updates an existing kind.
     *
     * @param kind the kind to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kind,
     * or with status {@code 400 (Bad Request)} if the kind is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kind couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kinds")
    public ResponseEntity<Kind> updateKind(@Valid @RequestBody Kind kind) throws URISyntaxException {
        log.debug("REST request to update Kind : {}", kind);
        if (kind.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Kind result = kindRepository.save(kind);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kind.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kinds} : get all the kinds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kinds in body.
     */
    @GetMapping("/kinds")
    public List<Kind> getAllKinds() {
        log.debug("REST request to get all Kinds");
        return kindRepository.findAll();
    }

    /**
     * {@code GET  /kinds/:id} : get the "id" kind.
     *
     * @param id the id of the kind to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kind, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kinds/{id}")
    public ResponseEntity<Kind> getKind(@PathVariable String id) {
        log.debug("REST request to get Kind : {}", id);
        Optional<Kind> kind = kindRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kind);
    }

    /**
     * {@code DELETE  /kinds/:id} : delete the "id" kind.
     *
     * @param id the id of the kind to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kinds/{id}")
    public ResponseEntity<Void> deleteKind(@PathVariable String id) {
        log.debug("REST request to delete Kind : {}", id);
        kindRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
