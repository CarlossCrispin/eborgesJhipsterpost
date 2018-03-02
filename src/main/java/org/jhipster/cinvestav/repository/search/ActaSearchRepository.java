package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Acta;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Acta entity.
 */
public interface ActaSearchRepository extends ElasticsearchRepository<Acta, Long> {
}
