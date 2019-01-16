package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.InfoModelAttr;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the InfoModelAttr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoModelAttrRepository extends MongoRepository<InfoModelAttr, String> {

}
