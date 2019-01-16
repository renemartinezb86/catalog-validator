package com.ericsson.devops.validator.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ValidationSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ValidationSearchRepositoryMockConfiguration {

    @MockBean
    private ValidationSearchRepository mockValidationSearchRepository;

}
