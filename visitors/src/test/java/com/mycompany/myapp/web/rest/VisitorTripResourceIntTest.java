package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.VisitorsApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.VisitorTrip;
import com.mycompany.myapp.repository.VisitorTripRepository;
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
 * Test class for the VisitorTripResource REST controller.
 *
 * @see VisitorTripResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VisitorsApp.class, SecurityBeanOverrideConfiguration.class})
public class VisitorTripResourceIntTest {

    private static final String DEFAULT_LOCATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_ENTRY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ENTRY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_EXIT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_EXIT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_FLIGHT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_CARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_CARD_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD_TOKEN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREDIT_CARD_EXPIRY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREDIT_CARD_EXPIRY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CARD_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CARD_HOLDER_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_MATCH_LARGE_TX = false;
    private static final Boolean UPDATED_MATCH_LARGE_TX = true;

    @Autowired
    private VisitorTripRepository visitorTripRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitorTripMockMvc;

    private VisitorTrip visitorTrip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitorTripResource visitorTripResource = new VisitorTripResource(visitorTripRepository);
        this.restVisitorTripMockMvc = MockMvcBuilders.standaloneSetup(visitorTripResource)
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
    public static VisitorTrip createEntity(EntityManager em) {
        VisitorTrip visitorTrip = new VisitorTrip()
            .locationCode(DEFAULT_LOCATION_CODE)
            .dateEntry(DEFAULT_DATE_ENTRY)
            .dateExit(DEFAULT_DATE_EXIT)
            .flightNumber(DEFAULT_FLIGHT_NUMBER)
            .creditCardType(DEFAULT_CREDIT_CARD_TYPE)
            .creditCardToken(DEFAULT_CREDIT_CARD_TOKEN)
            .creditCardExpiry(DEFAULT_CREDIT_CARD_EXPIRY)
            .cardHolderName(DEFAULT_CARD_HOLDER_NAME)
            .createdDate(DEFAULT_CREATED_DATE)
            .matchLargeTX(DEFAULT_MATCH_LARGE_TX);
        return visitorTrip;
    }

    @Before
    public void initTest() {
        visitorTrip = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisitorTrip() throws Exception {
        int databaseSizeBeforeCreate = visitorTripRepository.findAll().size();

        // Create the VisitorTrip
        restVisitorTripMockMvc.perform(post("/api/visitor-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorTrip)))
            .andExpect(status().isCreated());

        // Validate the VisitorTrip in the database
        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeCreate + 1);
        VisitorTrip testVisitorTrip = visitorTripList.get(visitorTripList.size() - 1);
        assertThat(testVisitorTrip.getLocationCode()).isEqualTo(DEFAULT_LOCATION_CODE);
        assertThat(testVisitorTrip.getDateEntry()).isEqualTo(DEFAULT_DATE_ENTRY);
        assertThat(testVisitorTrip.getDateExit()).isEqualTo(DEFAULT_DATE_EXIT);
        assertThat(testVisitorTrip.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testVisitorTrip.getCreditCardType()).isEqualTo(DEFAULT_CREDIT_CARD_TYPE);
        assertThat(testVisitorTrip.getCreditCardToken()).isEqualTo(DEFAULT_CREDIT_CARD_TOKEN);
        assertThat(testVisitorTrip.getCreditCardExpiry()).isEqualTo(DEFAULT_CREDIT_CARD_EXPIRY);
        assertThat(testVisitorTrip.getCardHolderName()).isEqualTo(DEFAULT_CARD_HOLDER_NAME);
        assertThat(testVisitorTrip.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testVisitorTrip.isMatchLargeTX()).isEqualTo(DEFAULT_MATCH_LARGE_TX);
    }

    @Test
    @Transactional
    public void createVisitorTripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitorTripRepository.findAll().size();

        // Create the VisitorTrip with an existing ID
        visitorTrip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitorTripMockMvc.perform(post("/api/visitor-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorTrip)))
            .andExpect(status().isBadRequest());

        // Validate the VisitorTrip in the database
        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFlightNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorTripRepository.findAll().size();
        // set the field null
        visitorTrip.setFlightNumber(null);

        // Create the VisitorTrip, which fails.

        restVisitorTripMockMvc.perform(post("/api/visitor-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorTrip)))
            .andExpect(status().isBadRequest());

        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditCardTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorTripRepository.findAll().size();
        // set the field null
        visitorTrip.setCreditCardType(null);

        // Create the VisitorTrip, which fails.

        restVisitorTripMockMvc.perform(post("/api/visitor-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorTrip)))
            .andExpect(status().isBadRequest());

        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditCardTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorTripRepository.findAll().size();
        // set the field null
        visitorTrip.setCreditCardToken(null);

        // Create the VisitorTrip, which fails.

        restVisitorTripMockMvc.perform(post("/api/visitor-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorTrip)))
            .andExpect(status().isBadRequest());

        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCardHolderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorTripRepository.findAll().size();
        // set the field null
        visitorTrip.setCardHolderName(null);

        // Create the VisitorTrip, which fails.

        restVisitorTripMockMvc.perform(post("/api/visitor-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorTrip)))
            .andExpect(status().isBadRequest());

        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisitorTrips() throws Exception {
        // Initialize the database
        visitorTripRepository.saveAndFlush(visitorTrip);

        // Get all the visitorTripList
        restVisitorTripMockMvc.perform(get("/api/visitor-trips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitorTrip.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationCode").value(hasItem(DEFAULT_LOCATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].dateEntry").value(hasItem(sameInstant(DEFAULT_DATE_ENTRY))))
            .andExpect(jsonPath("$.[*].dateExit").value(hasItem(sameInstant(DEFAULT_DATE_EXIT))))
            .andExpect(jsonPath("$.[*].flightNumber").value(hasItem(DEFAULT_FLIGHT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].creditCardType").value(hasItem(DEFAULT_CREDIT_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creditCardToken").value(hasItem(DEFAULT_CREDIT_CARD_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].creditCardExpiry").value(hasItem(sameInstant(DEFAULT_CREDIT_CARD_EXPIRY))))
            .andExpect(jsonPath("$.[*].cardHolderName").value(hasItem(DEFAULT_CARD_HOLDER_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].matchLargeTX").value(hasItem(DEFAULT_MATCH_LARGE_TX.booleanValue())));
    }

    @Test
    @Transactional
    public void getVisitorTrip() throws Exception {
        // Initialize the database
        visitorTripRepository.saveAndFlush(visitorTrip);

        // Get the visitorTrip
        restVisitorTripMockMvc.perform(get("/api/visitor-trips/{id}", visitorTrip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visitorTrip.getId().intValue()))
            .andExpect(jsonPath("$.locationCode").value(DEFAULT_LOCATION_CODE.toString()))
            .andExpect(jsonPath("$.dateEntry").value(sameInstant(DEFAULT_DATE_ENTRY)))
            .andExpect(jsonPath("$.dateExit").value(sameInstant(DEFAULT_DATE_EXIT)))
            .andExpect(jsonPath("$.flightNumber").value(DEFAULT_FLIGHT_NUMBER.toString()))
            .andExpect(jsonPath("$.creditCardType").value(DEFAULT_CREDIT_CARD_TYPE.toString()))
            .andExpect(jsonPath("$.creditCardToken").value(DEFAULT_CREDIT_CARD_TOKEN.toString()))
            .andExpect(jsonPath("$.creditCardExpiry").value(sameInstant(DEFAULT_CREDIT_CARD_EXPIRY)))
            .andExpect(jsonPath("$.cardHolderName").value(DEFAULT_CARD_HOLDER_NAME.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.matchLargeTX").value(DEFAULT_MATCH_LARGE_TX.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVisitorTrip() throws Exception {
        // Get the visitorTrip
        restVisitorTripMockMvc.perform(get("/api/visitor-trips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisitorTrip() throws Exception {
        // Initialize the database
        visitorTripRepository.saveAndFlush(visitorTrip);
        int databaseSizeBeforeUpdate = visitorTripRepository.findAll().size();

        // Update the visitorTrip
        VisitorTrip updatedVisitorTrip = visitorTripRepository.findOne(visitorTrip.getId());
        updatedVisitorTrip
            .locationCode(UPDATED_LOCATION_CODE)
            .dateEntry(UPDATED_DATE_ENTRY)
            .dateExit(UPDATED_DATE_EXIT)
            .flightNumber(UPDATED_FLIGHT_NUMBER)
            .creditCardType(UPDATED_CREDIT_CARD_TYPE)
            .creditCardToken(UPDATED_CREDIT_CARD_TOKEN)
            .creditCardExpiry(UPDATED_CREDIT_CARD_EXPIRY)
            .cardHolderName(UPDATED_CARD_HOLDER_NAME)
            .createdDate(UPDATED_CREATED_DATE)
            .matchLargeTX(UPDATED_MATCH_LARGE_TX);

        restVisitorTripMockMvc.perform(put("/api/visitor-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitorTrip)))
            .andExpect(status().isOk());

        // Validate the VisitorTrip in the database
        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeUpdate);
        VisitorTrip testVisitorTrip = visitorTripList.get(visitorTripList.size() - 1);
        assertThat(testVisitorTrip.getLocationCode()).isEqualTo(UPDATED_LOCATION_CODE);
        assertThat(testVisitorTrip.getDateEntry()).isEqualTo(UPDATED_DATE_ENTRY);
        assertThat(testVisitorTrip.getDateExit()).isEqualTo(UPDATED_DATE_EXIT);
        assertThat(testVisitorTrip.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testVisitorTrip.getCreditCardType()).isEqualTo(UPDATED_CREDIT_CARD_TYPE);
        assertThat(testVisitorTrip.getCreditCardToken()).isEqualTo(UPDATED_CREDIT_CARD_TOKEN);
        assertThat(testVisitorTrip.getCreditCardExpiry()).isEqualTo(UPDATED_CREDIT_CARD_EXPIRY);
        assertThat(testVisitorTrip.getCardHolderName()).isEqualTo(UPDATED_CARD_HOLDER_NAME);
        assertThat(testVisitorTrip.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testVisitorTrip.isMatchLargeTX()).isEqualTo(UPDATED_MATCH_LARGE_TX);
    }

    @Test
    @Transactional
    public void updateNonExistingVisitorTrip() throws Exception {
        int databaseSizeBeforeUpdate = visitorTripRepository.findAll().size();

        // Create the VisitorTrip

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisitorTripMockMvc.perform(put("/api/visitor-trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorTrip)))
            .andExpect(status().isCreated());

        // Validate the VisitorTrip in the database
        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisitorTrip() throws Exception {
        // Initialize the database
        visitorTripRepository.saveAndFlush(visitorTrip);
        int databaseSizeBeforeDelete = visitorTripRepository.findAll().size();

        // Get the visitorTrip
        restVisitorTripMockMvc.perform(delete("/api/visitor-trips/{id}", visitorTrip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VisitorTrip> visitorTripList = visitorTripRepository.findAll();
        assertThat(visitorTripList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitorTrip.class);
        VisitorTrip visitorTrip1 = new VisitorTrip();
        visitorTrip1.setId(1L);
        VisitorTrip visitorTrip2 = new VisitorTrip();
        visitorTrip2.setId(visitorTrip1.getId());
        assertThat(visitorTrip1).isEqualTo(visitorTrip2);
        visitorTrip2.setId(2L);
        assertThat(visitorTrip1).isNotEqualTo(visitorTrip2);
        visitorTrip1.setId(null);
        assertThat(visitorTrip1).isNotEqualTo(visitorTrip2);
    }
}
