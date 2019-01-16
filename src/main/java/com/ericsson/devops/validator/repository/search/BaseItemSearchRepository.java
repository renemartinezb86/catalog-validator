package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.BaseItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BaseItem entity.
 */
public interface BaseItemSearchRepository extends ElasticsearchRepository<BaseItem, String> {
}
