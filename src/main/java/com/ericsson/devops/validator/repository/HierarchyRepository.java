package com.ericsson.devops.validator.repository;

import com.ericsson.devops.validator.domain.Hierarchy;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Hierarchy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HierarchyRepository extends MongoRepository<Hierarchy, String> {

}
