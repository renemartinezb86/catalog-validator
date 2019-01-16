package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.InfoModel;
import com.ericsson.devops.validator.repository.InfoModelRepository;
import com.ericsson.devops.validator.repository.search.InfoModelSearchRepository;
import com.ericsson.devops.validator.service.InfoModelService;
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
 * Test class for the InfoModelResource REST controller.
 *
 * @see InfoModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class InfoModelResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_IS_DYNAM = "AAAAAAAAAA";
    private static final String UPDATED_IS_DYNAM = "BBBBBBBBBB";

    private static final String DEFAULT_DB_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DB_TABLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRATIONAL_INTERVAL = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRATIONAL_INTERVAL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUBTYPE = "BBBBBBBBBB";

    @Autowired
    private InfoModelRepository infoModelRepository;

    @Autowired
    private InfoModelService infoModelService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.InfoModelSearchRepositoryMockConfiguration
     */
    @Autowired
    private InfoModelSearchRepository mockInfoModelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restInfoModelMockMvc;

    private InfoModel infoModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InfoModelResource infoModelResource = new InfoModelResource(infoModelService);
        this.restInfoModelMockMvc = MockMvcBuilders.standaloneSetup(infoModelResource)
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
    public static InfoModel createEntity() {
        InfoModel infoModel = new InfoModel()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .desc(DEFAULT_DESC)
            .isDynam(DEFAULT_IS_DYNAM)
            .dbTableName(DEFAULT_DB_TABLE_NAME)
            .expirationalInterval(DEFAULT_EXPIRATIONAL_INTERVAL)
            .type(DEFAULT_TYPE)
            .subtype(DEFAULT_SUBTYPE);
        return infoModel;
    }

    @Before
    public void initTest() {
        infoModelRepository.deleteAll();
        infoModel = createEntity();
    }

    @Test
    public void createInfoModel() throws Exception {
        int databaseSizeBeforeCreate = infoModelRepository.findAll().size();

        // Create the InfoModel
        restInfoModelMockMvc.perform(post("/api/info-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoModel)))
            .andExpect(status().isCreated());

        // Validate the InfoModel in the database
        List<InfoModel> infoModelList = infoModelRepository.findAll();
        assertThat(infoModelList).hasSize(databaseSizeBeforeCreate + 1);
        InfoModel testInfoModel = infoModelList.get(infoModelList.size() - 1);
        assertThat(testInfoModel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInfoModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInfoModel.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInfoModel.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testInfoModel.getIsDynam()).isEqualTo(DEFAULT_IS_DYNAM);
        assertThat(testInfoModel.getDbTableName()).isEqualTo(DEFAULT_DB_TABLE_NAME);
        assertThat(testInfoModel.getExpirationalInterval()).isEqualTo(DEFAULT_EXPIRATIONAL_INTERVAL);
        assertThat(testInfoModel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInfoModel.getSubtype()).isEqualTo(DEFAULT_SUBTYPE);

        // Validate the InfoModel in Elasticsearch
        verify(mockInfoModelSearchRepository, times(1)).save(testInfoModel);
    }

    @Test
    public void createInfoModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoModelRepository.findAll().size();

        // Create the InfoModel with an existing ID
        infoModel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoModelMockMvc.perform(post("/api/info-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoModel)))
            .andExpect(status().isBadRequest());

        // Validate the InfoModel in the database
        List<InfoModel> infoModelList = infoModelRepository.findAll();
        assertThat(infoModelList).hasSize(databaseSizeBeforeCreate);

        // Validate the InfoModel in Elasticsearch
        verify(mockInfoModelSearchRepository, times(0)).save(infoModel);
    }

    @Test
    public void getAllInfoModels() throws Exception {
        // Initialize the database
        infoModelRepository.save(infoModel);

        // Get all the infoModelList
        restInfoModelMockMvc.perform(get("/api/info-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoModel.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].isDynam").value(hasItem(DEFAULT_IS_DYNAM.toString())))
            .andExpect(jsonPath("$.[*].dbTableName").value(hasItem(DEFAULT_DB_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].expirationalInterval").value(hasItem(DEFAULT_EXPIRATIONAL_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE.toString())));
    }
    
    @Test
    public void getInfoModel() throws Exception {
        // Initialize the database
        infoModelRepository.save(infoModel);

        // Get the infoModel
        restInfoModelMockMvc.perform(get("/api/info-models/{id}", infoModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(infoModel.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.isDynam").value(DEFAULT_IS_DYNAM.toString()))
            .andExpect(jsonPath("$.dbTableName").value(DEFAULT_DB_TABLE_NAME.toString()))
            .andExpect(jsonPath("$.expirationalInterval").value(DEFAULT_EXPIRATIONAL_INTERVAL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.subtype").value(DEFAULT_SUBTYPE.toString()));
    }

    @Test
    public void getNonExistingInfoModel() throws Exception {
        // Get the infoModel
        restInfoModelMockMvc.perform(get("/api/info-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInfoModel() throws Exception {
        // Initialize the database
        infoModelService.save(infoModel);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInfoModelSearchRepository);

        int databaseSizeBeforeUpdate = infoModelRepository.findAll().size();

        // Update the infoModel
        InfoModel updatedInfoModel = infoModelRepository.findById(infoModel.getId()).get();
        updatedInfoModel
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .desc(UPDATED_DESC)
            .isDynam(UPDATED_IS_DYNAM)
            .dbTableName(UPDATED_DB_TABLE_NAME)
            .expirationalInterval(UPDATED_EXPIRATIONAL_INTERVAL)
            .type(UPDATED_TYPE)
            .subtype(UPDATED_SUBTYPE);

        restInfoModelMockMvc.perform(put("/api/info-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfoModel)))
            .andExpect(status().isOk());

        // Validate the InfoModel in the database
        List<InfoModel> infoModelList = infoModelRepository.findAll();
        assertThat(infoModelList).hasSize(databaseSizeBeforeUpdate);
        InfoModel testInfoModel = infoModelList.get(infoModelList.size() - 1);
        assertThat(testInfoModel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInfoModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInfoModel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInfoModel.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testInfoModel.getIsDynam()).isEqualTo(UPDATED_IS_DYNAM);
        assertThat(testInfoModel.getDbTableName()).isEqualTo(UPDATED_DB_TABLE_NAME);
        assertThat(testInfoModel.getExpirationalInterval()).isEqualTo(UPDATED_EXPIRATIONAL_INTERVAL);
        assertThat(testInfoModel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInfoModel.getSubtype()).isEqualTo(UPDATED_SUBTYPE);

        // Validate the InfoModel in Elasticsearch
        verify(mockInfoModelSearchRepository, times(1)).save(testInfoModel);
    }

    @Test
    public void updateNonExistingInfoModel() throws Exception {
        int databaseSizeBeforeUpdate = infoModelRepository.findAll().size();

        // Create the InfoModel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoModelMockMvc.perform(put("/api/info-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoModel)))
            .andExpect(status().isBadRequest());

        // Validate the InfoModel in the database
        List<InfoModel> infoModelList = infoModelRepository.findAll();
        assertThat(infoModelList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InfoModel in Elasticsearch
        verify(mockInfoModelSearchRepository, times(0)).save(infoModel);
    }

    @Test
    public void deleteInfoModel() throws Exception {
        // Initialize the database
        infoModelService.save(infoModel);

        int databaseSizeBeforeDelete = infoModelRepository.findAll().size();

        // Get the infoModel
        restInfoModelMockMvc.perform(delete("/api/info-models/{id}", infoModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InfoModel> infoModelList = infoModelRepository.findAll();
        assertThat(infoModelList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InfoModel in Elasticsearch
        verify(mockInfoModelSearchRepository, times(1)).deleteById(infoModel.getId());
    }

    @Test
    public void searchInfoModel() throws Exception {
        // Initialize the database
        infoModelService.save(infoModel);
        when(mockInfoModelSearchRepository.search(queryStringQuery("id:" + infoModel.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(infoModel), PageRequest.of(0, 1), 1));
        // Search the infoModel
        restInfoModelMockMvc.perform(get("/api/_search/info-models?query=id:" + infoModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoModel.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].isDynam").value(hasItem(DEFAULT_IS_DYNAM)))
            .andExpect(jsonPath("$.[*].dbTableName").value(hasItem(DEFAULT_DB_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].expirationalInterval").value(hasItem(DEFAULT_EXPIRATIONAL_INTERVAL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoModel.class);
        InfoModel infoModel1 = new InfoModel();
        infoModel1.setId("id1");
        InfoModel infoModel2 = new InfoModel();
        infoModel2.setId(infoModel1.getId());
        assertThat(infoModel1).isEqualTo(infoModel2);
        infoModel2.setId("id2");
        assertThat(infoModel1).isNotEqualTo(infoModel2);
        infoModel1.setId(null);
        assertThat(infoModel1).isNotEqualTo(infoModel2);
    }
}
