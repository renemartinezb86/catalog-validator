package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.InfoModel;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the InfoModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoModelRepository extends MongoRepository<InfoModel, String> {

}
