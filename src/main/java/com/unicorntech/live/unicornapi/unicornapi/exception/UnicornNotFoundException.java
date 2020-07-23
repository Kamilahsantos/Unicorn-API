package com.unicorntech.live.unicornapi.unicornapi.exception;

public class UnicornNotFoundException extends RuntimeException{


    public UnicornNotFoundException(String unicornId) {
      super("Unicorn not found with id " + unicornId);
    }
}
