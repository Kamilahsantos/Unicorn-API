package com.unicorntech.live.unicornapi.unicornapi.controller;

import com.unicorntech.live.unicornapi.unicornapi.exception.UnicornNotFoundException;
import com.unicorntech.live.unicornapi.unicornapi.model.Unicorn;
import com.unicorntech.live.unicornapi.unicornapi.repository.UnicornRepository;
import com.unicorntech.live.unicornapi.unicornapi.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class UnicornController {

  @Autowired
  private UnicornRepository unicornRepository;

  @GetMapping("/unicorns")
  public Flux<Unicorn> getAllUnicorn() {
    return unicornRepository.findAll();
  }

  @PostMapping("/unicorns")
  public Mono<Unicorn> createUnicorn(@Valid @RequestBody Unicorn unicorn) {
    return unicornRepository.save(unicorn);
  }

  @GetMapping("/unicorns/{id}")
  public Mono<ResponseEntity<Unicorn>> getUnicornById(@PathVariable(value = "id") String unicornId) {
    return unicornRepository.findById(unicornId)
      .map(savedUnicorn-> ResponseEntity.ok(savedUnicorn))
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/unicorns/{id}")
  public Mono<ResponseEntity<Unicorn>> updateUnicorn(@PathVariable(value = "id") String unicornId,
                                               @Valid @RequestBody Unicorn unicorn) {
    return unicornRepository.findById(unicornId)
      .flatMap(existingUnicorn -> {
        existingUnicorn.setName(unicorn.getName());
        return unicornRepository.save(existingUnicorn);
      })
      .map(updateUnicorn -> new ResponseEntity<>(updateUnicorn, HttpStatus.OK))
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/unicorns/{id}")
  public Mono<ResponseEntity<Void>> deleteUnicorn(@PathVariable(value = "id") String unicornId) {

    return unicornRepository.findById(unicornId)
      .flatMap(existingUnicorn ->
        unicornRepository.delete(existingUnicorn)
          .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
      )
      .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(value = "/stream/unicorns", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Unicorn> streamAllUnicorns() {
    return unicornRepository.findAll();
  }


  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("A Unicorns with the same text already exists"));
  }

  @ExceptionHandler(UnicornNotFoundException.class)
  public ResponseEntity handleHeroNotFoundException(UnicornNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }

}
