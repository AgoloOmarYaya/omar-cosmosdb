package com.agolo.omarcosmosdb.repository;

import com.agolo.omarcosmosdb.model.User;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCosmosRepository<User, String> {
    Flux<User> findByFirstName(String firstName);
}
