package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Alumno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Alumno entity.
 */
public interface AlumnoSearchRepository extends ElasticsearchRepository<Alumno, Long> {
}
