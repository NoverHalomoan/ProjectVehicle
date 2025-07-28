package com.bengkel.backendBengkel.base.DTO;

import java.util.List;

public record DTOUpdateProfile(
        String nameEmployee,
        float salary,
        String password,
        String email,
        String profilepic,
        Boolean isactive,
        List<String> role
        ) {

}
