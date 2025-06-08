package com.app.ecom_application.dto;

import com.app.ecom_application.model.enums.UserRole;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDto address;




}
