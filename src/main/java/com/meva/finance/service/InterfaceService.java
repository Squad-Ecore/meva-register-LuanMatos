package com.meva.finance.service;

import org.springframework.http.ResponseEntity;

public interface InterfaceService<T> {
    ResponseEntity<T> register(T dto);

    ResponseEntity<T> update(T dto);
}
