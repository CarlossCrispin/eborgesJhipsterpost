package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.Investigador;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Investigador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvestigadorRepository extends JpaRepository<Investigador, Long> {

}
