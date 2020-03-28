package com.dnstth.vtmg.repository;

import com.dnstth.vtmg.domain.Place;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Place entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlaceRepository extends MongoRepository<Place, String> {
}
