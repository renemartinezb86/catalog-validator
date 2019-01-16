package com.ericsson.devops.validator.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of INDSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class INDSearchRepositoryMockConfiguration {

    @MockBean
    private INDSearchRepository mockINDSearchRepository;

}
