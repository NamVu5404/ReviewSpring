package com.NamVu.ReviewSpring.dto.request;

import com.NamVu.ReviewSpring.model.User;
import lombok.Getter;

@Getter
public class AddressRequest {
    private String apartmentNumber;
    private String floor;
    private String building;
    private String streetNumber;
    private String street;
    private String city;
    private String country;
    private User user;
    private Integer addressType;
}
