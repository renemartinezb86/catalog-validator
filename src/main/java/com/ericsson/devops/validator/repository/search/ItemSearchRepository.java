package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Item entity.
 */
public interface ItemSearchRepository extends ElasticsearchRepository<Item, String> {
}
