package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Departamento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Departamento entity.
 */
public interface DepartamentoSearchRepository extends ElasticsearchRepository<Departamento, Long> {
}
