package gov.fdic.tip.integration.repository;

import gov.fdic.tip.entity.ReviewCycleGroup;
import gov.fdic.tip.repository.ReviewCycleGroupRepository;
import gov.fdic.tip.util.ReviewCycleGroupTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ReviewCycleGroupRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReviewCycleGroupRepository reviewCycleGroupRepository;

    private ReviewCycleGroup reviewCycleGroup;

    @BeforeEach
    void setUp() {
        reviewCycleGroup = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(1L);
    }

    @Test
    @DisplayName("Should save and find ReviewCycleGroup")
    void shouldSaveAndFindReviewCycleGroup() {
        // Given
        ReviewCycleGroup savedEntity = entityManager.persistAndFlush(reviewCycleGroup);

        // When
        Optional<ReviewCycleGroup> found = reviewCycleGroupRepository.findById(savedEntity.getReviewCycleGroupId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getReviewGroupName()).isEqualTo("Test Review Group 1");
        assertThat(found.get().getListOfIdis()).containsExactly("IDI001", "IDI002", "IDI003");
    }

    @Test
    @DisplayName("Should find ReviewCycleGroups by list of IDIs containing specific IDI")
    void shouldFindByListOfIdisContaining() {
        // Given
        ReviewCycleGroup entity1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithIdis(
                Arrays.asList("IDI001", "IDI002", "IDI003"));
        ReviewCycleGroup entity2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithIdis(
                Arrays.asList("IDI004", "IDI005"));
        ReviewCycleGroup entity3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithIdis(
                Arrays.asList("IDI001", "IDI006"));

        entityManager.persistAndFlush(entity1);
        entityManager.persistAndFlush(entity2);
        entityManager.persistAndFlush(entity3);

        // When
        List<ReviewCycleGroup> result = reviewCycleGroupRepository.findByListOfIdisContaining("IDI001");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ReviewCycleGroup::getReviewCycleGroupId)
                .contains(entity1.getReviewCycleGroupId(), entity3.getReviewCycleGroupId());
    }

    @Test
    @DisplayName("Should find ReviewCycleGroups by review cycle ID")
    void shouldFindByReviewCycleId() {
        // Given
        ReviewCycleGroup entity1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(1L);
        entity1.setReviewCycleId(100L);
        ReviewCycleGroup entity2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(2L);
        entity2.setReviewCycleId(200L);
        ReviewCycleGroup entity3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(3L);
        entity3.setReviewCycleId(100L);

        entityManager.persistAndFlush(entity1);
        entityManager.persistAndFlush(entity2);
        entityManager.persistAndFlush(entity3);

        // When
        List<ReviewCycleGroup> result = reviewCycleGroupRepository.findByReviewCycleId(100L);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ReviewCycleGroup::getReviewCycleId)
                .containsOnly(100L);
    }

    @Test
    @DisplayName("Should check if ReviewCycleGroup exists by name")
    void shouldCheckExistsByReviewGroupName() {
        // Given
        ReviewCycleGroup entity = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithName("Unique Name");
        entityManager.persistAndFlush(entity);

        // When
        boolean exists = reviewCycleGroupRepository.existsByReviewGroupName("Unique Name");
        boolean notExists = reviewCycleGroupRepository.existsByReviewGroupName("Non-existent Name");

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("Should find ReviewCycleGroups by name containing ignore case")
    void shouldFindByReviewGroupNameContainingIgnoreCase() {
        // Given
        ReviewCycleGroup entity1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithName("Financial Review");
        ReviewCycleGroup entity2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithName("Technical Review");
        ReviewCycleGroup entity3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroupWithName("Security Audit");

        entityManager.persistAndFlush(entity1);
        entityManager.persistAndFlush(entity2);
        entityManager.persistAndFlush(entity3);

        // When
        Page<ReviewCycleGroup> result = reviewCycleGroupRepository.findByReviewGroupNameContainingIgnoreCase(
                "review", PageRequest.of(0, 10));

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).extracting(ReviewCycleGroup::getReviewGroupName)
                .contains("Financial Review", "Technical Review");
    }

    @Test
    @DisplayName("Should count ReviewCycleGroups by review cycle ID")
    void shouldCountByReviewCycleId() {
        // Given
        ReviewCycleGroup entity1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(1L);
        entity1.setReviewCycleId(100L);
        ReviewCycleGroup entity2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(2L);
        entity2.setReviewCycleId(200L);
        ReviewCycleGroup entity3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(3L);
        entity3.setReviewCycleId(100L);

        entityManager.persistAndFlush(entity1);
        entityManager.persistAndFlush(entity2);
        entityManager.persistAndFlush(entity3);

        // When
        long count = reviewCycleGroupRepository.countByReviewCycleId(100L);

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should find ReviewCycleGroups by boolean state")
    void shouldFindByBooleanState() {
        // Given
        ReviewCycleGroup entity1 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(1L);
        entity1.setBooleanState(true);
        ReviewCycleGroup entity2 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(2L);
        entity2.setBooleanState(false);
        ReviewCycleGroup entity3 = ReviewCycleGroupTestDataBuilder.createReviewCycleGroup(3L);
        entity3.setBooleanState(true);

        entityManager.persistAndFlush(entity1);
        entityManager.persistAndFlush(entity2);
        entityManager.persistAndFlush(entity3);

        // When
        List<ReviewCycleGroup> result = reviewCycleGroupRepository.findByBooleanState(true);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(ReviewCycleGroup::getBooleanState)
                .containsOnly(true);
    }
}