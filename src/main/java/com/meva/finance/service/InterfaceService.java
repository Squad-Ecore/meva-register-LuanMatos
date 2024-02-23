package com.meva.finance.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface InterfaceService<T> {
    ResponseEntity<T> register(T dto);

    ResponseEntity<T> update(T dto, UriComponentsBuilder builder);
}
