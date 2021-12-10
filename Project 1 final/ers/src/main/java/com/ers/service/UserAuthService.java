package com.ers.service;

import com.ers.dto.UserAuthRequest;
import com.ers.model.ErsUsers;
import com.ers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ErsUsers userRegister(UserAuthRequest userAuthRequest) {
        ErsUsers user = new ErsUsers();
        user.setErsUserName(userAuthRequest.getErsUserName());
        user.setErsPassword(passwordEncoder.encode(userAuthRequest.getErsPassword()));
        user.setUserFirstName(userAuthRequest.getUserFirstName());
        user.setUserLastName(userAuthRequest.getUserLastName());
        user.setUserEmail(userAuthRequest.getUserEmail());
        user.setUserRole(userAuthRequest.getUserRole());
        return userRepository.save(user);
    }
}
