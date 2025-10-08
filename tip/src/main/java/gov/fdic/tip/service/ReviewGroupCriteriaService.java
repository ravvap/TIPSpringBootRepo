package gov.fdic.tip.service;

import gov.fdic.tip.dto.ReviewGroupCriteriaDTO;
import gov.fdic.tip.entity.ReviewGroupCriteria;
import gov.fdic.tip.exception.ResourceNotFoundException;
import gov.fdic.tip.repository.ReviewGroupCriteriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Your Name
 * @Project TIP
 * @Module Review Group Criteria
 * @Date 2024-01-15
 * Service class for managing ReviewGroupCriteria entities.
 */
@Service
@Transactional
public class ReviewGroupCriteriaService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewGroupCriteriaService.class);

    @Autowired
    private ReviewGroupCriteriaRepository reviewGroupCriteriaRepository;

    @Autowired
    private ReviewGroupCriteriaMapper reviewGroupCriteriaMapper;

    // CRUD Operations
    
    @Transactional(readOnly = true)
    public ReviewGroupCriteriaDTO findById(Long id) {
        logger.info("Finding ReviewGroupCriteria with ID: {}", id);
        
        ReviewGroupCriteria criteria = reviewGroupCriteriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewGroupCriteria", id));
        
        return reviewGroupCriteriaMapper.toDto(criteria);
    }

    @Transactional(readOnly = true)
    public List<ReviewGroupCriteriaDTO> findAll() {
        logger.info("Finding all ReviewGroupCriteria");
        List<ReviewGroupCriteria> criteriaList = reviewGroupCriteriaRepository.findAll();
        return reviewGroupCriteriaMapper.toDtoList(criteriaList);
    }

    @Transactional(readOnly = true)
    public Page<ReviewGroupCriteriaDTO> findAllPaginated(Pageable pageable) {
        logger.info("Finding paginated ReviewGroupCriteria");
        
        Page<ReviewGroupCriteria> criteriaPage = reviewGroupCriteriaRepository.findAll(pageable);
        List<ReviewGroupCriteriaDTO> dtos = reviewGroupCriteriaMapper.toDtoList(criteriaPage.getContent());
        
        return new PageImpl<>(dtos, pageable, criteriaPage.getTotalElements());
    }

    public ReviewGroupCriteriaDTO create(ReviewGroupCriteriaDTO criteriaDTO, String createdBy) {
        logger.info("Creating new ReviewGroupCriteria with name: {}", criteriaDTO.getCriteriaName());
        
        // Check if criteria name already exists
        if (reviewGroupCriteriaRepository.existsByCriteriaName(criteriaDTO.getCriteriaName())) {
            throw new BusinessException("CRITERIA_EXISTS", 
                "ReviewGroupCriteria with name already exists: " + criteriaDTO.getCriteriaName());
        }
        
        ReviewGroupCriteria criteria = reviewGroupCriteriaMapper.toEntity(criteriaDTO);
        criteria.setCreatedBy(createdBy);
        criteria.setUpdatedBy(createdBy);
        
        ReviewGroupCriteria savedCriteria = reviewGroupCriteriaRepository.save(criteria);
        
        logger.info("Created ReviewGroupCriteria with ID: {}", savedCriteria.getReviewGroupCriteriaId());
        return reviewGroupCriteriaMapper.toDto(savedCriteria);
    }

    public ReviewGroupCriteriaDTO update(Long id, ReviewGroupCriteriaDTO criteriaDTO, String updatedBy) {
        logger.info("Updating ReviewGroupCriteria with ID: {}", id);
        
        ReviewGroupCriteria existingCriteria = reviewGroupCriteriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewGroupCriteria", id));
        
        // Check if new name conflicts with existing criteria (excluding current)
        if (criteriaDTO.getCriteriaName() != null && 
            !existingCriteria.getCriteriaName().equals(criteriaDTO.getCriteriaName()) &&
            reviewGroupCriteriaRepository.existsByCriteriaNameAndIdNot(criteriaDTO.getCriteriaName(), id)) {
            throw new BusinessException("CRITERIA_EXISTS", 
                "ReviewGroupCriteria with name already exists: " + criteriaDTO.getCriteriaName());
        }
        
        reviewGroupCriteriaMapper.updateEntityFromDto(criteriaDTO, existingCriteria);
        existingCriteria.setUpdatedBy(updatedBy);
         
        ReviewGroupCriteria updatedCriteria = reviewGroupCriteriaRepository.save(existingCriteria);
        
        logger.info("Updated ReviewGroupCriteria with ID: {}", id);
        return reviewGroupCriteriaMapper.toDto(updatedCriteria);
    }

    public void delete(Long id) {
        logger.info("Deleting ReviewGroupCriteria with ID: {}", id);
        
        if (!reviewGroupCriteriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("ReviewGroupCriteria", id);
        }
        
        reviewGroupCriteriaRepository.deleteById(id);
        logger.info("Deleted ReviewGroupCriteria with ID: {}", id);
    }

    // Custom Business Methods
    
    @Transactional(readOnly = true)
    public List<ReviewGroupCriteriaDTO> findByCriteriaType(GroupCriteriaType criteriaType) {
        logger.info("Finding ReviewGroupCriteria by type: {}", criteriaType);
        List<ReviewGroupCriteria> criteriaList = reviewGroupCriteriaRepository.findByCriteriaType(criteriaType);
        return reviewGroupCriteriaMapper.toDtoList(criteriaList);
    }

    @Transactional(readOnly = true)
    public List<ReviewGroupCriteriaDTO> findByCriteriaNameContaining(String criteriaName) {
        logger.info("Finding ReviewGroupCriteria by name containing: {}", criteriaName);
        List<ReviewGroupCriteria> criteriaList = reviewGroupCriteriaRepository.findByCriteriaNameContainingIgnoreCase(criteriaName);
        return reviewGroupCriteriaMapper.toDtoList(criteriaList);
    }

    @Transactional(readOnly = true)
    public Page<ReviewGroupCriteriaDTO> searchByCriteriaName(String criteriaName, Pageable pageable) {
        logger.info("Searching ReviewGroupCriteria by name: {}", criteriaName);
        Page<ReviewGroupCriteria> criteriaPage = reviewGroupCriteriaRepository.findByCriteriaNameContainingIgnoreCase(criteriaName, pageable);
        List<ReviewGroupCriteriaDTO> dtos = reviewGroupCriteriaMapper.toDtoList(criteriaPage.getContent());
        return new PageImpl<>(dtos, pageable, criteriaPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<ReviewGroupCriteriaDTO> findByCriteriaTypes(List<GroupCriteriaType> criteriaTypes) {
        logger.info("Finding ReviewGroupCriteria by multiple types: {}", criteriaTypes);
        List<ReviewGroupCriteria> criteriaList = reviewGroupCriteriaRepository.findByCriteriaTypeIn(criteriaTypes);
        return reviewGroupCriteriaMapper.toDtoList(criteriaList);
    }

    @Transactional(readOnly = true)
    public long countByCriteriaType(GroupCriteriaType criteriaType) {
        return reviewGroupCriteriaRepository.countByCriteriaType(criteriaType);
    }

    public boolean existsById(Long id) {
        return reviewGroupCriteriaRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByCriteriaName(String criteriaName) {
        return reviewGroupCriteriaRepository.existsByCriteriaName(criteriaName);
    }

    @Transactional(readOnly = true)
    public long count() {
        return reviewGroupCriteriaRepository.count();
    }
}