package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.Catalog;
import com.ericsson.devops.validator.repository.CatalogRepository;
import com.ericsson.devops.validator.repository.search.CatalogSearchRepository;
import com.ericsson.devops.validator.service.CatalogService;
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Test class for the CatalogResource REST controller.
 *
 * @see CatalogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class CatalogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogService catalogService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.CatalogSearchRepositoryMockConfiguration
     */
    @Autowired
    private CatalogSearchRepository mockCatalogSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCatalogMockMvc;

    private Catalog catalog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CatalogResource catalogResource = new CatalogResource(catalogService);
        this.restCatalogMockMvc = MockMvcBuilders.standaloneSetup(catalogResource)
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
    public static Catalog createEntity() {
        Catalog catalog = new Catalog()
            .name(DEFAULT_NAME)
            .fileName(DEFAULT_FILE_NAME)
            .createdDate(DEFAULT_CREATED_DATE);
        return catalog;
    }

    @Before
    public void initTest() {
        catalogRepository.deleteAll();
        catalog = createEntity();
    }

    @Test
    public void createCatalog() throws Exception {
        int databaseSizeBeforeCreate = catalogRepository.findAll().size();

        // Create the Catalog
        restCatalogMockMvc.perform(post("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalog)))
            .andExpect(status().isCreated());

        // Validate the Catalog in the database
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeCreate + 1);
        Catalog testCatalog = catalogList.get(catalogList.size() - 1);
        assertThat(testCatalog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCatalog.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testCatalog.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);

        // Validate the Catalog in Elasticsearch
        verify(mockCatalogSearchRepository, times(1)).save(testCatalog);
    }

    @Test
    public void createCatalogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catalogRepository.findAll().size();

        // Create the Catalog with an existing ID
        catalog.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogMockMvc.perform(post("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalog)))
            .andExpect(status().isBadRequest());

        // Validate the Catalog in the database
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeCreate);

        // Validate the Catalog in Elasticsearch
        verify(mockCatalogSearchRepository, times(0)).save(catalog);
    }

    @Test
    public void getAllCatalogs() throws Exception {
        // Initialize the database
        catalogRepository.save(catalog);

        // Get all the catalogList
        restCatalogMockMvc.perform(get("/api/catalogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalog.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    public void getCatalog() throws Exception {
        // Initialize the database
        catalogRepository.save(catalog);

        // Get the catalog
        restCatalogMockMvc.perform(get("/api/catalogs/{id}", catalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(catalog.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    public void getNonExistingCatalog() throws Exception {
        // Get the catalog
        restCatalogMockMvc.perform(get("/api/catalogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCatalog() throws Exception {
        // Initialize the database
        catalogService.save(catalog);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCatalogSearchRepository);

        int databaseSizeBeforeUpdate = catalogRepository.findAll().size();

        // Update the catalog
        Catalog updatedCatalog = catalogRepository.findById(catalog.getId()).get();
        updatedCatalog
            .name(UPDATED_NAME)
            .fileName(UPDATED_FILE_NAME)
            .createdDate(UPDATED_CREATED_DATE);

        restCatalogMockMvc.perform(put("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatalog)))
            .andExpect(status().isOk());

        // Validate the Catalog in the database
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeUpdate);
        Catalog testCatalog = catalogList.get(catalogList.size() - 1);
        assertThat(testCatalog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCatalog.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testCatalog.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);

        // Validate the Catalog in Elasticsearch
        verify(mockCatalogSearchRepository, times(1)).save(testCatalog);
    }

    @Test
    public void updateNonExistingCatalog() throws Exception {
        int databaseSizeBeforeUpdate = catalogRepository.findAll().size();

        // Create the Catalog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogMockMvc.perform(put("/api/catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catalog)))
            .andExpect(status().isBadRequest());

        // Validate the Catalog in the database
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Catalog in Elasticsearch
        verify(mockCatalogSearchRepository, times(0)).save(catalog);
    }

    @Test
    public void deleteCatalog() throws Exception {
        // Initialize the database
        catalogService.save(catalog);

        int databaseSizeBeforeDelete = catalogRepository.findAll().size();

        // Get the catalog
        restCatalogMockMvc.perform(delete("/api/catalogs/{id}", catalog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Catalog> catalogList = catalogRepository.findAll();
        assertThat(catalogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Catalog in Elasticsearch
        verify(mockCatalogSearchRepository, times(1)).deleteById(catalog.getId());
    }

    @Test
    public void searchCatalog() throws Exception {
        // Initialize the database
        catalogService.save(catalog);
        when(mockCatalogSearchRepository.search(queryStringQuery("id:" + catalog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(catalog), PageRequest.of(0, 1), 1));
        // Search the catalog
        restCatalogMockMvc.perform(get("/api/_search/catalogs?query=id:" + catalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalog.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Catalog.class);
        Catalog catalog1 = new Catalog();
        catalog1.setId("id1");
        Catalog catalog2 = new Catalog();
        catalog2.setId(catalog1.getId());
        assertThat(catalog1).isEqualTo(catalog2);
        catalog2.setId("id2");
        assertThat(catalog1).isNotEqualTo(catalog2);
        catalog1.setId(null);
        assertThat(catalog1).isNotEqualTo(catalog2);
    }
}
