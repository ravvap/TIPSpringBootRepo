package gov.fdic.tip.unit.service;

import gov.fdic.tip.dto.ReviewGroupCriteriaDTO;
import gov.fdic.tip.entity.GroupCriteriaType;
import gov.fdic.tip.entity.ReviewGroupCriteria;
import gov.fdic.tip.exception.BusinessException;
import gov.fdic.tip.exception.ResourceNotFoundException;
import gov.fdic.tip.mapper.ReviewGroupCriteriaMapper;
import gov.fdic.tip.repository.ReviewGroupCriteriaRepository;
import gov.fdic.tip.service.ReviewGroupCriteriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReviewGroupCriteriaService
 */
@ExtendWith(MockitoExtension.class)
class ReviewGroupCriteriaServiceTest {

    @Mock
    private ReviewGroupCriteriaRepository reviewGroupCriteriaRepository;

    @Spy
    private ReviewGroupCriteriaMapper reviewGroupCriteriaMapper = Mappers.getMapper(ReviewGroupCriteriaMapper.class);

    @InjectMocks
    private ReviewGroupCriteriaService reviewGroupCriteriaService;

    private ReviewGroupCriteria testCriteria;
    private ReviewGroupCriteriaDTO testCriteriaDTO;
    private final String TEST_USER = "test-user";

    @BeforeEach
    void setUp() {
        testCriteria = createTestCriteria(1L);
        testCriteriaDTO = createTestCriteriaDTO(1L);
    }

    private ReviewGroupCriteria createTestCriteria(Long id) {
        ReviewGroupCriteria criteria = new ReviewGroupCriteria();
        criteria.setReviewGroupCriteriaId(id);
        criteria.setCriteriaName("Test Criteria " + id);
        criteria.setCriteriaType(GroupCriteriaType.FINANCIAL);
        criteria.setCreatedBy(TEST_USER);
        criteria.setUpdatedBy(TEST_USER);
        return criteria;
    }

    private ReviewGroupCriteriaDTO createTestCriteriaDTO(Long id) {
        ReviewGroupCriteriaDTO dto = new ReviewGroupCriteriaDTO();
        dto.setReviewGroupCriteriaId(id);
        dto.setCriteriaName("Test Criteria " + id);
        dto.setCriteriaType(GroupCriteriaType.FINANCIAL);
        dto.setCreatedBy(TEST_USER);
        dto.setUpdatedBy(TEST_USER);
        return dto;
    }

    @Test
    @DisplayName("Should find criteria by ID successfully")
    void shouldFindById() {
        // Given
        when(reviewGroupCriteriaRepository.findById(1L)).thenReturn(Optional.of(testCriteria));

        // When
        ReviewGroupCriteriaDTO result = reviewGroupCriteriaService.findById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviewGroupCriteriaId()).isEqualTo(1L);
        assertThat(result.getCriteriaName()).isEqualTo("Test Criteria 1");
        assertThat(result.getCriteriaType()).isEqualTo(GroupCriteriaType.FINANCIAL);

        verify(reviewGroupCriteriaRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when criteria not found by ID")
    void shouldThrowExceptionWhenNotFoundById() {
        // Given
        when(reviewGroupCriteriaRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> reviewGroupCriteriaService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ReviewGroupCriteria not found with id: 999");

        verify(reviewGroupCriteriaRepository).findById(999L);
    }

    @Test
    @DisplayName("Should find all criteria successfully")
    void shouldFindAll() {
        // Given
        List<ReviewGroupCriteria> criteriaList = Arrays.asList(
            createTestCriteria(1L),
            createTestCriteria(2L)
        );
        when(reviewGroupCriteriaRepository.findAll()).thenReturn(criteriaList);

        // When
        List<ReviewGroupCriteriaDTO> result = reviewGroupCriteriaService.findAll();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getReviewGroupCriteriaId()).isEqualTo(1L);
        assertThat(result.get(1).getReviewGroupCriteriaId()).isEqualTo(2L);

        verify(reviewGroupCriteriaRepository).findAll();
    }

    @Test
    @DisplayName("Should find all criteria with pagination")
    void shouldFindAllPaginated() {
        // Given
        List<ReviewGroupCriteria> criteriaList = Arrays.asList(
            createTestCriteria(1L),
            createTestCriteria(2L)
        );
        Page<ReviewGroupCriteria> criteriaPage = new PageImpl<>(criteriaList);
        Pageable pageable = PageRequest.of(0, 10);
        
        when(reviewGroupCriteriaRepository.findAll(pageable)).thenReturn(criteriaPage);

        // When
        Page<ReviewGroupCriteriaDTO> result = reviewGroupCriteriaService.findAllPaginated(pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);

        verify(reviewGroupCriteriaRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Should create criteria successfully")
    void shouldCreateCriteria() {
        // Given
        ReviewGroupCriteriaDTO createDTO = createTestCriteriaDTO(null);
        ReviewGroupCriteria savedCriteria = createTestCriteria(1L);
        
        when(reviewGroupCriteriaRepository.existsByCriteriaName(createDTO.getCriteriaName())).thenReturn(false);
        when(reviewGroupCriteriaRepository.save(any(ReviewGroupCriteria.class))).thenReturn(savedCriteria);

        // When
        ReviewGroupCriteriaDTO result = reviewGroupCriteriaService.create(createDTO, TEST_USER);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviewGroupCriteriaId()).isEqualTo(1L);
        assertThat(result.getCreatedBy()).isEqualTo(TEST_USER);
        assertThat(result.getUpdatedBy()).isEqualTo(TEST_USER);

        verify(reviewGroupCriteriaRepository).existsByCriteriaName(createDTO.getCriteriaName());
        verify(reviewGroupCriteriaRepository).save(any(ReviewGroupCriteria.class));
    }

    @Test
    @DisplayName("Should throw BusinessException when creating duplicate criteria name")
    void shouldThrowExceptionWhenCreatingDuplicateName() {
        // Given
        ReviewGroupCriteriaDTO createDTO = createTestCriteriaDTO(null);
        when(reviewGroupCriteriaRepository.existsByCriteriaName(createDTO.getCriteriaName())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> reviewGroupCriteriaService.create(createDTO, TEST_USER))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("ReviewGroupCriteria with name already exists");

        verify(reviewGroupCriteriaRepository).existsByCriteriaName(createDTO.getCriteriaName());
        verify(reviewGroupCriteriaRepository, never()).save(any(ReviewGroupCriteria.class));
    }

    @Test
    @DisplayName("Should update criteria successfully")
    void shouldUpdateCriteria() {
        // Given
        ReviewGroupCriteriaDTO updateDTO = createTestCriteriaDTO(1L);
        updateDTO.setCriteriaName("Updated Criteria Name");
        
        when(reviewGroupCriteriaRepository.findById(1L)).thenReturn(Optional.of(testCriteria));
        when(reviewGroupCriteriaRepository.existsByCriteriaNameAndIdNot("Updated Criteria Name", 1L)).thenReturn(false);
        when(reviewGroupCriteriaRepository.save(any(ReviewGroupCriteria.class))).thenReturn(testCriteria);

        // When
        ReviewGroupCriteriaDTO result = reviewGroupCriteriaService.update(1L, updateDTO, "updated-user");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUpdatedBy()).isEqualTo("updated-user");

        verify(reviewGroupCriteriaRepository).findById(1L);
        verify(reviewGroupCriteriaRepository).existsByCriteriaNameAndIdNot("Updated Criteria Name", 1L);
        verify(reviewGroupCriteriaRepository).save(any(ReviewGroupCriteria.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existent criteria")
    void shouldThrowExceptionWhenUpdatingNonExistentCriteria() {
        // Given
        ReviewGroupCriteriaDTO updateDTO = createTestCriteriaDTO(999L);
        when(reviewGroupCriteriaRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> reviewGroupCriteriaService.update(999L, updateDTO, TEST_USER))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ReviewGroupCriteria not found with id: 999");

        verify(reviewGroupCriteriaRepository).findById(999L);
        verify(reviewGroupCriteriaRepository, never()).save(any(ReviewGroupCriteria.class));
    }

    @Test
    @DisplayName("Should delete criteria successfully")
    void shouldDeleteCriteria() {
        // Given
        when(reviewGroupCriteriaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reviewGroupCriteriaRepository).deleteById(1L);

        // When
        reviewGroupCriteriaService.delete(1L);

        // Then
        verify(reviewGroupCriteriaRepository).existsById(1L);
        verify(reviewGroupCriteriaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existent criteria")
    void shouldThrowExceptionWhenDeletingNonExistentCriteria() {
        // Given
        when(reviewGroupCriteriaRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> reviewGroupCriteriaService.delete(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ReviewGroupCriteria not found with id: 999");

        verify(reviewGroupCriteriaRepository).existsById(999L);
        verify(reviewGroupCriteriaRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should find criteria by type successfully")
    void shouldFindByCriteriaType() {
        // Given
        List<ReviewGroupCriteria> criteriaList = Arrays.asList(
            createTestCriteria(1L),
            createTestCriteria(2L)
        );
        when(reviewGroupCriteriaRepository.findByCriteriaType(GroupCriteriaType.FINANCIAL)).thenReturn(criteriaList);

        // When
        List<ReviewGroupCriteriaDTO> result = reviewGroupCriteriaService.findByCriteriaType(GroupCriteriaType.FINANCIAL);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).allSatisfy(dto -> 
            assertThat(dto.getCriteriaType()).isEqualTo(GroupCriteriaType.FINANCIAL)
        );

        verify(reviewGroupCriteriaRepository).findByCriteriaType(GroupCriteriaType.FINANCIAL);
    }

    @Test
    @DisplayName("Should search criteria by name")
    void shouldSearchByCriteriaName() {
        // Given
        List<ReviewGroupCriteria> criteriaList = Arrays.asList(createTestCriteria(1L));
        when(reviewGroupCriteriaRepository.findByCriteriaNameContainingIgnoreCase("Test")).thenReturn(criteriaList);

        // When
        List<ReviewGroupCriteriaDTO> result = reviewGroupCriteriaService.findByCriteriaNameContaining("Test");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCriteriaName()).contains("Test");

        verify(reviewGroupCriteriaRepository).findByCriteriaNameContainingIgnoreCase("Test");
    }

    @Test
    @DisplayName("Should check if criteria exists by ID")
    void shouldCheckExistsById() {
        // Given
        when(reviewGroupCriteriaRepository.existsById(1L)).thenReturn(true);
        when(reviewGroupCriteriaRepository.existsById(999L)).thenReturn(false);

        // When
        boolean exists = reviewGroupCriteriaService.existsById(1L);
        boolean notExists = reviewGroupCriteriaService.existsById(999L);

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();

        verify(reviewGroupCriteriaRepository).existsById(1L);
        verify(reviewGroupCriteriaRepository).existsById(999L);
    }

    @Test
    @DisplayName("Should count criteria by type")
    void shouldCountByCriteriaType() {
        // Given
        when(reviewGroupCriteriaRepository.countByCriteriaType(GroupCriteriaType.FINANCIAL)).thenReturn(5L);

        // When
        long count = reviewGroupCriteriaService.countByCriteriaType(GroupCriteriaType.FINANCIAL);

        // Then
        assertThat(count).isEqualTo(5L);

        verify(reviewGroupCriteriaRepository).countByCriteriaType(GroupCriteriaType.FINANCIAL);
    }

    @Test
    @DisplayName("Should get total count of criteria")
    void shouldGetTotalCount() {
        // Given
        when(reviewGroupCriteriaRepository.count()).thenReturn(10L);

        // When
        long count = reviewGroupCriteriaService.count();

        // Then
        assertThat(count).isEqualTo(10L);

        verify(reviewGroupCriteriaRepository).count();
    }
}