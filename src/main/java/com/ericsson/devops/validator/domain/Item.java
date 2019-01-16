package com.ericsson.devops.validator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Item.
 */
@Document(collection = "item")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("index")
    private Integer index;

    @Field("to_delete")
    private Boolean toDelete;

    @Field("unique")
    private Boolean unique;

    @DBRef
    @Field("simpleItem")
    private SimpleItem simpleItem;

    @DBRef
    @Field("ind")
    private IND ind;

    @DBRef
    @Field("characteristics")
    private Set<Characteristic> characteristics = new HashSet<>();
    @DBRef
    @Field("attributes")
    private Set<Attribute> attributes = new HashSet<>();
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

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public Item index(Integer index) {
        this.index = index;
        return this;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Boolean isToDelete() {
        return toDelete;
    }

    public Item toDelete(Boolean toDelete) {
        this.toDelete = toDelete;
        return this;
    }

    public void setToDelete(Boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Boolean isUnique() {
        return unique;
    }

    public Item unique(Boolean unique) {
        this.unique = unique;
        return this;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public SimpleItem getSimpleItem() {
        return simpleItem;
    }

    public Item simpleItem(SimpleItem simpleItem) {
        this.simpleItem = simpleItem;
        return this;
    }

    public void setSimpleItem(SimpleItem simpleItem) {
        this.simpleItem = simpleItem;
    }

    public IND getInd() {
        return ind;
    }

    public Item ind(IND iND) {
        this.ind = iND;
        return this;
    }

    public void setInd(IND iND) {
        this.ind = iND;
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public Item characteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
        return this;
    }

    public Item addCharacteristics(Characteristic characteristic) {
        this.characteristics.add(characteristic);
        characteristic.setItem(this);
        return this;
    }

    public Item removeCharacteristics(Characteristic characteristic) {
        this.characteristics.remove(characteristic);
        characteristic.setItem(null);
        return this;
    }

    public void setCharacteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Item attributes(Set<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public Item addAttributes(Attribute attribute) {
        this.attributes.add(attribute);
        attribute.setItem(this);
        return this;
    }

    public Item removeAttributes(Attribute attribute) {
        this.attributes.remove(attribute);
        attribute.setItem(null);
        return this;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
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
        Item item = (Item) o;
        if (item.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", index=" + getIndex() +
            ", toDelete='" + isToDelete() + "'" +
            ", unique='" + isUnique() + "'" +
            "}";
    }
}
