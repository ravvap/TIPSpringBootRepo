package gov.fdic.tip.util;

import gov.fdic.tip.dto.ReviewCycleGroupDTO;
import gov.fdic.tip.entity.ReviewCycleGroup;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ReviewCycleGroupTestDataBuilder {

    

    // For new entities (no ID)
    public static ReviewCycleGroup createReviewCycleGroup() {
        ReviewCycleGroup entity = new ReviewCycleGroup();
        // DON'T set ID
        entity.setReviewGroupName("Test Review Group");
        entity.setReviewCycleId(1L);
        entity.setReviewTypeId(2L);
        entity.setReviewConditionId(3L);
        entity.setRangeStart(100);
        entity.setRangeEnd(500);
        entity.setBooleanState(true);
        entity.setListOfIdis(Arrays.asList("IDI001", "IDI002", "IDI003"));
        entity.setReviewFrequency("QUARTERLY");
        entity.setReviewsPerYear(4);
        entity.setCreatedBy("test-user");
        entity.setUpdatedBy("test-user");
         return entity;
    }

 // For existing entities (with ID)
    public static ReviewCycleGroup createReviewCycleGroupWithId(Long id) {
        ReviewCycleGroup entity = createReviewCycleGroup();
        entity.setReviewCycleGroupId(id);
        entity.setReviewGroupName("Test Review Group " + id);
        return entity;
    }

    // For DTOs without ID (create operations)
    public static ReviewCycleGroupDTO createReviewCycleGroupDTOWithoutId() {
        ReviewCycleGroupDTO dto = new ReviewCycleGroupDTO();
        // DON'T set ID
        dto.setReviewGroupName("Test Review Group");
        dto.setReviewCycleId(1L);
        dto.setReviewTypeId(2L);
        dto.setReviewConditionId(3L);
        dto.setRangeStart(100);
        dto.setRangeEnd(500);
        dto.setBooleanState(true);
        dto.setListOfIdis(Arrays.asList("IDI001", "IDI002", "IDI003"));
        dto.setReviewFrequency("QUARTERLY");
        dto.setReviewsPerYear(4);
        return dto;
    }

    // Similar fix for DTO methods
    public static ReviewCycleGroupDTO createReviewCycleGroupDTO() {
        return createReviewCycleGroupDTO(null);
    }
 // For DTOs with ID (response/update operations)
    public static ReviewCycleGroupDTO createReviewCycleGroupDTO(Long id) {
        ReviewCycleGroupDTO dto = createReviewCycleGroupDTOWithoutId();
        dto.setReviewCycleGroupId(id);
        dto.setReviewGroupName("Test Review Group " + id);
        return dto;
    }
    // Helper method for creating entities with specific names
    public static ReviewCycleGroup createReviewCycleGroupWithName(String name) {
        ReviewCycleGroup entity = createReviewCycleGroup();
        entity.setReviewGroupName(name);
        return entity;
    }

    // Helper method for creating entities with specific IDIs
    public static ReviewCycleGroup createReviewCycleGroupWithIdis(List<String> idis) {
        ReviewCycleGroup entity = createReviewCycleGroup();
        entity.setListOfIdis(idis);
        return entity;
    }

    // Helper method for creating entities with specific review cycle ID
    public static ReviewCycleGroup createReviewCycleGroupWithReviewCycleId(Long reviewCycleId) {
        ReviewCycleGroup entity = createReviewCycleGroup();
        entity.setReviewCycleId(reviewCycleId);
        return entity;
    }

    // Helper method for creating entities with specific boolean state
    public static ReviewCycleGroup createReviewCycleGroupWithBooleanState(Boolean booleanState) {
        ReviewCycleGroup entity = createReviewCycleGroup();
        entity.setBooleanState(booleanState);
        return entity;
    }
    
    public static List<String> createSampleIdis() {
        return Arrays.asList("IDI001", "IDI002", "IDI003", "IDI004", "IDI005");
    }


    // ... other existing methods
}