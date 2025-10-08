package gov.fdic.tip.dto;

import gov.fdic.tip.enums.GroupCriteriaType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author Your Name
 * @Project TIP
 * @Module Review Group Criteria
 * @Date 2024-01-15
 * DTO for Review Group Criteria operations.
 */
@Schema(description = "Data Transfer Object for Review Group Criteria operations")
public class ReviewGroupCriteriaDTO {

    @Schema(description = "Unique identifier of the review group criteria", 
            example = "1", 
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long reviewGroupCriteriaId;

    @Schema(description = "Name of the criteria", 
            example = "Asset Quality Review", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Criteria name is required")
    @Size(max = 255, message = "Criteria name must not exceed 255 characters")
    private String criteriaName;

    @Schema(description = "Type of criteria", 
            example = "FINANCIAL", 
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"FINANCIAL", "OPERATIONAL", "COMPLIANCE", "TECHNICAL", "SECURITY", 
                             "PERFORMANCE", "RISK_BASED", "QUALITY", "QUANTITATIVE", "QUALITATIVE"})
    @NotNull(message = "Criteria type is required")
    private GroupCriteriaType criteriaType;

    @Schema(description = "User who created the record", 
            example = "system-admin", 
            accessMode = Schema.AccessMode.READ_ONLY)
    private String createdBy;

    @Schema(description = "User who last updated the record", 
            example = "system-admin", 
            accessMode = Schema.AccessMode.READ_ONLY)
    private String updatedBy;

    // Constructors
    public ReviewGroupCriteriaDTO() {
    }

    public ReviewGroupCriteriaDTO(Long reviewGroupCriteriaId, String criteriaName, GroupCriteriaType criteriaType) {
        this.reviewGroupCriteriaId = reviewGroupCriteriaId;
        this.criteriaName = criteriaName;
        this.criteriaType = criteriaType;
    }

    // Getters and Setters
    public Long getReviewGroupCriteriaId() {
        return reviewGroupCriteriaId;
    }

    public void setReviewGroupCriteriaId(Long reviewGroupCriteriaId) {
        this.reviewGroupCriteriaId = reviewGroupCriteriaId;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public GroupCriteriaType getCriteriaType() {
        return criteriaType;
    }

    public void setCriteriaType(GroupCriteriaType criteriaType) {
        this.criteriaType = criteriaType;
    }

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

    @Override
    public String toString() {
        return "ReviewGroupCriteriaDTO{" +
                "reviewGroupCriteriaId=" + reviewGroupCriteriaId +
                ", criteriaName='" + criteriaName + '\'' +
                ", criteriaType=" + criteriaType +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}