package com.agolo.omarcosmosdb;


import com.agolo.omarcosmosdb.model.User;
import com.agolo.omarcosmosdb.repository.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;

    @PostMapping("/cosmos")
    public ResponseEntity<Object> addUser(@RequestBody User payload) {

        LOGGER.info("payload: {}", payload);
        Mono<User> userMono = repository.save(payload);
        User user = userMono.block();
        if(ObjectUtils.isNotEmpty(user)) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cosmos")
    public ResponseEntity<List<User>> getUsers() {

        Flux<User> usersFlux = repository.findAll();
        List<User> users = usersFlux.collectList().block();

        if(ObjectUtils.isNotEmpty(users)) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
