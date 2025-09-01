package com.onlinevoting.model;
import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@MappedSuperclass
@Getter
public class AuditDetail {
    private String createdBy;
    private LocalDateTime createdDate;
    private String updateBy;
    private LocalDateTime updatedDate;
    private Boolean isActive;
    @PrePersist
    protected void onCreate() {
        this.createdBy = "system"; // or get from security context
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.isActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateBy = "system"; // or get from security context
        this.updatedDate = LocalDateTime.now();
    }
}