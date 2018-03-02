package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Investigador;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Investigador entity.
 */
public interface InvestigadorSearchRepository extends ElasticsearchRepository<Investigador, Long> {
}
