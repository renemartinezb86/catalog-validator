package com.ericsson.devops.validator.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A IND.
 */
@Document(collection = "ind")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ind")
public class IND implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("desc")
    private String desc;

    @Field("write_to_lld")
    private Boolean writeToLLD;

    @Field("write_translation")
    private Boolean writeTranslation;

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

    public IND name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public IND desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Boolean isWriteToLLD() {
        return writeToLLD;
    }

    public IND writeToLLD(Boolean writeToLLD) {
        this.writeToLLD = writeToLLD;
        return this;
    }

    public void setWriteToLLD(Boolean writeToLLD) {
        this.writeToLLD = writeToLLD;
    }

    public Boolean isWriteTranslation() {
        return writeTranslation;
    }

    public IND writeTranslation(Boolean writeTranslation) {
        this.writeTranslation = writeTranslation;
        return this;
    }

    public void setWriteTranslation(Boolean writeTranslation) {
        this.writeTranslation = writeTranslation;
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
        IND iND = (IND) o;
        if (iND.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), iND.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IND{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", writeToLLD='" + isWriteToLLD() + "'" +
            ", writeTranslation='" + isWriteTranslation() + "'" +
            "}";
    }
}
