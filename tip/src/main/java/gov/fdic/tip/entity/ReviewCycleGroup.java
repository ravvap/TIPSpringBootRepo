package gov.fdic.tip.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;


/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Entity class representing a Review Cycle Group.
 */

@Entity
@Table(name = "review_cycle_group")
public class ReviewCycleGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_cycle_group_id")
    private Long reviewCycleGroupId;

    @Column(name = "review_group_name", nullable = false, length = 255)
    private String reviewGroupName;

    @Column(name = "review_cycle_id")
    private Long reviewCycleId;

    @Column(name = "review_type_id")
    private Long reviewTypeId;

    @Column(name = "review_condition_id")
    private Long reviewConditionId;

    @Column(name = "range_start")
    private Integer rangeStart;

    @Column(name = "range_end")
    private Integer rangeEnd;

    @Column(name = "boolean_state")
    private Boolean booleanState;

    @ElementCollection
    @CollectionTable(name = "review_cycle_group_idis", joinColumns = @JoinColumn(name = "review_cycle_group_id"))
    @Column(name = "idi_value")
    private List<String> listOfIdis;

    @Column(name = "review_frequency", length = 100)
    private String reviewFrequency;

    @Column(name = "reviews_per_year")
    private Integer reviewsPerYear;

    

    // Constructors
    public ReviewCycleGroup() {
     
    }

    public ReviewCycleGroup(String reviewGroupName, Long reviewCycleId, Long reviewTypeId) {
        this();
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

   
}