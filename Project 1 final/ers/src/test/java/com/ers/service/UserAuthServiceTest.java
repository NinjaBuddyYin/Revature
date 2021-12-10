package com.ers.service;

import com.ers.model.ErsUsers;
import com.ers.repository.UserReimbRepository;
import com.ers.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthServiceTest {

    @Test
    void userRegister() {
        ErsUsers user = new ErsUsers();
        user.setUserEmail("admin@gmail.com");
        user.setErsUserName("admin");

        UserRepository localMockRepository = Mockito.mock(UserRepository.class);
        Mockito.when(localMockRepository.save(user)).thenReturn(user);

        assertEquals(user,localMockRepository.save(user));
    }
}