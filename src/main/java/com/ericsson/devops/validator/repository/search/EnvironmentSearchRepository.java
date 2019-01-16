package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.Environment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Environment entity.
 */
public interface EnvironmentSearchRepository extends ElasticsearchRepository<Environment, String> {
}
