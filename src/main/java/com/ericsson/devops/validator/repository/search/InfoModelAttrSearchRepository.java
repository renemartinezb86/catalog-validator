package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.InfoModelAttr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the InfoModelAttr entity.
 */
public interface InfoModelAttrSearchRepository extends ElasticsearchRepository<InfoModelAttr, String> {
}
