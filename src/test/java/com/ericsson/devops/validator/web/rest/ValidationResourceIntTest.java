package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.Validation;
import com.ericsson.devops.validator.repository.ValidationRepository;
import com.ericsson.devops.validator.repository.search.ValidationSearchRepository;
import com.ericsson.devops.validator.service.ValidationService;
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
 * Test class for the ValidationResource REST controller.
 *
 * @see ValidationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class ValidationResourceIntTest {

    private static final String DEFAULT_IMPORT_VALIDATION_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMPORT_VALIDATION_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_ECM_VALIDATE_FILE = "AAAAAAAAAA";
    private static final String UPDATED_ECM_VALIDATE_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_TIMESTAMP_MARK = "AAAAAAAAAA";
    private static final String UPDATED_TIMESTAMP_MARK = "BBBBBBBBBB";

    @Autowired
    private ValidationRepository validationRepository;

    @Autowired
    private ValidationService validationService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.ValidationSearchRepositoryMockConfiguration
     */
    @Autowired
    private ValidationSearchRepository mockValidationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restValidationMockMvc;

    private Validation validation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValidationResource validationResource = new ValidationResource(validationService);
        this.restValidationMockMvc = MockMvcBuilders.standaloneSetup(validationResource)
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
    public static Validation createEntity() {
        Validation validation = new Validation()
            .importValidationFile(DEFAULT_IMPORT_VALIDATION_FILE)
            .ecmValidateFile(DEFAULT_ECM_VALIDATE_FILE)
            .timestampMark(DEFAULT_TIMESTAMP_MARK);
        return validation;
    }

    @Before
    public void initTest() {
        validationRepository.deleteAll();
        validation = createEntity();
    }

    @Test
    public void createValidation() throws Exception {
        int databaseSizeBeforeCreate = validationRepository.findAll().size();

        // Create the Validation
        restValidationMockMvc.perform(post("/api/validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validation)))
            .andExpect(status().isCreated());

        // Validate the Validation in the database
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeCreate + 1);
        Validation testValidation = validationList.get(validationList.size() - 1);
        assertThat(testValidation.getImportValidationFile()).isEqualTo(DEFAULT_IMPORT_VALIDATION_FILE);
        assertThat(testValidation.getEcmValidateFile()).isEqualTo(DEFAULT_ECM_VALIDATE_FILE);
        assertThat(testValidation.getTimestampMark()).isEqualTo(DEFAULT_TIMESTAMP_MARK);

        // Validate the Validation in Elasticsearch
        verify(mockValidationSearchRepository, times(1)).save(testValidation);
    }

    @Test
    public void createValidationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = validationRepository.findAll().size();

        // Create the Validation with an existing ID
        validation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidationMockMvc.perform(post("/api/validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validation)))
            .andExpect(status().isBadRequest());

        // Validate the Validation in the database
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Validation in Elasticsearch
        verify(mockValidationSearchRepository, times(0)).save(validation);
    }

    @Test
    public void getAllValidations() throws Exception {
        // Initialize the database
        validationRepository.save(validation);

        // Get all the validationList
        restValidationMockMvc.perform(get("/api/validations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validation.getId())))
            .andExpect(jsonPath("$.[*].importValidationFile").value(hasItem(DEFAULT_IMPORT_VALIDATION_FILE.toString())))
            .andExpect(jsonPath("$.[*].ecmValidateFile").value(hasItem(DEFAULT_ECM_VALIDATE_FILE.toString())))
            .andExpect(jsonPath("$.[*].timestampMark").value(hasItem(DEFAULT_TIMESTAMP_MARK.toString())));
    }
    
    @Test
    public void getValidation() throws Exception {
        // Initialize the database
        validationRepository.save(validation);

        // Get the validation
        restValidationMockMvc.perform(get("/api/validations/{id}", validation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(validation.getId()))
            .andExpect(jsonPath("$.importValidationFile").value(DEFAULT_IMPORT_VALIDATION_FILE.toString()))
            .andExpect(jsonPath("$.ecmValidateFile").value(DEFAULT_ECM_VALIDATE_FILE.toString()))
            .andExpect(jsonPath("$.timestampMark").value(DEFAULT_TIMESTAMP_MARK.toString()));
    }

    @Test
    public void getNonExistingValidation() throws Exception {
        // Get the validation
        restValidationMockMvc.perform(get("/api/validations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateValidation() throws Exception {
        // Initialize the database
        validationService.save(validation);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockValidationSearchRepository);

        int databaseSizeBeforeUpdate = validationRepository.findAll().size();

        // Update the validation
        Validation updatedValidation = validationRepository.findById(validation.getId()).get();
        updatedValidation
            .importValidationFile(UPDATED_IMPORT_VALIDATION_FILE)
            .ecmValidateFile(UPDATED_ECM_VALIDATE_FILE)
            .timestampMark(UPDATED_TIMESTAMP_MARK);

        restValidationMockMvc.perform(put("/api/validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValidation)))
            .andExpect(status().isOk());

        // Validate the Validation in the database
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeUpdate);
        Validation testValidation = validationList.get(validationList.size() - 1);
        assertThat(testValidation.getImportValidationFile()).isEqualTo(UPDATED_IMPORT_VALIDATION_FILE);
        assertThat(testValidation.getEcmValidateFile()).isEqualTo(UPDATED_ECM_VALIDATE_FILE);
        assertThat(testValidation.getTimestampMark()).isEqualTo(UPDATED_TIMESTAMP_MARK);

        // Validate the Validation in Elasticsearch
        verify(mockValidationSearchRepository, times(1)).save(testValidation);
    }

    @Test
    public void updateNonExistingValidation() throws Exception {
        int databaseSizeBeforeUpdate = validationRepository.findAll().size();

        // Create the Validation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidationMockMvc.perform(put("/api/validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(validation)))
            .andExpect(status().isBadRequest());

        // Validate the Validation in the database
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Validation in Elasticsearch
        verify(mockValidationSearchRepository, times(0)).save(validation);
    }

    @Test
    public void deleteValidation() throws Exception {
        // Initialize the database
        validationService.save(validation);

        int databaseSizeBeforeDelete = validationRepository.findAll().size();

        // Get the validation
        restValidationMockMvc.perform(delete("/api/validations/{id}", validation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Validation> validationList = validationRepository.findAll();
        assertThat(validationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Validation in Elasticsearch
        verify(mockValidationSearchRepository, times(1)).deleteById(validation.getId());
    }

    @Test
    public void searchValidation() throws Exception {
        // Initialize the database
        validationService.save(validation);
        when(mockValidationSearchRepository.search(queryStringQuery("id:" + validation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(validation), PageRequest.of(0, 1), 1));
        // Search the validation
        restValidationMockMvc.perform(get("/api/_search/validations?query=id:" + validation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validation.getId())))
            .andExpect(jsonPath("$.[*].importValidationFile").value(hasItem(DEFAULT_IMPORT_VALIDATION_FILE)))
            .andExpect(jsonPath("$.[*].ecmValidateFile").value(hasItem(DEFAULT_ECM_VALIDATE_FILE)))
            .andExpect(jsonPath("$.[*].timestampMark").value(hasItem(DEFAULT_TIMESTAMP_MARK)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Validation.class);
        Validation validation1 = new Validation();
        validation1.setId("id1");
        Validation validation2 = new Validation();
        validation2.setId(validation1.getId());
        assertThat(validation1).isEqualTo(validation2);
        validation2.setId("id2");
        assertThat(validation1).isNotEqualTo(validation2);
        validation1.setId(null);
        assertThat(validation1).isNotEqualTo(validation2);
    }
}
