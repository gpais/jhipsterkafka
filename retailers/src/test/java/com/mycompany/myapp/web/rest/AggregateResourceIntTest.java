package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RetailersApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.Aggregate;
import com.mycompany.myapp.repository.AggregateRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AggregateResource REST controller.
 *
 * @see AggregateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RetailersApp.class, SecurityBeanOverrideConfiguration.class})
public class AggregateResourceIntTest {

    private static final String DEFAULT_AGGREGATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_AGGREGATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    private static final Integer DEFAULT_SEQUENCE_NUMBER = 1;
    private static final Integer UPDATED_SEQUENCE_NUMBER = 2;

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    @Autowired
    private AggregateRepository aggregateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAggregateMockMvc;

    private Aggregate aggregate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AggregateResource aggregateResource = new AggregateResource(aggregateRepository);
        this.restAggregateMockMvc = MockMvcBuilders.standaloneSetup(aggregateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aggregate createEntity(EntityManager em) {
        Aggregate aggregate = new Aggregate()
            .aggregateId(DEFAULT_AGGREGATE_ID)
            .type(DEFAULT_TYPE)
            .version(DEFAULT_VERSION)
            .sequenceNumber(DEFAULT_SEQUENCE_NUMBER)
            .data(DEFAULT_DATA);
        return aggregate;
    }

    @Before
    public void initTest() {
        aggregate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAggregate() throws Exception {
        int databaseSizeBeforeCreate = aggregateRepository.findAll().size();

        // Create the Aggregate
        restAggregateMockMvc.perform(post("/api/aggregates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aggregate)))
            .andExpect(status().isCreated());

        // Validate the Aggregate in the database
        List<Aggregate> aggregateList = aggregateRepository.findAll();
        assertThat(aggregateList).hasSize(databaseSizeBeforeCreate + 1);
        Aggregate testAggregate = aggregateList.get(aggregateList.size() - 1);
        assertThat(testAggregate.getAggregateId()).isEqualTo(DEFAULT_AGGREGATE_ID);
        assertThat(testAggregate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAggregate.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAggregate.getSequenceNumber()).isEqualTo(DEFAULT_SEQUENCE_NUMBER);
        assertThat(testAggregate.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createAggregateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aggregateRepository.findAll().size();

        // Create the Aggregate with an existing ID
        aggregate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAggregateMockMvc.perform(post("/api/aggregates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aggregate)))
            .andExpect(status().isBadRequest());

        // Validate the Aggregate in the database
        List<Aggregate> aggregateList = aggregateRepository.findAll();
        assertThat(aggregateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAggregates() throws Exception {
        // Initialize the database
        aggregateRepository.saveAndFlush(aggregate);

        // Get all the aggregateList
        restAggregateMockMvc.perform(get("/api/aggregates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aggregate.getId().intValue())))
            .andExpect(jsonPath("$.[*].aggregateId").value(hasItem(DEFAULT_AGGREGATE_ID.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].sequenceNumber").value(hasItem(DEFAULT_SEQUENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    @Transactional
    public void getAggregate() throws Exception {
        // Initialize the database
        aggregateRepository.saveAndFlush(aggregate);

        // Get the aggregate
        restAggregateMockMvc.perform(get("/api/aggregates/{id}", aggregate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aggregate.getId().intValue()))
            .andExpect(jsonPath("$.aggregateId").value(DEFAULT_AGGREGATE_ID.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.sequenceNumber").value(DEFAULT_SEQUENCE_NUMBER))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAggregate() throws Exception {
        // Get the aggregate
        restAggregateMockMvc.perform(get("/api/aggregates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAggregate() throws Exception {
        // Initialize the database
        aggregateRepository.saveAndFlush(aggregate);
        int databaseSizeBeforeUpdate = aggregateRepository.findAll().size();

        // Update the aggregate
        Aggregate updatedAggregate = aggregateRepository.findOne(aggregate.getId());
        updatedAggregate
            .aggregateId(UPDATED_AGGREGATE_ID)
            .type(UPDATED_TYPE)
            .version(UPDATED_VERSION)
            .sequenceNumber(UPDATED_SEQUENCE_NUMBER)
            .data(UPDATED_DATA);

        restAggregateMockMvc.perform(put("/api/aggregates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAggregate)))
            .andExpect(status().isOk());

        // Validate the Aggregate in the database
        List<Aggregate> aggregateList = aggregateRepository.findAll();
        assertThat(aggregateList).hasSize(databaseSizeBeforeUpdate);
        Aggregate testAggregate = aggregateList.get(aggregateList.size() - 1);
        assertThat(testAggregate.getAggregateId()).isEqualTo(UPDATED_AGGREGATE_ID);
        assertThat(testAggregate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAggregate.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAggregate.getSequenceNumber()).isEqualTo(UPDATED_SEQUENCE_NUMBER);
        assertThat(testAggregate.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingAggregate() throws Exception {
        int databaseSizeBeforeUpdate = aggregateRepository.findAll().size();

        // Create the Aggregate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAggregateMockMvc.perform(put("/api/aggregates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aggregate)))
            .andExpect(status().isCreated());

        // Validate the Aggregate in the database
        List<Aggregate> aggregateList = aggregateRepository.findAll();
        assertThat(aggregateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAggregate() throws Exception {
        // Initialize the database
        aggregateRepository.saveAndFlush(aggregate);
        int databaseSizeBeforeDelete = aggregateRepository.findAll().size();

        // Get the aggregate
        restAggregateMockMvc.perform(delete("/api/aggregates/{id}", aggregate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Aggregate> aggregateList = aggregateRepository.findAll();
        assertThat(aggregateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aggregate.class);
        Aggregate aggregate1 = new Aggregate();
        aggregate1.setId(1L);
        Aggregate aggregate2 = new Aggregate();
        aggregate2.setId(aggregate1.getId());
        assertThat(aggregate1).isEqualTo(aggregate2);
        aggregate2.setId(2L);
        assertThat(aggregate1).isNotEqualTo(aggregate2);
        aggregate1.setId(null);
        assertThat(aggregate1).isNotEqualTo(aggregate2);
    }
}
