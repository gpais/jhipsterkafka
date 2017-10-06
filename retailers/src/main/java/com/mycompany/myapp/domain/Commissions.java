package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Commissions.
 */
@Entity
@Table(name = "commissions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "retailer_commission", precision=10, scale=2)
    private BigDecimal retailerCommission;

    @Column(name = "tfs_commission", precision=10, scale=2)
    private BigDecimal tfsCommission;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "commissions_retailers",
               joinColumns = @JoinColumn(name="commissions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="retailers_id", referencedColumnName="id"))
    private Set<Retailers> retailers = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Commissions description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRetailerCommission() {
        return retailerCommission;
    }

    public Commissions retailerCommission(BigDecimal retailerCommission) {
        this.retailerCommission = retailerCommission;
        return this;
    }

    public void setRetailerCommission(BigDecimal retailerCommission) {
        this.retailerCommission = retailerCommission;
    }

    public BigDecimal getTfsCommission() {
        return tfsCommission;
    }

    public Commissions tfsCommission(BigDecimal tfsCommission) {
        this.tfsCommission = tfsCommission;
        return this;
    }

    public void setTfsCommission(BigDecimal tfsCommission) {
        this.tfsCommission = tfsCommission;
    }

    public Set<Retailers> getRetailers() {
        return retailers;
    }

    public Commissions retailers(Set<Retailers> retailers) {
        this.retailers = retailers;
        return this;
    }

    public Commissions addRetailers(Retailers retailers) {
        this.retailers.add(retailers);
        retailers.getCommissions().add(this);
        return this;
    }

    public Commissions removeRetailers(Retailers retailers) {
        this.retailers.remove(retailers);
        retailers.getCommissions().remove(this);
        return this;
    }

    public void setRetailers(Set<Retailers> retailers) {
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
        Commissions commissions = (Commissions) o;
        if (commissions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commissions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commissions{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", retailerCommission='" + getRetailerCommission() + "'" +
            ", tfsCommission='" + getTfsCommission() + "'" +
            "}";
    }
}
