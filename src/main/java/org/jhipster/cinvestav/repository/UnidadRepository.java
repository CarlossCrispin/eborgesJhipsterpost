package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.Unidad;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Unidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Long> {

}
