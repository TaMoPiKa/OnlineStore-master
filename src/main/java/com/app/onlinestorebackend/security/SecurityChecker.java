package com.app.onlinestorebackend.security;


import com.app.onlinestorebackend.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class SecurityChecker {


    private final UserServices userServices;

    public boolean isValidRequest(String username, String password){
        return userServices.usernameAndPasswordChecker(username, password);
    }


}
