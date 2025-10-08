package gov.fdic.tip.repository;

import gov.fdic.tip.entity.ReviewGroupCriteria;
import gov.fdic.tip.enums.GroupCriteriaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Your Name
 * @Project TIP
 * @Module Review Group Criteria
 * @Date 2024-01-15
 * Repository interface for Review Group Criteria entities.
 */
@Repository
public interface ReviewGroupCriteriaRepository extends JpaRepository<ReviewGroupCriteria, Long> {

    // Find by criteria name (exact match)
    Optional<ReviewGroupCriteria> findByCriteriaName(String criteriaName);
    
    // Find by criteria type
    List<ReviewGroupCriteria> findByCriteriaType(GroupCriteriaType criteriaType);
    
    // Find by criteria name containing (case-insensitive)
    List<ReviewGroupCriteria> findByCriteriaNameContainingIgnoreCase(String criteriaName);
    
    // Find by criteria name containing with pagination
    Page<ReviewGroupCriteria> findByCriteriaNameContainingIgnoreCase(String criteriaName, Pageable pageable);
    
    // Check if criteria name exists
    boolean existsByCriteriaName(String criteriaName);
    
    // Check if criteria name exists excluding current ID (for updates)
    @Query("SELECT COUNT(r) > 0 FROM ReviewGroupCriteria r WHERE r.criteriaName = :criteriaName AND r.reviewGroupCriteriaId != :excludeId")
    boolean existsByCriteriaNameAndIdNot(@Param("criteriaName") String criteriaName, @Param("excludeId") Long excludeId);
    
    // Find by multiple criteria types
    List<ReviewGroupCriteria> findByCriteriaTypeIn(List<GroupCriteriaType> criteriaTypes);
    
    // Count by criteria type
    long countByCriteriaType(GroupCriteriaType criteriaType);
    
    // Find all with pagination and sorting
    Page<ReviewGroupCriteria> findAll(Pageable pageable);
}