package com.ericsson.devops.validator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Catalog.
 */
@Document(collection = "catalog")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "catalog")
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("file_name")
    private String fileName;

    @Field("created_date")
    private Instant createdDate;

    @DBRef
    @Field("taxModels")
    private Set<TaxModel> taxModels = new HashSet<>();
    @DBRef
    @Field("baseItems")
    private Set<BaseItem> baseItems = new HashSet<>();
    @DBRef
    @Field("hierarchies")
    private Set<Hierarchy> hierarchies = new HashSet<>();
    @DBRef
    @Field("infoModels")
    private Set<InfoModel> infoModels = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Catalog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public Catalog fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Catalog createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<TaxModel> getTaxModels() {
        return taxModels;
    }

    public Catalog taxModels(Set<TaxModel> taxModels) {
        this.taxModels = taxModels;
        return this;
    }

    public Catalog addTaxModels(TaxModel taxModel) {
        this.taxModels.add(taxModel);
        taxModel.setCatalog(this);
        return this;
    }

    public Catalog removeTaxModels(TaxModel taxModel) {
        this.taxModels.remove(taxModel);
        taxModel.setCatalog(null);
        return this;
    }

    public void setTaxModels(Set<TaxModel> taxModels) {
        this.taxModels = taxModels;
    }

    public Set<BaseItem> getBaseItems() {
        return baseItems;
    }

    public Catalog baseItems(Set<BaseItem> baseItems) {
        this.baseItems = baseItems;
        return this;
    }

    public Catalog addBaseItems(BaseItem baseItem) {
        this.baseItems.add(baseItem);
        baseItem.setCatalog(this);
        return this;
    }

    public Catalog removeBaseItems(BaseItem baseItem) {
        this.baseItems.remove(baseItem);
        baseItem.setCatalog(null);
        return this;
    }

    public void setBaseItems(Set<BaseItem> baseItems) {
        this.baseItems = baseItems;
    }

    public Set<Hierarchy> getHierarchies() {
        return hierarchies;
    }

    public Catalog hierarchies(Set<Hierarchy> hierarchies) {
        this.hierarchies = hierarchies;
        return this;
    }

    public Catalog addHierarchies(Hierarchy hierarchy) {
        this.hierarchies.add(hierarchy);
        hierarchy.setCatalog(this);
        return this;
    }

    public Catalog removeHierarchies(Hierarchy hierarchy) {
        this.hierarchies.remove(hierarchy);
        hierarchy.setCatalog(null);
        return this;
    }

    public void setHierarchies(Set<Hierarchy> hierarchies) {
        this.hierarchies = hierarchies;
    }

    public Set<InfoModel> getInfoModels() {
        return infoModels;
    }

    public Catalog infoModels(Set<InfoModel> infoModels) {
        this.infoModels = infoModels;
        return this;
    }

    public Catalog addInfoModels(InfoModel infoModel) {
        this.infoModels.add(infoModel);
        infoModel.setCatalog(this);
        return this;
    }

    public Catalog removeInfoModels(InfoModel infoModel) {
        this.infoModels.remove(infoModel);
        infoModel.setCatalog(null);
        return this;
    }

    public void setInfoModels(Set<InfoModel> infoModels) {
        this.infoModels = infoModels;
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
        Catalog catalog = (Catalog) o;
        if (catalog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), catalog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Catalog{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
