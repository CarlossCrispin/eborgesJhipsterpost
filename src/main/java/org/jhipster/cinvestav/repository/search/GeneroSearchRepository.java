package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Genero;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Genero entity.
 */
public interface GeneroSearchRepository extends ElasticsearchRepository<Genero, Long> {
}
