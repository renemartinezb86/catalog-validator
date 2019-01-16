package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.Environment;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Environment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvironmentRepository extends MongoRepository<Environment, String> {

}
