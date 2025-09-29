package gov.fdic.tip.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Data Transfer Object for Review Cycle Group.
 */

public class ReviewCycleGroupDTO {

    @JsonProperty("reviewCycleGroupId")
    private Long reviewCycleGroupId;

    @NotBlank(message = "Review group name is required")
    @JsonProperty("reviewGroupName")
    private String reviewGroupName;

    @NotNull(message = "Review cycle ID is required")
    @JsonProperty("reviewCycleId")
    private Long reviewCycleId;

    @NotNull(message = "Review type ID is required")
    @JsonProperty("reviewTypeId")
    private Long reviewTypeId;

    @JsonProperty("reviewConditionId")
    private Long reviewConditionId;

    @JsonProperty("rangeStart")
    private Integer rangeStart;

    @JsonProperty("rangeEnd")
    private Integer rangeEnd;

    @JsonProperty("booleanState")
    private Boolean booleanState;

    @JsonProperty("listOfIdis")
    private List<String> listOfIdis;

    @JsonProperty("reviewFrequency")
    private String reviewFrequency;

    @JsonProperty("reviewsPerYear")
    private Integer reviewsPerYear;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createdDttm")
    private LocalDateTime createdDttm;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("updatedBy")
    private String updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("updatedDttm")
    private LocalDateTime updatedDttm;

    // Validation groups
    public interface CreateGroup {}
    public interface UpdateGroup {}

    // Constructors
    public ReviewCycleGroupDTO() {}

    public ReviewCycleGroupDTO(String reviewGroupName, Long reviewCycleId, Long reviewTypeId) {
        this.reviewGroupName = reviewGroupName;
        this.reviewCycleId = reviewCycleId;
        this.reviewTypeId = reviewTypeId;
    }

    // Getters and Setters
    public Long getReviewCycleGroupId() { return reviewCycleGroupId; }
    public void setReviewCycleGroupId(Long reviewCycleGroupId) { this.reviewCycleGroupId = reviewCycleGroupId; }

    public String getReviewGroupName() { return reviewGroupName; }
    public void setReviewGroupName(String reviewGroupName) { this.reviewGroupName = reviewGroupName; }

    public Long getReviewCycleId() { return reviewCycleId; }
    public void setReviewCycleId(Long reviewCycleId) { this.reviewCycleId = reviewCycleId; }

    public Long getReviewTypeId() { return reviewTypeId; }
    public void setReviewTypeId(Long reviewTypeId) { this.reviewTypeId = reviewTypeId; }

    public Long getReviewConditionId() { return reviewConditionId; }
    public void setReviewConditionId(Long reviewConditionId) { this.reviewConditionId = reviewConditionId; }

    public Integer getRangeStart() { return rangeStart; }
    public void setRangeStart(Integer rangeStart) { this.rangeStart = rangeStart; }

    public Integer getRangeEnd() { return rangeEnd; }
    public void setRangeEnd(Integer rangeEnd) { this.rangeEnd = rangeEnd; }

    public Boolean getBooleanState() { return booleanState; }
    public void setBooleanState(Boolean booleanState) { this.booleanState = booleanState; }

    public List<String> getListOfIdis() { return listOfIdis; }
    public void setListOfIdis(List<String> listOfIdis) { this.listOfIdis = listOfIdis; }

    public String getReviewFrequency() { return reviewFrequency; }
    public void setReviewFrequency(String reviewFrequency) { this.reviewFrequency = reviewFrequency; }

    public Integer getReviewsPerYear() { return reviewsPerYear; }
    public void setReviewsPerYear(Integer reviewsPerYear) { this.reviewsPerYear = reviewsPerYear; }

    public LocalDateTime getCreatedDttm() { return createdDttm; }
    public void setCreatedDttm(LocalDateTime createdDttm) { this.createdDttm = createdDttm; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public LocalDateTime getUpdatedDttm() { return updatedDttm; }
    public void setUpdatedDttm(LocalDateTime updatedDttm) { this.updatedDttm = updatedDttm; }
}