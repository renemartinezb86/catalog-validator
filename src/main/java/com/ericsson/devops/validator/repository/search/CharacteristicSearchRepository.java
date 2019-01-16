package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.Characteristic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Characteristic entity.
 */
public interface CharacteristicSearchRepository extends ElasticsearchRepository<Characteristic, String> {
}
