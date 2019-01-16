package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.SimpleItem;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the SimpleItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SimpleItemRepository extends MongoRepository<SimpleItem, String> {

}
