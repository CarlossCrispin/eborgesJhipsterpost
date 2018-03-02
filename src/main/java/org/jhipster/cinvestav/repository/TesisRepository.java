package org.jhipster.cinvestav.repository;

import org.jhipster.cinvestav.domain.Tesis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tesis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TesisRepository extends JpaRepository<Tesis, Long> {

}
