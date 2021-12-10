package com.ers.service;

import com.ers.model.ErsUsers;
import com.ers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    public static final String USER_NOT_FOUND_FOR_THIS_EMAIL = "User not found for this email";
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        ErsUsers user = userRepository.findByUserEmail(userEmail);

        if(user == null){
            throw new UsernameNotFoundException(USER_NOT_FOUND_FOR_THIS_EMAIL);
        }
        return new CustomUserDetails(user);

    }

}
