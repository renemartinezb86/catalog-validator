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
 * A InfoModel.
 */
@Document(collection = "info_model")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "infomodel")
public class InfoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("code")
    private String code;

    @Field("name")
    private String name;

    @Field("status")
    private String status;

    @Field("desc")
    private String desc;

    @Field("is_dynam")
    private String isDynam;

    @Field("db_table_name")
    private String dbTableName;

    @Field("expirational_interval")
    private String expirationalInterval;

    @Field("type")
    private String type;

    @Field("subtype")
    private String subtype;

    @DBRef
    @Field("catalog")
    @JsonIgnoreProperties("infoModels")
    private Catalog catalog;

    @DBRef
    @Field("attributes")
    private Set<InfoModelAttr> attributes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public InfoModel code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public InfoModel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public InfoModel status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public InfoModel desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIsDynam() {
        return isDynam;
    }

    public InfoModel isDynam(String isDynam) {
        this.isDynam = isDynam;
        return this;
    }

    public void setIsDynam(String isDynam) {
        this.isDynam = isDynam;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public InfoModel dbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
        return this;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }

    public String getExpirationalInterval() {
        return expirationalInterval;
    }

    public InfoModel expirationalInterval(String expirationalInterval) {
        this.expirationalInterval = expirationalInterval;
        return this;
    }

    public void setExpirationalInterval(String expirationalInterval) {
        this.expirationalInterval = expirationalInterval;
    }

    public String getType() {
        return type;
    }

    public InfoModel type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public InfoModel subtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public InfoModel catalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Set<InfoModelAttr> getAttributes() {
        return attributes;
    }

    public InfoModel attributes(Set<InfoModelAttr> infoModelAttrs) {
        this.attributes = infoModelAttrs;
        return this;
    }

    public InfoModel addAttributes(InfoModelAttr infoModelAttr) {
        this.attributes.add(infoModelAttr);
        infoModelAttr.setInfoModel(this);
        return this;
    }

    public InfoModel removeAttributes(InfoModelAttr infoModelAttr) {
        this.attributes.remove(infoModelAttr);
        infoModelAttr.setInfoModel(null);
        return this;
    }

    public void setAttributes(Set<InfoModelAttr> infoModelAttrs) {
        this.attributes = infoModelAttrs;
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
        InfoModel infoModel = (InfoModel) o;
        if (infoModel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), infoModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InfoModel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", desc='" + getDesc() + "'" +
            ", isDynam='" + getIsDynam() + "'" +
            ", dbTableName='" + getDbTableName() + "'" +
            ", expirationalInterval='" + getExpirationalInterval() + "'" +
            ", type='" + getType() + "'" +
            ", subtype='" + getSubtype() + "'" +
            "}";
    }
}
