package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.Especialidad;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Especialidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

}
