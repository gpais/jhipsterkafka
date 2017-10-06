package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.VisitorsApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.VisitorQueries;
import com.mycompany.myapp.repository.VisitorQueriesRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VisitorQueriesResource REST controller.
 *
 * @see VisitorQueriesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VisitorsApp.class, SecurityBeanOverrideConfiguration.class})
public class VisitorQueriesResourceIntTest {

    private static final ZonedDateTime DEFAULT_QUERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_QUERY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_QUERY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_QUERY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_SALUTATION = "AAAAAAAAAA";
    private static final String UPDATED_SALUTATION = "BBBBBBBBBB";

    @Autowired
    private VisitorQueriesRepository visitorQueriesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitorQueriesMockMvc;

    private VisitorQueries visitorQueries;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitorQueriesResource visitorQueriesResource = new VisitorQueriesResource(visitorQueriesRepository);
        this.restVisitorQueriesMockMvc = MockMvcBuilders.standaloneSetup(visitorQueriesResource)
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
    public static VisitorQueries createEntity(EntityManager em) {
        VisitorQueries visitorQueries = new VisitorQueries()
            .queryDate(DEFAULT_QUERY_DATE)
            .queryDescription(DEFAULT_QUERY_DESCRIPTION)
            .processDate(DEFAULT_PROCESS_DATE)
            .salutation(DEFAULT_SALUTATION);
        return visitorQueries;
    }

    @Before
    public void initTest() {
        visitorQueries = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisitorQueries() throws Exception {
        int databaseSizeBeforeCreate = visitorQueriesRepository.findAll().size();

        // Create the VisitorQueries
        restVisitorQueriesMockMvc.perform(post("/api/visitor-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorQueries)))
            .andExpect(status().isCreated());

        // Validate the VisitorQueries in the database
        List<VisitorQueries> visitorQueriesList = visitorQueriesRepository.findAll();
        assertThat(visitorQueriesList).hasSize(databaseSizeBeforeCreate + 1);
        VisitorQueries testVisitorQueries = visitorQueriesList.get(visitorQueriesList.size() - 1);
        assertThat(testVisitorQueries.getQueryDate()).isEqualTo(DEFAULT_QUERY_DATE);
        assertThat(testVisitorQueries.getQueryDescription()).isEqualTo(DEFAULT_QUERY_DESCRIPTION);
        assertThat(testVisitorQueries.getProcessDate()).isEqualTo(DEFAULT_PROCESS_DATE);
        assertThat(testVisitorQueries.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
    }

    @Test
    @Transactional
    public void createVisitorQueriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitorQueriesRepository.findAll().size();

        // Create the VisitorQueries with an existing ID
        visitorQueries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitorQueriesMockMvc.perform(post("/api/visitor-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorQueries)))
            .andExpect(status().isBadRequest());

        // Validate the VisitorQueries in the database
        List<VisitorQueries> visitorQueriesList = visitorQueriesRepository.findAll();
        assertThat(visitorQueriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVisitorQueries() throws Exception {
        // Initialize the database
        visitorQueriesRepository.saveAndFlush(visitorQueries);

        // Get all the visitorQueriesList
        restVisitorQueriesMockMvc.perform(get("/api/visitor-queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitorQueries.getId().intValue())))
            .andExpect(jsonPath("$.[*].queryDate").value(hasItem(sameInstant(DEFAULT_QUERY_DATE))))
            .andExpect(jsonPath("$.[*].queryDescription").value(hasItem(DEFAULT_QUERY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].processDate").value(hasItem(DEFAULT_PROCESS_DATE.toString())))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION.toString())));
    }

    @Test
    @Transactional
    public void getVisitorQueries() throws Exception {
        // Initialize the database
        visitorQueriesRepository.saveAndFlush(visitorQueries);

        // Get the visitorQueries
        restVisitorQueriesMockMvc.perform(get("/api/visitor-queries/{id}", visitorQueries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visitorQueries.getId().intValue()))
            .andExpect(jsonPath("$.queryDate").value(sameInstant(DEFAULT_QUERY_DATE)))
            .andExpect(jsonPath("$.queryDescription").value(DEFAULT_QUERY_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.processDate").value(DEFAULT_PROCESS_DATE.toString()))
            .andExpect(jsonPath("$.salutation").value(DEFAULT_SALUTATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVisitorQueries() throws Exception {
        // Get the visitorQueries
        restVisitorQueriesMockMvc.perform(get("/api/visitor-queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisitorQueries() throws Exception {
        // Initialize the database
        visitorQueriesRepository.saveAndFlush(visitorQueries);
        int databaseSizeBeforeUpdate = visitorQueriesRepository.findAll().size();

        // Update the visitorQueries
        VisitorQueries updatedVisitorQueries = visitorQueriesRepository.findOne(visitorQueries.getId());
        updatedVisitorQueries
            .queryDate(UPDATED_QUERY_DATE)
            .queryDescription(UPDATED_QUERY_DESCRIPTION)
            .processDate(UPDATED_PROCESS_DATE)
            .salutation(UPDATED_SALUTATION);

        restVisitorQueriesMockMvc.perform(put("/api/visitor-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitorQueries)))
            .andExpect(status().isOk());

        // Validate the VisitorQueries in the database
        List<VisitorQueries> visitorQueriesList = visitorQueriesRepository.findAll();
        assertThat(visitorQueriesList).hasSize(databaseSizeBeforeUpdate);
        VisitorQueries testVisitorQueries = visitorQueriesList.get(visitorQueriesList.size() - 1);
        assertThat(testVisitorQueries.getQueryDate()).isEqualTo(UPDATED_QUERY_DATE);
        assertThat(testVisitorQueries.getQueryDescription()).isEqualTo(UPDATED_QUERY_DESCRIPTION);
        assertThat(testVisitorQueries.getProcessDate()).isEqualTo(UPDATED_PROCESS_DATE);
        assertThat(testVisitorQueries.getSalutation()).isEqualTo(UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void updateNonExistingVisitorQueries() throws Exception {
        int databaseSizeBeforeUpdate = visitorQueriesRepository.findAll().size();

        // Create the VisitorQueries

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisitorQueriesMockMvc.perform(put("/api/visitor-queries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorQueries)))
            .andExpect(status().isCreated());

        // Validate the VisitorQueries in the database
        List<VisitorQueries> visitorQueriesList = visitorQueriesRepository.findAll();
        assertThat(visitorQueriesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisitorQueries() throws Exception {
        // Initialize the database
        visitorQueriesRepository.saveAndFlush(visitorQueries);
        int databaseSizeBeforeDelete = visitorQueriesRepository.findAll().size();

        // Get the visitorQueries
        restVisitorQueriesMockMvc.perform(delete("/api/visitor-queries/{id}", visitorQueries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VisitorQueries> visitorQueriesList = visitorQueriesRepository.findAll();
        assertThat(visitorQueriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitorQueries.class);
        VisitorQueries visitorQueries1 = new VisitorQueries();
        visitorQueries1.setId(1L);
        VisitorQueries visitorQueries2 = new VisitorQueries();
        visitorQueries2.setId(visitorQueries1.getId());
        assertThat(visitorQueries1).isEqualTo(visitorQueries2);
        visitorQueries2.setId(2L);
        assertThat(visitorQueries1).isNotEqualTo(visitorQueries2);
        visitorQueries1.setId(null);
        assertThat(visitorQueries1).isNotEqualTo(visitorQueries2);
    }
}
