package com.ericsson.devops.validator.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SimpleItem.
 */
@Document(collection = "simple_item")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "simpleitem")
public class SimpleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("start_pos")
    private Integer startPos;

    @Field("end_pos")
    private Integer endPos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public SimpleItem startPos(Integer startPos) {
        this.startPos = startPos;
        return this;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public SimpleItem endPos(Integer endPos) {
        this.endPos = endPos;
        return this;
    }

    public void setEndPos(Integer endPos) {
        this.endPos = endPos;
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
        SimpleItem simpleItem = (SimpleItem) o;
        if (simpleItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), simpleItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SimpleItem{" +
            "id=" + getId() +
            ", startPos=" + getStartPos() +
            ", endPos=" + getEndPos() +
            "}";
    }
}
