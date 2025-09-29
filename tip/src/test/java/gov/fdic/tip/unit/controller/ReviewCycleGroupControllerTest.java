package gov.fdic.tip.unit.controller;

import gov.fdic.tip.controller.ReviewCycleGroupController;
import gov.fdic.tip.dto.ReviewCycleGroupDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewCycleGroupControllerTest {

    @Mock
    private ReviewCycleGroupService reviewCycleGroupService;

    @InjectMocks
    private ReviewCycleGroupController reviewCycleGroupController;

    private ReviewCycleGroupDTO reviewCycleGroupDTO;
    private final Long REVIEW_CYCLE_GROUP_ID = 1L;
    private final String USER_ID = "test-user";

    @BeforeEach
    void setUp() {
        reviewCycleGroupDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTO(REVIEW_CYCLE_GROUP_ID);
    }

    @Test
    @DisplayName("Should get all ReviewCycleGroups successfully")
    void shouldGetAllReviewCycleGroups() {
        // Given
        List<ReviewCycleGroupDTO> reviewCycleGroupDTOs = Arrays.asList(reviewCycleGroupDTO);
        when(reviewCycleGroupService.findAll()).thenReturn(reviewCycleGroupDTOs);

        // When
        ResponseEntity<List<ReviewCycleGroupDTO>> response = reviewCycleGroupController.getAllReviewCycleGroups();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getReviewCycleGroupId()).isEqualTo(REVIEW_CYCLE_GROUP_ID);
        
        verify(reviewCycleGroupService).findAll();
    }

    @Test
    @DisplayName("Should get ReviewCycleGroup by ID successfully")
    void shouldGetReviewCycleGroupById() {
        // Given
        when(reviewCycleGroupService.findById(REVIEW_CYCLE_GROUP_ID)).thenReturn(reviewCycleGroupDTO);

        // When
        ResponseEntity<ReviewCycleGroupDTO> response = reviewCycleGroupController.getReviewCycleGroupById(REVIEW_CYCLE_GROUP_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getReviewCycleGroupId()).isEqualTo(REVIEW_CYCLE_GROUP_ID);
        
        verify(reviewCycleGroupService).findById(REVIEW_CYCLE_GROUP_ID);
    }

    @Test
    @DisplayName("Should create ReviewCycleGroup successfully")
    void shouldCreateReviewCycleGroup() {
        // Given
        ReviewCycleGroupDTO createDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        when(reviewCycleGroupService.create(any(ReviewCycleGroupDTO.class), eq(USER_ID)))
                .thenReturn(reviewCycleGroupDTO);

        // When
        ResponseEntity<ReviewCycleGroupDTO> response = reviewCycleGroupController
                .createReviewCycleGroup(createDTO, USER_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getReviewCycleGroupId()).isEqualTo(REVIEW_CYCLE_GROUP_ID);
        
        verify(reviewCycleGroupService).create(createDTO, USER_ID);
    }

    @Test
    @DisplayName("Should create ReviewCycleGroup with default user when no header provided")
    void shouldCreateReviewCycleGroupWithDefaultUser() {
        // Given
        ReviewCycleGroupDTO createDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTOWithoutId();
        when(reviewCycleGroupService.create(any(ReviewCycleGroupDTO.class), eq("system")))
                .thenReturn(reviewCycleGroupDTO);

        // When
        ResponseEntity<ReviewCycleGroupDTO> response = reviewCycleGroupController
                .createReviewCycleGroup(createDTO, null);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(reviewCycleGroupService).create(createDTO, "system");
    }

    @Test
    @DisplayName("Should update ReviewCycleGroup successfully")
    void shouldUpdateReviewCycleGroup() {
        // Given
        ReviewCycleGroupDTO updateDTO = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupDTO(REVIEW_CYCLE_GROUP_ID);
        updateDTO.setReviewGroupName("Updated Name");

        when(reviewCycleGroupService.update(eq(REVIEW_CYCLE_GROUP_ID), any(ReviewCycleGroupDTO.class), eq(USER_ID)))
                .thenReturn(reviewCycleGroupDTO);

        // When
        ResponseEntity<ReviewCycleGroupDTO> response = reviewCycleGroupController
                .updateReviewCycleGroup(REVIEW_CYCLE_GROUP_ID, updateDTO, USER_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        
        verify(reviewCycleGroupService).update(REVIEW_CYCLE_GROUP_ID, updateDTO, USER_ID);
    }

    @Test
    @DisplayName("Should delete ReviewCycleGroup successfully")
    void shouldDeleteReviewCycleGroup() {
        // When
        ResponseEntity<Void> response = reviewCycleGroupController.deleteReviewCycleGroup(REVIEW_CYCLE_GROUP_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        
        verify(reviewCycleGroupService).delete(REVIEW_CYCLE_GROUP_ID);
    }

    // List of IDIs Specific Controller Tests
    @Test
    @DisplayName("Should get ReviewCycleGroups by list of IDIs containing specific IDI")
    void shouldGetReviewCycleGroupsByListOfIdis() {
        // Given
        String idi = "IDI002";
        List<ReviewCycleGroupDTO> reviewCycleGroupDTOs = Arrays.asList(reviewCycleGroupDTO);
        when(reviewCycleGroupService.findByListOfIdisContaining(idi)).thenReturn(reviewCycleGroupDTOs);

        // When
        ResponseEntity<List<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .getReviewCycleGroupsByListOfIdis(idi);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        
        verify(reviewCycleGroupService).findByListOfIdisContaining(idi);
    }

    @Test
    @DisplayName("Should return empty list when no ReviewCycleGroups contain specific IDI")
    void shouldReturnEmptyListWhenNoReviewCycleGroupsContainIdi() {
        // Given
        String idi = "NON_EXISTENT_IDI";
        when(reviewCycleGroupService.findByListOfIdisContaining(idi)).thenReturn(Arrays.asList());

        // When
        ResponseEntity<List<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .getReviewCycleGroupsByListOfIdis(idi);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEmpty();
        
        verify(reviewCycleGroupService).findByListOfIdisContaining(idi);
    }

    @Test
    @DisplayName("Should get paginated ReviewCycleGroups successfully")
    void shouldGetPaginatedReviewCycleGroups() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReviewCycleGroupDTO> reviewCycleGroupPage = new PageImpl<>(Arrays.asList(reviewCycleGroupDTO), pageable, 1);
        when(reviewCycleGroupService.findAllPaginated(pageable)).thenReturn(reviewCycleGroupPage);

        // When
        ResponseEntity<Page<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .getAllReviewCycleGroupsPaginated(0, 10, "reviewCycleGroupId", "asc");

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(1);
        
        verify(reviewCycleGroupService).findAllPaginated(pageable);
    }

    @Test
    @DisplayName("Should get ReviewCycleGroups by review cycle ID")
    void shouldGetReviewCycleGroupsByReviewCycleId() {
        // Given
        Long reviewCycleId = 1L;
        List<ReviewCycleGroupDTO> reviewCycleGroupDTOs = Arrays.asList(reviewCycleGroupDTO);
        when(reviewCycleGroupService.findByReviewCycleId(reviewCycleId)).thenReturn(reviewCycleGroupDTOs);

        // When
        ResponseEntity<List<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .getReviewCycleGroupsByReviewCycleId(reviewCycleId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        
        verify(reviewCycleGroupService).findByReviewCycleId(reviewCycleId);
    }

    @Test
    @DisplayName("Should get ReviewCycleGroups by review type ID")
    void shouldGetReviewCycleGroupsByReviewTypeId() {
        // Given
        Long reviewTypeId = 2L;
        List<ReviewCycleGroupDTO> reviewCycleGroupDTOs = Arrays.asList(reviewCycleGroupDTO);
        when(reviewCycleGroupService.findByReviewTypeId(reviewTypeId)).thenReturn(reviewCycleGroupDTOs);

        // When
        ResponseEntity<List<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .getReviewCycleGroupsByReviewTypeId(reviewTypeId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        
        verify(reviewCycleGroupService).findByReviewTypeId(reviewTypeId);
    }

    @Test
    @DisplayName("Should get ReviewCycleGroups by boolean state")
    void shouldGetReviewCycleGroupsByBooleanState() {
        // Given
        Boolean booleanState = true;
        List<ReviewCycleGroupDTO> reviewCycleGroupDTOs = Arrays.asList(reviewCycleGroupDTO);
        when(reviewCycleGroupService.findByBooleanState(booleanState)).thenReturn(reviewCycleGroupDTOs);

        // When
        ResponseEntity<List<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .getReviewCycleGroupsByBooleanState(booleanState);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        
        verify(reviewCycleGroupService).findByBooleanState(booleanState);
    }

    @Test
    @DisplayName("Should search ReviewCycleGroups by name")
    void shouldSearchReviewCycleGroupsByName() {
        // Given
        String searchName = "Test";
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReviewCycleGroupDTO> reviewCycleGroupPage = new PageImpl<>(Arrays.asList(reviewCycleGroupDTO), pageable, 1);
        when(reviewCycleGroupService.searchByGroupName(searchName, pageable)).thenReturn(reviewCycleGroupPage);

        // When
        ResponseEntity<Page<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .searchReviewCycleGroups(searchName, 0, 10);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(1);
        
        verify(reviewCycleGroupService).searchByGroupName(searchName, pageable);
    }

    @Test
    @DisplayName("Should check if ReviewCycleGroup exists")
    void shouldCheckIfReviewCycleGroupExists() {
        // Given
        when(reviewCycleGroupService.existsById(REVIEW_CYCLE_GROUP_ID)).thenReturn(true);

        // When
        ResponseEntity<Boolean> response = reviewCycleGroupController.checkExists(REVIEW_CYCLE_GROUP_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isTrue();
        
        verify(reviewCycleGroupService).existsById(REVIEW_CYCLE_GROUP_ID);
    }

    @Test
    @DisplayName("Should get count of ReviewCycleGroups")
    void shouldGetCountOfReviewCycleGroups() {
        // Given
        long expectedCount = 5L;
        when(reviewCycleGroupService.count()).thenReturn(expectedCount);

        // When
        ResponseEntity<Long> response = reviewCycleGroupController.getCount();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedCount);
        
        verify(reviewCycleGroupService).count();
    }

    @Test
    @DisplayName("Should get count by review cycle ID")
    void shouldGetCountByReviewCycleId() {
        // Given
        Long reviewCycleId = 1L;
        long expectedCount = 3L;
        when(reviewCycleGroupService.countByReviewCycleId(reviewCycleId)).thenReturn(expectedCount);

        // When
        ResponseEntity<Long> response = reviewCycleGroupController
                .getCountByReviewCycleId(reviewCycleId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedCount);
        
        verify(reviewCycleGroupService).countByReviewCycleId(reviewCycleId);
    }
}