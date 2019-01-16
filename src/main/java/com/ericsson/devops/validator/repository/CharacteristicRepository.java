package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.Characteristic;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Characteristic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacteristicRepository extends MongoRepository<Characteristic, String> {

}
