package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.IND;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IND entity.
 */
public interface INDSearchRepository extends ElasticsearchRepository<IND, String> {
}
