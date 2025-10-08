package gov.fdic.tip.repository;

import gov.fdic.tip.entity.ReviewCycleGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Repository interface for ReviewCycleGroup entity with custom query methods.
 */

@Repository
public interface ReviewCycleGroupRepository extends JpaRepository<ReviewCycleGroup, Long> {

    Optional<ReviewCycleGroup> findByReviewGroupName(String reviewGroupName);
    
    List<ReviewCycleGroup> findByReviewCycleId(Long reviewCycleId);
    
    List<ReviewCycleGroup> findByReviewTypeId(Long reviewTypeId);
    
    List<ReviewCycleGroup> findByReviewConditionId(Long reviewConditionId);
    
    List<ReviewCycleGroup> findByBooleanState(Boolean booleanState);
    
    @Query("SELECT rcg FROM ReviewCycleGroup rcg WHERE rcg.rangeStart <= :value AND rcg.rangeEnd >= :value")
    List<ReviewCycleGroup> findByValueInRange(@Param("value") Integer value);
    
    @Query("SELECT rcg FROM ReviewCycleGroup rcg WHERE :idi MEMBER OF rcg.listOfIdis")
    List<ReviewCycleGroup> findByListOfIdisContaining(@Param("idi") String idi);
    
    Page<ReviewCycleGroup> findByReviewGroupNameContainingIgnoreCase(String reviewGroupName, Pageable pageable);
    
    @Query("SELECT COUNT(rcg) FROM ReviewCycleGroup rcg WHERE rcg.reviewCycleId = :reviewCycleId")
    long countByReviewCycleId(@Param("reviewCycleId") Long reviewCycleId);
    
    boolean existsByReviewGroupName(String reviewGroupName);
}