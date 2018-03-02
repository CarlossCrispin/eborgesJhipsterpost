package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.TipoAsesor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TipoAsesor entity.
 */
public interface TipoAsesorSearchRepository extends ElasticsearchRepository<TipoAsesor, Long> {
}
