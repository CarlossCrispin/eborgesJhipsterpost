package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.TesisDirector;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TesisDirector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TesisDirectorRepository extends JpaRepository<TesisDirector, Long> {

}
