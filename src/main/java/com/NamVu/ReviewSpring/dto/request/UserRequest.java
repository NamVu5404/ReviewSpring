package com.NamVu.ReviewSpring.dto.request;

import com.NamVu.ReviewSpring.enums.Gender;
import com.NamVu.ReviewSpring.enums.UserStatus;
import com.NamVu.ReviewSpring.enums.UserType;
import com.NamVu.ReviewSpring.util.EnumPattern;
import com.NamVu.ReviewSpring.util.EnumValue;
import com.NamVu.ReviewSpring.util.GenderSubset;
import com.NamVu.ReviewSpring.util.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserRequest implements Serializable {
    @NotBlank(message = "firstName must be not blank")
    private String firstName;

    @NotNull(message = "lastName must be not null")
    private String lastName;

    // @Email(message = "email invalid format")
    @PhoneNumber
    private String phone;

    @Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
    private String email;

    @NotNull(message = "dateOfBirth must by not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    @NotEmpty
    private List<String> permissions;

    @EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
    private UserStatus status;

    @GenderSubset(anyOf = {Gender.MALE, Gender.FEMALE, Gender.OTHER})
    private Gender gender;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)
    private String type;

    public UserRequest() {
    }

    public UserRequest(String firstName, String lastName, String phone, String email, Date dateOfBirth, List<String> permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.permissions = permissions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
