package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.TaxObject;
import com.ericsson.devops.validator.repository.TaxObjectRepository;
import com.ericsson.devops.validator.repository.search.TaxObjectSearchRepository;
import com.ericsson.devops.validator.service.TaxObjectService;
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
 * Test class for the TaxObjectResource REST controller.
 *
 * @see TaxObjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class TaxObjectResourceIntTest {

    private static final String DEFAULT_TAX_PERCENT = "AAAAAAAAAA";
    private static final String UPDATED_TAX_PERCENT = "BBBBBBBBBB";

    private static final String DEFAULT_INC_IN_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_INC_IN_PRICE = "BBBBBBBBBB";

    @Autowired
    private TaxObjectRepository taxObjectRepository;

    @Autowired
    private TaxObjectService taxObjectService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.TaxObjectSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaxObjectSearchRepository mockTaxObjectSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restTaxObjectMockMvc;

    private TaxObject taxObject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxObjectResource taxObjectResource = new TaxObjectResource(taxObjectService);
        this.restTaxObjectMockMvc = MockMvcBuilders.standaloneSetup(taxObjectResource)
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
    public static TaxObject createEntity() {
        TaxObject taxObject = new TaxObject()
            .taxPercent(DEFAULT_TAX_PERCENT)
            .incInPrice(DEFAULT_INC_IN_PRICE);
        return taxObject;
    }

    @Before
    public void initTest() {
        taxObjectRepository.deleteAll();
        taxObject = createEntity();
    }

    @Test
    public void createTaxObject() throws Exception {
        int databaseSizeBeforeCreate = taxObjectRepository.findAll().size();

        // Create the TaxObject
        restTaxObjectMockMvc.perform(post("/api/tax-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxObject)))
            .andExpect(status().isCreated());

        // Validate the TaxObject in the database
        List<TaxObject> taxObjectList = taxObjectRepository.findAll();
        assertThat(taxObjectList).hasSize(databaseSizeBeforeCreate + 1);
        TaxObject testTaxObject = taxObjectList.get(taxObjectList.size() - 1);
        assertThat(testTaxObject.getTaxPercent()).isEqualTo(DEFAULT_TAX_PERCENT);
        assertThat(testTaxObject.getIncInPrice()).isEqualTo(DEFAULT_INC_IN_PRICE);

        // Validate the TaxObject in Elasticsearch
        verify(mockTaxObjectSearchRepository, times(1)).save(testTaxObject);
    }

    @Test
    public void createTaxObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxObjectRepository.findAll().size();

        // Create the TaxObject with an existing ID
        taxObject.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxObjectMockMvc.perform(post("/api/tax-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxObject)))
            .andExpect(status().isBadRequest());

        // Validate the TaxObject in the database
        List<TaxObject> taxObjectList = taxObjectRepository.findAll();
        assertThat(taxObjectList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaxObject in Elasticsearch
        verify(mockTaxObjectSearchRepository, times(0)).save(taxObject);
    }

    @Test
    public void getAllTaxObjects() throws Exception {
        // Initialize the database
        taxObjectRepository.save(taxObject);

        // Get all the taxObjectList
        restTaxObjectMockMvc.perform(get("/api/tax-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxObject.getId())))
            .andExpect(jsonPath("$.[*].taxPercent").value(hasItem(DEFAULT_TAX_PERCENT.toString())))
            .andExpect(jsonPath("$.[*].incInPrice").value(hasItem(DEFAULT_INC_IN_PRICE.toString())));
    }
    
    @Test
    public void getTaxObject() throws Exception {
        // Initialize the database
        taxObjectRepository.save(taxObject);

        // Get the taxObject
        restTaxObjectMockMvc.perform(get("/api/tax-objects/{id}", taxObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxObject.getId()))
            .andExpect(jsonPath("$.taxPercent").value(DEFAULT_TAX_PERCENT.toString()))
            .andExpect(jsonPath("$.incInPrice").value(DEFAULT_INC_IN_PRICE.toString()));
    }

    @Test
    public void getNonExistingTaxObject() throws Exception {
        // Get the taxObject
        restTaxObjectMockMvc.perform(get("/api/tax-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaxObject() throws Exception {
        // Initialize the database
        taxObjectService.save(taxObject);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTaxObjectSearchRepository);

        int databaseSizeBeforeUpdate = taxObjectRepository.findAll().size();

        // Update the taxObject
        TaxObject updatedTaxObject = taxObjectRepository.findById(taxObject.getId()).get();
        updatedTaxObject
            .taxPercent(UPDATED_TAX_PERCENT)
            .incInPrice(UPDATED_INC_IN_PRICE);

        restTaxObjectMockMvc.perform(put("/api/tax-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxObject)))
            .andExpect(status().isOk());

        // Validate the TaxObject in the database
        List<TaxObject> taxObjectList = taxObjectRepository.findAll();
        assertThat(taxObjectList).hasSize(databaseSizeBeforeUpdate);
        TaxObject testTaxObject = taxObjectList.get(taxObjectList.size() - 1);
        assertThat(testTaxObject.getTaxPercent()).isEqualTo(UPDATED_TAX_PERCENT);
        assertThat(testTaxObject.getIncInPrice()).isEqualTo(UPDATED_INC_IN_PRICE);

        // Validate the TaxObject in Elasticsearch
        verify(mockTaxObjectSearchRepository, times(1)).save(testTaxObject);
    }

    @Test
    public void updateNonExistingTaxObject() throws Exception {
        int databaseSizeBeforeUpdate = taxObjectRepository.findAll().size();

        // Create the TaxObject

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxObjectMockMvc.perform(put("/api/tax-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxObject)))
            .andExpect(status().isBadRequest());

        // Validate the TaxObject in the database
        List<TaxObject> taxObjectList = taxObjectRepository.findAll();
        assertThat(taxObjectList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaxObject in Elasticsearch
        verify(mockTaxObjectSearchRepository, times(0)).save(taxObject);
    }

    @Test
    public void deleteTaxObject() throws Exception {
        // Initialize the database
        taxObjectService.save(taxObject);

        int databaseSizeBeforeDelete = taxObjectRepository.findAll().size();

        // Get the taxObject
        restTaxObjectMockMvc.perform(delete("/api/tax-objects/{id}", taxObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaxObject> taxObjectList = taxObjectRepository.findAll();
        assertThat(taxObjectList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaxObject in Elasticsearch
        verify(mockTaxObjectSearchRepository, times(1)).deleteById(taxObject.getId());
    }

    @Test
    public void searchTaxObject() throws Exception {
        // Initialize the database
        taxObjectService.save(taxObject);
        when(mockTaxObjectSearchRepository.search(queryStringQuery("id:" + taxObject.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taxObject), PageRequest.of(0, 1), 1));
        // Search the taxObject
        restTaxObjectMockMvc.perform(get("/api/_search/tax-objects?query=id:" + taxObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxObject.getId())))
            .andExpect(jsonPath("$.[*].taxPercent").value(hasItem(DEFAULT_TAX_PERCENT)))
            .andExpect(jsonPath("$.[*].incInPrice").value(hasItem(DEFAULT_INC_IN_PRICE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxObject.class);
        TaxObject taxObject1 = new TaxObject();
        taxObject1.setId("id1");
        TaxObject taxObject2 = new TaxObject();
        taxObject2.setId(taxObject1.getId());
        assertThat(taxObject1).isEqualTo(taxObject2);
        taxObject2.setId("id2");
        assertThat(taxObject1).isNotEqualTo(taxObject2);
        taxObject1.setId(null);
        assertThat(taxObject1).isNotEqualTo(taxObject2);
    }
}
