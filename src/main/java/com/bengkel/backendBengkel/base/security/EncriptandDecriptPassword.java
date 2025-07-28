package com.bengkel.backendBengkel.base.security;

import org.springframework.http.HttpStatus;

import com.bengkel.backendBengkel.base.exception.CostumeResponse;

public class EncriptandDecriptPassword {

    public static String encriptPassword(String Password) {
        return BCrypt.hashpw(Password, BCrypt.gensalt());
    }

    public static Boolean checkPassword(String Password, String passDatabase) {

        if (BCrypt.checkpw(Password, passDatabase)) {
            return true;
        } else {
            throw new CostumeResponse(HttpStatus.BAD_REQUEST, "Password didn't match");
        }
    }

}
