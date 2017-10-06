package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RetailersApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.RetailersTransactions;
import com.mycompany.myapp.repository.RetailersTransactionsRepository;
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
 * Test class for the RetailersTransactionsResource REST controller.
 *
 * @see RetailersTransactionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RetailersApp.class, SecurityBeanOverrideConfiguration.class})
public class RetailersTransactionsResourceIntTest {

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

    private static final String DEFAULT_HASH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CODE = "BBBBBBBBBB";

    @Autowired
    private RetailersTransactionsRepository retailersTransactionsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRetailersTransactionsMockMvc;

    private RetailersTransactions retailersTransactions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RetailersTransactionsResource retailersTransactionsResource = new RetailersTransactionsResource(retailersTransactionsRepository);
        this.restRetailersTransactionsMockMvc = MockMvcBuilders.standaloneSetup(retailersTransactionsResource)
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
    public static RetailersTransactions createEntity(EntityManager em) {
        RetailersTransactions retailersTransactions = new RetailersTransactions()
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
            .uuid(DEFAULT_UUID)
            .hashCode(DEFAULT_HASH_CODE);
        return retailersTransactions;
    }

    @Before
    public void initTest() {
        retailersTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createRetailersTransactions() throws Exception {
        int databaseSizeBeforeCreate = retailersTransactionsRepository.findAll().size();

        // Create the RetailersTransactions
        restRetailersTransactionsMockMvc.perform(post("/api/retailers-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retailersTransactions)))
            .andExpect(status().isCreated());

        // Validate the RetailersTransactions in the database
        List<RetailersTransactions> retailersTransactionsList = retailersTransactionsRepository.findAll();
        assertThat(retailersTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        RetailersTransactions testRetailersTransactions = retailersTransactionsList.get(retailersTransactionsList.size() - 1);
        assertThat(testRetailersTransactions.getRetailerNo()).isEqualTo(DEFAULT_RETAILER_NO);
        assertThat(testRetailersTransactions.getTfscNumber()).isEqualTo(DEFAULT_TFSC_NUMBER);
        assertThat(testRetailersTransactions.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testRetailersTransactions.getCreditCardNo()).isEqualTo(DEFAULT_CREDIT_CARD_NO);
        assertThat(testRetailersTransactions.getCreditCardExpiry()).isEqualTo(DEFAULT_CREDIT_CARD_EXPIRY);
        assertThat(testRetailersTransactions.getPurchaseTime()).isEqualTo(DEFAULT_PURCHASE_TIME);
        assertThat(testRetailersTransactions.getVatOff()).isEqualTo(DEFAULT_VAT_OFF);
        assertThat(testRetailersTransactions.getVatAmount()).isEqualTo(DEFAULT_VAT_AMOUNT);
        assertThat(testRetailersTransactions.getGrossAmount()).isEqualTo(DEFAULT_GROSS_AMOUNT);
        assertThat(testRetailersTransactions.getRefundAmount()).isEqualTo(DEFAULT_REFUND_AMOUNT);
        assertThat(testRetailersTransactions.getVatRate()).isEqualTo(DEFAULT_VAT_RATE);
        assertThat(testRetailersTransactions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRetailersTransactions.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testRetailersTransactions.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testRetailersTransactions.getHashCode()).isEqualTo(DEFAULT_HASH_CODE);
    }

    @Test
    @Transactional
    public void createRetailersTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = retailersTransactionsRepository.findAll().size();

        // Create the RetailersTransactions with an existing ID
        retailersTransactions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetailersTransactionsMockMvc.perform(post("/api/retailers-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retailersTransactions)))
            .andExpect(status().isBadRequest());

        // Validate the RetailersTransactions in the database
        List<RetailersTransactions> retailersTransactionsList = retailersTransactionsRepository.findAll();
        assertThat(retailersTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRetailersTransactions() throws Exception {
        // Initialize the database
        retailersTransactionsRepository.saveAndFlush(retailersTransactions);

        // Get all the retailersTransactionsList
        restRetailersTransactionsMockMvc.perform(get("/api/retailers-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(retailersTransactions.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].hashCode").value(hasItem(DEFAULT_HASH_CODE.toString())));
    }

    @Test
    @Transactional
    public void getRetailersTransactions() throws Exception {
        // Initialize the database
        retailersTransactionsRepository.saveAndFlush(retailersTransactions);

        // Get the retailersTransactions
        restRetailersTransactionsMockMvc.perform(get("/api/retailers-transactions/{id}", retailersTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(retailersTransactions.getId().intValue()))
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
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.hashCode").value(DEFAULT_HASH_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRetailersTransactions() throws Exception {
        // Get the retailersTransactions
        restRetailersTransactionsMockMvc.perform(get("/api/retailers-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRetailersTransactions() throws Exception {
        // Initialize the database
        retailersTransactionsRepository.saveAndFlush(retailersTransactions);
        int databaseSizeBeforeUpdate = retailersTransactionsRepository.findAll().size();

        // Update the retailersTransactions
        RetailersTransactions updatedRetailersTransactions = retailersTransactionsRepository.findOne(retailersTransactions.getId());
        updatedRetailersTransactions
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
            .uuid(UPDATED_UUID)
            .hashCode(UPDATED_HASH_CODE);

        restRetailersTransactionsMockMvc.perform(put("/api/retailers-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRetailersTransactions)))
            .andExpect(status().isOk());

        // Validate the RetailersTransactions in the database
        List<RetailersTransactions> retailersTransactionsList = retailersTransactionsRepository.findAll();
        assertThat(retailersTransactionsList).hasSize(databaseSizeBeforeUpdate);
        RetailersTransactions testRetailersTransactions = retailersTransactionsList.get(retailersTransactionsList.size() - 1);
        assertThat(testRetailersTransactions.getRetailerNo()).isEqualTo(UPDATED_RETAILER_NO);
        assertThat(testRetailersTransactions.getTfscNumber()).isEqualTo(UPDATED_TFSC_NUMBER);
        assertThat(testRetailersTransactions.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testRetailersTransactions.getCreditCardNo()).isEqualTo(UPDATED_CREDIT_CARD_NO);
        assertThat(testRetailersTransactions.getCreditCardExpiry()).isEqualTo(UPDATED_CREDIT_CARD_EXPIRY);
        assertThat(testRetailersTransactions.getPurchaseTime()).isEqualTo(UPDATED_PURCHASE_TIME);
        assertThat(testRetailersTransactions.getVatOff()).isEqualTo(UPDATED_VAT_OFF);
        assertThat(testRetailersTransactions.getVatAmount()).isEqualTo(UPDATED_VAT_AMOUNT);
        assertThat(testRetailersTransactions.getGrossAmount()).isEqualTo(UPDATED_GROSS_AMOUNT);
        assertThat(testRetailersTransactions.getRefundAmount()).isEqualTo(UPDATED_REFUND_AMOUNT);
        assertThat(testRetailersTransactions.getVatRate()).isEqualTo(UPDATED_VAT_RATE);
        assertThat(testRetailersTransactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRetailersTransactions.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testRetailersTransactions.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testRetailersTransactions.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRetailersTransactions() throws Exception {
        int databaseSizeBeforeUpdate = retailersTransactionsRepository.findAll().size();

        // Create the RetailersTransactions

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRetailersTransactionsMockMvc.perform(put("/api/retailers-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retailersTransactions)))
            .andExpect(status().isCreated());

        // Validate the RetailersTransactions in the database
        List<RetailersTransactions> retailersTransactionsList = retailersTransactionsRepository.findAll();
        assertThat(retailersTransactionsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRetailersTransactions() throws Exception {
        // Initialize the database
        retailersTransactionsRepository.saveAndFlush(retailersTransactions);
        int databaseSizeBeforeDelete = retailersTransactionsRepository.findAll().size();

        // Get the retailersTransactions
        restRetailersTransactionsMockMvc.perform(delete("/api/retailers-transactions/{id}", retailersTransactions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RetailersTransactions> retailersTransactionsList = retailersTransactionsRepository.findAll();
        assertThat(retailersTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RetailersTransactions.class);
        RetailersTransactions retailersTransactions1 = new RetailersTransactions();
        retailersTransactions1.setId(1L);
        RetailersTransactions retailersTransactions2 = new RetailersTransactions();
        retailersTransactions2.setId(retailersTransactions1.getId());
        assertThat(retailersTransactions1).isEqualTo(retailersTransactions2);
        retailersTransactions2.setId(2L);
        assertThat(retailersTransactions1).isNotEqualTo(retailersTransactions2);
        retailersTransactions1.setId(null);
        assertThat(retailersTransactions1).isNotEqualTo(retailersTransactions2);
    }
}
