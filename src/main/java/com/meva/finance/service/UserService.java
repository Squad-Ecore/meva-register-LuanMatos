package com.meva.finance.service;

import com.meva.finance.dto.UserDto;
import com.meva.finance.exception.CustomException;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FamilyRepository familyRepository;

    @Transactional
    public ResponseEntity<UserDto> cadastrar(UserDto userDto) {
        User user = userDto.converter();

        Optional<Family> optionalFamily = familyRepository.findById(user.getFamily().getId());

        if (user.getFamily().getId() == 0) {
            //Cria uma nova família
            Family newFamily = new Family();
            newFamily.setDescription(userDto.getFamilyDto().getDescription());
            familyRepository.save(newFamily);

            //Associa o usuário a nova família criada
            user.setFamily(newFamily);

            //Salva o novo usuário
            userRepository.save(user);
            return ResponseEntity.ok().build();

        } else if (optionalFamily.isPresent()) {
            userRepository.save(user);
            return ResponseEntity.ok().build();

        } else {
            throw new CustomException("Não existe uma família com esse ID: " + user.getFamily().getId());
        }
    }
}
