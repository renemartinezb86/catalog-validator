package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.Catalog;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Catalog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogRepository extends MongoRepository<Catalog, String> {

}
