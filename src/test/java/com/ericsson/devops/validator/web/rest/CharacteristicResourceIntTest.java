package com.ericsson.devops.validator.web.rest;

import com.ericsson.devops.validator.CatalogValidatorApp;

import com.ericsson.devops.validator.domain.Characteristic;
import com.ericsson.devops.validator.repository.CharacteristicRepository;
import com.ericsson.devops.validator.repository.search.CharacteristicSearchRepository;
import com.ericsson.devops.validator.service.CharacteristicService;
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
 * Test class for the CharacteristicResource REST controller.
 *
 * @see CharacteristicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogValidatorApp.class)
public class CharacteristicResourceIntTest {

    private static final String DEFAULT_CHAR_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHAR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CHAR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHAR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHAR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CHAR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CHAR_CLASSIFICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CHAR_CLASSIFICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SEQUENCE = "AAAAAAAAAA";
    private static final String UPDATED_SEQUENCE = "BBBBBBBBBB";

    private static final String DEFAULT_ASSOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSOC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PROMOTED = "AAAAAAAAAA";
    private static final String UPDATED_PROMOTED = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_CHAR_DEFAULT_RULE = "AAAAAAAAAA";
    private static final String UPDATED_CHAR_DEFAULT_RULE = "BBBBBBBBBB";

    private static final String DEFAULT_MAPTO = "AAAAAAAAAA";
    private static final String UPDATED_MAPTO = "BBBBBBBBBB";

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private CharacteristicService characteristicService;

    /**
     * This repository is mocked in the com.ericsson.devops.validator.repository.search test package.
     *
     * @see com.ericsson.devops.validator.repository.search.CharacteristicSearchRepositoryMockConfiguration
     */
    @Autowired
    private CharacteristicSearchRepository mockCharacteristicSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCharacteristicMockMvc;

    private Characteristic characteristic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CharacteristicResource characteristicResource = new CharacteristicResource(characteristicService);
        this.restCharacteristicMockMvc = MockMvcBuilders.standaloneSetup(characteristicResource)
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
    public static Characteristic createEntity() {
        Characteristic characteristic = new Characteristic()
            .charID(DEFAULT_CHAR_ID)
            .charName(DEFAULT_CHAR_NAME)
            .charType(DEFAULT_CHAR_TYPE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .charClassificationType(DEFAULT_CHAR_CLASSIFICATION_TYPE)
            .sequence(DEFAULT_SEQUENCE)
            .assocType(DEFAULT_ASSOC_TYPE)
            .promoted(DEFAULT_PROMOTED)
            .sourceItem(DEFAULT_SOURCE_ITEM)
            .charDefaultRule(DEFAULT_CHAR_DEFAULT_RULE)
            .mapto(DEFAULT_MAPTO);
        return characteristic;
    }

    @Before
    public void initTest() {
        characteristicRepository.deleteAll();
        characteristic = createEntity();
    }

    @Test
    public void createCharacteristic() throws Exception {
        int databaseSizeBeforeCreate = characteristicRepository.findAll().size();

        // Create the Characteristic
        restCharacteristicMockMvc.perform(post("/api/characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isCreated());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeCreate + 1);
        Characteristic testCharacteristic = characteristicList.get(characteristicList.size() - 1);
        assertThat(testCharacteristic.getCharID()).isEqualTo(DEFAULT_CHAR_ID);
        assertThat(testCharacteristic.getCharName()).isEqualTo(DEFAULT_CHAR_NAME);
        assertThat(testCharacteristic.getCharType()).isEqualTo(DEFAULT_CHAR_TYPE);
        assertThat(testCharacteristic.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testCharacteristic.getCharClassificationType()).isEqualTo(DEFAULT_CHAR_CLASSIFICATION_TYPE);
        assertThat(testCharacteristic.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testCharacteristic.getAssocType()).isEqualTo(DEFAULT_ASSOC_TYPE);
        assertThat(testCharacteristic.getPromoted()).isEqualTo(DEFAULT_PROMOTED);
        assertThat(testCharacteristic.getSourceItem()).isEqualTo(DEFAULT_SOURCE_ITEM);
        assertThat(testCharacteristic.getCharDefaultRule()).isEqualTo(DEFAULT_CHAR_DEFAULT_RULE);
        assertThat(testCharacteristic.getMapto()).isEqualTo(DEFAULT_MAPTO);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(1)).save(testCharacteristic);
    }

    @Test
    public void createCharacteristicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = characteristicRepository.findAll().size();

        // Create the Characteristic with an existing ID
        characteristic.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacteristicMockMvc.perform(post("/api/characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isBadRequest());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeCreate);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(0)).save(characteristic);
    }

    @Test
    public void getAllCharacteristics() throws Exception {
        // Initialize the database
        characteristicRepository.save(characteristic);

        // Get all the characteristicList
        restCharacteristicMockMvc.perform(get("/api/characteristics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characteristic.getId())))
            .andExpect(jsonPath("$.[*].charID").value(hasItem(DEFAULT_CHAR_ID.toString())))
            .andExpect(jsonPath("$.[*].charName").value(hasItem(DEFAULT_CHAR_NAME.toString())))
            .andExpect(jsonPath("$.[*].charType").value(hasItem(DEFAULT_CHAR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].charClassificationType").value(hasItem(DEFAULT_CHAR_CLASSIFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE.toString())))
            .andExpect(jsonPath("$.[*].assocType").value(hasItem(DEFAULT_ASSOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].promoted").value(hasItem(DEFAULT_PROMOTED.toString())))
            .andExpect(jsonPath("$.[*].sourceItem").value(hasItem(DEFAULT_SOURCE_ITEM.toString())))
            .andExpect(jsonPath("$.[*].charDefaultRule").value(hasItem(DEFAULT_CHAR_DEFAULT_RULE.toString())))
            .andExpect(jsonPath("$.[*].mapto").value(hasItem(DEFAULT_MAPTO.toString())));
    }
    
    @Test
    public void getCharacteristic() throws Exception {
        // Initialize the database
        characteristicRepository.save(characteristic);

        // Get the characteristic
        restCharacteristicMockMvc.perform(get("/api/characteristics/{id}", characteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(characteristic.getId()))
            .andExpect(jsonPath("$.charID").value(DEFAULT_CHAR_ID.toString()))
            .andExpect(jsonPath("$.charName").value(DEFAULT_CHAR_NAME.toString()))
            .andExpect(jsonPath("$.charType").value(DEFAULT_CHAR_TYPE.toString()))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.charClassificationType").value(DEFAULT_CHAR_CLASSIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE.toString()))
            .andExpect(jsonPath("$.assocType").value(DEFAULT_ASSOC_TYPE.toString()))
            .andExpect(jsonPath("$.promoted").value(DEFAULT_PROMOTED.toString()))
            .andExpect(jsonPath("$.sourceItem").value(DEFAULT_SOURCE_ITEM.toString()))
            .andExpect(jsonPath("$.charDefaultRule").value(DEFAULT_CHAR_DEFAULT_RULE.toString()))
            .andExpect(jsonPath("$.mapto").value(DEFAULT_MAPTO.toString()));
    }

    @Test
    public void getNonExistingCharacteristic() throws Exception {
        // Get the characteristic
        restCharacteristicMockMvc.perform(get("/api/characteristics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCharacteristic() throws Exception {
        // Initialize the database
        characteristicService.save(characteristic);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCharacteristicSearchRepository);

        int databaseSizeBeforeUpdate = characteristicRepository.findAll().size();

        // Update the characteristic
        Characteristic updatedCharacteristic = characteristicRepository.findById(characteristic.getId()).get();
        updatedCharacteristic
            .charID(UPDATED_CHAR_ID)
            .charName(UPDATED_CHAR_NAME)
            .charType(UPDATED_CHAR_TYPE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .charClassificationType(UPDATED_CHAR_CLASSIFICATION_TYPE)
            .sequence(UPDATED_SEQUENCE)
            .assocType(UPDATED_ASSOC_TYPE)
            .promoted(UPDATED_PROMOTED)
            .sourceItem(UPDATED_SOURCE_ITEM)
            .charDefaultRule(UPDATED_CHAR_DEFAULT_RULE)
            .mapto(UPDATED_MAPTO);

        restCharacteristicMockMvc.perform(put("/api/characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCharacteristic)))
            .andExpect(status().isOk());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeUpdate);
        Characteristic testCharacteristic = characteristicList.get(characteristicList.size() - 1);
        assertThat(testCharacteristic.getCharID()).isEqualTo(UPDATED_CHAR_ID);
        assertThat(testCharacteristic.getCharName()).isEqualTo(UPDATED_CHAR_NAME);
        assertThat(testCharacteristic.getCharType()).isEqualTo(UPDATED_CHAR_TYPE);
        assertThat(testCharacteristic.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testCharacteristic.getCharClassificationType()).isEqualTo(UPDATED_CHAR_CLASSIFICATION_TYPE);
        assertThat(testCharacteristic.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testCharacteristic.getAssocType()).isEqualTo(UPDATED_ASSOC_TYPE);
        assertThat(testCharacteristic.getPromoted()).isEqualTo(UPDATED_PROMOTED);
        assertThat(testCharacteristic.getSourceItem()).isEqualTo(UPDATED_SOURCE_ITEM);
        assertThat(testCharacteristic.getCharDefaultRule()).isEqualTo(UPDATED_CHAR_DEFAULT_RULE);
        assertThat(testCharacteristic.getMapto()).isEqualTo(UPDATED_MAPTO);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(1)).save(testCharacteristic);
    }

    @Test
    public void updateNonExistingCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = characteristicRepository.findAll().size();

        // Create the Characteristic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacteristicMockMvc.perform(put("/api/characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isBadRequest());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(0)).save(characteristic);
    }

    @Test
    public void deleteCharacteristic() throws Exception {
        // Initialize the database
        characteristicService.save(characteristic);

        int databaseSizeBeforeDelete = characteristicRepository.findAll().size();

        // Get the characteristic
        restCharacteristicMockMvc.perform(delete("/api/characteristics/{id}", characteristic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(1)).deleteById(characteristic.getId());
    }

    @Test
    public void searchCharacteristic() throws Exception {
        // Initialize the database
        characteristicService.save(characteristic);
        when(mockCharacteristicSearchRepository.search(queryStringQuery("id:" + characteristic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(characteristic), PageRequest.of(0, 1), 1));
        // Search the characteristic
        restCharacteristicMockMvc.perform(get("/api/_search/characteristics?query=id:" + characteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characteristic.getId())))
            .andExpect(jsonPath("$.[*].charID").value(hasItem(DEFAULT_CHAR_ID)))
            .andExpect(jsonPath("$.[*].charName").value(hasItem(DEFAULT_CHAR_NAME)))
            .andExpect(jsonPath("$.[*].charType").value(hasItem(DEFAULT_CHAR_TYPE)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].charClassificationType").value(hasItem(DEFAULT_CHAR_CLASSIFICATION_TYPE)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].assocType").value(hasItem(DEFAULT_ASSOC_TYPE)))
            .andExpect(jsonPath("$.[*].promoted").value(hasItem(DEFAULT_PROMOTED)))
            .andExpect(jsonPath("$.[*].sourceItem").value(hasItem(DEFAULT_SOURCE_ITEM)))
            .andExpect(jsonPath("$.[*].charDefaultRule").value(hasItem(DEFAULT_CHAR_DEFAULT_RULE)))
            .andExpect(jsonPath("$.[*].mapto").value(hasItem(DEFAULT_MAPTO)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Characteristic.class);
        Characteristic characteristic1 = new Characteristic();
        characteristic1.setId("id1");
        Characteristic characteristic2 = new Characteristic();
        characteristic2.setId(characteristic1.getId());
        assertThat(characteristic1).isEqualTo(characteristic2);
        characteristic2.setId("id2");
        assertThat(characteristic1).isNotEqualTo(characteristic2);
        characteristic1.setId(null);
        assertThat(characteristic1).isNotEqualTo(characteristic2);
    }
}
