package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.Alumno;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Alumno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

}
