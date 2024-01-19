package com.projetmicroservice.myapp.web.rest;

import com.projetmicroservice.myapp.domain.Medecin;
import com.projetmicroservice.myapp.repository.MedecinRepository;
import com.projetmicroservice.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.projetmicroservice.myapp.domain.Medecin}.
 */
@RestController
@RequestMapping("/api/medecins")
@Transactional
public class MedecinResource {

    private final Logger log = LoggerFactory.getLogger(MedecinResource.class);

    private static final String ENTITY_NAME = "projetmicroserviceMedecin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedecinRepository medecinRepository;

    public MedecinResource(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    /**
     * {@code POST  /medecins} : Create a new medecin.
     *
     * @param medecin the medecin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medecin, or with status {@code 400 (Bad Request)} if the medecin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Medecin> createMedecin(@RequestBody Medecin medecin) throws URISyntaxException {
        log.debug("REST request to save Medecin : {}", medecin);
        if (medecin.getId() != null) {
            throw new BadRequestAlertException("A new medecin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medecin result = medecinRepository.save(medecin);
        return ResponseEntity
            .created(new URI("/api/medecins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medecins/:id} : Updates an existing medecin.
     *
     * @param id the id of the medecin to save.
     * @param medecin the medecin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medecin,
     * or with status {@code 400 (Bad Request)} if the medecin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medecin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medecin> updateMedecin(@PathVariable(value = "id", required = false) final Long id, @RequestBody Medecin medecin)
        throws URISyntaxException {
        log.debug("REST request to update Medecin : {}, {}", id, medecin);
        if (medecin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medecin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medecinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Medecin result = medecinRepository.save(medecin);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medecin.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medecins/:id} : Partial updates given fields of an existing medecin, field will ignore if it is null
     *
     * @param id the id of the medecin to save.
     * @param medecin the medecin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medecin,
     * or with status {@code 400 (Bad Request)} if the medecin is not valid,
     * or with status {@code 404 (Not Found)} if the medecin is not found,
     * or with status {@code 500 (Internal Server Error)} if the medecin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Medecin> partialUpdateMedecin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Medecin medecin
    ) throws URISyntaxException {
        log.debug("REST request to partial update Medecin partially : {}, {}", id, medecin);
        if (medecin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medecin.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medecinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Medecin> result = medecinRepository
            .findById(medecin.getId())
            .map(existingMedecin -> {
                if (medecin.getNom() != null) {
                    existingMedecin.setNom(medecin.getNom());
                }
                if (medecin.getPrenom() != null) {
                    existingMedecin.setPrenom(medecin.getPrenom());
                }
                if (medecin.getRole() != null) {
                    existingMedecin.setRole(medecin.getRole());
                }
                if (medecin.getDisponibilite() != null) {
                    existingMedecin.setDisponibilite(medecin.getDisponibilite());
                }

                return existingMedecin;
            })
            .map(medecinRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medecin.getId().toString())
        );
    }

    /**
     * {@code GET  /medecins} : get all the medecins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medecins in body.
     */
    @GetMapping("")
    public List<Medecin> getAllMedecins() {
        log.debug("REST request to get all Medecins");
        return medecinRepository.findAll();
    }

    /**
     * {@code GET  /medecins/:id} : get the "id" medecin.
     *
     * @param id the id of the medecin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medecin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medecin> getMedecin(@PathVariable("id") Long id) {
        log.debug("REST request to get Medecin : {}", id);
        Optional<Medecin> medecin = medecinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(medecin);
    }

    /**
     * {@code DELETE  /medecins/:id} : delete the "id" medecin.
     *
     * @param id the id of the medecin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable("id") Long id) {
        log.debug("REST request to delete Medecin : {}", id);
        medecinRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
