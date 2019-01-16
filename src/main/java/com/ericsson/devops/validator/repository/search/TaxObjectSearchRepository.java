package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.TaxObject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaxObject entity.
 */
public interface TaxObjectSearchRepository extends ElasticsearchRepository<TaxObject, String> {
}
