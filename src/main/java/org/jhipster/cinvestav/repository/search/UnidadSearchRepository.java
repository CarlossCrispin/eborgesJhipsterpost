package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Unidad;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Unidad entity.
 */
public interface UnidadSearchRepository extends ElasticsearchRepository<Unidad, Long> {
}
