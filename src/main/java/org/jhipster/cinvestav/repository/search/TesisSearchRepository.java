package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Tesis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tesis entity.
 */
public interface TesisSearchRepository extends ElasticsearchRepository<Tesis, Long> {
}
