package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.TaxObject;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the TaxObject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxObjectRepository extends MongoRepository<TaxObject, String> {

}
