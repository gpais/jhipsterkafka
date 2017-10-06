package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.VisitorsApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.VisitorCard;
import com.mycompany.myapp.repository.VisitorCardRepository;
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
 * Test class for the VisitorCardResource REST controller.
 *
 * @see VisitorCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VisitorsApp.class, SecurityBeanOverrideConfiguration.class})
public class VisitorCardResourceIntTest {

    private static final String DEFAULT_TFSC_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TFSC_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTERED_DATE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTERED_DATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TERMS_AND_CONDITIONS = false;
    private static final Boolean UPDATED_TERMS_AND_CONDITIONS = true;

    @Autowired
    private VisitorCardRepository visitorCardRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitorCardMockMvc;

    private VisitorCard visitorCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitorCardResource visitorCardResource = new VisitorCardResource(visitorCardRepository);
        this.restVisitorCardMockMvc = MockMvcBuilders.standaloneSetup(visitorCardResource)
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
    public static VisitorCard createEntity(EntityManager em) {
        VisitorCard visitorCard = new VisitorCard()
            .tfscNumber(DEFAULT_TFSC_NUMBER)
            .registeredDate(DEFAULT_REGISTERED_DATE)
            .termsAndConditions(DEFAULT_TERMS_AND_CONDITIONS);
        return visitorCard;
    }

    @Before
    public void initTest() {
        visitorCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisitorCard() throws Exception {
        int databaseSizeBeforeCreate = visitorCardRepository.findAll().size();

        // Create the VisitorCard
        restVisitorCardMockMvc.perform(post("/api/visitor-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorCard)))
            .andExpect(status().isCreated());

        // Validate the VisitorCard in the database
        List<VisitorCard> visitorCardList = visitorCardRepository.findAll();
        assertThat(visitorCardList).hasSize(databaseSizeBeforeCreate + 1);
        VisitorCard testVisitorCard = visitorCardList.get(visitorCardList.size() - 1);
        assertThat(testVisitorCard.getTfscNumber()).isEqualTo(DEFAULT_TFSC_NUMBER);
        assertThat(testVisitorCard.getRegisteredDate()).isEqualTo(DEFAULT_REGISTERED_DATE);
        assertThat(testVisitorCard.isTermsAndConditions()).isEqualTo(DEFAULT_TERMS_AND_CONDITIONS);
    }

    @Test
    @Transactional
    public void createVisitorCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitorCardRepository.findAll().size();

        // Create the VisitorCard with an existing ID
        visitorCard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitorCardMockMvc.perform(post("/api/visitor-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorCard)))
            .andExpect(status().isBadRequest());

        // Validate the VisitorCard in the database
        List<VisitorCard> visitorCardList = visitorCardRepository.findAll();
        assertThat(visitorCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTfscNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorCardRepository.findAll().size();
        // set the field null
        visitorCard.setTfscNumber(null);

        // Create the VisitorCard, which fails.

        restVisitorCardMockMvc.perform(post("/api/visitor-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorCard)))
            .andExpect(status().isBadRequest());

        List<VisitorCard> visitorCardList = visitorCardRepository.findAll();
        assertThat(visitorCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTermsAndConditionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorCardRepository.findAll().size();
        // set the field null
        visitorCard.setTermsAndConditions(null);

        // Create the VisitorCard, which fails.

        restVisitorCardMockMvc.perform(post("/api/visitor-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorCard)))
            .andExpect(status().isBadRequest());

        List<VisitorCard> visitorCardList = visitorCardRepository.findAll();
        assertThat(visitorCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisitorCards() throws Exception {
        // Initialize the database
        visitorCardRepository.saveAndFlush(visitorCard);

        // Get all the visitorCardList
        restVisitorCardMockMvc.perform(get("/api/visitor-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitorCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].tfscNumber").value(hasItem(DEFAULT_TFSC_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].registeredDate").value(hasItem(DEFAULT_REGISTERED_DATE.toString())))
            .andExpect(jsonPath("$.[*].termsAndConditions").value(hasItem(DEFAULT_TERMS_AND_CONDITIONS.booleanValue())));
    }

    @Test
    @Transactional
    public void getVisitorCard() throws Exception {
        // Initialize the database
        visitorCardRepository.saveAndFlush(visitorCard);

        // Get the visitorCard
        restVisitorCardMockMvc.perform(get("/api/visitor-cards/{id}", visitorCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visitorCard.getId().intValue()))
            .andExpect(jsonPath("$.tfscNumber").value(DEFAULT_TFSC_NUMBER.toString()))
            .andExpect(jsonPath("$.registeredDate").value(DEFAULT_REGISTERED_DATE.toString()))
            .andExpect(jsonPath("$.termsAndConditions").value(DEFAULT_TERMS_AND_CONDITIONS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVisitorCard() throws Exception {
        // Get the visitorCard
        restVisitorCardMockMvc.perform(get("/api/visitor-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisitorCard() throws Exception {
        // Initialize the database
        visitorCardRepository.saveAndFlush(visitorCard);
        int databaseSizeBeforeUpdate = visitorCardRepository.findAll().size();

        // Update the visitorCard
        VisitorCard updatedVisitorCard = visitorCardRepository.findOne(visitorCard.getId());
        updatedVisitorCard
            .tfscNumber(UPDATED_TFSC_NUMBER)
            .registeredDate(UPDATED_REGISTERED_DATE)
            .termsAndConditions(UPDATED_TERMS_AND_CONDITIONS);

        restVisitorCardMockMvc.perform(put("/api/visitor-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitorCard)))
            .andExpect(status().isOk());

        // Validate the VisitorCard in the database
        List<VisitorCard> visitorCardList = visitorCardRepository.findAll();
        assertThat(visitorCardList).hasSize(databaseSizeBeforeUpdate);
        VisitorCard testVisitorCard = visitorCardList.get(visitorCardList.size() - 1);
        assertThat(testVisitorCard.getTfscNumber()).isEqualTo(UPDATED_TFSC_NUMBER);
        assertThat(testVisitorCard.getRegisteredDate()).isEqualTo(UPDATED_REGISTERED_DATE);
        assertThat(testVisitorCard.isTermsAndConditions()).isEqualTo(UPDATED_TERMS_AND_CONDITIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingVisitorCard() throws Exception {
        int databaseSizeBeforeUpdate = visitorCardRepository.findAll().size();

        // Create the VisitorCard

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisitorCardMockMvc.perform(put("/api/visitor-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorCard)))
            .andExpect(status().isCreated());

        // Validate the VisitorCard in the database
        List<VisitorCard> visitorCardList = visitorCardRepository.findAll();
        assertThat(visitorCardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisitorCard() throws Exception {
        // Initialize the database
        visitorCardRepository.saveAndFlush(visitorCard);
        int databaseSizeBeforeDelete = visitorCardRepository.findAll().size();

        // Get the visitorCard
        restVisitorCardMockMvc.perform(delete("/api/visitor-cards/{id}", visitorCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VisitorCard> visitorCardList = visitorCardRepository.findAll();
        assertThat(visitorCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitorCard.class);
        VisitorCard visitorCard1 = new VisitorCard();
        visitorCard1.setId(1L);
        VisitorCard visitorCard2 = new VisitorCard();
        visitorCard2.setId(visitorCard1.getId());
        assertThat(visitorCard1).isEqualTo(visitorCard2);
        visitorCard2.setId(2L);
        assertThat(visitorCard1).isNotEqualTo(visitorCard2);
        visitorCard1.setId(null);
        assertThat(visitorCard1).isNotEqualTo(visitorCard2);
    }
}
