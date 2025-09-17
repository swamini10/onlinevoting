package com.onlinevoting.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserOtpDetails extends AuditDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer otp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetail userDetail;

    private Long expiryTime;

    private boolean isOtpUsed;

    public UserOtpDetails() {}

    public UserOtpDetails(UserDetail userDetail, Integer otp) {
        this.userDetail = userDetail;
        this.otp = otp;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getOtp() {
        return otp;
    }
    public void setOtp(Integer otp) {
        this.otp = otp;
    }
    public UserDetail getUserDetail() {
        return userDetail;
    }
    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public Long getExpiryTime() {
        return expiryTime;
    }
    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isOtpUsed() {
        return isOtpUsed;
    }
    public void setOtpUsed(boolean isOtpUsed) {
        this.isOtpUsed = isOtpUsed;
    }

}
