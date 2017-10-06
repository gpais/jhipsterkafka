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
 * A Visitor.
 */
@Entity
@Table(name = "visitor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    @Column(name = "forename", nullable = false)
    private String forename;

    @NotNull
    @Column(name = "address_1", nullable = false)
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    @Column(name = "address_4")
    private String address4;

    @Column(name = "post_code")
    private String postCode;

    @NotNull
    @Column(name = "country_id", nullable = false)
    private String countryId;

    @NotNull
    @Column(name = "telephone_no", nullable = false)
    private String telephoneNo;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @NotNull
    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @OneToMany(mappedBy = "visitor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VisitorCard> cards = new HashSet<>();

    @OneToMany(mappedBy = "visitor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VisitorTrip> trips = new HashSet<>();

    @OneToMany(mappedBy = "visitor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VisitorQueries> queries = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public Visitor surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public Visitor forename(String forename) {
        this.forename = forename;
        return this;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getAddress1() {
        return address1;
    }

    public Visitor address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Visitor address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public Visitor address3(String address3) {
        this.address3 = address3;
        return this;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public Visitor address4(String address4) {
        this.address4 = address4;
        return this;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getPostCode() {
        return postCode;
    }

    public Visitor postCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public Visitor countryId(String countryId) {
        this.countryId = countryId;
        return this;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public Visitor telephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
        return this;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Visitor emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public Visitor passportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Set<VisitorCard> getCards() {
        return cards;
    }

    public Visitor cards(Set<VisitorCard> visitorCards) {
        this.cards = visitorCards;
        return this;
    }

    public Visitor addCards(VisitorCard visitorCard) {
        this.cards.add(visitorCard);
        visitorCard.setVisitor(this);
        return this;
    }

    public Visitor removeCards(VisitorCard visitorCard) {
        this.cards.remove(visitorCard);
        visitorCard.setVisitor(null);
        return this;
    }

    public void setCards(Set<VisitorCard> visitorCards) {
        this.cards = visitorCards;
    }

    public Set<VisitorTrip> getTrips() {
        return trips;
    }

    public Visitor trips(Set<VisitorTrip> visitorTrips) {
        this.trips = visitorTrips;
        return this;
    }

    public Visitor addTrip(VisitorTrip visitorTrip) {
        this.trips.add(visitorTrip);
        visitorTrip.setVisitor(this);
        return this;
    }

    public Visitor removeTrip(VisitorTrip visitorTrip) {
        this.trips.remove(visitorTrip);
        visitorTrip.setVisitor(null);
        return this;
    }

    public void setTrips(Set<VisitorTrip> visitorTrips) {
        this.trips = visitorTrips;
    }

    public Set<VisitorQueries> getQueries() {
        return queries;
    }

    public Visitor queries(Set<VisitorQueries> visitorQueries) {
        this.queries = visitorQueries;
        return this;
    }

    public Visitor addQueries(VisitorQueries visitorQueries) {
        this.queries.add(visitorQueries);
        visitorQueries.setVisitor(this);
        return this;
    }

    public Visitor removeQueries(VisitorQueries visitorQueries) {
        this.queries.remove(visitorQueries);
        visitorQueries.setVisitor(null);
        return this;
    }

    public void setQueries(Set<VisitorQueries> visitorQueries) {
        this.queries = visitorQueries;
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
        Visitor visitor = (Visitor) o;
        if (visitor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visitor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Visitor{" +
            "id=" + getId() +
            ", surname='" + getSurname() + "'" +
            ", forename='" + getForename() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", address3='" + getAddress3() + "'" +
            ", address4='" + getAddress4() + "'" +
            ", postCode='" + getPostCode() + "'" +
            ", countryId='" + getCountryId() + "'" +
            ", telephoneNo='" + getTelephoneNo() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", passportNumber='" + getPassportNumber() + "'" +
            "}";
    }
}
