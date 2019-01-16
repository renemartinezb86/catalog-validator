package com.ericsson.devops.validator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Characteristic.
 */
@Document(collection = "characteristic")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "characteristic")
public class Characteristic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("char_id")
    private String charID;

    @Field("char_name")
    private String charName;

    @Field("char_type")
    private String charType;

    @Field("default_value")
    private String defaultValue;

    @Field("char_classification_type")
    private String charClassificationType;

    @Field("sequence")
    private String sequence;

    @Field("assoc_type")
    private String assocType;

    @Field("promoted")
    private String promoted;

    @Field("source_item")
    private String sourceItem;

    @Field("char_default_rule")
    private String charDefaultRule;

    @Field("mapto")
    private String mapto;

    @DBRef
    @Field("item")
    @JsonIgnoreProperties("characteristics")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharID() {
        return charID;
    }

    public Characteristic charID(String charID) {
        this.charID = charID;
        return this;
    }

    public void setCharID(String charID) {
        this.charID = charID;
    }

    public String getCharName() {
        return charName;
    }

    public Characteristic charName(String charName) {
        this.charName = charName;
        return this;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public String getCharType() {
        return charType;
    }

    public Characteristic charType(String charType) {
        this.charType = charType;
        return this;
    }

    public void setCharType(String charType) {
        this.charType = charType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Characteristic defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getCharClassificationType() {
        return charClassificationType;
    }

    public Characteristic charClassificationType(String charClassificationType) {
        this.charClassificationType = charClassificationType;
        return this;
    }

    public void setCharClassificationType(String charClassificationType) {
        this.charClassificationType = charClassificationType;
    }

    public String getSequence() {
        return sequence;
    }

    public Characteristic sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getAssocType() {
        return assocType;
    }

    public Characteristic assocType(String assocType) {
        this.assocType = assocType;
        return this;
    }

    public void setAssocType(String assocType) {
        this.assocType = assocType;
    }

    public String getPromoted() {
        return promoted;
    }

    public Characteristic promoted(String promoted) {
        this.promoted = promoted;
        return this;
    }

    public void setPromoted(String promoted) {
        this.promoted = promoted;
    }

    public String getSourceItem() {
        return sourceItem;
    }

    public Characteristic sourceItem(String sourceItem) {
        this.sourceItem = sourceItem;
        return this;
    }

    public void setSourceItem(String sourceItem) {
        this.sourceItem = sourceItem;
    }

    public String getCharDefaultRule() {
        return charDefaultRule;
    }

    public Characteristic charDefaultRule(String charDefaultRule) {
        this.charDefaultRule = charDefaultRule;
        return this;
    }

    public void setCharDefaultRule(String charDefaultRule) {
        this.charDefaultRule = charDefaultRule;
    }

    public String getMapto() {
        return mapto;
    }

    public Characteristic mapto(String mapto) {
        this.mapto = mapto;
        return this;
    }

    public void setMapto(String mapto) {
        this.mapto = mapto;
    }

    public Item getItem() {
        return item;
    }

    public Characteristic item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
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
        Characteristic characteristic = (Characteristic) o;
        if (characteristic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), characteristic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Characteristic{" +
            "id=" + getId() +
            ", charID='" + getCharID() + "'" +
            ", charName='" + getCharName() + "'" +
            ", charType='" + getCharType() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", charClassificationType='" + getCharClassificationType() + "'" +
            ", sequence='" + getSequence() + "'" +
            ", assocType='" + getAssocType() + "'" +
            ", promoted='" + getPromoted() + "'" +
            ", sourceItem='" + getSourceItem() + "'" +
            ", charDefaultRule='" + getCharDefaultRule() + "'" +
            ", mapto='" + getMapto() + "'" +
            "}";
    }
}
