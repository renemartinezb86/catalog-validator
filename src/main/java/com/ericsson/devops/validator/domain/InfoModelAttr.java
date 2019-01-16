package com.ericsson.devops.validator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A InfoModelAttr.
 */
@Document(collection = "info_model_attr")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "infomodelattr")
public class InfoModelAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("attr_code")
    private String attrCode;

    @Field("char_type")
    private String charType;

    @Field("name")
    private String name;

    @Field("is_null")
    private String isNull;

    @Field("is_search")
    private String isSearch;

    @Field("descending")
    private String descending;

    @Field("type")
    private String type;

    @Field("assoctype")
    private String assoctype;

    @Field("seq")
    private String seq;

    @DBRef
    @Field("infoModel")
    @JsonIgnoreProperties("attributes")
    private InfoModel infoModel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public InfoModelAttr attrCode(String attrCode) {
        this.attrCode = attrCode;
        return this;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getCharType() {
        return charType;
    }

    public InfoModelAttr charType(String charType) {
        this.charType = charType;
        return this;
    }

    public void setCharType(String charType) {
        this.charType = charType;
    }

    public String getName() {
        return name;
    }

    public InfoModelAttr name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsNull() {
        return isNull;
    }

    public InfoModelAttr isNull(String isNull) {
        this.isNull = isNull;
        return this;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsSearch() {
        return isSearch;
    }

    public InfoModelAttr isSearch(String isSearch) {
        this.isSearch = isSearch;
        return this;
    }

    public void setIsSearch(String isSearch) {
        this.isSearch = isSearch;
    }

    public String getDescending() {
        return descending;
    }

    public InfoModelAttr descending(String descending) {
        this.descending = descending;
        return this;
    }

    public void setDescending(String descending) {
        this.descending = descending;
    }

    public String getType() {
        return type;
    }

    public InfoModelAttr type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssoctype() {
        return assoctype;
    }

    public InfoModelAttr assoctype(String assoctype) {
        this.assoctype = assoctype;
        return this;
    }

    public void setAssoctype(String assoctype) {
        this.assoctype = assoctype;
    }

    public String getSeq() {
        return seq;
    }

    public InfoModelAttr seq(String seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public InfoModel getInfoModel() {
        return infoModel;
    }

    public InfoModelAttr infoModel(InfoModel infoModel) {
        this.infoModel = infoModel;
        return this;
    }

    public void setInfoModel(InfoModel infoModel) {
        this.infoModel = infoModel;
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
        InfoModelAttr infoModelAttr = (InfoModelAttr) o;
        if (infoModelAttr.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), infoModelAttr.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InfoModelAttr{" +
            "id=" + getId() +
            ", attrCode='" + getAttrCode() + "'" +
            ", charType='" + getCharType() + "'" +
            ", name='" + getName() + "'" +
            ", isNull='" + getIsNull() + "'" +
            ", isSearch='" + getIsSearch() + "'" +
            ", descending='" + getDescending() + "'" +
            ", type='" + getType() + "'" +
            ", assoctype='" + getAssoctype() + "'" +
            ", seq='" + getSeq() + "'" +
            "}";
    }
}
