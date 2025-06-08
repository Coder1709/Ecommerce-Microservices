package com.app.ecom_application.dto;


import lombok.Data;


@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDto address;

}
