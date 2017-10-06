package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A VisitorCard.
 */
@Entity
@Table(name = "visitor_card")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VisitorCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tfsc_number", nullable = false)
    private String tfscNumber;

    @Column(name = "registered_date")
    private String registeredDate;

    @NotNull
    @Column(name = "terms_and_conditions", nullable = false)
    private Boolean termsAndConditions;

    @OneToMany(mappedBy = "visitorCard")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VisitorCardTransactions> cards = new HashSet<>();

    @ManyToOne
    private Visitor visitor;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTfscNumber() {
        return tfscNumber;
    }

    public VisitorCard tfscNumber(String tfscNumber) {
        this.tfscNumber = tfscNumber;
        return this;
    }

    public void setTfscNumber(String tfscNumber) {
        this.tfscNumber = tfscNumber;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public VisitorCard registeredDate(String registeredDate) {
        this.registeredDate = registeredDate;
        return this;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Boolean isTermsAndConditions() {
        return termsAndConditions;
    }

    public VisitorCard termsAndConditions(Boolean termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
        return this;
    }

    public void setTermsAndConditions(Boolean termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public Set<VisitorCardTransactions> getCards() {
        return cards;
    }

    public VisitorCard cards(Set<VisitorCardTransactions> visitorCardTransactions) {
        this.cards = visitorCardTransactions;
        return this;
    }

    public VisitorCard addCard(VisitorCardTransactions visitorCardTransactions) {
        this.cards.add(visitorCardTransactions);
        visitorCardTransactions.setVisitorCard(this);
        return this;
    }

    public VisitorCard removeCard(VisitorCardTransactions visitorCardTransactions) {
        this.cards.remove(visitorCardTransactions);
        visitorCardTransactions.setVisitorCard(null);
        return this;
    }

    public void setCards(Set<VisitorCardTransactions> visitorCardTransactions) {
        this.cards = visitorCardTransactions;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public VisitorCard visitor(Visitor visitor) {
        this.visitor = visitor;
        return this;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
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
        VisitorCard visitorCard = (VisitorCard) o;
        if (visitorCard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visitorCard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VisitorCard{" +
            "id=" + getId() +
            ", tfscNumber='" + getTfscNumber() + "'" +
            ", registeredDate='" + getRegisteredDate() + "'" +
            ", termsAndConditions='" + isTermsAndConditions() + "'" +
            "}";
    }
}
