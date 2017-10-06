package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Aggregate.
 */
@Entity
@Table(name = "aggregate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aggregate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "aggregate_id")
    private String aggregateId;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "version")
    private Integer version;

    @Column(name = "sequence_number")
    private Integer sequenceNumber;

    @Column(name = "data")
    private String data;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public Aggregate aggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
        return this;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getType() {
        return type;
    }

    public Aggregate type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public Aggregate version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public Aggregate sequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getData() {
        return data;
    }

    public Aggregate data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
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
        Aggregate aggregate = (Aggregate) o;
        if (aggregate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aggregate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Aggregate{" +
            "id=" + getId() +
            ", aggregateId='" + getAggregateId() + "'" +
            ", type='" + getType() + "'" +
            ", version='" + getVersion() + "'" +
            ", sequenceNumber='" + getSequenceNumber() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
