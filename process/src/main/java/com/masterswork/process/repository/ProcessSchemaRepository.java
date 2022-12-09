package com.masterswork.process.repository;

import com.masterswork.process.model.nosql.ProcessSchema;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessSchemaRepository extends MongoRepository<ProcessSchema, String> {
}
