package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.Hierarchy;
import com.ericsson.devops.validator.repository.HierarchyRepository;
import com.ericsson.devops.validator.repository.search.HierarchySearchRepository;
import com.ericsson.devops.validator.service.HierarchyService;
import com.ericsson.devops.validator.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;


import static com.ericsson.devops.validator.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HierarchyResource REST controller.
 *
 * @see HierarchyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class HierarchyResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT = "AAAAAAAAAA";
    private static final String UPDATED_PARENT = "BBBBBBBBBB";

    @Autowired
    private HierarchyRepository hierarchyRepository;

    @Autowired
    private HierarchyService hierarchyService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.HierarchySearchRepositoryMockConfiguration
     */
    @Autowired
    private HierarchySearchRepository mockHierarchySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restHierarchyMockMvc;

    private Hierarchy hierarchy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HierarchyResource hierarchyResource = new HierarchyResource(hierarchyService);
        this.restHierarchyMockMvc = MockMvcBuilders.standaloneSetup(hierarchyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hierarchy createEntity() {
        Hierarchy hierarchy = new Hierarchy()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .parent(DEFAULT_PARENT);
        return hierarchy;
    }

    @Before
    public void initTest() {
        hierarchyRepository.deleteAll();
        hierarchy = createEntity();
    }

    @Test
    public void createHierarchy() throws Exception {
        int databaseSizeBeforeCreate = hierarchyRepository.findAll().size();

        // Create the Hierarchy
        restHierarchyMockMvc.perform(post("/api/hierarchies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hierarchy)))
            .andExpect(status().isCreated());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeCreate + 1);
        Hierarchy testHierarchy = hierarchyList.get(hierarchyList.size() - 1);
        assertThat(testHierarchy.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testHierarchy.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHierarchy.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testHierarchy.getParent()).isEqualTo(DEFAULT_PARENT);

        // Validate the Hierarchy in Elasticsearch
        verify(mockHierarchySearchRepository, times(1)).save(testHierarchy);
    }

    @Test
    public void createHierarchyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hierarchyRepository.findAll().size();

        // Create the Hierarchy with an existing ID
        hierarchy.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restHierarchyMockMvc.perform(post("/api/hierarchies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hierarchy)))
            .andExpect(status().isBadRequest());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Hierarchy in Elasticsearch
        verify(mockHierarchySearchRepository, times(0)).save(hierarchy);
    }

    @Test
    public void getAllHierarchies() throws Exception {
        // Initialize the database
        hierarchyRepository.save(hierarchy);

        // Get all the hierarchyList
        restHierarchyMockMvc.perform(get("/api/hierarchies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hierarchy.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.toString())));
    }
    
    @Test
    public void getHierarchy() throws Exception {
        // Initialize the database
        hierarchyRepository.save(hierarchy);

        // Get the hierarchy
        restHierarchyMockMvc.perform(get("/api/hierarchies/{id}", hierarchy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hierarchy.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT.toString()));
    }

    @Test
    public void getNonExistingHierarchy() throws Exception {
        // Get the hierarchy
        restHierarchyMockMvc.perform(get("/api/hierarchies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateHierarchy() throws Exception {
        // Initialize the database
        hierarchyService.save(hierarchy);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockHierarchySearchRepository);

        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();

        // Update the hierarchy
        Hierarchy updatedHierarchy = hierarchyRepository.findById(hierarchy.getId()).get();
        updatedHierarchy
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .parent(UPDATED_PARENT);

        restHierarchyMockMvc.perform(put("/api/hierarchies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHierarchy)))
            .andExpect(status().isOk());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
        Hierarchy testHierarchy = hierarchyList.get(hierarchyList.size() - 1);
        assertThat(testHierarchy.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testHierarchy.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHierarchy.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testHierarchy.getParent()).isEqualTo(UPDATED_PARENT);

        // Validate the Hierarchy in Elasticsearch
        verify(mockHierarchySearchRepository, times(1)).save(testHierarchy);
    }

    @Test
    public void updateNonExistingHierarchy() throws Exception {
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();

        // Create the Hierarchy

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHierarchyMockMvc.perform(put("/api/hierarchies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hierarchy)))
            .andExpect(status().isBadRequest());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Hierarchy in Elasticsearch
        verify(mockHierarchySearchRepository, times(0)).save(hierarchy);
    }

    @Test
    public void deleteHierarchy() throws Exception {
        // Initialize the database
        hierarchyService.save(hierarchy);

        int databaseSizeBeforeDelete = hierarchyRepository.findAll().size();

        // Get the hierarchy
        restHierarchyMockMvc.perform(delete("/api/hierarchies/{id}", hierarchy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Hierarchy in Elasticsearch
        verify(mockHierarchySearchRepository, times(1)).deleteById(hierarchy.getId());
    }

    @Test
    public void searchHierarchy() throws Exception {
        // Initialize the database
        hierarchyService.save(hierarchy);
        when(mockHierarchySearchRepository.search(queryStringQuery("id:" + hierarchy.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(hierarchy), PageRequest.of(0, 1), 1));
        // Search the hierarchy
        restHierarchyMockMvc.perform(get("/api/_search/hierarchies?query=id:" + hierarchy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hierarchy.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hierarchy.class);
        Hierarchy hierarchy1 = new Hierarchy();
        hierarchy1.setId("id1");
        Hierarchy hierarchy2 = new Hierarchy();
        hierarchy2.setId(hierarchy1.getId());
        assertThat(hierarchy1).isEqualTo(hierarchy2);
        hierarchy2.setId("id2");
        assertThat(hierarchy1).isNotEqualTo(hierarchy2);
        hierarchy1.setId(null);
        assertThat(hierarchy1).isNotEqualTo(hierarchy2);
    }
}
