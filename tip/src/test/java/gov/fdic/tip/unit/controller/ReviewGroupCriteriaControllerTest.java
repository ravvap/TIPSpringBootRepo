package gov.fdic.tip.unit.controller;

import gov.fdic.tip.controller.ReviewGroupCriteriaController;
import gov.fdic.tip.dto.ReviewGroupCriteriaDTO;
import gov.fdic.tip.entity.GroupCriteriaType;
import gov.fdic.tip.service.ReviewGroupCriteriaService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ReviewGroupCriteriaController
 */
@ExtendWith(MockitoExtension.class)
class ReviewGroupCriteriaControllerTest {

    @Mock
    private ReviewGroupCriteriaService reviewGroupCriteriaService;

    @InjectMocks
    private ReviewGroupCriteriaController reviewGroupCriteriaController;

    private ReviewGroupCriteriaDTO testCriteriaDTO;

    @BeforeEach
    void setUp() {
        testCriteriaDTO = new ReviewGroupCriteriaDTO();
        testCriteriaDTO.setReviewGroupCriteriaId(1L);
        testCriteriaDTO.setCriteriaName("Test Criteria");
        testCriteriaDTO.setCriteriaType(GroupCriteriaType.FINANCIAL);
        testCriteriaDTO.setCreatedBy("test-user");
        testCriteriaDTO.setUpdatedBy("test-user");
    }

    @Test
    @DisplayName("Should get criteria by ID")
    void shouldGetById() {
        // Given
        when(reviewGroupCriteriaService.findById(1L)).thenReturn(testCriteriaDTO);

        // When
        ResponseEntity<ReviewGroupCriteriaDTO> response = reviewGroupCriteriaController.getById(1L);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getReviewGroupCriteriaId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should get all criteria")
    void shouldGetAll() {
        // Given
        List<ReviewGroupCriteriaDTO> criteriaList = Arrays.asList(testCriteriaDTO);
        when(reviewGroupCriteriaService.findAll()).thenReturn(criteriaList);

        // When
        ResponseEntity<List<ReviewGroupCriteriaDTO>> response = reviewGroupCriteriaController.getAll();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getReviewGroupCriteriaId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should get paginated criteria")
    void shouldGetAllPaginated() {
        // Given
        Page<ReviewGroupCriteriaDTO> criteriaPage = new PageImpl<>(Arrays.asList(testCriteriaDTO));
        when(reviewGroupCriteriaService.findAllPaginated(any())).thenReturn(criteriaPage);

        // When
        ResponseEntity<Page<ReviewGroupCriteriaDTO>> response = 
            reviewGroupCriteriaController.getAllPaginated(PageRequest.of(0, 10));

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Should create criteria")
    void shouldCreateCriteria() {
        // Given
        when(reviewGroupCriteriaService.create(any(ReviewGroupCriteriaDTO.class), anyString()))
            .thenReturn(testCriteriaDTO);

        // When
        ResponseEntity<ReviewGroupCriteriaDTO> response = 
            reviewGroupCriteriaController.create(testCriteriaDTO, "creator");

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getReviewGroupCriteriaId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should update criteria")
    void shouldUpdateCriteria() {
        // Given
        when(reviewGroupCriteriaService.update(anyLong(), any(ReviewGroupCriteriaDTO.class), anyString()))
            .thenReturn(testCriteriaDTO);

        // When
        ResponseEntity<ReviewGroupCriteriaDTO> response = 
            reviewGroupCriteriaController.update(1L, testCriteriaDTO, "updater");

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Should delete criteria")
    void shouldDeleteCriteria() {
        // Given
        // Mock service call in controller

        // When
        ResponseEntity<Void> response = reviewGroupCriteriaController.delete(1L);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Should find criteria by type")
    void shouldFindByCriteriaType() {
        // Given
        List<ReviewGroupCriteriaDTO> criteriaList = Arrays.asList(testCriteriaDTO);
        when(reviewGroupCriteriaService.findByCriteriaType(GroupCriteriaType.FINANCIAL)).thenReturn(criteriaList);

        // When
        ResponseEntity<List<ReviewGroupCriteriaDTO>> response = 
            reviewGroupCriteriaController.findByCriteriaType(GroupCriteriaType.FINANCIAL);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    @DisplayName("Should search criteria by name")
    void shouldSearchByCriteriaName() {
        // Given
        Page<ReviewGroupCriteriaDTO> criteriaPage = new PageImpl<>(Arrays.asList(testCriteriaDTO));
        when(reviewGroupCriteriaService.searchByCriteriaName(anyString(), any())).thenReturn(criteriaPage);

        // When
        ResponseEntity<Page<ReviewGroupCriteriaDTO>> response = 
            reviewGroupCriteriaController.searchByCriteriaName("Test", PageRequest.of(0, 10));

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent()).hasSize(1);
    }

    @Test
    @DisplayName("Should count criteria by type")
    void shouldCountByCriteriaType() {
        // Given
        when(reviewGroupCriteriaService.countByCriteriaType(GroupCriteriaType.FINANCIAL)).thenReturn(5L);

        // When
        ResponseEntity<Long> response = 
            reviewGroupCriteriaController.countByCriteriaType(GroupCriteriaType.FINANCIAL);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(5L);
    }

    @Test
    @DisplayName("Should check if criteria exists")
    void shouldCheckExistsById() {
        // Given
        when(reviewGroupCriteriaService.existsById(1L)).thenReturn(true);

        // When
        ResponseEntity<Boolean> response = reviewGroupCriteriaController.existsById(1L);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isTrue();
    }

    @Test
    @DisplayName("Should get all criteria types")
    void shouldGetAllCriteriaTypes() {
        // When
        ResponseEntity<GroupCriteriaType[]> response = reviewGroupCriteriaController.getAllCriteriaTypes();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody()).contains(GroupCriteriaType.FINANCIAL);
    }
}