package gov.fdic.tip.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Base entity class to be extended by all entities for common fields.
 */

@MappedSuperclass
public abstract class BaseEntity {
     
	@PrePersist
	 protected void onCreate() {
	        createdDttm = LocalDateTime.now();
	        updatedDttm = LocalDateTime.now();
	}
	@PreUpdate
	protected void onUpdate() {
		updatedDttm = LocalDateTime.now();
	}
    
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDttm;


    @Column(name = "updated_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDttm;
    
     
    
    // Getters and setters
    
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedDttm() { return createdDttm; }
    public void setCreatedDttm(LocalDateTime createdDttm) { this.createdDttm = createdDttm; }

    public LocalDateTime getUpdatedDttm() { return updatedDttm; }
    public void setUpdatedDttm(LocalDateTime updatedDttm) { this.updatedDttm = updatedDttm; }
    
}

