package gov.fdic.tip.unit.service;

import gov.fdic.tip.dto.ReviewCycleGroupDTO;
import gov.fdic.tip.entity.ReviewCycleGroup;
import gov.fdic.tip.exception.BusinessException;
import gov.fdic.tip.exception.ResourceNotFoundException;
import gov.fdic.tip.mapper.ReviewCycleGroupMapper;
import gov.fdic.tip.repository.ReviewCycleGroupRepository;
import gov.fdic.tip.service.ReviewCycleGroupService;
import gov.fdic.tip.util.ReviewCycleGroupTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewCycleGroupServiceTest {

    @Mock
    private ReviewCycleGroupRepository reviewCycleGroupRepository;

    @Mock
    private ReviewCycleGroupMapper reviewCycleGroupMapper;

    @InjectMocks
    private ReviewCycleGroupService reviewCycleGroupService;

    private ReviewCycleGroup reviewCycleGroup;
    private ReviewCycleGroupDTO reviewCycleGroupDTO;
    private final Long REVIEW_CYCLE_GROUP_ID = 1L;
    private final String CREATED_BY = "test-user";

    @BeforeEach
    void setUp() {
        // Use entity WITH ID for read operations
        reviewCycleGroup = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithId(REVIEW_CYCLE_GROUP_ID);
        reviewCycleGroupDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTO(REVIEW_CYCLE_GROUP_ID);
    }

    @Test
    @DisplayName("Should create ReviewCycleGroup successfully")
    void shouldCreateReviewCycleGroupSuccessfully() {
        // Given
        ReviewCycleGroupDTO createDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        
        // Create entity WITHOUT ID for persistence
        ReviewCycleGroup newEntity = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(); // No ID
        ReviewCycleGroup savedEntity = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithId(REVIEW_CYCLE_GROUP_ID); // With ID after save

        when(reviewCycleGroupRepository.existsByReviewGroupName(createDTO.getReviewGroupName()))
                .thenReturn(false);
        when(reviewCycleGroupMapper.toEntity(createDTO)).thenReturn(newEntity);
        when(reviewCycleGroupRepository.save(newEntity)).thenReturn(savedEntity);
        when(reviewCycleGroupMapper.toDto(savedEntity)).thenReturn(reviewCycleGroupDTO);

        // When
        ReviewCycleGroupDTO result = reviewCycleGroupService.create(createDTO, CREATED_BY);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviewCycleGroupId()).isEqualTo(REVIEW_CYCLE_GROUP_ID);
        
        verify(reviewCycleGroupRepository).existsByReviewGroupName(createDTO.getReviewGroupName());
        verify(reviewCycleGroupMapper).toEntity(createDTO);
        verify(reviewCycleGroupRepository).save(newEntity);
        verify(reviewCycleGroupMapper).toDto(savedEntity);
    }

    @Test
    @DisplayName("Should find ReviewCycleGroup by ID successfully")
    void shouldFindReviewCycleGroupById() {
        // Given
        when(reviewCycleGroupRepository.findById(REVIEW_CYCLE_GROUP_ID))
                .thenReturn(Optional.of(reviewCycleGroup));
        when(reviewCycleGroupMapper.toDto(reviewCycleGroup))
                .thenReturn(reviewCycleGroupDTO);

        // When
        ReviewCycleGroupDTO result = reviewCycleGroupService.findById(REVIEW_CYCLE_GROUP_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviewCycleGroupId()).isEqualTo(REVIEW_CYCLE_GROUP_ID);
        assertThat(result.getReviewGroupName()).isEqualTo("Test Review Group 1");
        
        verify(reviewCycleGroupRepository).findById(REVIEW_CYCLE_GROUP_ID);
        verify(reviewCycleGroupMapper).toDto(reviewCycleGroup);
    }

    @Test
    @DisplayName("Should update ReviewCycleGroup successfully")
    void shouldUpdateReviewCycleGroupSuccessfully() {
        // Given
        ReviewCycleGroupDTO updateDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTO(REVIEW_CYCLE_GROUP_ID);
        updateDTO.setReviewGroupName("Updated Review Group");
        updateDTO.setBooleanState(false);
        updateDTO.setReviewsPerYear(2);

        // Use entity WITH ID for existing entity
        ReviewCycleGroup existingEntity = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithId(REVIEW_CYCLE_GROUP_ID);

        when(reviewCycleGroupRepository.findById(REVIEW_CYCLE_GROUP_ID))
                .thenReturn(Optional.of(existingEntity));
        when(reviewCycleGroupRepository.existsByReviewGroupName(updateDTO.getReviewGroupName()))
                .thenReturn(false);
        when(reviewCycleGroupRepository.save(existingEntity)).thenReturn(existingEntity);
        when(reviewCycleGroupMapper.toDto(existingEntity)).thenReturn(updateDTO);

        // When
        ReviewCycleGroupDTO result = reviewCycleGroupService.update(REVIEW_CYCLE_GROUP_ID, updateDTO, CREATED_BY);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviewGroupName()).isEqualTo("Updated Review Group");
        assertThat(result.getBooleanState()).isFalse();
        assertThat(result.getReviewsPerYear()).isEqualTo(2);
        
        verify(reviewCycleGroupRepository).findById(REVIEW_CYCLE_GROUP_ID);
        verify(reviewCycleGroupRepository).existsByReviewGroupName(updateDTO.getReviewGroupName());
        verify(reviewCycleGroupMapper).updateEntityFromDto(updateDTO, existingEntity);
        verify(reviewCycleGroupRepository).save(existingEntity);
    }

    @Test
    @DisplayName("Should create ReviewCycleGroup with list of IDIs successfully")
    void shouldCreateReviewCycleGroupWithListOfIdis() {
        // Given
        List<String> idis = ReviewCycleGroupTestDataBuilder.createSampleIdis();
        ReviewCycleGroupDTO createDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        createDTO.setListOfIdis(idis);

        // Create entities without ID for persistence
        ReviewCycleGroup newEntity = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup();
        newEntity.setListOfIdis(idis);
        
        ReviewCycleGroup savedEntity = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithId(REVIEW_CYCLE_GROUP_ID);
        savedEntity.setListOfIdis(idis);

        ReviewCycleGroupDTO resultDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTO(REVIEW_CYCLE_GROUP_ID);
        resultDTO.setListOfIdis(idis);

        when(reviewCycleGroupRepository.existsByReviewGroupName(anyString())).thenReturn(false);
        when(reviewCycleGroupMapper.toEntity(createDTO)).thenReturn(newEntity);
        when(reviewCycleGroupRepository.save(newEntity)).thenReturn(savedEntity);
        when(reviewCycleGroupMapper.toDto(savedEntity)).thenReturn(resultDTO);

        // When
        ReviewCycleGroupDTO result = reviewCycleGroupService.create(createDTO, CREATED_BY);

        // Then
        assertThat(result.getListOfIdis()).isNotEmpty();
        assertThat(result.getListOfIdis()).hasSize(5);
        assertThat(result.getListOfIdis()).containsExactly("IDI001", "IDI002", "IDI003", "IDI004", "IDI005");
    }

    @Test
    @DisplayName("Should throw BusinessException when creating duplicate ReviewCycleGroup name")
    void shouldThrowExceptionWhenCreatingDuplicateReviewCycleGroupName() {
        // Given
        ReviewCycleGroupDTO createDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();

        when(reviewCycleGroupRepository.existsByReviewGroupName(createDTO.getReviewGroupName()))
                .thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> reviewCycleGroupService.create(createDTO, CREATED_BY))
                .isInstanceOf(BusinessException.class)
                .hasMessage("ReviewCycleGroup with name already exists: " + createDTO.getReviewGroupName());
        
        verify(reviewCycleGroupRepository).existsByReviewGroupName(createDTO.getReviewGroupName());
        verify(reviewCycleGroupRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when ReviewCycleGroup not found by ID")
    void shouldThrowExceptionWhenReviewCycleGroupNotFoundById() {
        // Given
        when(reviewCycleGroupRepository.findById(REVIEW_CYCLE_GROUP_ID))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> reviewCycleGroupService.findById(REVIEW_CYCLE_GROUP_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ReviewCycleGroup not found with id: " + REVIEW_CYCLE_GROUP_ID);
    }

    @Test
    @DisplayName("Should delete ReviewCycleGroup successfully")
    void shouldDeleteReviewCycleGroupSuccessfully() {
        // Given
        when(reviewCycleGroupRepository.existsById(REVIEW_CYCLE_GROUP_ID)).thenReturn(true);

        // When
        reviewCycleGroupService.delete(REVIEW_CYCLE_GROUP_ID);

        // Then
        verify(reviewCycleGroupRepository).existsById(REVIEW_CYCLE_GROUP_ID);
        verify(reviewCycleGroupRepository).deleteById(REVIEW_CYCLE_GROUP_ID);
    }

    // ... other test methods
}