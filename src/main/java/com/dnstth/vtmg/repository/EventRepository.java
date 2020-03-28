package com.dnstth.vtmg.repository;

import com.dnstth.vtmg.domain.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Event entity.
 */
@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    @Query("{}")
    Page<Event> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Event> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Event> findOneWithEagerRelationships(String id);
}
