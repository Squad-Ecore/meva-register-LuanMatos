package com.meva.finance.api;

import com.meva.finance.dto.UserDto;
import com.meva.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto, UriComponentsBuilder builder) {
        return userService.update(userDto, builder);
    }
}
