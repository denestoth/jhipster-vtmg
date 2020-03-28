package com.dnstth.vtmg.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Kind.
 */
@Document(collection = "kind")
public class Kind implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("description")
    private String description;

    @Field("details")
    private String details;

    @DBRef
    @Field("person")
    private Set<Person> people = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Kind description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public Kind details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public Kind people(Set<Person> people) {
        this.people = people;
        return this;
    }

    public Kind addPerson(Person person) {
        this.people.add(person);
        person.setKind(this);
        return this;
    }

    public Kind removePerson(Person person) {
        this.people.remove(person);
        person.setKind(null);
        return this;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kind)) {
            return false;
        }
        return id != null && id.equals(((Kind) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Kind{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
