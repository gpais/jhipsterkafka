package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TransactionApp;

import com.mycompany.myapp.config.SecurityBeanOverrideConfiguration;

import com.mycompany.myapp.domain.Transaction;
import com.mycompany.myapp.repository.TransactionRepository;
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

import com.mycompany.myapp.domain.enumeration.TransactionStatus;
/**
 * Test class for the TransactionResource REST controller.
 *
 * @see TransactionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TransactionApp.class, SecurityBeanOverrideConfiguration.class})
public class TransactionResourceIntTest {

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

    private static final ZonedDateTime DEFAULT_PURCHASE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PURCHASE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

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
    private TransactionRepository transactionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionResource transactionResource = new TransactionResource(transactionRepository);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
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
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .retailerNo(DEFAULT_RETAILER_NO)
            .tfscNumber(DEFAULT_TFSC_NUMBER)
            .voucherNo(DEFAULT_VOUCHER_NO)
            .creditCardNo(DEFAULT_CREDIT_CARD_NO)
            .creditCardExpiry(DEFAULT_CREDIT_CARD_EXPIRY)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .purchaseTime(DEFAULT_PURCHASE_TIME)
            .vatOff(DEFAULT_VAT_OFF)
            .vatAmount(DEFAULT_VAT_AMOUNT)
            .grossAmount(DEFAULT_GROSS_AMOUNT)
            .refundAmount(DEFAULT_REFUND_AMOUNT)
            .vatRate(DEFAULT_VAT_RATE)
            .status(DEFAULT_STATUS)
            .reason(DEFAULT_REASON)
            .uuid(DEFAULT_UUID);
        return transaction;
    }

    @Before
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getRetailerNo()).isEqualTo(DEFAULT_RETAILER_NO);
        assertThat(testTransaction.getTfscNumber()).isEqualTo(DEFAULT_TFSC_NUMBER);
        assertThat(testTransaction.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testTransaction.getCreditCardNo()).isEqualTo(DEFAULT_CREDIT_CARD_NO);
        assertThat(testTransaction.getCreditCardExpiry()).isEqualTo(DEFAULT_CREDIT_CARD_EXPIRY);
        assertThat(testTransaction.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testTransaction.getPurchaseTime()).isEqualTo(DEFAULT_PURCHASE_TIME);
        assertThat(testTransaction.getVatOff()).isEqualTo(DEFAULT_VAT_OFF);
        assertThat(testTransaction.getVatAmount()).isEqualTo(DEFAULT_VAT_AMOUNT);
        assertThat(testTransaction.getGrossAmount()).isEqualTo(DEFAULT_GROSS_AMOUNT);
        assertThat(testTransaction.getRefundAmount()).isEqualTo(DEFAULT_REFUND_AMOUNT);
        assertThat(testTransaction.getVatRate()).isEqualTo(DEFAULT_VAT_RATE);
        assertThat(testTransaction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransaction.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testTransaction.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].retailerNo").value(hasItem(DEFAULT_RETAILER_NO.toString())))
            .andExpect(jsonPath("$.[*].tfscNumber").value(hasItem(DEFAULT_TFSC_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].creditCardNo").value(hasItem(DEFAULT_CREDIT_CARD_NO.toString())))
            .andExpect(jsonPath("$.[*].creditCardExpiry").value(hasItem(DEFAULT_CREDIT_CARD_EXPIRY.toString())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(sameInstant(DEFAULT_PURCHASE_DATE))))
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
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.retailerNo").value(DEFAULT_RETAILER_NO.toString()))
            .andExpect(jsonPath("$.tfscNumber").value(DEFAULT_TFSC_NUMBER.toString()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.creditCardNo").value(DEFAULT_CREDIT_CARD_NO.toString()))
            .andExpect(jsonPath("$.creditCardExpiry").value(DEFAULT_CREDIT_CARD_EXPIRY.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(sameInstant(DEFAULT_PURCHASE_DATE)))
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
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findOne(transaction.getId());
        updatedTransaction
            .retailerNo(UPDATED_RETAILER_NO)
            .tfscNumber(UPDATED_TFSC_NUMBER)
            .voucherNo(UPDATED_VOUCHER_NO)
            .creditCardNo(UPDATED_CREDIT_CARD_NO)
            .creditCardExpiry(UPDATED_CREDIT_CARD_EXPIRY)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .purchaseTime(UPDATED_PURCHASE_TIME)
            .vatOff(UPDATED_VAT_OFF)
            .vatAmount(UPDATED_VAT_AMOUNT)
            .grossAmount(UPDATED_GROSS_AMOUNT)
            .refundAmount(UPDATED_REFUND_AMOUNT)
            .vatRate(UPDATED_VAT_RATE)
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON)
            .uuid(UPDATED_UUID);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getRetailerNo()).isEqualTo(UPDATED_RETAILER_NO);
        assertThat(testTransaction.getTfscNumber()).isEqualTo(UPDATED_TFSC_NUMBER);
        assertThat(testTransaction.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testTransaction.getCreditCardNo()).isEqualTo(UPDATED_CREDIT_CARD_NO);
        assertThat(testTransaction.getCreditCardExpiry()).isEqualTo(UPDATED_CREDIT_CARD_EXPIRY);
        assertThat(testTransaction.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testTransaction.getPurchaseTime()).isEqualTo(UPDATED_PURCHASE_TIME);
        assertThat(testTransaction.getVatOff()).isEqualTo(UPDATED_VAT_OFF);
        assertThat(testTransaction.getVatAmount()).isEqualTo(UPDATED_VAT_AMOUNT);
        assertThat(testTransaction.getGrossAmount()).isEqualTo(UPDATED_GROSS_AMOUNT);
        assertThat(testTransaction.getRefundAmount()).isEqualTo(UPDATED_REFUND_AMOUNT);
        assertThat(testTransaction.getVatRate()).isEqualTo(UPDATED_VAT_RATE);
        assertThat(testTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransaction.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testTransaction.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);
        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Get the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction1.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        transaction2.setId(2L);
        assertThat(transaction1).isNotEqualTo(transaction2);
        transaction1.setId(null);
        assertThat(transaction1).isNotEqualTo(transaction2);
    }
}
