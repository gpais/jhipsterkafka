package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A VisitorTrip.
 */
@Entity
@Table(name = "visitor_trip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VisitorTrip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "date_entry")
    private ZonedDateTime dateEntry;

    @Column(name = "date_exit")
    private ZonedDateTime dateExit;

    @NotNull
    @Column(name = "flight_number", nullable = false)
    private String flightNumber;

    @NotNull
    @Column(name = "credit_card_type", nullable = false)
    private String creditCardType;

    @NotNull
    @Column(name = "credit_card_token", nullable = false)
    private String creditCardToken;

    @Column(name = "credit_card_expiry")
    private ZonedDateTime creditCardExpiry;

    @NotNull
    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "match_large_tx")
    private Boolean matchLargeTX;

    @ManyToOne
    private Visitor visitor;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public VisitorTrip locationCode(String locationCode) {
        this.locationCode = locationCode;
        return this;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public ZonedDateTime getDateEntry() {
        return dateEntry;
    }

    public VisitorTrip dateEntry(ZonedDateTime dateEntry) {
        this.dateEntry = dateEntry;
        return this;
    }

    public void setDateEntry(ZonedDateTime dateEntry) {
        this.dateEntry = dateEntry;
    }

    public ZonedDateTime getDateExit() {
        return dateExit;
    }

    public VisitorTrip dateExit(ZonedDateTime dateExit) {
        this.dateExit = dateExit;
        return this;
    }

    public void setDateExit(ZonedDateTime dateExit) {
        this.dateExit = dateExit;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public VisitorTrip flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public VisitorTrip creditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
        return this;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardToken() {
        return creditCardToken;
    }

    public VisitorTrip creditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
        return this;
    }

    public void setCreditCardToken(String creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

    public ZonedDateTime getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public VisitorTrip creditCardExpiry(ZonedDateTime creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
        return this;
    }

    public void setCreditCardExpiry(ZonedDateTime creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public VisitorTrip cardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        return this;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public VisitorTrip createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean isMatchLargeTX() {
        return matchLargeTX;
    }

    public VisitorTrip matchLargeTX(Boolean matchLargeTX) {
        this.matchLargeTX = matchLargeTX;
        return this;
    }

    public void setMatchLargeTX(Boolean matchLargeTX) {
        this.matchLargeTX = matchLargeTX;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public VisitorTrip visitor(Visitor visitor) {
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
        VisitorTrip visitorTrip = (VisitorTrip) o;
        if (visitorTrip.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visitorTrip.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VisitorTrip{" +
            "id=" + getId() +
            ", locationCode='" + getLocationCode() + "'" +
            ", dateEntry='" + getDateEntry() + "'" +
            ", dateExit='" + getDateExit() + "'" +
            ", flightNumber='" + getFlightNumber() + "'" +
            ", creditCardType='" + getCreditCardType() + "'" +
            ", creditCardToken='" + getCreditCardToken() + "'" +
            ", creditCardExpiry='" + getCreditCardExpiry() + "'" +
            ", cardHolderName='" + getCardHolderName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", matchLargeTX='" + isMatchLargeTX() + "'" +
            "}";
    }
}
