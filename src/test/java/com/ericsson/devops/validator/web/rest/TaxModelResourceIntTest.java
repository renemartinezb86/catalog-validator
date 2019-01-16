package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.TaxModel;
import com.ericsson.devops.validator.repository.TaxModelRepository;
import com.ericsson.devops.validator.repository.search.TaxModelSearchRepository;
import com.ericsson.devops.validator.service.TaxModelService;
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
 * Test class for the TaxModelResource REST controller.
 *
 * @see TaxModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class TaxModelResourceIntTest {

    @Autowired
    private TaxModelRepository taxModelRepository;

    @Autowired
    private TaxModelService taxModelService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.TaxModelSearchRepositoryMockConfiguration
     */
    @Autowired
    private TaxModelSearchRepository mockTaxModelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restTaxModelMockMvc;

    private TaxModel taxModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaxModelResource taxModelResource = new TaxModelResource(taxModelService);
        this.restTaxModelMockMvc = MockMvcBuilders.standaloneSetup(taxModelResource)
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
    public static TaxModel createEntity() {
        TaxModel taxModel = new TaxModel();
        return taxModel;
    }

    @Before
    public void initTest() {
        taxModelRepository.deleteAll();
        taxModel = createEntity();
    }

    @Test
    public void createTaxModel() throws Exception {
        int databaseSizeBeforeCreate = taxModelRepository.findAll().size();

        // Create the TaxModel
        restTaxModelMockMvc.perform(post("/api/tax-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxModel)))
            .andExpect(status().isCreated());

        // Validate the TaxModel in the database
        List<TaxModel> taxModelList = taxModelRepository.findAll();
        assertThat(taxModelList).hasSize(databaseSizeBeforeCreate + 1);
        TaxModel testTaxModel = taxModelList.get(taxModelList.size() - 1);

        // Validate the TaxModel in Elasticsearch
        verify(mockTaxModelSearchRepository, times(1)).save(testTaxModel);
    }

    @Test
    public void createTaxModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxModelRepository.findAll().size();

        // Create the TaxModel with an existing ID
        taxModel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxModelMockMvc.perform(post("/api/tax-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxModel)))
            .andExpect(status().isBadRequest());

        // Validate the TaxModel in the database
        List<TaxModel> taxModelList = taxModelRepository.findAll();
        assertThat(taxModelList).hasSize(databaseSizeBeforeCreate);

        // Validate the TaxModel in Elasticsearch
        verify(mockTaxModelSearchRepository, times(0)).save(taxModel);
    }

    @Test
    public void getAllTaxModels() throws Exception {
        // Initialize the database
        taxModelRepository.save(taxModel);

        // Get all the taxModelList
        restTaxModelMockMvc.perform(get("/api/tax-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxModel.getId())));
    }
    
    @Test
    public void getTaxModel() throws Exception {
        // Initialize the database
        taxModelRepository.save(taxModel);

        // Get the taxModel
        restTaxModelMockMvc.perform(get("/api/tax-models/{id}", taxModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxModel.getId()));
    }

    @Test
    public void getNonExistingTaxModel() throws Exception {
        // Get the taxModel
        restTaxModelMockMvc.perform(get("/api/tax-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTaxModel() throws Exception {
        // Initialize the database
        taxModelService.save(taxModel);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTaxModelSearchRepository);

        int databaseSizeBeforeUpdate = taxModelRepository.findAll().size();

        // Update the taxModel
        TaxModel updatedTaxModel = taxModelRepository.findById(taxModel.getId()).get();

        restTaxModelMockMvc.perform(put("/api/tax-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxModel)))
            .andExpect(status().isOk());

        // Validate the TaxModel in the database
        List<TaxModel> taxModelList = taxModelRepository.findAll();
        assertThat(taxModelList).hasSize(databaseSizeBeforeUpdate);
        TaxModel testTaxModel = taxModelList.get(taxModelList.size() - 1);

        // Validate the TaxModel in Elasticsearch
        verify(mockTaxModelSearchRepository, times(1)).save(testTaxModel);
    }

    @Test
    public void updateNonExistingTaxModel() throws Exception {
        int databaseSizeBeforeUpdate = taxModelRepository.findAll().size();

        // Create the TaxModel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxModelMockMvc.perform(put("/api/tax-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxModel)))
            .andExpect(status().isBadRequest());

        // Validate the TaxModel in the database
        List<TaxModel> taxModelList = taxModelRepository.findAll();
        assertThat(taxModelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TaxModel in Elasticsearch
        verify(mockTaxModelSearchRepository, times(0)).save(taxModel);
    }

    @Test
    public void deleteTaxModel() throws Exception {
        // Initialize the database
        taxModelService.save(taxModel);

        int databaseSizeBeforeDelete = taxModelRepository.findAll().size();

        // Get the taxModel
        restTaxModelMockMvc.perform(delete("/api/tax-models/{id}", taxModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TaxModel> taxModelList = taxModelRepository.findAll();
        assertThat(taxModelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TaxModel in Elasticsearch
        verify(mockTaxModelSearchRepository, times(1)).deleteById(taxModel.getId());
    }

    @Test
    public void searchTaxModel() throws Exception {
        // Initialize the database
        taxModelService.save(taxModel);
        when(mockTaxModelSearchRepository.search(queryStringQuery("id:" + taxModel.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(taxModel), PageRequest.of(0, 1), 1));
        // Search the taxModel
        restTaxModelMockMvc.perform(get("/api/_search/tax-models?query=id:" + taxModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxModel.getId())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxModel.class);
        TaxModel taxModel1 = new TaxModel();
        taxModel1.setId("id1");
        TaxModel taxModel2 = new TaxModel();
        taxModel2.setId(taxModel1.getId());
        assertThat(taxModel1).isEqualTo(taxModel2);
        taxModel2.setId("id2");
        assertThat(taxModel1).isNotEqualTo(taxModel2);
        taxModel1.setId(null);
        assertThat(taxModel1).isNotEqualTo(taxModel2);
    }
}
