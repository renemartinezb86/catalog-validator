package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.SimpleItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SimpleItem entity.
 */
public interface SimpleItemSearchRepository extends ElasticsearchRepository<SimpleItem, String> {
}
