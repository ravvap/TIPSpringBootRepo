package gov.fdic.tip.service;

import gov.fdic.tip.dto.ReviewCycleGroupDTO;
import gov.fdic.tip.entity.ReviewCycleGroup;
import gov.fdic.tip.exception.BusinessException;
import gov.fdic.tip.exception.ResourceNotFoundException;
import gov.fdic.tip.mapper.ReviewCycleGroupMapper;
import gov.fdic.tip.repository.ReviewCycleGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * Service class for managing ReviewCycleGroup entities.
 */

@Service
@Transactional
public class ReviewCycleGroupService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewCycleGroupService.class);

    @Autowired
    private ReviewCycleGroupRepository reviewCycleGroupRepository;

    @Autowired
    private ReviewCycleGroupMapper reviewCycleGroupMapper;

    // CRUD Operations
    @Transactional(readOnly = true)
    public ReviewCycleGroupDTO findById(Long id) {
        logger.info("Finding ReviewCycleGroup with ID: {}", id);
        
        ReviewCycleGroup reviewCycleGroup = reviewCycleGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewCycleGroup", id));
        
        return reviewCycleGroupMapper.toDto(reviewCycleGroup);
    }

    @Transactional(readOnly = true)
    public List<ReviewCycleGroupDTO> findAll() {
        logger.info("Finding all ReviewCycleGroups");
        List<ReviewCycleGroup> reviewCycleGroups = reviewCycleGroupRepository.findAll();
        return reviewCycleGroupMapper.toDtoList(reviewCycleGroups);
    }

    @Transactional(readOnly = true)
    public Page<ReviewCycleGroupDTO> findAllPaginated(Pageable pageable) {
        logger.info("Finding paginated ReviewCycleGroups");
        
        Page<ReviewCycleGroup> reviewCycleGroupPage = reviewCycleGroupRepository.findAll(pageable);
        List<ReviewCycleGroupDTO> dtos = reviewCycleGroupMapper.toDtoList(reviewCycleGroupPage.getContent());
        
        return new PageImpl<>(dtos, pageable, reviewCycleGroupPage.getTotalElements());
    }

    public ReviewCycleGroupDTO create(ReviewCycleGroupDTO reviewCycleGroupDTO, String createdBy) {
        logger.info("Creating new ReviewCycleGroup with name: {}", reviewCycleGroupDTO.getReviewGroupName());
        
        // Check if review group name already exists
        if (reviewCycleGroupRepository.existsByReviewGroupName(reviewCycleGroupDTO.getReviewGroupName())) {
            throw new BusinessException("REVIEW_GROUP_EXISTS", 
                "ReviewCycleGroup with name already exists: " + reviewCycleGroupDTO.getReviewGroupName());
        }
        
        ReviewCycleGroup reviewCycleGroup = reviewCycleGroupMapper.toEntity(reviewCycleGroupDTO);
        reviewCycleGroup.setCreatedBy(createdBy);
        reviewCycleGroup.setUpdatedBy(createdBy);
        
        ReviewCycleGroup savedReviewCycleGroup = reviewCycleGroupRepository.save(reviewCycleGroup);
        
        logger.info("Created ReviewCycleGroup with ID: {}", savedReviewCycleGroup.getReviewCycleGroupId());
        return reviewCycleGroupMapper.toDto(savedReviewCycleGroup);
    }

    public ReviewCycleGroupDTO update(Long id, ReviewCycleGroupDTO reviewCycleGroupDTO, String updatedBy) {
        logger.info("Updating ReviewCycleGroup with ID: {}", id);
        
        ReviewCycleGroup existingReviewCycleGroup = reviewCycleGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewCycleGroup", id));
        
        // Check if new name conflicts with existing groups (excluding current)
        if (reviewCycleGroupDTO.getReviewGroupName() != null && 
            !existingReviewCycleGroup.getReviewGroupName().equals(reviewCycleGroupDTO.getReviewGroupName()) &&
            reviewCycleGroupRepository.existsByReviewGroupName(reviewCycleGroupDTO.getReviewGroupName())) {
            throw new BusinessException("REVIEW_GROUP_EXISTS", 
                "ReviewCycleGroup with name already exists: " + reviewCycleGroupDTO.getReviewGroupName());
        }
        
        reviewCycleGroupMapper.updateEntityFromDto(reviewCycleGroupDTO, existingReviewCycleGroup);
        existingReviewCycleGroup.setUpdatedBy(updatedBy);
         
        ReviewCycleGroup updatedReviewCycleGroup = reviewCycleGroupRepository.save(existingReviewCycleGroup);
        
        logger.info("Updated ReviewCycleGroup with ID: {}", id);
        return reviewCycleGroupMapper.toDto(updatedReviewCycleGroup);
    }

    public void delete(Long id) {
        logger.info("Deleting ReviewCycleGroup with ID: {}", id);
        
        if (!reviewCycleGroupRepository.existsById(id)) {
            throw new ResourceNotFoundException("ReviewCycleGroup", id);
        }
        
        reviewCycleGroupRepository.deleteById(id);
        logger.info("Deleted ReviewCycleGroup with ID: {}", id);
    }

    // Custom Business Methods
    @Transactional(readOnly = true)
    public List<ReviewCycleGroupDTO> findByReviewCycleId(Long reviewCycleId) {
        logger.info("Finding ReviewCycleGroups by reviewCycleId: {}", reviewCycleId);
        List<ReviewCycleGroup> reviewCycleGroups = reviewCycleGroupRepository.findByReviewCycleId(reviewCycleId);
        return reviewCycleGroupMapper.toDtoList(reviewCycleGroups);
    }

    @Transactional(readOnly = true)
    public List<ReviewCycleGroupDTO> findByReviewTypeId(Long reviewTypeId) {
        logger.info("Finding ReviewCycleGroups by reviewTypeId: {}", reviewTypeId);
        List<ReviewCycleGroup> reviewCycleGroups = reviewCycleGroupRepository.findByReviewTypeId(reviewTypeId);
        return reviewCycleGroupMapper.toDtoList(reviewCycleGroups);
    }

    @Transactional(readOnly = true)
    public List<ReviewCycleGroupDTO> findByBooleanState(Boolean booleanState) {
        logger.info("Finding ReviewCycleGroups by booleanState: {}", booleanState);
        List<ReviewCycleGroup> reviewCycleGroups = reviewCycleGroupRepository.findByBooleanState(booleanState);
        return reviewCycleGroupMapper.toDtoList(reviewCycleGroups);
    }

    @Transactional(readOnly = true)
    public List<ReviewCycleGroupDTO> findByValueInRange(Integer value) {
        logger.info("Finding ReviewCycleGroups with value {} in range", value);
        List<ReviewCycleGroup> reviewCycleGroups = reviewCycleGroupRepository.findByValueInRange(value);
        return reviewCycleGroupMapper.toDtoList(reviewCycleGroups);
    }

    @Transactional(readOnly = true)
    public List<ReviewCycleGroupDTO> findByListOfIdisContaining(String idi) {
        logger.info("Finding ReviewCycleGroups containing IDI: {}", idi);
        List<ReviewCycleGroup> reviewCycleGroups = reviewCycleGroupRepository.findByListOfIdisContaining(idi);
        return reviewCycleGroupMapper.toDtoList(reviewCycleGroups);
    }

    @Transactional(readOnly = true)
    public Page<ReviewCycleGroupDTO> searchByGroupName(String reviewGroupName, Pageable pageable) {
        logger.info("Searching ReviewCycleGroups by name: {}", reviewGroupName);
        Page<ReviewCycleGroup> reviewCycleGroupPage = reviewCycleGroupRepository.findByReviewGroupNameContainingIgnoreCase(reviewGroupName, pageable);
        List<ReviewCycleGroupDTO> dtos = reviewCycleGroupMapper.toDtoList(reviewCycleGroupPage.getContent());
        return new PageImpl<>(dtos, pageable, reviewCycleGroupPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public long countByReviewCycleId(Long reviewCycleId) {
        return reviewCycleGroupRepository.countByReviewCycleId(reviewCycleId);
    }

    public boolean existsById(Long id) {
        return reviewCycleGroupRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return reviewCycleGroupRepository.count();
    }
}