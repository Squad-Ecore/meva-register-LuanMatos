package com.meva.finance.service;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserDto;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyRepository familyRepository;

    //Método cadastra usuário retornando 201.
    public ResponseEntity<UserDto> cadastrar(@RequestBody UserDto userDto) {

            User user = userDto.converter();

            Optional<Family> optionalFamily = familyRepository.findById(user.getFamily().getId());

        
    }
/*
    public Family validarFamily(FamilyDto familyDto){

        if(user.getFamily().getId() == 0){
            return ResponseEntity.ok().build();
        }
        else if(optionalFamily.isPresent()){
            userRepository.save(user);
            return ResponseEntity.created(uri).body(new UserDto(user));
        }  else{
            return ResponseEntity.notFound().build();
        }
    }*/
}
