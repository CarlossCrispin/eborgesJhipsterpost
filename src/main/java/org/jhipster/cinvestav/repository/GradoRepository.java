package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.Grado;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Grado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradoRepository extends JpaRepository<Grado, Long> {

}
