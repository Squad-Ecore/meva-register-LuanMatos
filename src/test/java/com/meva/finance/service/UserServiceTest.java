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
import static org.mockito.Mockito.*;

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
        // User DTOs
        UserDto userDto = createUserDto(0);
        User user = userDto.converter();

        // Mock behavior
        when(familyRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());
        //when(familyRepositoryMock.findById(user.getFamily().getId())).thenReturn(Optional.empty());
        when(familyRepositoryMock.save(any(Family.class))).thenReturn(user.getFamily());
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

        // Call the method
        ResponseEntity<UserDto> responseEntity = userService.register(userDto);

        //Verifications
        verify(familyRepositoryMock, times(1)).save(any(Family.class));
        verify(userRepositoryMock, times(1)).save(any(User.class));

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testRegisterUserWhenFamilyIsPresent() {
        // User DTOs
        UserDto userDto = createUserDto(1);
        User user = userDto.converter();

        // Mock behavior
        when(familyRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(user.getFamily()));
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

        // Call the method
        ResponseEntity<UserDto> responseEntity = userService.register(userDto);

        //Verifications
        verify(userRepositoryMock, times(1)).save(user);
        verify(familyRepositoryMock, never()).save(any(Family.class));

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testRegisterException() {
        // User DTOs
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
            verify(userRepositoryMock, never()).save(any(User.class));
        }
    }

    @Test
    void testUpdateWhenUserAndFamilyIsPresent() {
        // User DTOs
        UserDto userDto = createUserDto(1);
        User user = userDto.converter();

        // Mock behavior
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(familyRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(user.getFamily()));
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

        // Call the method
        ResponseEntity<UserDto> responseEntity = userService.update(userDto);

        //Verifications
        verify(userRepositoryMock, times(1)).findById(user.getCpf());
        verify(familyRepositoryMock, times(1)).findById(user.getFamily().getId());
        verify(userRepositoryMock, times(1)).save(user);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(ResponseEntity.ok(new UserDto(user)), responseEntity);
    }

    @Test
    void testUpdateWhenUserIsPresentButFamilyNot() {
        // User DTOs
        UserDto userDto = createUserDto(1);
        User user = userDto.converter();

        // Mock behavior
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(familyRepositoryMock.findById(anyInt())).thenReturn(Optional.empty());

        // Assertions
        try {
            // Call the method
            ResponseEntity<UserDto> responseEntity = userService.update(userDto);
            fail("Não deu a exception");
        } catch (Exception e) {
            assertEquals("Não existe uma família com esse ID: " + user.getFamily().getId(), e.getMessage());
            verify(userRepositoryMock, never()).save(any(User.class));
            verify(familyRepositoryMock, never()).save(any(Family.class));
        }
    }

    @Test
    void testUpdateWhenUserDoesNotExist() {
        // User DTOs
        UserDto userDto = createUserDto(1);
        User user = userDto.converter();

        // Mock behavior
        when(userRepositoryMock.findById(any())).thenReturn(Optional.empty());

        // Call the method
        ResponseEntity<UserDto> responseEntity = userService.update(userDto);

        //Verifications
        verify(userRepositoryMock, never()).save(any(User.class));
        verify(familyRepositoryMock, never()).save(any(Family.class));

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
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
