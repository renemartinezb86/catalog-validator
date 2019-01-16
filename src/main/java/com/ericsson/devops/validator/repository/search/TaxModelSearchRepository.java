package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.TaxModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaxModel entity.
 */
public interface TaxModelSearchRepository extends ElasticsearchRepository<TaxModel, String> {
}
