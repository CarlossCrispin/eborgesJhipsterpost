package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.Sinodal;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sinodal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SinodalRepository extends JpaRepository<Sinodal, Long> {

}
