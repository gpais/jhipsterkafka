package com.mycompany.myapp.domain;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.TransactionStatus;

/**
 * A VisitorCardTransactions.
 */
@Entity
@Table(name = "visitor_card_transactions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VisitorCardTransactions implements Serializable {

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

    @ManyToOne
    private VisitorCard visitorCard;

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

    public VisitorCardTransactions retailerNo(String retailerNo) {
        this.retailerNo = retailerNo;
        return this;
    }

    public void setRetailerNo(String retailerNo) {
        this.retailerNo = retailerNo;
    }

    public String getTfscNumber() {
        return tfscNumber;
    }

    public VisitorCardTransactions tfscNumber(String tfscNumber) {
        this.tfscNumber = tfscNumber;
        return this;
    }

    public void setTfscNumber(String tfscNumber) {
        this.tfscNumber = tfscNumber;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public VisitorCardTransactions voucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
        return this;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public VisitorCardTransactions creditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
        return this;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public VisitorCardTransactions creditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
        return this;
    }

    public void setCreditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public VisitorCardTransactions purchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
        return this;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getVatOff() {
        return vatOff;
    }

    public VisitorCardTransactions vatOff(String vatOff) {
        this.vatOff = vatOff;
        return this;
    }

    public void setVatOff(String vatOff) {
        this.vatOff = vatOff;
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public VisitorCardTransactions vatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
        return this;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public VisitorCardTransactions grossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
        return this;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public VisitorCardTransactions refundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Double getVatRate() {
        return vatRate;
    }

    public VisitorCardTransactions vatRate(Double vatRate) {
        this.vatRate = vatRate;
        return this;
    }

    public void setVatRate(Double vatRate) {
        this.vatRate = vatRate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public VisitorCardTransactions status(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public VisitorCardTransactions reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUuid() {
        return uuid;
    }

    public VisitorCardTransactions uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public VisitorCard getVisitorCard() {
        return visitorCard;
    }

    public VisitorCardTransactions visitorCard(VisitorCard visitorCard) {
        this.visitorCard = visitorCard;
        return this;
    }

    public void setVisitorCard(VisitorCard visitorCard) {
        this.visitorCard = visitorCard;
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
        VisitorCardTransactions visitorCardTransactions = (VisitorCardTransactions) o;
        if (visitorCardTransactions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visitorCardTransactions.getId());
    }

    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(3, 3)
        		.append(getRetailerNo())
        		.append(getTfscNumber())
        		.append(getCreditCardNo())
        		.append( getCreditCardExpiry())
        		.append(getPurchaseTime())
        		.append(getVatOff())
        		.append(getVatAmount() )
        		.append(getGrossAmount() )
        		.build();
    }


    @Override
    public String toString() {
        return "VisitorCardTransactions{" +
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
            "}";
    }
}
