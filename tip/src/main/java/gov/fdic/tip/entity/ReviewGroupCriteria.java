package gov.fdic.tip.entity;

import gov.fdic.tip.enums.GroupCriteriaType;
import jakarta.persistence.*;

/**
 * @author Your Name
 * @Project TIP
 * @Module Review Group Criteria
 * @Date 2024-01-15
 * Entity class representing Review Group Criteria.
 */
@Entity
@Table(name = "review_group_criteria")
public class ReviewGroupCriteria extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_group_criteria_id")
    private Long reviewGroupCriteriaId;

    @Column(name = "criteria_name", nullable = false, length = 255)
    private String criteriaName;

    @Enumerated(EnumType.STRING)
    @Column(name = "criteria_type", nullable = false, length = 50)
    private GroupCriteriaType criteriaType;

    // Constructors
    public ReviewGroupCriteria() {
    }

    public ReviewGroupCriteria(String criteriaName, GroupCriteriaType criteriaType) {
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

    public String toString() {
        return "ReviewGroupCriteria{" +
                "reviewGroupCriteriaId=" + reviewGroupCriteriaId +
                ", criteriaName='" + criteriaName + '\'' +
                ", criteriaType=" + criteriaType +
                '}';
    }
}