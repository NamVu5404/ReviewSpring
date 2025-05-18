package com.NamVu.ReviewSpring.dto.response;

import java.io.Serializable;

public class UserResponse implements Serializable {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public UserResponse() {
    }

    public UserResponse(String lastName, String firstName, String phone, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
