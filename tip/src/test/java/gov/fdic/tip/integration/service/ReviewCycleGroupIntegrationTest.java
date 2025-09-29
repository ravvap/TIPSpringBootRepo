package gov.fdic.tip.integration.service;

import gov.fdic.tip.dto.ReviewCycleGroupDTO;
import gov.fdic.tip.entity.ReviewCycleGroup;
import gov.fdic.tip.exception.BusinessException;
import gov.fdic.tip.exception.ResourceNotFoundException;
import gov.fdic.tip.repository.ReviewCycleGroupRepository;
import gov.fdic.tip.service.ReviewCycleGroupService;
import gov.fdic.tip.util.ReviewCycleGroupTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReviewCycleGroupServiceIntegrationTest {

    @Autowired
    private ReviewCycleGroupService reviewCycleGroupService;

    @Autowired
    private ReviewCycleGroupRepository reviewCycleGroupRepository;

    private ReviewCycleGroupDTO testReviewCycleGroupDTO;
    private final String CREATED_BY = "integration-test-user";

    @BeforeEach
    void setUp() {
        // Clear any existing data
        reviewCycleGroupRepository.deleteAll();
        
        testReviewCycleGroupDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
    }

    @Test
    @DisplayName("Should perform full CRUD operations with list of IDIs integration test")
    void shouldPerformFullCrudOperationsWithListOfIdis() {
        // Create
        ReviewCycleGroupDTO createdDTO = reviewCycleGroupService.create(testReviewCycleGroupDTO, CREATED_BY);
        assertThat(createdDTO.getReviewCycleGroupId()).isNotNull();
        assertThat(createdDTO.getListOfIdis()).hasSize(3);
        assertThat(createdDTO.getListOfIdis()).containsExactly("IDI001", "IDI002", "IDI003");
        assertThat(createdDTO.getCreatedBy()).isEqualTo(CREATED_BY);
        assertThat(createdDTO.getUpdatedBy()).isEqualTo(CREATED_BY);

        // Read
        ReviewCycleGroupDTO foundDTO = reviewCycleGroupService.findById(createdDTO.getReviewCycleGroupId());
        assertThat(foundDTO).isNotNull();
        assertThat(foundDTO.getReviewGroupName()).isEqualTo("Test Review Group null");
        assertThat(foundDTO.getListOfIdis()).containsExactly("IDI001", "IDI002", "IDI003");

        // Update with new IDIs
        List<String> updatedIdis = Arrays.asList("IDI004", "IDI005", "IDI006");
        foundDTO.setListOfIdis(updatedIdis);
        foundDTO.setReviewGroupName("Updated Integration Test Group");

        ReviewCycleGroupDTO updatedDTO = reviewCycleGroupService.update(
                foundDTO.getReviewCycleGroupId(), foundDTO, "updated-user");
        assertThat(updatedDTO.getReviewGroupName()).isEqualTo("Updated Integration Test Group");
        assertThat(updatedDTO.getListOfIdis()).isEqualTo(updatedIdis);
        assertThat(updatedDTO.getUpdatedBy()).isEqualTo("updated-user");

        // Find by IDI
        List<ReviewCycleGroupDTO> groupsWithIdi = reviewCycleGroupService.findByListOfIdisContaining("IDI005");
        assertThat(groupsWithIdi).isNotEmpty();
        assertThat(groupsWithIdi.get(0).getReviewCycleGroupId()).isEqualTo(createdDTO.getReviewCycleGroupId());

        // Delete
        reviewCycleGroupService.delete(createdDTO.getReviewCycleGroupId());
        
        Optional<ReviewCycleGroup> deletedEntity = reviewCycleGroupRepository.findById(createdDTO.getReviewCycleGroupId());
        assertThat(deletedEntity).isEmpty();
    }

    @Test
    @DisplayName("Should throw BusinessException when creating duplicate ReviewCycleGroup name")
    void shouldThrowExceptionWhenCreatingDuplicateName() {
        // Given
        ReviewCycleGroupDTO firstDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        firstDTO.setReviewGroupName("Duplicate Name");

        ReviewCycleGroupDTO secondDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        secondDTO.setReviewGroupName("Duplicate Name");

        // When
        reviewCycleGroupService.create(firstDTO, CREATED_BY);

        // Then
        assertThatThrownBy(() -> reviewCycleGroupService.create(secondDTO, CREATED_BY))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("ReviewCycleGroup with name already exists");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existent ReviewCycleGroup")
    void shouldThrowExceptionWhenUpdatingNonExistentReviewCycleGroup() {
        // Given
        ReviewCycleGroupDTO updateDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTO(999L);

        // When & Then
        assertThatThrownBy(() -> reviewCycleGroupService.update(999L, updateDTO, CREATED_BY))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ReviewCycleGroup not found with id: 999");
    }

    @Test
    @DisplayName("Should find all ReviewCycleGroups with pagination")
    void shouldFindAllReviewCycleGroupsWithPagination() {
        // Given
        ReviewCycleGroupDTO dto1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto1.setReviewGroupName("Group 1");
        ReviewCycleGroupDTO dto2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto2.setReviewGroupName("Group 2");
        ReviewCycleGroupDTO dto3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto3.setReviewGroupName("Group 3");

        reviewCycleGroupService.create(dto1, CREATED_BY);
        reviewCycleGroupService.create(dto2, CREATED_BY);
        reviewCycleGroupService.create(dto3, CREATED_BY);

        // When
        Pageable pageable = PageRequest.of(0, 2);
        Page<ReviewCycleGroupDTO> result = reviewCycleGroupService.findAllPaginated(pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should search ReviewCycleGroups by name with pagination")
    void shouldSearchReviewCycleGroupsByName() {
        // Given
        ReviewCycleGroupDTO dto1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto1.setReviewGroupName("Financial Review Group");
        ReviewCycleGroupDTO dto2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto2.setReviewGroupName("Technical Review Group");
        ReviewCycleGroupDTO dto3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto3.setReviewGroupName("Security Review Group");

        reviewCycleGroupService.create(dto1, CREATED_BY);
        reviewCycleGroupService.create(dto2, CREATED_BY);
        reviewCycleGroupService.create(dto3, CREATED_BY);

        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReviewCycleGroupDTO> result = reviewCycleGroupService.searchByGroupName("Review", pageable);

        // Then
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should find ReviewCycleGroups by review cycle ID")
    void shouldFindReviewCycleGroupsByReviewCycleId() {
        // Given
        ReviewCycleGroupDTO dto1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto1.setReviewCycleId(1L);
        ReviewCycleGroupDTO dto2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto2.setReviewCycleId(2L);
        ReviewCycleGroupDTO dto3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto3.setReviewCycleId(1L);

        reviewCycleGroupService.create(dto1, CREATED_BY);
        reviewCycleGroupService.create(dto2, CREATED_BY);
        reviewCycleGroupService.create(dto3, CREATED_BY);

        // When
        List<ReviewCycleGroupDTO> result = reviewCycleGroupService.findByReviewCycleId(1L);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).allSatisfy(dto -> assertThat(dto.getReviewCycleId()).isEqualTo(1L));
    }

    @Test
    @DisplayName("Should find ReviewCycleGroups by boolean state")
    void shouldFindReviewCycleGroupsByBooleanState() {
        // Given
        ReviewCycleGroupDTO dto1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto1.setBooleanState(true);
        ReviewCycleGroupDTO dto2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto2.setBooleanState(false);
        ReviewCycleGroupDTO dto3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto3.setBooleanState(true);

        reviewCycleGroupService.create(dto1, CREATED_BY);
        reviewCycleGroupService.create(dto2, CREATED_BY);
        reviewCycleGroupService.create(dto3, CREATED_BY);

        // When
        List<ReviewCycleGroupDTO> result = reviewCycleGroupService.findByBooleanState(true);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).allSatisfy(dto -> assertThat(dto.getBooleanState()).isTrue());
    }

    @Test
    @DisplayName("Should handle empty list of IDIs")
    void shouldHandleEmptyListOfIdis() {
        // Given
        ReviewCycleGroupDTO createDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        createDTO.setListOfIdis(Arrays.asList());

        // When
        ReviewCycleGroupDTO result = reviewCycleGroupService.create(createDTO, CREATED_BY);

        // Then
        assertThat(result.getListOfIdis()).isEmpty();
        
        // Verify it can be retrieved
        ReviewCycleGroupDTO found = reviewCycleGroupService.findById(result.getReviewCycleGroupId());
        assertThat(found.getListOfIdis()).isEmpty();
    }

    @Test
    @DisplayName("Should count ReviewCycleGroups by review cycle ID")
    void shouldCountReviewCycleGroupsByReviewCycleId() {
        // Given
        ReviewCycleGroupDTO dto1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto1.setReviewCycleId(1L);
        ReviewCycleGroupDTO dto2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto2.setReviewCycleId(2L);
        ReviewCycleGroupDTO dto3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        dto3.setReviewCycleId(1L);

        reviewCycleGroupService.create(dto1, CREATED_BY);
        reviewCycleGroupService.create(dto2, CREATED_BY);
        reviewCycleGroupService.create(dto3, CREATED_BY);

        // When
        long count = reviewCycleGroupService.countByReviewCycleId(1L);

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should check if ReviewCycleGroup exists by ID")
    void shouldCheckIfReviewCycleGroupExistsById() {
        // Given
        ReviewCycleGroupDTO createdDTO = reviewCycleGroupService.create(testReviewCycleGroupDTO, CREATED_BY);

        // When
        boolean exists = reviewCycleGroupService.existsById(createdDTO.getReviewCycleGroupId());
        boolean notExists = reviewCycleGroupService.existsById(999L);

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}