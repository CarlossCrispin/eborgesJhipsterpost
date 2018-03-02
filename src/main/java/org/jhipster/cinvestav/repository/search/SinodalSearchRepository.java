package org.jhipster.cinvestav.repository.search;

import org.jhipster.cinvestav.domain.Sinodal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sinodal entity.
 */
public interface SinodalSearchRepository extends ElasticsearchRepository<Sinodal, Long> {
}
