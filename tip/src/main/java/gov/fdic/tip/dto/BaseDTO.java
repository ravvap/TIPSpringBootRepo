package gov.fdic.tip.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author Your Name
 * @Project TIP
 * @Module Common DTO
 * @Date 2024-01-15
 * Base DTO class containing common audit fields for all DTOs.
 */
@Schema(description = "Base DTO containing common audit fields")
public abstract class BaseDTO {

    @Schema(description = "User who created the record", 
            example = "system-admin", 
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @Schema(description = "User who last updated the record", 
            example = "system-admin", 
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updatedBy;

    @Schema(description = "Date and time when the record was created", 
            example = "2024-01-15T10:30:00", 
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdDttm;

    @Schema(description = "Date and time when the record was last updated", 
            example = "2024-01-15T10:30:00", 
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedDttm;

    // Constructors
    public BaseDTO() {
    }

    public BaseDTO(String createdBy, String updatedBy, LocalDateTime createdDttm, LocalDateTime updatedDttm) {
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdDttm = createdDttm;
        this.updatedDttm = updatedDttm;
    }

    // Getters and Setters
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedDttm() {
        return createdDttm;
    }

    public void setCreatedDttm(LocalDateTime createdDttm) {
        this.createdDttm = createdDttm;
    }

    public LocalDateTime getUpdatedDttm() {
        return updatedDttm;
    }

    public void setUpdatedDttm(LocalDateTime updatedDttm) {
        this.updatedDttm = updatedDttm;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdDttm=" + createdDttm +
                ", updatedDttm=" + updatedDttm +
                '}';
    }
}