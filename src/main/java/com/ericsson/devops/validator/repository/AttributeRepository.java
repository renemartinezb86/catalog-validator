package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.Attribute;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Attribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeRepository extends MongoRepository<Attribute, String> {

}
