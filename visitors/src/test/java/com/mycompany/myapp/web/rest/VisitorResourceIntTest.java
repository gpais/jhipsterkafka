package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.VisitorsApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.Visitor;
import com.mycompany.myapp.repository.VisitorRepository;
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
 * Test class for the VisitorResource REST controller.
 *
 * @see VisitorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VisitorsApp.class, SecurityBeanOverrideConfiguration.class})
public class VisitorResourceIntTest {

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORENAME = "AAAAAAAAAA";
    private static final String UPDATED_FORENAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_4 = "BBBBBBBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POST_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_ID = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitorMockMvc;

    private Visitor visitor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitorResource visitorResource = new VisitorResource(visitorRepository);
        this.restVisitorMockMvc = MockMvcBuilders.standaloneSetup(visitorResource)
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
    public static Visitor createEntity(EntityManager em) {
        Visitor visitor = new Visitor()
            .surname(DEFAULT_SURNAME)
            .forename(DEFAULT_FORENAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .address3(DEFAULT_ADDRESS_3)
            .address4(DEFAULT_ADDRESS_4)
            .postCode(DEFAULT_POST_CODE)
            .countryId(DEFAULT_COUNTRY_ID)
            .telephoneNo(DEFAULT_TELEPHONE_NO)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .passportNumber(DEFAULT_PASSPORT_NUMBER);
        return visitor;
    }

    @Before
    public void initTest() {
        visitor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisitor() throws Exception {
        int databaseSizeBeforeCreate = visitorRepository.findAll().size();

        // Create the Visitor
        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isCreated());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeCreate + 1);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testVisitor.getForename()).isEqualTo(DEFAULT_FORENAME);
        assertThat(testVisitor.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testVisitor.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testVisitor.getAddress3()).isEqualTo(DEFAULT_ADDRESS_3);
        assertThat(testVisitor.getAddress4()).isEqualTo(DEFAULT_ADDRESS_4);
        assertThat(testVisitor.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testVisitor.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testVisitor.getTelephoneNo()).isEqualTo(DEFAULT_TELEPHONE_NO);
        assertThat(testVisitor.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testVisitor.getPassportNumber()).isEqualTo(DEFAULT_PASSPORT_NUMBER);
    }

    @Test
    @Transactional
    public void createVisitorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitorRepository.findAll().size();

        // Create the Visitor with an existing ID
        visitor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setSurname(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkForenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setForename(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddress1IsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setAddress1(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setCountryId(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setTelephoneNo(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setEmailAddress(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassportNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitorRepository.findAll().size();
        // set the field null
        visitor.setPassportNumber(null);

        // Create the Visitor, which fails.

        restVisitorMockMvc.perform(post("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isBadRequest());

        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisitors() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        // Get all the visitorList
        restVisitorMockMvc.perform(get("/api/visitors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitor.getId().intValue())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].forename").value(hasItem(DEFAULT_FORENAME.toString())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3.toString())))
            .andExpect(jsonPath("$.[*].address4").value(hasItem(DEFAULT_ADDRESS_4.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(DEFAULT_COUNTRY_ID.toString())))
            .andExpect(jsonPath("$.[*].telephoneNo").value(hasItem(DEFAULT_TELEPHONE_NO.toString())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].passportNumber").value(hasItem(DEFAULT_PASSPORT_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);

        // Get the visitor
        restVisitorMockMvc.perform(get("/api/visitors/{id}", visitor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visitor.getId().intValue()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.forename").value(DEFAULT_FORENAME.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS_3.toString()))
            .andExpect(jsonPath("$.address4").value(DEFAULT_ADDRESS_4.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()))
            .andExpect(jsonPath("$.countryId").value(DEFAULT_COUNTRY_ID.toString()))
            .andExpect(jsonPath("$.telephoneNo").value(DEFAULT_TELEPHONE_NO.toString()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.passportNumber").value(DEFAULT_PASSPORT_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVisitor() throws Exception {
        // Get the visitor
        restVisitorMockMvc.perform(get("/api/visitors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // Update the visitor
        Visitor updatedVisitor = visitorRepository.findOne(visitor.getId());
        updatedVisitor
            .surname(UPDATED_SURNAME)
            .forename(UPDATED_FORENAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .postCode(UPDATED_POST_CODE)
            .countryId(UPDATED_COUNTRY_ID)
            .telephoneNo(UPDATED_TELEPHONE_NO)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .passportNumber(UPDATED_PASSPORT_NUMBER);

        restVisitorMockMvc.perform(put("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitor)))
            .andExpect(status().isOk());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate);
        Visitor testVisitor = visitorList.get(visitorList.size() - 1);
        assertThat(testVisitor.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testVisitor.getForename()).isEqualTo(UPDATED_FORENAME);
        assertThat(testVisitor.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testVisitor.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testVisitor.getAddress3()).isEqualTo(UPDATED_ADDRESS_3);
        assertThat(testVisitor.getAddress4()).isEqualTo(UPDATED_ADDRESS_4);
        assertThat(testVisitor.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testVisitor.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testVisitor.getTelephoneNo()).isEqualTo(UPDATED_TELEPHONE_NO);
        assertThat(testVisitor.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testVisitor.getPassportNumber()).isEqualTo(UPDATED_PASSPORT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingVisitor() throws Exception {
        int databaseSizeBeforeUpdate = visitorRepository.findAll().size();

        // Create the Visitor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisitorMockMvc.perform(put("/api/visitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitor)))
            .andExpect(status().isCreated());

        // Validate the Visitor in the database
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisitor() throws Exception {
        // Initialize the database
        visitorRepository.saveAndFlush(visitor);
        int databaseSizeBeforeDelete = visitorRepository.findAll().size();

        // Get the visitor
        restVisitorMockMvc.perform(delete("/api/visitors/{id}", visitor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Visitor> visitorList = visitorRepository.findAll();
        assertThat(visitorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visitor.class);
        Visitor visitor1 = new Visitor();
        visitor1.setId(1L);
        Visitor visitor2 = new Visitor();
        visitor2.setId(visitor1.getId());
        assertThat(visitor1).isEqualTo(visitor2);
        visitor2.setId(2L);
        assertThat(visitor1).isNotEqualTo(visitor2);
        visitor1.setId(null);
        assertThat(visitor1).isNotEqualTo(visitor2);
    }
}
