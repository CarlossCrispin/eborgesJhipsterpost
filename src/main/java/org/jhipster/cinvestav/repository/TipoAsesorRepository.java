package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.TipoAsesor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoAsesor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoAsesorRepository extends JpaRepository<TipoAsesor, Long> {

}
