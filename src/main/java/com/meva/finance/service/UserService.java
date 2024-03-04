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
public class UserService implements InterfaceService<UserDto> {
    private UserRepository userRepository;
    private FamilyRepository familyRepository;

    @Autowired
    public UserService(UserRepository userRepository, FamilyRepository familyRepository) {
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    @Transactional
    public ResponseEntity<UserDto> register(UserDto userDto) {
        User user = userDto.converter();

        Optional<Family> optionalFamily = familyRepository.findById(user.getFamily().getId());

        if (user.getFamily().getId() == 0) {
            return registerNewFamilyAndUser(userDto, user);

        } else if (optionalFamily.isPresent()) {
            userRepository.save(user);
            return ResponseEntity.ok().build();

        } else {
            throw new CustomException("Não existe uma família com esse ID: " + user.getFamily().getId());
        }
    }

    @Transactional
    public ResponseEntity<UserDto> update(UserDto userDto) {

        User user = userDto.converter();

        Optional<User> optionalUser = userRepository.findById(user.getCpf());

        Optional<Family> optionalFamily = familyRepository.findById(user.getFamily().getId());

        if (optionalUser.isPresent()) {
            if (optionalFamily.isPresent()) {
                userRepository.save(user);

                return ResponseEntity.ok(new UserDto(user));
            } else {
                throw new CustomException("Não existe uma família com esse ID: " + user.getFamily().getId());
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<UserDto> registerNewFamilyAndUser(UserDto userDto, User user) {
        Family newFamily = createAndSaveNewFamily(userDto);
        user.setFamily(newFamily);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    private Family createAndSaveNewFamily(UserDto userDto) {
        Family newFamily = new Family();
        newFamily.setDescription(userDto.getFamilyDto().getDescription());
        return familyRepository.save(newFamily);
    }
}
