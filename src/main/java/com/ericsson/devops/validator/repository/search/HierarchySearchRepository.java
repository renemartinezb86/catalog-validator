package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.Hierarchy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Hierarchy entity.
 */
public interface HierarchySearchRepository extends ElasticsearchRepository<Hierarchy, String> {
}
