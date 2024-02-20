package com.meva.finance.api;

import com.meva.finance.dto.UserDto;
import com.meva.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDto> cadastrar(@RequestBody UserDto userDto) {
         userService.cadastrar(userDto);
         return ResponseEntity.ok().build();
    }
}
