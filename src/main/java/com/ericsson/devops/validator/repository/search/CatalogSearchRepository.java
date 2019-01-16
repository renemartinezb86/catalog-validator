package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.Catalog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Catalog entity.
 */
public interface CatalogSearchRepository extends ElasticsearchRepository<Catalog, String> {
}
