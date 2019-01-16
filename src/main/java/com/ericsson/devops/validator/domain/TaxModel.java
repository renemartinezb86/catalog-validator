package com.ericsson.devops.validator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TaxModel.
 */
@Document(collection = "tax_model")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "taxmodel")
public class TaxModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("catalog")
    @JsonIgnoreProperties("taxModels")
    private Catalog catalog;

    @DBRef
    @Field("oIND")
    private IND oIND;

    @DBRef
    @Field("relatedTaxObject")
    private TaxObject relatedTaxObject;

    @DBRef
    @Field("taxModel")
    @JsonIgnoreProperties("relatedTaxModels")
    private TaxModel taxModel;

    @DBRef
    @Field("relatedTaxModel")
    private Set<TaxModel> relatedTaxModels = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public TaxModel catalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public IND getOIND() {
        return oIND;
    }

    public TaxModel oIND(IND iND) {
        this.oIND = iND;
        return this;
    }

    public void setOIND(IND iND) {
        this.oIND = iND;
    }

    public TaxObject getRelatedTaxObject() {
        return relatedTaxObject;
    }

    public TaxModel relatedTaxObject(TaxObject taxObject) {
        this.relatedTaxObject = taxObject;
        return this;
    }

    public void setRelatedTaxObject(TaxObject taxObject) {
        this.relatedTaxObject = taxObject;
    }

    public TaxModel getTaxModel() {
        return taxModel;
    }

    public TaxModel taxModel(TaxModel taxModel) {
        this.taxModel = taxModel;
        return this;
    }

    public void setTaxModel(TaxModel taxModel) {
        this.taxModel = taxModel;
    }

    public Set<TaxModel> getRelatedTaxModels() {
        return relatedTaxModels;
    }

    public TaxModel relatedTaxModels(Set<TaxModel> taxModels) {
        this.relatedTaxModels = taxModels;
        return this;
    }

    public TaxModel addRelatedTaxModel(TaxModel taxModel) {
        this.relatedTaxModels.add(taxModel);
        taxModel.setTaxModel(this);
        return this;
    }

    public TaxModel removeRelatedTaxModel(TaxModel taxModel) {
        this.relatedTaxModels.remove(taxModel);
        taxModel.setTaxModel(null);
        return this;
    }

    public void setRelatedTaxModels(Set<TaxModel> taxModels) {
        this.relatedTaxModels = taxModels;
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
        TaxModel taxModel = (TaxModel) o;
        if (taxModel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taxModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaxModel{" +
            "id=" + getId() +
            "}";
    }
}
