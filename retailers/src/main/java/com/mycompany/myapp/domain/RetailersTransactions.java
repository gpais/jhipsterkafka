package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.TransactionStatus;

/**
 * A RetailersTransactions.
 */
@Entity
@Table(name = "retailers_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RetailersTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "retailer_no")
    private String retailerNo;

    @Column(name = "tfsc_number")
    private String tfscNumber;

    @Column(name = "voucher_no")
    private String voucherNo;

    @Column(name = "credit_card_no")
    private String creditCardNo;

    @Column(name = "credit_card_expiry")
    private String creditCardExpiry;

    @Column(name = "purchase_time")
    private String purchaseTime;

    @Column(name = "vat_off")
    private String vatOff;

    @Column(name = "vat_amount")
    private Double vatAmount;

    @Column(name = "gross_amount")
    private Double grossAmount;

    @Column(name = "refund_amount")
    private Double refundAmount;

    @Column(name = "vat_rate")
    private Double vatRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "hash_code")
    private String hashCode;

    @ManyToOne
    private Retailers retailers;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRetailerNo() {
        return retailerNo;
    }

    public RetailersTransactions retailerNo(String retailerNo) {
        this.retailerNo = retailerNo;
        return this;
    }

    public void setRetailerNo(String retailerNo) {
        this.retailerNo = retailerNo;
    }

    public String getTfscNumber() {
        return tfscNumber;
    }

    public RetailersTransactions tfscNumber(String tfscNumber) {
        this.tfscNumber = tfscNumber;
        return this;
    }

    public void setTfscNumber(String tfscNumber) {
        this.tfscNumber = tfscNumber;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public RetailersTransactions voucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
        return this;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public RetailersTransactions creditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
        return this;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public RetailersTransactions creditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
        return this;
    }

    public void setCreditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public RetailersTransactions purchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
        return this;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getVatOff() {
        return vatOff;
    }

    public RetailersTransactions vatOff(String vatOff) {
        this.vatOff = vatOff;
        return this;
    }

    public void setVatOff(String vatOff) {
        this.vatOff = vatOff;
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public RetailersTransactions vatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
        return this;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public RetailersTransactions grossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
        return this;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public RetailersTransactions refundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public RetailersTransactions vatRate(Double vatRate) {
        this.vatRate = vatRate;
        return this;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public RetailersTransactions status(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public RetailersTransactions reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUuid() {
        return uuid;
    }

    public RetailersTransactions uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHashCode() {
        return hashCode;
    }

    public RetailersTransactions hashCode(String hashCode) {
        this.hashCode = hashCode;
        return this;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Retailers getRetailers() {
        return retailers;
    }

    public RetailersTransactions retailers(Retailers retailers) {
        this.retailers = retailers;
        return this;
    }

    public void setRetailers(Retailers retailers) {
        this.retailers = retailers;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RetailersTransactions retailersTransactions = (RetailersTransactions) o;
        if (retailersTransactions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), retailersTransactions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RetailersTransactions{" +
            "id=" + getId() +
            ", retailerNo='" + getRetailerNo() + "'" +
            ", tfscNumber='" + getTfscNumber() + "'" +
            ", voucherNo='" + getVoucherNo() + "'" +
            ", creditCardNo='" + getCreditCardNo() + "'" +
            ", creditCardExpiry='" + getCreditCardExpiry() + "'" +
            ", purchaseTime='" + getPurchaseTime() + "'" +
            ", vatOff='" + getVatOff() + "'" +
            ", vatAmount='" + getVatAmount() + "'" +
            ", grossAmount='" + getGrossAmount() + "'" +
            ", refundAmount='" + getRefundAmount() + "'" +
            ", vatRate='" + getVatRate() + "'" +
            ", status='" + getStatus() + "'" +
            ", reason='" + getReason() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", hashCode='" + getHashCode() + "'" +
            "}";
    }
}
