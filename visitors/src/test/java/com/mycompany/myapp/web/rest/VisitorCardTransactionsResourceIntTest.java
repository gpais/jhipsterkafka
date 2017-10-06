package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.VisitorsApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.VisitorCardTransactions;
import com.mycompany.myapp.repository.VisitorCardTransactionsRepository;
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

import com.mycompany.myapp.domain.enumeration.TransactionStatus;
/**
 * Test class for the VisitorCardTransactionsResource REST controller.
 *
 * @see VisitorCardTransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VisitorsApp.class, SecurityBeanOverrideConfiguration.class})
public class VisitorCardTransactionsResourceIntTest {

    private static final String DEFAULT_RETAILER_NO = "AAAAAAAAAA";
    private static final String UPDATED_RETAILER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TFSC_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TFSC_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_CARD_EXPIRY = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD_EXPIRY = "BBBBBBBBBB";

    private static final String DEFAULT_PURCHASE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_OFF = "AAAAAAAAAA";
    private static final String UPDATED_VAT_OFF = "BBBBBBBBBB";

    private static final Double DEFAULT_VAT_AMOUNT = 1D;
    private static final Double UPDATED_VAT_AMOUNT = 2D;

    private static final Double DEFAULT_GROSS_AMOUNT = 1D;
    private static final Double UPDATED_GROSS_AMOUNT = 2D;

    private static final Double DEFAULT_REFUND_AMOUNT = 1D;
    private static final Double UPDATED_REFUND_AMOUNT = 2D;

    private static final Double DEFAULT_VAT_RATE = 1D;
    private static final Double UPDATED_VAT_RATE = 2D;

    private static final TransactionStatus DEFAULT_STATUS = TransactionStatus.NOT_MATCHED;
    private static final TransactionStatus UPDATED_STATUS = TransactionStatus.MATCHED;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    @Autowired
    private VisitorCardTransactionsRepository visitorCardTransactionsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitorCardTransactionsMockMvc;

    private VisitorCardTransactions visitorCardTransactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitorCardTransactionsResource visitorCardTransactionsResource = new VisitorCardTransactionsResource(visitorCardTransactionsRepository);
        this.restVisitorCardTransactionsMockMvc = MockMvcBuilders.standaloneSetup(visitorCardTransactionsResource)
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
    public static VisitorCardTransactions createEntity(EntityManager em) {
        VisitorCardTransactions visitorCardTransactions = new VisitorCardTransactions()
            .retailerNo(DEFAULT_RETAILER_NO)
            .tfscNumber(DEFAULT_TFSC_NUMBER)
            .voucherNo(DEFAULT_VOUCHER_NO)
            .creditCardNo(DEFAULT_CREDIT_CARD_NO)
            .creditCardExpiry(DEFAULT_CREDIT_CARD_EXPIRY)
            .purchaseTime(DEFAULT_PURCHASE_TIME)
            .vatOff(DEFAULT_VAT_OFF)
            .vatAmount(DEFAULT_VAT_AMOUNT)
            .grossAmount(DEFAULT_GROSS_AMOUNT)
            .refundAmount(DEFAULT_REFUND_AMOUNT)
            .vatRate(DEFAULT_VAT_RATE)
            .status(DEFAULT_STATUS)
            .reason(DEFAULT_REASON)
            .uuid(DEFAULT_UUID);
        return visitorCardTransactions;
    }

    @Before
    public void initTest() {
        visitorCardTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisitorCardTransactions() throws Exception {
        int databaseSizeBeforeCreate = visitorCardTransactionsRepository.findAll().size();

        // Create the VisitorCardTransactions
        restVisitorCardTransactionsMockMvc.perform(post("/api/visitor-card-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorCardTransactions)))
            .andExpect(status().isCreated());

        // Validate the VisitorCardTransactions in the database
        List<VisitorCardTransactions> visitorCardTransactionsList = visitorCardTransactionsRepository.findAll();
        assertThat(visitorCardTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        VisitorCardTransactions testVisitorCardTransactions = visitorCardTransactionsList.get(visitorCardTransactionsList.size() - 1);
        assertThat(testVisitorCardTransactions.getRetailerNo()).isEqualTo(DEFAULT_RETAILER_NO);
        assertThat(testVisitorCardTransactions.getTfscNumber()).isEqualTo(DEFAULT_TFSC_NUMBER);
        assertThat(testVisitorCardTransactions.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testVisitorCardTransactions.getCreditCardNo()).isEqualTo(DEFAULT_CREDIT_CARD_NO);
        assertThat(testVisitorCardTransactions.getCreditCardExpiry()).isEqualTo(DEFAULT_CREDIT_CARD_EXPIRY);
        assertThat(testVisitorCardTransactions.getPurchaseTime()).isEqualTo(DEFAULT_PURCHASE_TIME);
        assertThat(testVisitorCardTransactions.getVatOff()).isEqualTo(DEFAULT_VAT_OFF);
        assertThat(testVisitorCardTransactions.getVatAmount()).isEqualTo(DEFAULT_VAT_AMOUNT);
        assertThat(testVisitorCardTransactions.getGrossAmount()).isEqualTo(DEFAULT_GROSS_AMOUNT);
        assertThat(testVisitorCardTransactions.getRefundAmount()).isEqualTo(DEFAULT_REFUND_AMOUNT);
        assertThat(testVisitorCardTransactions.getVatRate()).isEqualTo(DEFAULT_VAT_RATE);
        assertThat(testVisitorCardTransactions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testVisitorCardTransactions.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testVisitorCardTransactions.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createVisitorCardTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitorCardTransactionsRepository.findAll().size();

        // Create the VisitorCardTransactions with an existing ID
        visitorCardTransactions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitorCardTransactionsMockMvc.perform(post("/api/visitor-card-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorCardTransactions)))
            .andExpect(status().isBadRequest());

        // Validate the VisitorCardTransactions in the database
        List<VisitorCardTransactions> visitorCardTransactionsList = visitorCardTransactionsRepository.findAll();
        assertThat(visitorCardTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVisitorCardTransactions() throws Exception {
        // Initialize the database
        visitorCardTransactionsRepository.saveAndFlush(visitorCardTransactions);

        // Get all the visitorCardTransactionsList
        restVisitorCardTransactionsMockMvc.perform(get("/api/visitor-card-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitorCardTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].retailerNo").value(hasItem(DEFAULT_RETAILER_NO.toString())))
            .andExpect(jsonPath("$.[*].tfscNumber").value(hasItem(DEFAULT_TFSC_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].creditCardNo").value(hasItem(DEFAULT_CREDIT_CARD_NO.toString())))
            .andExpect(jsonPath("$.[*].creditCardExpiry").value(hasItem(DEFAULT_CREDIT_CARD_EXPIRY.toString())))
            .andExpect(jsonPath("$.[*].purchaseTime").value(hasItem(DEFAULT_PURCHASE_TIME.toString())))
            .andExpect(jsonPath("$.[*].vatOff").value(hasItem(DEFAULT_VAT_OFF.toString())))
            .andExpect(jsonPath("$.[*].vatAmount").value(hasItem(DEFAULT_VAT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].grossAmount").value(hasItem(DEFAULT_GROSS_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].refundAmount").value(hasItem(DEFAULT_REFUND_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].vatRate").value(hasItem(DEFAULT_VAT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }

    @Test
    @Transactional
    public void getVisitorCardTransactions() throws Exception {
        // Initialize the database
        visitorCardTransactionsRepository.saveAndFlush(visitorCardTransactions);

        // Get the visitorCardTransactions
        restVisitorCardTransactionsMockMvc.perform(get("/api/visitor-card-transactions/{id}", visitorCardTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visitorCardTransactions.getId().intValue()))
            .andExpect(jsonPath("$.retailerNo").value(DEFAULT_RETAILER_NO.toString()))
            .andExpect(jsonPath("$.tfscNumber").value(DEFAULT_TFSC_NUMBER.toString()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.creditCardNo").value(DEFAULT_CREDIT_CARD_NO.toString()))
            .andExpect(jsonPath("$.creditCardExpiry").value(DEFAULT_CREDIT_CARD_EXPIRY.toString()))
            .andExpect(jsonPath("$.purchaseTime").value(DEFAULT_PURCHASE_TIME.toString()))
            .andExpect(jsonPath("$.vatOff").value(DEFAULT_VAT_OFF.toString()))
            .andExpect(jsonPath("$.vatAmount").value(DEFAULT_VAT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.grossAmount").value(DEFAULT_GROSS_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.refundAmount").value(DEFAULT_REFUND_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.vatRate").value(DEFAULT_VAT_RATE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVisitorCardTransactions() throws Exception {
        // Get the visitorCardTransactions
        restVisitorCardTransactionsMockMvc.perform(get("/api/visitor-card-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisitorCardTransactions() throws Exception {
        // Initialize the database
        visitorCardTransactionsRepository.saveAndFlush(visitorCardTransactions);
        int databaseSizeBeforeUpdate = visitorCardTransactionsRepository.findAll().size();

        // Update the visitorCardTransactions
        VisitorCardTransactions updatedVisitorCardTransactions = visitorCardTransactionsRepository.findOne(visitorCardTransactions.getId());
        updatedVisitorCardTransactions
            .retailerNo(UPDATED_RETAILER_NO)
            .tfscNumber(UPDATED_TFSC_NUMBER)
            .voucherNo(UPDATED_VOUCHER_NO)
            .creditCardNo(UPDATED_CREDIT_CARD_NO)
            .creditCardExpiry(UPDATED_CREDIT_CARD_EXPIRY)
            .purchaseTime(UPDATED_PURCHASE_TIME)
            .vatOff(UPDATED_VAT_OFF)
            .vatAmount(UPDATED_VAT_AMOUNT)
            .grossAmount(UPDATED_GROSS_AMOUNT)
            .refundAmount(UPDATED_REFUND_AMOUNT)
            .vatRate(UPDATED_VAT_RATE)
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON)
            .uuid(UPDATED_UUID);

        restVisitorCardTransactionsMockMvc.perform(put("/api/visitor-card-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitorCardTransactions)))
            .andExpect(status().isOk());

        // Validate the VisitorCardTransactions in the database
        List<VisitorCardTransactions> visitorCardTransactionsList = visitorCardTransactionsRepository.findAll();
        assertThat(visitorCardTransactionsList).hasSize(databaseSizeBeforeUpdate);
        VisitorCardTransactions testVisitorCardTransactions = visitorCardTransactionsList.get(visitorCardTransactionsList.size() - 1);
        assertThat(testVisitorCardTransactions.getRetailerNo()).isEqualTo(UPDATED_RETAILER_NO);
        assertThat(testVisitorCardTransactions.getTfscNumber()).isEqualTo(UPDATED_TFSC_NUMBER);
        assertThat(testVisitorCardTransactions.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testVisitorCardTransactions.getCreditCardNo()).isEqualTo(UPDATED_CREDIT_CARD_NO);
        assertThat(testVisitorCardTransactions.getCreditCardExpiry()).isEqualTo(UPDATED_CREDIT_CARD_EXPIRY);
        assertThat(testVisitorCardTransactions.getPurchaseTime()).isEqualTo(UPDATED_PURCHASE_TIME);
        assertThat(testVisitorCardTransactions.getVatOff()).isEqualTo(UPDATED_VAT_OFF);
        assertThat(testVisitorCardTransactions.getVatAmount()).isEqualTo(UPDATED_VAT_AMOUNT);
        assertThat(testVisitorCardTransactions.getGrossAmount()).isEqualTo(UPDATED_GROSS_AMOUNT);
        assertThat(testVisitorCardTransactions.getRefundAmount()).isEqualTo(UPDATED_REFUND_AMOUNT);
        assertThat(testVisitorCardTransactions.getVatRate()).isEqualTo(UPDATED_VAT_RATE);
        assertThat(testVisitorCardTransactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testVisitorCardTransactions.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testVisitorCardTransactions.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingVisitorCardTransactions() throws Exception {
        int databaseSizeBeforeUpdate = visitorCardTransactionsRepository.findAll().size();

        // Create the VisitorCardTransactions

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVisitorCardTransactionsMockMvc.perform(put("/api/visitor-card-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visitorCardTransactions)))
            .andExpect(status().isCreated());

        // Validate the VisitorCardTransactions in the database
        List<VisitorCardTransactions> visitorCardTransactionsList = visitorCardTransactionsRepository.findAll();
        assertThat(visitorCardTransactionsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVisitorCardTransactions() throws Exception {
        // Initialize the database
        visitorCardTransactionsRepository.saveAndFlush(visitorCardTransactions);
        int databaseSizeBeforeDelete = visitorCardTransactionsRepository.findAll().size();

        // Get the visitorCardTransactions
        restVisitorCardTransactionsMockMvc.perform(delete("/api/visitor-card-transactions/{id}", visitorCardTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VisitorCardTransactions> visitorCardTransactionsList = visitorCardTransactionsRepository.findAll();
        assertThat(visitorCardTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitorCardTransactions.class);
        VisitorCardTransactions visitorCardTransactions1 = new VisitorCardTransactions();
        visitorCardTransactions1.setId(1L);
        VisitorCardTransactions visitorCardTransactions2 = new VisitorCardTransactions();
        visitorCardTransactions2.setId(visitorCardTransactions1.getId());
        assertThat(visitorCardTransactions1).isEqualTo(visitorCardTransactions2);
        visitorCardTransactions2.setId(2L);
        assertThat(visitorCardTransactions1).isNotEqualTo(visitorCardTransactions2);
        visitorCardTransactions1.setId(null);
        assertThat(visitorCardTransactions1).isNotEqualTo(visitorCardTransactions2);
    }
}
