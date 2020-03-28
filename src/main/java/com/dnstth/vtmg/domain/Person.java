package com.dnstth.vtmg.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.dnstth.vtmg.domain.enumeration.Gender;

/**
 * A Person.
 */
@Document(collection = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("bith_date")
    private LocalDate bithDate;

    @Field("death_date")
    private LocalDate deathDate;

    @Field("details")
    private String details;

    @Field("gender")
    private Gender gender;

    @DBRef
    @Field("kind")
    @JsonIgnoreProperties("people")
    private Kind kind;

    @DBRef
    @Field("events")
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

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

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBithDate() {
        return bithDate;
    }

    public Person bithDate(LocalDate bithDate) {
        this.bithDate = bithDate;
        return this;
    }

    public void setBithDate(LocalDate bithDate) {
        this.bithDate = bithDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public Person deathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
        return this;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public String getDetails() {
        return details;
    }

    public Person details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Gender getGender() {
        return gender;
    }

    public Person gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Kind getKind() {
        return kind;
    }

    public Person kind(Kind kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Person events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Person addEvent(Event event) {
        this.events.add(event);
        event.getPeople().add(this);
        return this;
    }

    public Person removeEvent(Event event) {
        this.events.remove(event);
        event.getPeople().remove(this);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bithDate='" + getBithDate() + "'" +
            ", deathDate='" + getDeathDate() + "'" +
            ", details='" + getDetails() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
