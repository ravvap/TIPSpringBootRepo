package gov.fdic.tip.unit.repository;

import gov.fdic.tip.entity.GroupCriteriaType;
import gov.fdic.tip.entity.ReviewGroupCriteria;
import gov.fdic.tip.repository.ReviewGroupCriteriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Data JPA tests for ReviewGroupCriteriaRepository
 */
@DataJpaTest
class ReviewGroupCriteriaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReviewGroupCriteriaRepository reviewGroupCriteriaRepository;

    private ReviewGroupCriteria testCriteria1;
    private ReviewGroupCriteria testCriteria2;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        reviewGroupCriteriaRepository.deleteAll();

        testCriteria1 = new ReviewGroupCriteria();
        testCriteria1.setCriteriaName("Financial Review Criteria");
        testCriteria1.setCriteriaType(GroupCriteriaType.FINANCIAL);
        testCriteria1.setCreatedBy("test-user");
        testCriteria1.setUpdatedBy("test-user");

        testCriteria2 = new ReviewGroupCriteria();
        testCriteria2.setCriteriaName("Operational Review Criteria");
        testCriteria2.setCriteriaType(GroupCriteriaType.OPERATIONAL);
        testCriteria2.setCreatedBy("test-user");
        testCriteria2.setUpdatedBy("test-user");

        entityManager.persist(testCriteria1);
        entityManager.persist(testCriteria2);
        entityManager.flush();
    }

    @Test
    @DisplayName("Should find criteria by ID")
    void shouldFindById() {
        // When
        Optional<ReviewGroupCriteria> found = reviewGroupCriteriaRepository.findById(testCriteria1.getReviewGroupCriteriaId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getCriteriaName()).isEqualTo("Financial Review Criteria");
    }

    @Test
    @DisplayName("Should find all criteria")
    void shouldFindAll() {
        // When
        List<ReviewGroupCriteria> allCriteria = reviewGroupCriteriaRepository.findAll();

        // Then
        assertThat(allCriteria).hasSize(2);
    }

    @Test
    @DisplayName("Should find criteria by name")
    void shouldFindByCriteriaName() {
        // When
        Optional<ReviewGroupCriteria> found = reviewGroupCriteriaRepository.findByCriteriaName("Financial Review Criteria");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getCriteriaType()).isEqualTo(GroupCriteriaType.FINANCIAL);
    }

    @Test
    @DisplayName("Should find criteria by type")
    void shouldFindByCriteriaType() {
        // When
        List<ReviewGroupCriteria> found = reviewGroupCriteriaRepository.findByCriteriaType(GroupCriteriaType.FINANCIAL);

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCriteriaName()).isEqualTo("Financial Review Criteria");
    }

    @Test
    @DisplayName("Should find criteria by name containing")
    void shouldFindByCriteriaNameContaining() {
        // When
        List<ReviewGroupCriteria> found = reviewGroupCriteriaRepository.findByCriteriaNameContainingIgnoreCase("Review");

        // Then
        assertThat(found).hasSize(2);
    }

    @Test
    @DisplayName("Should check if criteria name exists")
    void shouldCheckExistsByCriteriaName() {
        // When
        boolean exists = reviewGroupCriteriaRepository.existsByCriteriaName("Financial Review Criteria");
        boolean notExists = reviewGroupCriteriaRepository.existsByCriteriaName("Non-existent Criteria");

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("Should find criteria by multiple types")
    void shouldFindByCriteriaTypeIn() {
        // Given
        List<GroupCriteriaType> types = Arrays.asList(GroupCriteriaType.FINANCIAL, GroupCriteriaType.COMPLIANCE);

        // When
        List<ReviewGroupCriteria> found = reviewGroupCriteriaRepository.findByCriteriaTypeIn(types);

        // Then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getCriteriaType()).isEqualTo(GroupCriteriaType.FINANCIAL);
    }

    @Test
    @DisplayName("Should count criteria by type")
    void shouldCountByCriteriaType() {
        // When
        long count = reviewGroupCriteriaRepository.countByCriteriaType(GroupCriteriaType.FINANCIAL);

        // Then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("Should find all with pagination")
    void shouldFindAllWithPagination() {
        // When
        Page<ReviewGroupCriteria> page = reviewGroupCriteriaRepository.findAll(PageRequest.of(0, 1));

        // Then
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalElements()).isEqualTo(2);
    }
}