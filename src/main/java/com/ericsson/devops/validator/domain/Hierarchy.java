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
 * A Hierarchy.
 */
@Document(collection = "hierarchy")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "hierarchy")
public class Hierarchy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("code")
    private String code;

    @Field("name")
    private String name;

    @Field("type")
    private String type;

    @Field("parent")
    private String parent;

    @DBRef
    @Field("catalog")
    @JsonIgnoreProperties("hierarchies")
    private Catalog catalog;

    @DBRef
    @Field("hierarchy")
    @JsonIgnoreProperties("subHierars")
    private Hierarchy hierarchy;

    @DBRef
    @Field("subHierar")
    private Set<Hierarchy> subHierars = new HashSet<>();
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

    public Hierarchy code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Hierarchy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Hierarchy type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public Hierarchy parent(String parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public Hierarchy catalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    public Hierarchy hierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
        return this;
    }

    public void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Set<Hierarchy> getSubHierars() {
        return subHierars;
    }

    public Hierarchy subHierars(Set<Hierarchy> hierarchies) {
        this.subHierars = hierarchies;
        return this;
    }

    public Hierarchy addSubHierar(Hierarchy hierarchy) {
        this.subHierars.add(hierarchy);
        hierarchy.setHierarchy(this);
        return this;
    }

    public Hierarchy removeSubHierar(Hierarchy hierarchy) {
        this.subHierars.remove(hierarchy);
        hierarchy.setHierarchy(null);
        return this;
    }

    public void setSubHierars(Set<Hierarchy> hierarchies) {
        this.subHierars = hierarchies;
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
        Hierarchy hierarchy = (Hierarchy) o;
        if (hierarchy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hierarchy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Hierarchy{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", parent='" + getParent() + "'" +
            "}";
    }
}
