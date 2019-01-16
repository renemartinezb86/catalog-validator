package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.IND;
import com.ericsson.devops.validator.repository.INDRepository;
import com.ericsson.devops.validator.repository.search.INDSearchRepository;
import com.ericsson.devops.validator.service.INDService;
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
 * Test class for the INDResource REST controller.
 *
 * @see INDResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class INDResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WRITE_TO_LLD = false;
    private static final Boolean UPDATED_WRITE_TO_LLD = true;

    private static final Boolean DEFAULT_WRITE_TRANSLATION = false;
    private static final Boolean UPDATED_WRITE_TRANSLATION = true;

    @Autowired
    private INDRepository iNDRepository;

    @Autowired
    private INDService iNDService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.INDSearchRepositoryMockConfiguration
     */
    @Autowired
    private INDSearchRepository mockINDSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restINDMockMvc;

    private IND iND;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final INDResource iNDResource = new INDResource(iNDService);
        this.restINDMockMvc = MockMvcBuilders.standaloneSetup(iNDResource)
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
    public static IND createEntity() {
        IND iND = new IND()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .writeToLLD(DEFAULT_WRITE_TO_LLD)
            .writeTranslation(DEFAULT_WRITE_TRANSLATION);
        return iND;
    }

    @Before
    public void initTest() {
        iNDRepository.deleteAll();
        iND = createEntity();
    }

    @Test
    public void createIND() throws Exception {
        int databaseSizeBeforeCreate = iNDRepository.findAll().size();

        // Create the IND
        restINDMockMvc.perform(post("/api/inds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iND)))
            .andExpect(status().isCreated());

        // Validate the IND in the database
        List<IND> iNDList = iNDRepository.findAll();
        assertThat(iNDList).hasSize(databaseSizeBeforeCreate + 1);
        IND testIND = iNDList.get(iNDList.size() - 1);
        assertThat(testIND.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIND.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testIND.isWriteToLLD()).isEqualTo(DEFAULT_WRITE_TO_LLD);
        assertThat(testIND.isWriteTranslation()).isEqualTo(DEFAULT_WRITE_TRANSLATION);

        // Validate the IND in Elasticsearch
        verify(mockINDSearchRepository, times(1)).save(testIND);
    }

    @Test
    public void createINDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iNDRepository.findAll().size();

        // Create the IND with an existing ID
        iND.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restINDMockMvc.perform(post("/api/inds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iND)))
            .andExpect(status().isBadRequest());

        // Validate the IND in the database
        List<IND> iNDList = iNDRepository.findAll();
        assertThat(iNDList).hasSize(databaseSizeBeforeCreate);

        // Validate the IND in Elasticsearch
        verify(mockINDSearchRepository, times(0)).save(iND);
    }

    @Test
    public void getAllINDS() throws Exception {
        // Initialize the database
        iNDRepository.save(iND);

        // Get all the iNDList
        restINDMockMvc.perform(get("/api/inds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iND.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].writeToLLD").value(hasItem(DEFAULT_WRITE_TO_LLD.booleanValue())))
            .andExpect(jsonPath("$.[*].writeTranslation").value(hasItem(DEFAULT_WRITE_TRANSLATION.booleanValue())));
    }
    
    @Test
    public void getIND() throws Exception {
        // Initialize the database
        iNDRepository.save(iND);

        // Get the iND
        restINDMockMvc.perform(get("/api/inds/{id}", iND.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(iND.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.writeToLLD").value(DEFAULT_WRITE_TO_LLD.booleanValue()))
            .andExpect(jsonPath("$.writeTranslation").value(DEFAULT_WRITE_TRANSLATION.booleanValue()));
    }

    @Test
    public void getNonExistingIND() throws Exception {
        // Get the iND
        restINDMockMvc.perform(get("/api/inds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateIND() throws Exception {
        // Initialize the database
        iNDService.save(iND);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockINDSearchRepository);

        int databaseSizeBeforeUpdate = iNDRepository.findAll().size();

        // Update the iND
        IND updatedIND = iNDRepository.findById(iND.getId()).get();
        updatedIND
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .writeToLLD(UPDATED_WRITE_TO_LLD)
            .writeTranslation(UPDATED_WRITE_TRANSLATION);

        restINDMockMvc.perform(put("/api/inds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIND)))
            .andExpect(status().isOk());

        // Validate the IND in the database
        List<IND> iNDList = iNDRepository.findAll();
        assertThat(iNDList).hasSize(databaseSizeBeforeUpdate);
        IND testIND = iNDList.get(iNDList.size() - 1);
        assertThat(testIND.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIND.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testIND.isWriteToLLD()).isEqualTo(UPDATED_WRITE_TO_LLD);
        assertThat(testIND.isWriteTranslation()).isEqualTo(UPDATED_WRITE_TRANSLATION);

        // Validate the IND in Elasticsearch
        verify(mockINDSearchRepository, times(1)).save(testIND);
    }

    @Test
    public void updateNonExistingIND() throws Exception {
        int databaseSizeBeforeUpdate = iNDRepository.findAll().size();

        // Create the IND

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restINDMockMvc.perform(put("/api/inds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(iND)))
            .andExpect(status().isBadRequest());

        // Validate the IND in the database
        List<IND> iNDList = iNDRepository.findAll();
        assertThat(iNDList).hasSize(databaseSizeBeforeUpdate);

        // Validate the IND in Elasticsearch
        verify(mockINDSearchRepository, times(0)).save(iND);
    }

    @Test
    public void deleteIND() throws Exception {
        // Initialize the database
        iNDService.save(iND);

        int databaseSizeBeforeDelete = iNDRepository.findAll().size();

        // Get the iND
        restINDMockMvc.perform(delete("/api/inds/{id}", iND.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<IND> iNDList = iNDRepository.findAll();
        assertThat(iNDList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the IND in Elasticsearch
        verify(mockINDSearchRepository, times(1)).deleteById(iND.getId());
    }

    @Test
    public void searchIND() throws Exception {
        // Initialize the database
        iNDService.save(iND);
        when(mockINDSearchRepository.search(queryStringQuery("id:" + iND.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(iND), PageRequest.of(0, 1), 1));
        // Search the iND
        restINDMockMvc.perform(get("/api/_search/inds?query=id:" + iND.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iND.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].writeToLLD").value(hasItem(DEFAULT_WRITE_TO_LLD.booleanValue())))
            .andExpect(jsonPath("$.[*].writeTranslation").value(hasItem(DEFAULT_WRITE_TRANSLATION.booleanValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IND.class);
        IND iND1 = new IND();
        iND1.setId("id1");
        IND iND2 = new IND();
        iND2.setId(iND1.getId());
        assertThat(iND1).isEqualTo(iND2);
        iND2.setId("id2");
        assertThat(iND1).isNotEqualTo(iND2);
        iND1.setId(null);
        assertThat(iND1).isNotEqualTo(iND2);
    }
}
