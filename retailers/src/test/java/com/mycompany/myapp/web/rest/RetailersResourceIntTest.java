package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RetailersApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.Retailers;
import com.mycompany.myapp.repository.RetailersRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RetailersResource REST controller.
 *
 * @see RetailersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RetailersApp.class, SecurityBeanOverrideConfiguration.class})
public class RetailersResourceIntTest {

    private static final String DEFAULT_RETAILER_NO = "AAAAAAAAAA";
    private static final String UPDATED_RETAILER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_1 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_2 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_3 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_4 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_5 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_5 = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MIN_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MIN_TAX_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_NO = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NO = "BBBBBBBBBB";

    @Autowired
    private RetailersRepository retailersRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRetailersMockMvc;

    private Retailers retailers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RetailersResource retailersResource = new RetailersResource(retailersRepository);
        this.restRetailersMockMvc = MockMvcBuilders.standaloneSetup(retailersResource)
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
    public static Retailers createEntity(EntityManager em) {
        Retailers retailers = new Retailers()
            .retailerNo(DEFAULT_RETAILER_NO)
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .contact1(DEFAULT_CONTACT_1)
            .contact2(DEFAULT_CONTACT_2)
            .contact3(DEFAULT_CONTACT_3)
            .contact4(DEFAULT_CONTACT_4)
            .contact5(DEFAULT_CONTACT_5)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .minTaxAmount(DEFAULT_MIN_TAX_AMOUNT)
            .email(DEFAULT_EMAIL)
            .vatNo(DEFAULT_VAT_NO);
        return retailers;
    }

    @Before
    public void initTest() {
        retailers = createEntity(em);
    }

    @Test
    @Transactional
    public void createRetailers() throws Exception {
        int databaseSizeBeforeCreate = retailersRepository.findAll().size();

        // Create the Retailers
        restRetailersMockMvc.perform(post("/api/retailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retailers)))
            .andExpect(status().isCreated());

        // Validate the Retailers in the database
        List<Retailers> retailersList = retailersRepository.findAll();
        assertThat(retailersList).hasSize(databaseSizeBeforeCreate + 1);
        Retailers testRetailers = retailersList.get(retailersList.size() - 1);
        assertThat(testRetailers.getRetailerNo()).isEqualTo(DEFAULT_RETAILER_NO);
        assertThat(testRetailers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRetailers.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testRetailers.getContact1()).isEqualTo(DEFAULT_CONTACT_1);
        assertThat(testRetailers.getContact2()).isEqualTo(DEFAULT_CONTACT_2);
        assertThat(testRetailers.getContact3()).isEqualTo(DEFAULT_CONTACT_3);
        assertThat(testRetailers.getContact4()).isEqualTo(DEFAULT_CONTACT_4);
        assertThat(testRetailers.getContact5()).isEqualTo(DEFAULT_CONTACT_5);
        assertThat(testRetailers.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testRetailers.getMinTaxAmount()).isEqualTo(DEFAULT_MIN_TAX_AMOUNT);
        assertThat(testRetailers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRetailers.getVatNo()).isEqualTo(DEFAULT_VAT_NO);
    }

    @Test
    @Transactional
    public void createRetailersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = retailersRepository.findAll().size();

        // Create the Retailers with an existing ID
        retailers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetailersMockMvc.perform(post("/api/retailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retailers)))
            .andExpect(status().isBadRequest());

        // Validate the Retailers in the database
        List<Retailers> retailersList = retailersRepository.findAll();
        assertThat(retailersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRetailerNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = retailersRepository.findAll().size();
        // set the field null
        retailers.setRetailerNo(null);

        // Create the Retailers, which fails.

        restRetailersMockMvc.perform(post("/api/retailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retailers)))
            .andExpect(status().isBadRequest());

        List<Retailers> retailersList = retailersRepository.findAll();
        assertThat(retailersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRetailers() throws Exception {
        // Initialize the database
        retailersRepository.saveAndFlush(retailers);

        // Get all the retailersList
        restRetailersMockMvc.perform(get("/api/retailers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(retailers.getId().intValue())))
            .andExpect(jsonPath("$.[*].retailerNo").value(hasItem(DEFAULT_RETAILER_NO.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].contact1").value(hasItem(DEFAULT_CONTACT_1.toString())))
            .andExpect(jsonPath("$.[*].contact2").value(hasItem(DEFAULT_CONTACT_2.toString())))
            .andExpect(jsonPath("$.[*].contact3").value(hasItem(DEFAULT_CONTACT_3.toString())))
            .andExpect(jsonPath("$.[*].contact4").value(hasItem(DEFAULT_CONTACT_4.toString())))
            .andExpect(jsonPath("$.[*].contact5").value(hasItem(DEFAULT_CONTACT_5.toString())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].minTaxAmount").value(hasItem(DEFAULT_MIN_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].vatNo").value(hasItem(DEFAULT_VAT_NO.toString())));
    }

    @Test
    @Transactional
    public void getRetailers() throws Exception {
        // Initialize the database
        retailersRepository.saveAndFlush(retailers);

        // Get the retailers
        restRetailersMockMvc.perform(get("/api/retailers/{id}", retailers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(retailers.getId().intValue()))
            .andExpect(jsonPath("$.retailerNo").value(DEFAULT_RETAILER_NO.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.contact1").value(DEFAULT_CONTACT_1.toString()))
            .andExpect(jsonPath("$.contact2").value(DEFAULT_CONTACT_2.toString()))
            .andExpect(jsonPath("$.contact3").value(DEFAULT_CONTACT_3.toString()))
            .andExpect(jsonPath("$.contact4").value(DEFAULT_CONTACT_4.toString()))
            .andExpect(jsonPath("$.contact5").value(DEFAULT_CONTACT_5.toString()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.minTaxAmount").value(DEFAULT_MIN_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.vatNo").value(DEFAULT_VAT_NO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRetailers() throws Exception {
        // Get the retailers
        restRetailersMockMvc.perform(get("/api/retailers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRetailers() throws Exception {
        // Initialize the database
        retailersRepository.saveAndFlush(retailers);
        int databaseSizeBeforeUpdate = retailersRepository.findAll().size();

        // Update the retailers
        Retailers updatedRetailers = retailersRepository.findOne(retailers.getId());
        updatedRetailers
            .retailerNo(UPDATED_RETAILER_NO)
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .contact1(UPDATED_CONTACT_1)
            .contact2(UPDATED_CONTACT_2)
            .contact3(UPDATED_CONTACT_3)
            .contact4(UPDATED_CONTACT_4)
            .contact5(UPDATED_CONTACT_5)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .minTaxAmount(UPDATED_MIN_TAX_AMOUNT)
            .email(UPDATED_EMAIL)
            .vatNo(UPDATED_VAT_NO);

        restRetailersMockMvc.perform(put("/api/retailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRetailers)))
            .andExpect(status().isOk());

        // Validate the Retailers in the database
        List<Retailers> retailersList = retailersRepository.findAll();
        assertThat(retailersList).hasSize(databaseSizeBeforeUpdate);
        Retailers testRetailers = retailersList.get(retailersList.size() - 1);
        assertThat(testRetailers.getRetailerNo()).isEqualTo(UPDATED_RETAILER_NO);
        assertThat(testRetailers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRetailers.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testRetailers.getContact1()).isEqualTo(UPDATED_CONTACT_1);
        assertThat(testRetailers.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testRetailers.getContact3()).isEqualTo(UPDATED_CONTACT_3);
        assertThat(testRetailers.getContact4()).isEqualTo(UPDATED_CONTACT_4);
        assertThat(testRetailers.getContact5()).isEqualTo(UPDATED_CONTACT_5);
        assertThat(testRetailers.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testRetailers.getMinTaxAmount()).isEqualTo(UPDATED_MIN_TAX_AMOUNT);
        assertThat(testRetailers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRetailers.getVatNo()).isEqualTo(UPDATED_VAT_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingRetailers() throws Exception {
        int databaseSizeBeforeUpdate = retailersRepository.findAll().size();

        // Create the Retailers

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRetailersMockMvc.perform(put("/api/retailers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retailers)))
            .andExpect(status().isCreated());

        // Validate the Retailers in the database
        List<Retailers> retailersList = retailersRepository.findAll();
        assertThat(retailersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRetailers() throws Exception {
        // Initialize the database
        retailersRepository.saveAndFlush(retailers);
        int databaseSizeBeforeDelete = retailersRepository.findAll().size();

        // Get the retailers
        restRetailersMockMvc.perform(delete("/api/retailers/{id}", retailers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Retailers> retailersList = retailersRepository.findAll();
        assertThat(retailersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Retailers.class);
        Retailers retailers1 = new Retailers();
        retailers1.setId(1L);
        Retailers retailers2 = new Retailers();
        retailers2.setId(retailers1.getId());
        assertThat(retailers1).isEqualTo(retailers2);
        retailers2.setId(2L);
        assertThat(retailers1).isNotEqualTo(retailers2);
        retailers1.setId(null);
        assertThat(retailers1).isNotEqualTo(retailers2);
    }
}
