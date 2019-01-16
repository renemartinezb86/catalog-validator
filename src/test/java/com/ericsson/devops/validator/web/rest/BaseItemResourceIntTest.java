package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.BaseItem;
import com.ericsson.devops.validator.repository.BaseItemRepository;
import com.ericsson.devops.validator.repository.search.BaseItemSearchRepository;
import com.ericsson.devops.validator.service.BaseItemService;
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
 * Test class for the BaseItemResource REST controller.
 *
 * @see BaseItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class BaseItemResourceIntTest {

    private static final String DEFAULT_BASENAME = "AAAAAAAAAA";
    private static final String UPDATED_BASENAME = "BBBBBBBBBB";

    private static final String DEFAULT_BASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_SUBTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_SUBTYPE = "BBBBBBBBBB";

    @Autowired
    private BaseItemRepository baseItemRepository;

    @Autowired
    private BaseItemService baseItemService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.BaseItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private BaseItemSearchRepository mockBaseItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBaseItemMockMvc;

    private BaseItem baseItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BaseItemResource baseItemResource = new BaseItemResource(baseItemService);
        this.restBaseItemMockMvc = MockMvcBuilders.standaloneSetup(baseItemResource)
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
    public static BaseItem createEntity() {
        BaseItem baseItem = new BaseItem()
            .basename(DEFAULT_BASENAME)
            .baseType(DEFAULT_BASE_TYPE)
            .itemName(DEFAULT_ITEM_NAME)
            .itemType(DEFAULT_ITEM_TYPE)
            .itemSubtype(DEFAULT_ITEM_SUBTYPE);
        return baseItem;
    }

    @Before
    public void initTest() {
        baseItemRepository.deleteAll();
        baseItem = createEntity();
    }

    @Test
    public void createBaseItem() throws Exception {
        int databaseSizeBeforeCreate = baseItemRepository.findAll().size();

        // Create the BaseItem
        restBaseItemMockMvc.perform(post("/api/base-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseItem)))
            .andExpect(status().isCreated());

        // Validate the BaseItem in the database
        List<BaseItem> baseItemList = baseItemRepository.findAll();
        assertThat(baseItemList).hasSize(databaseSizeBeforeCreate + 1);
        BaseItem testBaseItem = baseItemList.get(baseItemList.size() - 1);
        assertThat(testBaseItem.getBasename()).isEqualTo(DEFAULT_BASENAME);
        assertThat(testBaseItem.getBaseType()).isEqualTo(DEFAULT_BASE_TYPE);
        assertThat(testBaseItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testBaseItem.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testBaseItem.getItemSubtype()).isEqualTo(DEFAULT_ITEM_SUBTYPE);

        // Validate the BaseItem in Elasticsearch
        verify(mockBaseItemSearchRepository, times(1)).save(testBaseItem);
    }

    @Test
    public void createBaseItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseItemRepository.findAll().size();

        // Create the BaseItem with an existing ID
        baseItem.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaseItemMockMvc.perform(post("/api/base-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseItem)))
            .andExpect(status().isBadRequest());

        // Validate the BaseItem in the database
        List<BaseItem> baseItemList = baseItemRepository.findAll();
        assertThat(baseItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the BaseItem in Elasticsearch
        verify(mockBaseItemSearchRepository, times(0)).save(baseItem);
    }

    @Test
    public void getAllBaseItems() throws Exception {
        // Initialize the database
        baseItemRepository.save(baseItem);

        // Get all the baseItemList
        restBaseItemMockMvc.perform(get("/api/base-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baseItem.getId())))
            .andExpect(jsonPath("$.[*].basename").value(hasItem(DEFAULT_BASENAME.toString())))
            .andExpect(jsonPath("$.[*].baseType").value(hasItem(DEFAULT_BASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].itemSubtype").value(hasItem(DEFAULT_ITEM_SUBTYPE.toString())));
    }
    
    @Test
    public void getBaseItem() throws Exception {
        // Initialize the database
        baseItemRepository.save(baseItem);

        // Get the baseItem
        restBaseItemMockMvc.perform(get("/api/base-items/{id}", baseItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(baseItem.getId()))
            .andExpect(jsonPath("$.basename").value(DEFAULT_BASENAME.toString()))
            .andExpect(jsonPath("$.baseType").value(DEFAULT_BASE_TYPE.toString()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.itemSubtype").value(DEFAULT_ITEM_SUBTYPE.toString()));
    }

    @Test
    public void getNonExistingBaseItem() throws Exception {
        // Get the baseItem
        restBaseItemMockMvc.perform(get("/api/base-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBaseItem() throws Exception {
        // Initialize the database
        baseItemService.save(baseItem);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockBaseItemSearchRepository);

        int databaseSizeBeforeUpdate = baseItemRepository.findAll().size();

        // Update the baseItem
        BaseItem updatedBaseItem = baseItemRepository.findById(baseItem.getId()).get();
        updatedBaseItem
            .basename(UPDATED_BASENAME)
            .baseType(UPDATED_BASE_TYPE)
            .itemName(UPDATED_ITEM_NAME)
            .itemType(UPDATED_ITEM_TYPE)
            .itemSubtype(UPDATED_ITEM_SUBTYPE);

        restBaseItemMockMvc.perform(put("/api/base-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBaseItem)))
            .andExpect(status().isOk());

        // Validate the BaseItem in the database
        List<BaseItem> baseItemList = baseItemRepository.findAll();
        assertThat(baseItemList).hasSize(databaseSizeBeforeUpdate);
        BaseItem testBaseItem = baseItemList.get(baseItemList.size() - 1);
        assertThat(testBaseItem.getBasename()).isEqualTo(UPDATED_BASENAME);
        assertThat(testBaseItem.getBaseType()).isEqualTo(UPDATED_BASE_TYPE);
        assertThat(testBaseItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testBaseItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testBaseItem.getItemSubtype()).isEqualTo(UPDATED_ITEM_SUBTYPE);

        // Validate the BaseItem in Elasticsearch
        verify(mockBaseItemSearchRepository, times(1)).save(testBaseItem);
    }

    @Test
    public void updateNonExistingBaseItem() throws Exception {
        int databaseSizeBeforeUpdate = baseItemRepository.findAll().size();

        // Create the BaseItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseItemMockMvc.perform(put("/api/base-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseItem)))
            .andExpect(status().isBadRequest());

        // Validate the BaseItem in the database
        List<BaseItem> baseItemList = baseItemRepository.findAll();
        assertThat(baseItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BaseItem in Elasticsearch
        verify(mockBaseItemSearchRepository, times(0)).save(baseItem);
    }

    @Test
    public void deleteBaseItem() throws Exception {
        // Initialize the database
        baseItemService.save(baseItem);

        int databaseSizeBeforeDelete = baseItemRepository.findAll().size();

        // Get the baseItem
        restBaseItemMockMvc.perform(delete("/api/base-items/{id}", baseItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BaseItem> baseItemList = baseItemRepository.findAll();
        assertThat(baseItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BaseItem in Elasticsearch
        verify(mockBaseItemSearchRepository, times(1)).deleteById(baseItem.getId());
    }

    @Test
    public void searchBaseItem() throws Exception {
        // Initialize the database
        baseItemService.save(baseItem);
        when(mockBaseItemSearchRepository.search(queryStringQuery("id:" + baseItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(baseItem), PageRequest.of(0, 1), 1));
        // Search the baseItem
        restBaseItemMockMvc.perform(get("/api/_search/base-items?query=id:" + baseItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baseItem.getId())))
            .andExpect(jsonPath("$.[*].basename").value(hasItem(DEFAULT_BASENAME)))
            .andExpect(jsonPath("$.[*].baseType").value(hasItem(DEFAULT_BASE_TYPE)))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE)))
            .andExpect(jsonPath("$.[*].itemSubtype").value(hasItem(DEFAULT_ITEM_SUBTYPE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseItem.class);
        BaseItem baseItem1 = new BaseItem();
        baseItem1.setId("id1");
        BaseItem baseItem2 = new BaseItem();
        baseItem2.setId(baseItem1.getId());
        assertThat(baseItem1).isEqualTo(baseItem2);
        baseItem2.setId("id2");
        assertThat(baseItem1).isNotEqualTo(baseItem2);
        baseItem1.setId(null);
        assertThat(baseItem1).isNotEqualTo(baseItem2);
    }
}
