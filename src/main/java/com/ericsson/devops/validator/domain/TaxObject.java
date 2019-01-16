package com.ericsson.devops.validator.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TaxObject.
 */
@Document(collection = "tax_object")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "taxobject")
public class TaxObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("tax_percent")
    private String taxPercent;

    @Field("inc_in_price")
    private String incInPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaxPercent() {
        return taxPercent;
    }

    public TaxObject taxPercent(String taxPercent) {
        this.taxPercent = taxPercent;
        return this;
    }

    public void setTaxPercent(String taxPercent) {
        this.taxPercent = taxPercent;
    }

    public String getIncInPrice() {
        return incInPrice;
    }

    public TaxObject incInPrice(String incInPrice) {
        this.incInPrice = incInPrice;
        return this;
    }

    public void setIncInPrice(String incInPrice) {
        this.incInPrice = incInPrice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaxObject taxObject = (TaxObject) o;
        if (taxObject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxObject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxObject{" +
            "id=" + getId() +
            ", taxPercent='" + getTaxPercent() + "'" +
            ", incInPrice='" + getIncInPrice() + "'" +
            "}";
    }
}
