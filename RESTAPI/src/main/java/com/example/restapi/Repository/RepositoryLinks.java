package com.example.restapi.Repository;

import com.example.restapi.Model.Links;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface RepositoryLinks extends MongoRepository<Links, String> {
}
