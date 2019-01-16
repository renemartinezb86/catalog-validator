package com.ericsson.devops.validator.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of HierarchySearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class HierarchySearchRepositoryMockConfiguration {

    @MockBean
    private HierarchySearchRepository mockHierarchySearchRepository;

}
