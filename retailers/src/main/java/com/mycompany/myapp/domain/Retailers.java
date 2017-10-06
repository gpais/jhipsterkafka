package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Retailers.
 */
@Entity
@Table(name = "retailers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Retailers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "retailer_no", nullable = false)
    private String retailerNo;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "contact_1")
    private String contact1;

    @Column(name = "contact_2")
    private String contact2;

    @Column(name = "contact_3")
    private String contact3;

    @Column(name = "contact_4")
    private String contact4;

    @Column(name = "contact_5")
    private String contact5;

    @Column(name = "tax_amount", precision=10, scale=2)
    private BigDecimal taxAmount;

    @Column(name = "min_tax_amount", precision=10, scale=2)
    private BigDecimal minTaxAmount;

    @Column(name = "email")
    private String email;

    @Column(name = "vat_no")
    private String vatNo;

    @ManyToOne
    private Location location;

    @OneToMany(mappedBy = "retailers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RetailersTransactions> transactions = new HashSet<>();

    @ManyToMany(mappedBy = "retailers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commissions> commissions = new HashSet<>();

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

    public Retailers retailerNo(String retailerNo) {
        this.retailerNo = retailerNo;
        return this;
    }

    public void setRetailerNo(String retailerNo) {
        this.retailerNo = retailerNo;
    }

    public String getName() {
        return name;
    }

    public Retailers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public Retailers phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact1() {
        return contact1;
    }

    public Retailers contact1(String contact1) {
        this.contact1 = contact1;
        return this;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public Retailers contact2(String contact2) {
        this.contact2 = contact2;
        return this;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact3() {
        return contact3;
    }

    public Retailers contact3(String contact3) {
        this.contact3 = contact3;
        return this;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }

    public String getContact4() {
        return contact4;
    }

    public Retailers contact4(String contact4) {
        this.contact4 = contact4;
        return this;
    }

    public void setContact4(String contact4) {
        this.contact4 = contact4;
    }

    public String getContact5() {
        return contact5;
    }

    public Retailers contact5(String contact5) {
        this.contact5 = contact5;
        return this;
    }

    public void setContact5(String contact5) {
        this.contact5 = contact5;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public Retailers taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getMinTaxAmount() {
        return minTaxAmount;
    }

    public Retailers minTaxAmount(BigDecimal minTaxAmount) {
        this.minTaxAmount = minTaxAmount;
        return this;
    }

    public void setMinTaxAmount(BigDecimal minTaxAmount) {
        this.minTaxAmount = minTaxAmount;
    }

    public String getEmail() {
        return email;
    }

    public Retailers email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVatNo() {
        return vatNo;
    }

    public Retailers vatNo(String vatNo) {
        this.vatNo = vatNo;
        return this;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public Location getLocation() {
        return location;
    }

    public Retailers location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<RetailersTransactions> getTransactions() {
        return transactions;
    }

    public Retailers transactions(Set<RetailersTransactions> retailersTransactions) {
        this.transactions = retailersTransactions;
        return this;
    }

    public Retailers addTransactions(RetailersTransactions retailersTransactions) {
        this.transactions.add(retailersTransactions);
        retailersTransactions.setRetailers(this);
        return this;
    }

    public Retailers removeTransactions(RetailersTransactions retailersTransactions) {
        this.transactions.remove(retailersTransactions);
        retailersTransactions.setRetailers(null);
        return this;
    }

    public void setTransactions(Set<RetailersTransactions> retailersTransactions) {
        this.transactions = retailersTransactions;
    }

    public Set<Commissions> getCommissions() {
        return commissions;
    }

    public Retailers commissions(Set<Commissions> commissions) {
        this.commissions = commissions;
        return this;
    }

    public Retailers addCommissions(Commissions commissions) {
        this.commissions.add(commissions);
        commissions.getRetailers().add(this);
        return this;
    }

    public Retailers removeCommissions(Commissions commissions) {
        this.commissions.remove(commissions);
        commissions.getRetailers().remove(this);
        return this;
    }

    public void setCommissions(Set<Commissions> commissions) {
        this.commissions = commissions;
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
        Retailers retailers = (Retailers) o;
        if (retailers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), retailers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Retailers{" +
            "id=" + getId() +
            ", retailerNo='" + getRetailerNo() + "'" +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", contact1='" + getContact1() + "'" +
            ", contact2='" + getContact2() + "'" +
            ", contact3='" + getContact3() + "'" +
            ", contact4='" + getContact4() + "'" +
            ", contact5='" + getContact5() + "'" +
            ", taxAmount='" + getTaxAmount() + "'" +
            ", minTaxAmount='" + getMinTaxAmount() + "'" +
            ", email='" + getEmail() + "'" +
            ", vatNo='" + getVatNo() + "'" +
            "}";
    }
}
