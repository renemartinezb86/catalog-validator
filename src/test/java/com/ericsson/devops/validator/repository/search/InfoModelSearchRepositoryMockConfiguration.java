package com.ericsson.devops.validator.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of InfoModelSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InfoModelSearchRepositoryMockConfiguration {

    @MockBean
    private InfoModelSearchRepository mockInfoModelSearchRepository;

}
