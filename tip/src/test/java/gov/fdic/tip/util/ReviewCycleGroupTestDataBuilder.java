package gov.fdic.tip.util;

import gov.fdic.tip.dto.ReviewCycleGroupDTO;
import gov.fdic.tip.entity.ReviewCycleGroup;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ReviewCycleGroupTestDataBuilder {

    public static ReviewCycleGroup createReviewCycleGroup() {
        return createReviewCycleGroup(1L);
    }

    public static ReviewCycleGroup createReviewCycleGroup(Long id) {
        ReviewCycleGroup entity = new ReviewCycleGroup();
        entity.setReviewCycleGroupId(id);
        entity.setReviewGroupName("Test Review Group " + id);
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
        entity.setCreatedDttm(LocalDateTime.now());
        entity.setUpdatedDttm(LocalDateTime.now());
        return entity;
    }

    public static ReviewCycleGroupDTO createReviewCycleGroupDTO() {
        return createReviewCycleGroupDTO(1L);
    }

    public static ReviewCycleGroupDTO createReviewCycleGroupDTO(Long id) {
        ReviewCycleGroupDTO dto = new ReviewCycleGroupDTO();
        dto.setReviewCycleGroupId(id);
        dto.setReviewGroupName("Test Review Group " + id);
        dto.setReviewCycleId(1L);
        dto.setReviewTypeId(2L);
        dto.setReviewConditionId(3L);
        dto.setRangeStart(100);
        dto.setRangeEnd(500);
        dto.setBooleanState(true);
        dto.setListOfIdis(Arrays.asList("IDI001", "IDI002", "IDI003"));
        dto.setReviewFrequency("QUARTERLY");
        dto.setReviewsPerYear(4);
        dto.setCreatedBy("test-user");
        dto.setUpdatedBy("test-user");
        dto.setCreatedDttm(LocalDateTime.now());
        dto.setUpdatedDttm(LocalDateTime.now());
        return dto;
    }

    public static ReviewCycleGroupDTO createReviewCycleGroupDTOWithoutId() {
        ReviewCycleGroupDTO dto = createReviewCycleGroupDTO(null);
        dto.setReviewCycleGroupId(null);
        return dto;
    }

    public static List<String> createSampleIdis() {
        return Arrays.asList("IDI001", "IDI002", "IDI003", "IDI004", "IDI005");
    }

    public static List<String> createEmptyIdis() {
        return Arrays.asList();
    }

    public static List<String> createSingleIdi() {
        return Arrays.asList("IDI001");
    }

    // Method to create entity with custom IDIs
    public static ReviewCycleGroup createReviewCycleGroupWithIdis(List<String> idis) {
        ReviewCycleGroup entity = createReviewCycleGroup();
        entity.setListOfIdis(idis);
        return entity;
    }

    // Method to create DTO with custom IDIs
    public static ReviewCycleGroupDTO createReviewCycleGroupDTOWithIdis(List<String> idis) {
        ReviewCycleGroupDTO dto = createReviewCycleGroupDTO();
        dto.setListOfIdis(idis);
        return dto;
    }

    // Method to create entity with custom name
    public static ReviewCycleGroup createReviewCycleGroupWithName(String name) {
        ReviewCycleGroup entity = createReviewCycleGroup();
        entity.setReviewGroupName(name);
        return entity;
    }

    // Method to create DTO with custom name
    public static ReviewCycleGroupDTO createReviewCycleGroupDTOWithName(String name) {
        ReviewCycleGroupDTO dto = createReviewCycleGroupDTO();
        dto.setReviewGroupName(name);
        return dto;
    }
}