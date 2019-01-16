package com.ericsson.devops.validator.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CatalogSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CatalogSearchRepositoryMockConfiguration {

    @MockBean
    private CatalogSearchRepository mockCatalogSearchRepository;

}
