package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Especialidad;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Especialidad entity.
 */
public interface EspecialidadSearchRepository extends ElasticsearchRepository<Especialidad, Long> {
}
