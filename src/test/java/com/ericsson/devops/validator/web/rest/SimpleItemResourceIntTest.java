package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.SimpleItem;
import com.ericsson.devops.validator.repository.SimpleItemRepository;
import com.ericsson.devops.validator.repository.search.SimpleItemSearchRepository;
import com.ericsson.devops.validator.service.SimpleItemService;
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
 * Test class for the SimpleItemResource REST controller.
 *
 * @see SimpleItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class SimpleItemResourceIntTest {

    private static final Integer DEFAULT_START_POS = 1;
    private static final Integer UPDATED_START_POS = 2;

    private static final Integer DEFAULT_END_POS = 1;
    private static final Integer UPDATED_END_POS = 2;

    @Autowired
    private SimpleItemRepository simpleItemRepository;

    @Autowired
    private SimpleItemService simpleItemService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.SimpleItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private SimpleItemSearchRepository mockSimpleItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSimpleItemMockMvc;

    private SimpleItem simpleItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SimpleItemResource simpleItemResource = new SimpleItemResource(simpleItemService);
        this.restSimpleItemMockMvc = MockMvcBuilders.standaloneSetup(simpleItemResource)
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
    public static SimpleItem createEntity() {
        SimpleItem simpleItem = new SimpleItem()
            .startPos(DEFAULT_START_POS)
            .endPos(DEFAULT_END_POS);
        return simpleItem;
    }

    @Before
    public void initTest() {
        simpleItemRepository.deleteAll();
        simpleItem = createEntity();
    }

    @Test
    public void createSimpleItem() throws Exception {
        int databaseSizeBeforeCreate = simpleItemRepository.findAll().size();

        // Create the SimpleItem
        restSimpleItemMockMvc.perform(post("/api/simple-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(simpleItem)))
            .andExpect(status().isCreated());

        // Validate the SimpleItem in the database
        List<SimpleItem> simpleItemList = simpleItemRepository.findAll();
        assertThat(simpleItemList).hasSize(databaseSizeBeforeCreate + 1);
        SimpleItem testSimpleItem = simpleItemList.get(simpleItemList.size() - 1);
        assertThat(testSimpleItem.getStartPos()).isEqualTo(DEFAULT_START_POS);
        assertThat(testSimpleItem.getEndPos()).isEqualTo(DEFAULT_END_POS);

        // Validate the SimpleItem in Elasticsearch
        verify(mockSimpleItemSearchRepository, times(1)).save(testSimpleItem);
    }

    @Test
    public void createSimpleItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = simpleItemRepository.findAll().size();

        // Create the SimpleItem with an existing ID
        simpleItem.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSimpleItemMockMvc.perform(post("/api/simple-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(simpleItem)))
            .andExpect(status().isBadRequest());

        // Validate the SimpleItem in the database
        List<SimpleItem> simpleItemList = simpleItemRepository.findAll();
        assertThat(simpleItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the SimpleItem in Elasticsearch
        verify(mockSimpleItemSearchRepository, times(0)).save(simpleItem);
    }

    @Test
    public void getAllSimpleItems() throws Exception {
        // Initialize the database
        simpleItemRepository.save(simpleItem);

        // Get all the simpleItemList
        restSimpleItemMockMvc.perform(get("/api/simple-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(simpleItem.getId())))
            .andExpect(jsonPath("$.[*].startPos").value(hasItem(DEFAULT_START_POS)))
            .andExpect(jsonPath("$.[*].endPos").value(hasItem(DEFAULT_END_POS)));
    }
    
    @Test
    public void getSimpleItem() throws Exception {
        // Initialize the database
        simpleItemRepository.save(simpleItem);

        // Get the simpleItem
        restSimpleItemMockMvc.perform(get("/api/simple-items/{id}", simpleItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(simpleItem.getId()))
            .andExpect(jsonPath("$.startPos").value(DEFAULT_START_POS))
            .andExpect(jsonPath("$.endPos").value(DEFAULT_END_POS));
    }

    @Test
    public void getNonExistingSimpleItem() throws Exception {
        // Get the simpleItem
        restSimpleItemMockMvc.perform(get("/api/simple-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSimpleItem() throws Exception {
        // Initialize the database
        simpleItemService.save(simpleItem);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockSimpleItemSearchRepository);

        int databaseSizeBeforeUpdate = simpleItemRepository.findAll().size();

        // Update the simpleItem
        SimpleItem updatedSimpleItem = simpleItemRepository.findById(simpleItem.getId()).get();
        updatedSimpleItem
            .startPos(UPDATED_START_POS)
            .endPos(UPDATED_END_POS);

        restSimpleItemMockMvc.perform(put("/api/simple-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSimpleItem)))
            .andExpect(status().isOk());

        // Validate the SimpleItem in the database
        List<SimpleItem> simpleItemList = simpleItemRepository.findAll();
        assertThat(simpleItemList).hasSize(databaseSizeBeforeUpdate);
        SimpleItem testSimpleItem = simpleItemList.get(simpleItemList.size() - 1);
        assertThat(testSimpleItem.getStartPos()).isEqualTo(UPDATED_START_POS);
        assertThat(testSimpleItem.getEndPos()).isEqualTo(UPDATED_END_POS);

        // Validate the SimpleItem in Elasticsearch
        verify(mockSimpleItemSearchRepository, times(1)).save(testSimpleItem);
    }

    @Test
    public void updateNonExistingSimpleItem() throws Exception {
        int databaseSizeBeforeUpdate = simpleItemRepository.findAll().size();

        // Create the SimpleItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSimpleItemMockMvc.perform(put("/api/simple-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(simpleItem)))
            .andExpect(status().isBadRequest());

        // Validate the SimpleItem in the database
        List<SimpleItem> simpleItemList = simpleItemRepository.findAll();
        assertThat(simpleItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SimpleItem in Elasticsearch
        verify(mockSimpleItemSearchRepository, times(0)).save(simpleItem);
    }

    @Test
    public void deleteSimpleItem() throws Exception {
        // Initialize the database
        simpleItemService.save(simpleItem);

        int databaseSizeBeforeDelete = simpleItemRepository.findAll().size();

        // Get the simpleItem
        restSimpleItemMockMvc.perform(delete("/api/simple-items/{id}", simpleItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SimpleItem> simpleItemList = simpleItemRepository.findAll();
        assertThat(simpleItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SimpleItem in Elasticsearch
        verify(mockSimpleItemSearchRepository, times(1)).deleteById(simpleItem.getId());
    }

    @Test
    public void searchSimpleItem() throws Exception {
        // Initialize the database
        simpleItemService.save(simpleItem);
        when(mockSimpleItemSearchRepository.search(queryStringQuery("id:" + simpleItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(simpleItem), PageRequest.of(0, 1), 1));
        // Search the simpleItem
        restSimpleItemMockMvc.perform(get("/api/_search/simple-items?query=id:" + simpleItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(simpleItem.getId())))
            .andExpect(jsonPath("$.[*].startPos").value(hasItem(DEFAULT_START_POS)))
            .andExpect(jsonPath("$.[*].endPos").value(hasItem(DEFAULT_END_POS)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SimpleItem.class);
        SimpleItem simpleItem1 = new SimpleItem();
        simpleItem1.setId("id1");
        SimpleItem simpleItem2 = new SimpleItem();
        simpleItem2.setId(simpleItem1.getId());
        assertThat(simpleItem1).isEqualTo(simpleItem2);
        simpleItem2.setId("id2");
        assertThat(simpleItem1).isNotEqualTo(simpleItem2);
        simpleItem1.setId(null);
        assertThat(simpleItem1).isNotEqualTo(simpleItem2);
    }
}
