package com.meva.finance.service;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserDto;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserService userService;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private FamilyRepository familyRepositoryMock;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepositoryMock, familyRepositoryMock);
    }

    @Test
    void testRegisterUserWhenFamilyIdIsZero() {
        // User and Family DTOs
        UserDto userDto = createUserDto(0);
        User user = userDto.converter();

        // Mock behavior
        when(familyRepositoryMock.save(any(Family.class))).thenReturn(new Family());
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

        // Call the method
        ResponseEntity<UserDto> responseEntity = userService.register(userDto);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testRegisterUserWhenFamilyIsPresent() {
        // User and Family DTOs
        UserDto userDto = createUserDto(1);
        User user = userDto.converter();

        // Mock behavior
        when(familyRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(user.getFamily()));

        // Call the method
        ResponseEntity<UserDto> responseEntity = userService.register(userDto);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testException() {
        // User and Family DTOs
        UserDto userDto = createUserDto(1);
        User user = userDto.converter();

        // Mock behavior
        when(familyRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        // Assertions
        try {
            // Call the method
            ResponseEntity<UserDto> responseEntity = userService.register(userDto);
            fail("Não deu a exception");
        } catch (Exception e) {
            assertEquals("Não existe uma família com esse ID: " + user.getFamily().getId(), e.getMessage());
        }
    }

    private UserDto createUserDto(int familyId) {
        UserDto userDto = new UserDto();
        userDto.setCpf("47858747858");
        userDto.setName("Leonardo");
        userDto.setGenre('M');
        userDto.setBirth(new Date());
        userDto.setCity("São Paulo");
        userDto.setState("São Paulo");
        userDto.setFamilyDto(new FamilyDto());
        userDto.getFamilyDto().setId(familyId);
        userDto.getFamilyDto().setDescription("Nino");
        return userDto;
    }
}
