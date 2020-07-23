package com.unicorntech.live.unicornapi.unicornapi.repository;

import com.unicorntech.live.unicornapi.unicornapi.model.Unicorn;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnicornRepository extends ReactiveMongoRepository <Unicorn, String> {
}
