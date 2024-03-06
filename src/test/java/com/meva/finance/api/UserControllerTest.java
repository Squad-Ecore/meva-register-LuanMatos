package com.meva.finance.api;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserDto;
import com.meva.finance.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.userController = new UserController(userServiceMock);
    }

    @Test
    void testRegisterSuccessfully() {
        // User and Response Entity
        UserDto userDto = createUserDto();
        ResponseEntity<UserDto> userDtoResponseEntity = ResponseEntity.ok().build();

        // Mock behavior
        when(userServiceMock.register(userDto)).thenReturn(userDtoResponseEntity);

        // Call the method
        ResponseEntity<UserDto> responseEntity = userController.register(userDto);

        // Verifications
        verify(userServiceMock, times(1)).register(userDto);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testUpdateSuccessfully() {
        // User and Response Entity
        UserDto userDto = createUserDto();

        // Mock behavior
        when(userServiceMock.update(userDto)).thenReturn(ResponseEntity.ok(userDto));
        //when(userServiceMock.update(any(UserDto.class))).thenReturn(userDtoResponseEntity);

        // Call the method
        ResponseEntity<UserDto> responseEntity = userController.update(userDto);

        // Verifications
        verify(userServiceMock, times(1)).update(userDto);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void testSearchByCpfSuccessfully() {
        // User and String
        UserDto userDto = createUserDto();
        String cpf = "47858747858";

        // Mock behavior
        when(userServiceMock.searchByCpf(cpf)).thenReturn(ResponseEntity.ok(userDto));

        // Call the method
        ResponseEntity<UserDto> responseEntity = userController.searchByCpf(cpf);

        // Verifications
        verify(userServiceMock, times(1)).searchByCpf(cpf);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void testDeleteSuccessfully() {
        // String
        String cpf = "47858747858";

        // Mock behavior
        when(userServiceMock.delete(cpf)).thenReturn(ResponseEntity.ok().build());

        // Call the method
        ResponseEntity<?> responseEntity = userController.delete(cpf);

        // Verifications
        verify(userServiceMock, times(1)).delete(cpf);

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setCpf("47858747858");
        userDto.setName("Leonardo");
        userDto.setGenre('M');
        userDto.setBirth(new Date());
        userDto.setCity("São Paulo");
        userDto.setState("São Paulo");
        userDto.setFamilyDto(new FamilyDto());
        userDto.getFamilyDto().setId(1);
        userDto.getFamilyDto().setDescription("Nino");
        return userDto;
    }
}
