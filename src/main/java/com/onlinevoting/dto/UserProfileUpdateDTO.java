package com.onlinevoting.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateDTO {
    
    // User basic information
    private Long userId;
    
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;
    
    @Size(max = 50, message = "Middle name cannot exceed 50 characters")
    private String middleName;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String emailId;
    
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNo;
    
    @NotNull(message = "Date of birth is required")
    private String dob;
    
    @NotNull(message = "Aadhar number is required")
    @Digits(integer = 12, fraction = 0, message = "Aadhar number must be 12 digits")
    private String aadharNumber;
    
    @Size(max = 512, message = "Document URL cannot exceed 512 characters")
    private String docsUrl;
    
    @Size(max = 20, message = "Status cannot exceed 20 characters")
    private String status;
    
    // Role information
    @NotNull(message = "Role ID is required")
    private Long roleId;
    
    // Address information
    private Long addressId;
    
    @Size(max = 255, message = "Street cannot exceed 255 characters")
    private String street;
    
    @NotNull(message = "Country ID is required")
    private Long countryId;
    
    @NotNull(message = "State ID is required")
    private Long stateId;
    
    @NotNull(message = "City ID is required")
    private Long cityId;
    
    @NotBlank(message = "Zip code is required")
    @Size(max = 10, message = "Zip code cannot exceed 10 characters")
    private String zipCode;
    
    // Additional fields for display purposes (read-only)
    private String roleName;
    private String countryName;
    private String stateName;
    private String cityName;
    private String profileImageUrl;
    
    /**
     * Constructor with all required fields for profile update
     */
    public UserProfileUpdateDTO(Long userId, String firstName, String lastName, String emailId, 
                               String phoneNo, String dob, String aadharNumber, Long roleId, 
                               String street, Long countryId, Long stateId, Long cityId, String zipCode) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.aadharNumber = aadharNumber;
        this.roleId = roleId;
        this.street = street;
        this.countryId = countryId;
        this.stateId = stateId;
        this.cityId = cityId;
        this.zipCode = zipCode;
    }
    
    /**
     * Get full name by combining first, middle, and last names
     */
    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        fullName.append(firstName);
        if (middleName != null && !middleName.trim().isEmpty()) {
            fullName.append(" ").append(middleName);
        }
        fullName.append(" ").append(lastName);
        return fullName.toString();
    }
    
    /**
     * Get complete address as a formatted string
     */
    public String getFormattedAddress() {
        StringBuilder address = new StringBuilder();
        if (street != null && !street.trim().isEmpty()) {
            address.append(street).append(", ");
        }
        if (cityName != null) {
            address.append(cityName).append(", ");
        }
        if (stateName != null) {
            address.append(stateName).append(", ");
        }
        if (countryName != null) {
            address.append(countryName).append(" ");
        }
        if (zipCode != null) {
            address.append(zipCode);
        }
        return address.toString().replaceAll(", $", ""); // Remove trailing comma and space
    }
    
    @Override
    public String toString() {
        return "UserProfileUpdateDTO{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", roleId=" + roleId +
                ", countryId=" + countryId +
                ", stateId=" + stateId +
                ", cityId=" + cityId +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}