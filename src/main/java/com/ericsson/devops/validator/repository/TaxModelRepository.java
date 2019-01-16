package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.TaxModel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the TaxModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxModelRepository extends MongoRepository<TaxModel, String> {

}
