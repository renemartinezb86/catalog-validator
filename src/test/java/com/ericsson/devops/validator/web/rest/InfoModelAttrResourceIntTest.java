package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.InfoModelAttr;
import com.ericsson.devops.validator.repository.InfoModelAttrRepository;
import com.ericsson.devops.validator.repository.search.InfoModelAttrSearchRepository;
import com.ericsson.devops.validator.service.InfoModelAttrService;
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
 * Test class for the InfoModelAttrResource REST controller.
 *
 * @see InfoModelAttrResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class InfoModelAttrResourceIntTest {

    private static final String DEFAULT_ATTR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ATTR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CHAR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CHAR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IS_NULL = "AAAAAAAAAA";
    private static final String UPDATED_IS_NULL = "BBBBBBBBBB";

    private static final String DEFAULT_IS_SEARCH = "AAAAAAAAAA";
    private static final String UPDATED_IS_SEARCH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCENDING = "AAAAAAAAAA";
    private static final String UPDATED_DESCENDING = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSOCTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SEQ = "AAAAAAAAAA";
    private static final String UPDATED_SEQ = "BBBBBBBBBB";

    @Autowired
    private InfoModelAttrRepository infoModelAttrRepository;

    @Autowired
    private InfoModelAttrService infoModelAttrService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.InfoModelAttrSearchRepositoryMockConfiguration
     */
    @Autowired
    private InfoModelAttrSearchRepository mockInfoModelAttrSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restInfoModelAttrMockMvc;

    private InfoModelAttr infoModelAttr;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InfoModelAttrResource infoModelAttrResource = new InfoModelAttrResource(infoModelAttrService);
        this.restInfoModelAttrMockMvc = MockMvcBuilders.standaloneSetup(infoModelAttrResource)
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
    public static InfoModelAttr createEntity() {
        InfoModelAttr infoModelAttr = new InfoModelAttr()
            .attrCode(DEFAULT_ATTR_CODE)
            .charType(DEFAULT_CHAR_TYPE)
            .name(DEFAULT_NAME)
            .isNull(DEFAULT_IS_NULL)
            .isSearch(DEFAULT_IS_SEARCH)
            .descending(DEFAULT_DESCENDING)
            .type(DEFAULT_TYPE)
            .assoctype(DEFAULT_ASSOCTYPE)
            .seq(DEFAULT_SEQ);
        return infoModelAttr;
    }

    @Before
    public void initTest() {
        infoModelAttrRepository.deleteAll();
        infoModelAttr = createEntity();
    }

    @Test
    public void createInfoModelAttr() throws Exception {
        int databaseSizeBeforeCreate = infoModelAttrRepository.findAll().size();

        // Create the InfoModelAttr
        restInfoModelAttrMockMvc.perform(post("/api/info-model-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoModelAttr)))
            .andExpect(status().isCreated());

        // Validate the InfoModelAttr in the database
        List<InfoModelAttr> infoModelAttrList = infoModelAttrRepository.findAll();
        assertThat(infoModelAttrList).hasSize(databaseSizeBeforeCreate + 1);
        InfoModelAttr testInfoModelAttr = infoModelAttrList.get(infoModelAttrList.size() - 1);
        assertThat(testInfoModelAttr.getAttrCode()).isEqualTo(DEFAULT_ATTR_CODE);
        assertThat(testInfoModelAttr.getCharType()).isEqualTo(DEFAULT_CHAR_TYPE);
        assertThat(testInfoModelAttr.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInfoModelAttr.getIsNull()).isEqualTo(DEFAULT_IS_NULL);
        assertThat(testInfoModelAttr.getIsSearch()).isEqualTo(DEFAULT_IS_SEARCH);
        assertThat(testInfoModelAttr.getDescending()).isEqualTo(DEFAULT_DESCENDING);
        assertThat(testInfoModelAttr.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInfoModelAttr.getAssoctype()).isEqualTo(DEFAULT_ASSOCTYPE);
        assertThat(testInfoModelAttr.getSeq()).isEqualTo(DEFAULT_SEQ);

        // Validate the InfoModelAttr in Elasticsearch
        verify(mockInfoModelAttrSearchRepository, times(1)).save(testInfoModelAttr);
    }

    @Test
    public void createInfoModelAttrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infoModelAttrRepository.findAll().size();

        // Create the InfoModelAttr with an existing ID
        infoModelAttr.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfoModelAttrMockMvc.perform(post("/api/info-model-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoModelAttr)))
            .andExpect(status().isBadRequest());

        // Validate the InfoModelAttr in the database
        List<InfoModelAttr> infoModelAttrList = infoModelAttrRepository.findAll();
        assertThat(infoModelAttrList).hasSize(databaseSizeBeforeCreate);

        // Validate the InfoModelAttr in Elasticsearch
        verify(mockInfoModelAttrSearchRepository, times(0)).save(infoModelAttr);
    }

    @Test
    public void getAllInfoModelAttrs() throws Exception {
        // Initialize the database
        infoModelAttrRepository.save(infoModelAttr);

        // Get all the infoModelAttrList
        restInfoModelAttrMockMvc.perform(get("/api/info-model-attrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoModelAttr.getId())))
            .andExpect(jsonPath("$.[*].attrCode").value(hasItem(DEFAULT_ATTR_CODE.toString())))
            .andExpect(jsonPath("$.[*].charType").value(hasItem(DEFAULT_CHAR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isNull").value(hasItem(DEFAULT_IS_NULL.toString())))
            .andExpect(jsonPath("$.[*].isSearch").value(hasItem(DEFAULT_IS_SEARCH.toString())))
            .andExpect(jsonPath("$.[*].descending").value(hasItem(DEFAULT_DESCENDING.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].assoctype").value(hasItem(DEFAULT_ASSOCTYPE.toString())))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ.toString())));
    }
    
    @Test
    public void getInfoModelAttr() throws Exception {
        // Initialize the database
        infoModelAttrRepository.save(infoModelAttr);

        // Get the infoModelAttr
        restInfoModelAttrMockMvc.perform(get("/api/info-model-attrs/{id}", infoModelAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(infoModelAttr.getId()))
            .andExpect(jsonPath("$.attrCode").value(DEFAULT_ATTR_CODE.toString()))
            .andExpect(jsonPath("$.charType").value(DEFAULT_CHAR_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isNull").value(DEFAULT_IS_NULL.toString()))
            .andExpect(jsonPath("$.isSearch").value(DEFAULT_IS_SEARCH.toString()))
            .andExpect(jsonPath("$.descending").value(DEFAULT_DESCENDING.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.assoctype").value(DEFAULT_ASSOCTYPE.toString()))
            .andExpect(jsonPath("$.seq").value(DEFAULT_SEQ.toString()));
    }

    @Test
    public void getNonExistingInfoModelAttr() throws Exception {
        // Get the infoModelAttr
        restInfoModelAttrMockMvc.perform(get("/api/info-model-attrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInfoModelAttr() throws Exception {
        // Initialize the database
        infoModelAttrService.save(infoModelAttr);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInfoModelAttrSearchRepository);

        int databaseSizeBeforeUpdate = infoModelAttrRepository.findAll().size();

        // Update the infoModelAttr
        InfoModelAttr updatedInfoModelAttr = infoModelAttrRepository.findById(infoModelAttr.getId()).get();
        updatedInfoModelAttr
            .attrCode(UPDATED_ATTR_CODE)
            .charType(UPDATED_CHAR_TYPE)
            .name(UPDATED_NAME)
            .isNull(UPDATED_IS_NULL)
            .isSearch(UPDATED_IS_SEARCH)
            .descending(UPDATED_DESCENDING)
            .type(UPDATED_TYPE)
            .assoctype(UPDATED_ASSOCTYPE)
            .seq(UPDATED_SEQ);

        restInfoModelAttrMockMvc.perform(put("/api/info-model-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfoModelAttr)))
            .andExpect(status().isOk());

        // Validate the InfoModelAttr in the database
        List<InfoModelAttr> infoModelAttrList = infoModelAttrRepository.findAll();
        assertThat(infoModelAttrList).hasSize(databaseSizeBeforeUpdate);
        InfoModelAttr testInfoModelAttr = infoModelAttrList.get(infoModelAttrList.size() - 1);
        assertThat(testInfoModelAttr.getAttrCode()).isEqualTo(UPDATED_ATTR_CODE);
        assertThat(testInfoModelAttr.getCharType()).isEqualTo(UPDATED_CHAR_TYPE);
        assertThat(testInfoModelAttr.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInfoModelAttr.getIsNull()).isEqualTo(UPDATED_IS_NULL);
        assertThat(testInfoModelAttr.getIsSearch()).isEqualTo(UPDATED_IS_SEARCH);
        assertThat(testInfoModelAttr.getDescending()).isEqualTo(UPDATED_DESCENDING);
        assertThat(testInfoModelAttr.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInfoModelAttr.getAssoctype()).isEqualTo(UPDATED_ASSOCTYPE);
        assertThat(testInfoModelAttr.getSeq()).isEqualTo(UPDATED_SEQ);

        // Validate the InfoModelAttr in Elasticsearch
        verify(mockInfoModelAttrSearchRepository, times(1)).save(testInfoModelAttr);
    }

    @Test
    public void updateNonExistingInfoModelAttr() throws Exception {
        int databaseSizeBeforeUpdate = infoModelAttrRepository.findAll().size();

        // Create the InfoModelAttr

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfoModelAttrMockMvc.perform(put("/api/info-model-attrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infoModelAttr)))
            .andExpect(status().isBadRequest());

        // Validate the InfoModelAttr in the database
        List<InfoModelAttr> infoModelAttrList = infoModelAttrRepository.findAll();
        assertThat(infoModelAttrList).hasSize(databaseSizeBeforeUpdate);

        // Validate the InfoModelAttr in Elasticsearch
        verify(mockInfoModelAttrSearchRepository, times(0)).save(infoModelAttr);
    }

    @Test
    public void deleteInfoModelAttr() throws Exception {
        // Initialize the database
        infoModelAttrService.save(infoModelAttr);

        int databaseSizeBeforeDelete = infoModelAttrRepository.findAll().size();

        // Get the infoModelAttr
        restInfoModelAttrMockMvc.perform(delete("/api/info-model-attrs/{id}", infoModelAttr.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InfoModelAttr> infoModelAttrList = infoModelAttrRepository.findAll();
        assertThat(infoModelAttrList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the InfoModelAttr in Elasticsearch
        verify(mockInfoModelAttrSearchRepository, times(1)).deleteById(infoModelAttr.getId());
    }

    @Test
    public void searchInfoModelAttr() throws Exception {
        // Initialize the database
        infoModelAttrService.save(infoModelAttr);
        when(mockInfoModelAttrSearchRepository.search(queryStringQuery("id:" + infoModelAttr.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(infoModelAttr), PageRequest.of(0, 1), 1));
        // Search the infoModelAttr
        restInfoModelAttrMockMvc.perform(get("/api/_search/info-model-attrs?query=id:" + infoModelAttr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infoModelAttr.getId())))
            .andExpect(jsonPath("$.[*].attrCode").value(hasItem(DEFAULT_ATTR_CODE)))
            .andExpect(jsonPath("$.[*].charType").value(hasItem(DEFAULT_CHAR_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isNull").value(hasItem(DEFAULT_IS_NULL)))
            .andExpect(jsonPath("$.[*].isSearch").value(hasItem(DEFAULT_IS_SEARCH)))
            .andExpect(jsonPath("$.[*].descending").value(hasItem(DEFAULT_DESCENDING)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].assoctype").value(hasItem(DEFAULT_ASSOCTYPE)))
            .andExpect(jsonPath("$.[*].seq").value(hasItem(DEFAULT_SEQ)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoModelAttr.class);
        InfoModelAttr infoModelAttr1 = new InfoModelAttr();
        infoModelAttr1.setId("id1");
        InfoModelAttr infoModelAttr2 = new InfoModelAttr();
        infoModelAttr2.setId(infoModelAttr1.getId());
        assertThat(infoModelAttr1).isEqualTo(infoModelAttr2);
        infoModelAttr2.setId("id2");
        assertThat(infoModelAttr1).isNotEqualTo(infoModelAttr2);
        infoModelAttr1.setId(null);
        assertThat(infoModelAttr1).isNotEqualTo(infoModelAttr2);
    }
}
