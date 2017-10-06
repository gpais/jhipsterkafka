package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A VisitorQueries.
 */
@Entity
@Table(name = "visitor_queries")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VisitorQueries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "query_date")
    private ZonedDateTime queryDate;

    @Column(name = "query_description")
    private String queryDescription;

    @Column(name = "process_date")
    private String processDate;

    @Column(name = "salutation")
    private String salutation;

    @ManyToOne
    private Visitor visitor;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getQueryDate() {
        return queryDate;
    }

    public VisitorQueries queryDate(ZonedDateTime queryDate) {
        this.queryDate = queryDate;
        return this;
    }

    public void setQueryDate(ZonedDateTime queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryDescription() {
        return queryDescription;
    }

    public VisitorQueries queryDescription(String queryDescription) {
        this.queryDescription = queryDescription;
        return this;
    }

    public void setQueryDescription(String queryDescription) {
        this.queryDescription = queryDescription;
    }

    public String getProcessDate() {
        return processDate;
    }

    public VisitorQueries processDate(String processDate) {
        this.processDate = processDate;
        return this;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getSalutation() {
        return salutation;
    }

    public VisitorQueries salutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public VisitorQueries visitor(Visitor visitor) {
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
        VisitorQueries visitorQueries = (VisitorQueries) o;
        if (visitorQueries.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visitorQueries.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VisitorQueries{" +
            "id=" + getId() +
            ", queryDate='" + getQueryDate() + "'" +
            ", queryDescription='" + getQueryDescription() + "'" +
            ", processDate='" + getProcessDate() + "'" +
            ", salutation='" + getSalutation() + "'" +
            "}";
    }
}
