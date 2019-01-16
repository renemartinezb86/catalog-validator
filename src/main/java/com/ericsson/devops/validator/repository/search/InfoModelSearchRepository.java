package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.InfoModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the InfoModel entity.
 */
public interface InfoModelSearchRepository extends ElasticsearchRepository<InfoModel, String> {
}
