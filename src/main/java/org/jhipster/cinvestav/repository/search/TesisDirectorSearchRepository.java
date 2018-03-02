package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.TesisDirector;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TesisDirector entity.
 */
public interface TesisDirectorSearchRepository extends ElasticsearchRepository<TesisDirector, Long> {
}
