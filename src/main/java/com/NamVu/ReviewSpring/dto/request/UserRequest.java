package com.NamVu.ReviewSpring.dto.request;

import com.NamVu.ReviewSpring.enums.Gender;
import com.NamVu.ReviewSpring.enums.UserStatus;
import com.NamVu.ReviewSpring.enums.UserType;
import com.NamVu.ReviewSpring.validator.EnumPattern;
import com.NamVu.ReviewSpring.validator.EnumValue;
import com.NamVu.ReviewSpring.validator.GenderSubset;
import com.NamVu.ReviewSpring.validator.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
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
}
