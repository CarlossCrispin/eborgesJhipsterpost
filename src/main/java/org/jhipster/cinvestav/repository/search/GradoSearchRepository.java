package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Grado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Grado entity.
 */
public interface GradoSearchRepository extends ElasticsearchRepository<Grado, Long> {
}
