package com.ericsson.devops.validator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BaseItem.
 */
@Document(collection = "base_item")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "baseitem")
public class BaseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("basename")
    private String basename;

    @Field("base_type")
    private String baseType;

    @Field("item_name")
    private String itemName;

    @Field("item_type")
    private String itemType;

    @Field("item_subtype")
    private String itemSubtype;

    @DBRef
    @Field("catalog")
    @JsonIgnoreProperties("baseItems")
    private Catalog catalog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBasename() {
        return basename;
    }

    public BaseItem basename(String basename) {
        this.basename = basename;
        return this;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public String getBaseType() {
        return baseType;
    }

    public BaseItem baseType(String baseType) {
        this.baseType = baseType;
        return this;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getItemName() {
        return itemName;
    }

    public BaseItem itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public BaseItem itemType(String itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemSubtype() {
        return itemSubtype;
    }

    public BaseItem itemSubtype(String itemSubtype) {
        this.itemSubtype = itemSubtype;
        return this;
    }

    public void setItemSubtype(String itemSubtype) {
        this.itemSubtype = itemSubtype;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public BaseItem catalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
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
        BaseItem baseItem = (BaseItem) o;
        if (baseItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseItem{" +
            "id=" + getId() +
            ", basename='" + getBasename() + "'" +
            ", baseType='" + getBaseType() + "'" +
            ", itemName='" + getItemName() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", itemSubtype='" + getItemSubtype() + "'" +
            "}";
    }
}
