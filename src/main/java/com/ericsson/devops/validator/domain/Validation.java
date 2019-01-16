package com.ericsson.devops.validator.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Validation.
 */
@Document(collection = "validation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "validation")
public class Validation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("import_validation_file")
    private String importValidationFile;

    @Field("ecm_validate_file")
    private String ecmValidateFile;

    @Field("timestamp_mark")
    private String timestampMark;

    @DBRef
    @Field("environment")
    private Environment environment;

    @DBRef
    @Field("catalog")
    private Catalog catalog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImportValidationFile() {
        return importValidationFile;
    }

    public Validation importValidationFile(String importValidationFile) {
        this.importValidationFile = importValidationFile;
        return this;
    }

    public void setImportValidationFile(String importValidationFile) {
        this.importValidationFile = importValidationFile;
    }

    public String getEcmValidateFile() {
        return ecmValidateFile;
    }

    public Validation ecmValidateFile(String ecmValidateFile) {
        this.ecmValidateFile = ecmValidateFile;
        return this;
    }

    public void setEcmValidateFile(String ecmValidateFile) {
        this.ecmValidateFile = ecmValidateFile;
    }

    public String getTimestampMark() {
        return timestampMark;
    }

    public Validation timestampMark(String timestampMark) {
        this.timestampMark = timestampMark;
        return this;
    }

    public void setTimestampMark(String timestampMark) {
        this.timestampMark = timestampMark;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Validation environment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public Validation catalog(Catalog catalog) {
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
        Validation validation = (Validation) o;
        if (validation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), validation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Validation{" +
            "id=" + getId() +
            ", importValidationFile='" + getImportValidationFile() + "'" +
            ", ecmValidateFile='" + getEcmValidateFile() + "'" +
            ", timestampMark='" + getTimestampMark() + "'" +
            "}";
    }
}
