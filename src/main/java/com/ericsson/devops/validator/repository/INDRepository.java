package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.IND;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the IND entity.
 */
@SuppressWarnings("unused")
@Repository
public interface INDRepository extends MongoRepository<IND, String> {

}
