package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RetailersApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.Commissions;
import com.mycompany.myapp.repository.CommissionsRepository;
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
 * Test class for the CommissionsResource REST controller.
 *
 * @see CommissionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RetailersApp.class, SecurityBeanOverrideConfiguration.class})
public class CommissionsResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RETAILER_COMMISSION = new BigDecimal(1);
    private static final BigDecimal UPDATED_RETAILER_COMMISSION = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TFS_COMMISSION = new BigDecimal(1);
    private static final BigDecimal UPDATED_TFS_COMMISSION = new BigDecimal(2);

    @Autowired
    private CommissionsRepository commissionsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommissionsMockMvc;

    private Commissions commissions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommissionsResource commissionsResource = new CommissionsResource(commissionsRepository);
        this.restCommissionsMockMvc = MockMvcBuilders.standaloneSetup(commissionsResource)
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
    public static Commissions createEntity(EntityManager em) {
        Commissions commissions = new Commissions()
            .description(DEFAULT_DESCRIPTION)
            .retailerCommission(DEFAULT_RETAILER_COMMISSION)
            .tfsCommission(DEFAULT_TFS_COMMISSION);
        return commissions;
    }

    @Before
    public void initTest() {
        commissions = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommissions() throws Exception {
        int databaseSizeBeforeCreate = commissionsRepository.findAll().size();

        // Create the Commissions
        restCommissionsMockMvc.perform(post("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissions)))
            .andExpect(status().isCreated());

        // Validate the Commissions in the database
        List<Commissions> commissionsList = commissionsRepository.findAll();
        assertThat(commissionsList).hasSize(databaseSizeBeforeCreate + 1);
        Commissions testCommissions = commissionsList.get(commissionsList.size() - 1);
        assertThat(testCommissions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCommissions.getRetailerCommission()).isEqualTo(DEFAULT_RETAILER_COMMISSION);
        assertThat(testCommissions.getTfsCommission()).isEqualTo(DEFAULT_TFS_COMMISSION);
    }

    @Test
    @Transactional
    public void createCommissionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commissionsRepository.findAll().size();

        // Create the Commissions with an existing ID
        commissions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionsMockMvc.perform(post("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissions)))
            .andExpect(status().isBadRequest());

        // Validate the Commissions in the database
        List<Commissions> commissionsList = commissionsRepository.findAll();
        assertThat(commissionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommissions() throws Exception {
        // Initialize the database
        commissionsRepository.saveAndFlush(commissions);

        // Get all the commissionsList
        restCommissionsMockMvc.perform(get("/api/commissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].retailerCommission").value(hasItem(DEFAULT_RETAILER_COMMISSION.intValue())))
            .andExpect(jsonPath("$.[*].tfsCommission").value(hasItem(DEFAULT_TFS_COMMISSION.intValue())));
    }

    @Test
    @Transactional
    public void getCommissions() throws Exception {
        // Initialize the database
        commissionsRepository.saveAndFlush(commissions);

        // Get the commissions
        restCommissionsMockMvc.perform(get("/api/commissions/{id}", commissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commissions.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.retailerCommission").value(DEFAULT_RETAILER_COMMISSION.intValue()))
            .andExpect(jsonPath("$.tfsCommission").value(DEFAULT_TFS_COMMISSION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCommissions() throws Exception {
        // Get the commissions
        restCommissionsMockMvc.perform(get("/api/commissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommissions() throws Exception {
        // Initialize the database
        commissionsRepository.saveAndFlush(commissions);
        int databaseSizeBeforeUpdate = commissionsRepository.findAll().size();

        // Update the commissions
        Commissions updatedCommissions = commissionsRepository.findOne(commissions.getId());
        updatedCommissions
            .description(UPDATED_DESCRIPTION)
            .retailerCommission(UPDATED_RETAILER_COMMISSION)
            .tfsCommission(UPDATED_TFS_COMMISSION);

        restCommissionsMockMvc.perform(put("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommissions)))
            .andExpect(status().isOk());

        // Validate the Commissions in the database
        List<Commissions> commissionsList = commissionsRepository.findAll();
        assertThat(commissionsList).hasSize(databaseSizeBeforeUpdate);
        Commissions testCommissions = commissionsList.get(commissionsList.size() - 1);
        assertThat(testCommissions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCommissions.getRetailerCommission()).isEqualTo(UPDATED_RETAILER_COMMISSION);
        assertThat(testCommissions.getTfsCommission()).isEqualTo(UPDATED_TFS_COMMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingCommissions() throws Exception {
        int databaseSizeBeforeUpdate = commissionsRepository.findAll().size();

        // Create the Commissions

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommissionsMockMvc.perform(put("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissions)))
            .andExpect(status().isCreated());

        // Validate the Commissions in the database
        List<Commissions> commissionsList = commissionsRepository.findAll();
        assertThat(commissionsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommissions() throws Exception {
        // Initialize the database
        commissionsRepository.saveAndFlush(commissions);
        int databaseSizeBeforeDelete = commissionsRepository.findAll().size();

        // Get the commissions
        restCommissionsMockMvc.perform(delete("/api/commissions/{id}", commissions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commissions> commissionsList = commissionsRepository.findAll();
        assertThat(commissionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commissions.class);
        Commissions commissions1 = new Commissions();
        commissions1.setId(1L);
        Commissions commissions2 = new Commissions();
        commissions2.setId(commissions1.getId());
        assertThat(commissions1).isEqualTo(commissions2);
        commissions2.setId(2L);
        assertThat(commissions1).isNotEqualTo(commissions2);
        commissions1.setId(null);
        assertThat(commissions1).isNotEqualTo(commissions2);
    }
}
