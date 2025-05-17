package com.NamVu.ReviewSpring.dto.request;

import java.io.Serializable;

public class UserRequest implements Serializable {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public UserRequest() {
    }

    public UserRequest(String lastName, String firstName, String phone, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
