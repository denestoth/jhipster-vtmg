package com.dnstth.vtmg.repository;

import com.dnstth.vtmg.domain.Kind;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Kind entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KindRepository extends MongoRepository<Kind, String> {
}
