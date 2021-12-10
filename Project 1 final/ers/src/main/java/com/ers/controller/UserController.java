package com.ers.controller;

import com.ers.configs.JwtUtility;
import com.ers.dto.LoginRequest;
import com.ers.dto.ReimbursementRequest;
import com.ers.dto.Response;
import com.ers.dto.UserAuthRequest;
import com.ers.model.ErsUsers;
import com.ers.model.UserReimbursement;
import com.ers.service.CustomUserDetails;
import com.ers.service.ReimbursementService;
import com.ers.service.UserAuthService;
import com.ers.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    static final Logger logger = LogManager.getLogger(UserController.class.getName());
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
    public static final String USER_LOGIN_SUCCESSFUL = "User login successful";
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private ReimbursementService reimbursementService;

    @PostMapping("/registration")
    public ResponseEntity<Response> userRegister(@Valid @RequestBody UserAuthRequest userAuthRequest){
        logger.info("register start");
        Response response = new Response();
        try{
            ErsUsers user =  userAuthService.userRegister(userAuthRequest);
            response.setMessage(USER_REGISTERED_SUCCESSFULLY);
            response.setUser(user);
            logger.info("register success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            logger.error("register fail");
            return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> userLogin(@RequestBody LoginRequest loginRequest){
        logger.info("login start");
        Response response = new Response();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
            if(userDetails != null){
                final String token =
                        jwtUtility.generateToken(userDetails);
                response.setUserId(((CustomUserDetails) userDetails).getUser().getId());
                response.setUserName(((CustomUserDetails) userDetails).getUser().getErsUserName());
                response.setEmail(((CustomUserDetails) userDetails).getUser().getUserEmail());
                response.setRole(((CustomUserDetails) userDetails).getUser().getUserRole());
                response.setToken(token);

            }

            response.setMessage(USER_LOGIN_SUCCESSFUL);
            logger.info("login success");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (BadCredentialsException e){
            response.setMessage(e.getMessage());
            logger.error("login error");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
    }

}
