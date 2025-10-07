package com.onlinevoting.dto;

import java.sql.Date;

import com.onlinevoting.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO  {
    private String firstName;
    private String lastName;
    private String middleName;
    private String emailId;
    private String phoneNo;
    private Long addressId;
    private Address address;
    private Date dob;
    private Long aadharNumber;
}