package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.Acta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Acta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActaRepository extends JpaRepository<Acta, Long> {

}
