package gov.fdic.tip.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import gov.fdic.tip.controller.ReviewCycleGroupController;
import gov.fdic.tip.dto.ReviewCycleGroupDTO;
import gov.fdic.tip.service.ReviewCycleGroupService;
import gov.fdic.tip.util.ReviewCycleGroupTestDataBuilder;

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

    // ... other tests remain the same ...

    @Test
    @DisplayName("Should get paginated ReviewCycleGroups successfully")
    void shouldGetPaginatedReviewCycleGroups() {
        // Given
        Page<ReviewCycleGroupDTO> reviewCycleGroupPage = new PageImpl<>(Arrays.asList(reviewCycleGroupDTO));
        
        when(reviewCycleGroupService.findAllPaginated(any(Pageable.class)))
                .thenReturn(reviewCycleGroupPage);

        // When
        ResponseEntity<Page<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .getAllReviewCycleGroupsPaginated(0, 10, "reviewCycleGroupId", "asc");

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(1);
        
        verify(reviewCycleGroupService).findAllPaginated(any(Pageable.class));
    }

    @Test
    @DisplayName("Should search ReviewCycleGroups by name")
    void shouldSearchReviewCycleGroupsByName() {
        // Given
        String searchName = "Test";
        Page<ReviewCycleGroupDTO> reviewCycleGroupPage = new PageImpl<>(Arrays.asList(reviewCycleGroupDTO));
        
        when(reviewCycleGroupService.searchByGroupName(eq(searchName), any(Pageable.class)))
                .thenReturn(reviewCycleGroupPage);

        // When
        ResponseEntity<Page<ReviewCycleGroupDTO>> response = reviewCycleGroupController
                .searchReviewCycleGroups(searchName, 0, 10);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(1);
        
        verify(reviewCycleGroupService).searchByGroupName(eq(searchName), any(Pageable.class));
    }

    // ... other tests remain the same ...
}