package gov.fdic.tip.unit.mapper;

import gov.fdic.tip.dto.ReviewGroupCriteriaDTO;
import gov.fdic.tip.entity.GroupCriteriaType;
import gov.fdic.tip.entity.ReviewGroupCriteria;
import gov.fdic.tip.mapper.ReviewGroupCriteriaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ReviewGroupCriteriaMapper
 */
class ReviewGroupCriteriaMapperTest {

    private ReviewGroupCriteriaMapper mapper = ReviewGroupCriteriaMapper.INSTANCE;

    private ReviewGroupCriteria testEntity;
    private ReviewGroupCriteriaDTO testDTO;

    @BeforeEach
    void setUp() {
        testEntity = new ReviewGroupCriteria();
        testEntity.setReviewGroupCriteriaId(1L);
        testEntity.setCriteriaName("Test Criteria");
        testEntity.setCriteriaType(GroupCriteriaType.FINANCIAL);
        testEntity.setCreatedBy("creator");
        testEntity.setUpdatedBy("updater");

        testDTO = new ReviewGroupCriteriaDTO();
        testDTO.setReviewGroupCriteriaId(1L);
        testDTO.setCriteriaName("Test Criteria");
        testDTO.setCriteriaType(GroupCriteriaType.FINANCIAL);
        testDTO.setCreatedBy("creator");
        testDTO.setUpdatedBy("updater");
    }

    @Test
    @DisplayName("Should map entity to DTO")
    void shouldMapEntityToDto() {
        // When
        ReviewGroupCriteriaDTO result = mapper.toDto(testEntity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviewGroupCriteriaId()).isEqualTo(testEntity.getReviewGroupCriteriaId());
        assertThat(result.getCriteriaName()).isEqualTo(testEntity.getCriteriaName());
        assertThat(result.getCriteriaType()).isEqualTo(testEntity.getCriteriaType());
        assertThat(result.getCreatedBy()).isEqualTo(testEntity.getCreatedBy());
        assertThat(result.getUpdatedBy()).isEqualTo(testEntity.getUpdatedBy());
    }

    @Test
    @DisplayName("Should map DTO to entity")
    void shouldMapDtoToEntity() {
        // When
        ReviewGroupCriteria result = mapper.toEntity(testDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getReviewGroupCriteriaId()).isEqualTo(testDTO.getReviewGroupCriteriaId());
        assertThat(result.getCriteriaName()).isEqualTo(testDTO.getCriteriaName());
        assertThat(result.getCriteriaType()).isEqualTo(testDTO.getCriteriaType());
        // Note: createdBy and updatedBy are ignored in toEntity mapping
    }

    @Test
    @DisplayName("Should map entity list to DTO list")
    void shouldMapEntityListToDtoList() {
        // Given
        List<ReviewGroupCriteria> entities = Arrays.asList(testEntity);

        // When
        List<ReviewGroupCriteriaDTO> result = mapper.toDtoList(entities);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getReviewGroupCriteriaId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should update entity from DTO")
    void shouldUpdateEntityFromDto() {
        // Given
        ReviewGroupCriteria targetEntity = new ReviewGroupCriteria();
        targetEntity.setReviewGroupCriteriaId(2L);
        targetEntity.setCriteriaName("Old Name");
        targetEntity.setCriteriaType(GroupCriteriaType.OPERATIONAL);

        ReviewGroupCriteriaDTO sourceDTO = new ReviewGroupCriteriaDTO();
        sourceDTO.setCriteriaName("New Name");
        sourceDTO.setCriteriaType(GroupCriteriaType.FINANCIAL);

        // When
        mapper.updateEntityFromDto(sourceDTO, targetEntity);

        // Then
        assertThat(targetEntity.getCriteriaName()).isEqualTo("New Name");
        assertThat(targetEntity.getCriteriaType()).isEqualTo(GroupCriteriaType.FINANCIAL);
        // ID should not be updated
        assertThat(targetEntity.getReviewGroupCriteriaId()).isEqualTo(2L);
    }
}