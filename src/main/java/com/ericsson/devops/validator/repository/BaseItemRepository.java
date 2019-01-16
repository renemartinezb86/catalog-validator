package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.BaseItem;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the BaseItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseItemRepository extends MongoRepository<BaseItem, String> {

}
