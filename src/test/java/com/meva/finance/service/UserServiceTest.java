package com.meva.finance.service;

import com.meva.finance.dto.FamilyDto;
import com.meva.finance.dto.UserDto;
import com.meva.finance.repository.FamilyRepository;
import com.meva.finance.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.Assert.assertEquals;

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

    
}
