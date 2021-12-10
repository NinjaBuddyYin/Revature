package com.ers.service;

import com.ers.model.ErsUsers;
import com.ers.repository.UserReimbRepository;
import com.ers.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void loadUserByUsername() {
        ErsUsers user = new ErsUsers();
        user.setErsUserName("admin");
        user.setUserEmail("admin@gmail.com");
        String userEmail = "admin@gmail.com";
        UserRepository localMockRepository = Mockito.mock(UserRepository.class);
        Mockito.when(localMockRepository.findByUserEmail(userEmail)).thenReturn(user);

        ErsUsers users = localMockRepository.findByUserEmail(userEmail);
        assertEquals("admin", users.getErsUserName());
    }
}