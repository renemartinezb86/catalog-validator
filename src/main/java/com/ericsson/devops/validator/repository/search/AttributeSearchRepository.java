package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.Attribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Attribute entity.
 */
public interface AttributeSearchRepository extends ElasticsearchRepository<Attribute, String> {
}
