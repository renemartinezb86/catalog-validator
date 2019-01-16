package com.ericsson.devops.validator.repository.search;

import com.ericsson.devops.validator.domain.Validation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Validation entity.
 */
public interface ValidationSearchRepository extends ElasticsearchRepository<Validation, String> {
}
