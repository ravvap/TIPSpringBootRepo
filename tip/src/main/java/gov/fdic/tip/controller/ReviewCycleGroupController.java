package gov.fdic.tip.controller;

import gov.fdic.tip.dto.ReviewCycleGroupDTO;
import gov.fdic.tip.service.ReviewCycleGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Prasad Ravva
 * @Project TIP
 * @Module Review Cycle Group
 * @Date 9/28/2025
 * REST controller for managing Review Cycle Groups.
 */

@RestController
@RequestMapping("/api/v1/review-cycle-groups")
@Tag(name = "Review Cycle Groups", description = "APIs for managing Review Cycle Groups")
@Validated
public class ReviewCycleGroupController {

    @Autowired
    private ReviewCycleGroupService reviewCycleGroupService;

    @GetMapping
    @Operation(summary = "Get all review cycle groups")
    public ResponseEntity<List<ReviewCycleGroupDTO>> getAllReviewCycleGroups() {
        List<ReviewCycleGroupDTO> reviewCycleGroups = reviewCycleGroupService.findAll();
        return ResponseEntity.ok(reviewCycleGroups);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get all review cycle groups with pagination")
    public ResponseEntity<Page<ReviewCycleGroupDTO>> getAllReviewCycleGroupsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "reviewCycleGroupId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ReviewCycleGroupDTO> reviewCycleGroupPage = reviewCycleGroupService.findAllPaginated(pageable);
        return ResponseEntity.ok(reviewCycleGroupPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review cycle group by ID")
    public ResponseEntity<ReviewCycleGroupDTO> getReviewCycleGroupById(@PathVariable Long id) {
        ReviewCycleGroupDTO reviewCycleGroup = reviewCycleGroupService.findById(id);
        return ResponseEntity.ok(reviewCycleGroup);
    }

    @PostMapping
    @Operation(summary = "Create a new review cycle group")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(ReviewCycleGroupDTO.CreateGroup.class)
    public ResponseEntity<ReviewCycleGroupDTO> createReviewCycleGroup(
            @Valid @RequestBody ReviewCycleGroupDTO reviewCycleGroupDTO,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String createdBy) {
        ReviewCycleGroupDTO createdReviewCycleGroup = reviewCycleGroupService.create(reviewCycleGroupDTO, createdBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReviewCycleGroup);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing review cycle group")
    public ResponseEntity<ReviewCycleGroupDTO> updateReviewCycleGroup(
            @PathVariable Long id,
            @Valid @RequestBody ReviewCycleGroupDTO reviewCycleGroupDTO,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String updatedBy) {
        ReviewCycleGroupDTO updatedReviewCycleGroup = reviewCycleGroupService.update(id, reviewCycleGroupDTO, updatedBy);
        return ResponseEntity.ok(updatedReviewCycleGroup);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review cycle group")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReviewCycleGroup(@PathVariable Long id) {
        reviewCycleGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Custom endpoints
    @GetMapping("/review-cycle/{reviewCycleId}")
    @Operation(summary = "Get review cycle groups by review cycle ID")
    public ResponseEntity<List<ReviewCycleGroupDTO>> getReviewCycleGroupsByReviewCycleId(@PathVariable Long reviewCycleId) {
        List<ReviewCycleGroupDTO> reviewCycleGroups = reviewCycleGroupService.findByReviewCycleId(reviewCycleId);
        return ResponseEntity.ok(reviewCycleGroups);
    }

    @GetMapping("/review-type/{reviewTypeId}")
    @Operation(summary = "Get review cycle groups by review type ID")
    public ResponseEntity<List<ReviewCycleGroupDTO>> getReviewCycleGroupsByReviewTypeId(@PathVariable Long reviewTypeId) {
        List<ReviewCycleGroupDTO> reviewCycleGroups = reviewCycleGroupService.findByReviewTypeId(reviewTypeId);
        return ResponseEntity.ok(reviewCycleGroups);
    }

    @GetMapping("/boolean-state/{booleanState}")
    @Operation(summary = "Get review cycle groups by boolean state")
    public ResponseEntity<List<ReviewCycleGroupDTO>> getReviewCycleGroupsByBooleanState(@PathVariable Boolean booleanState) {
        List<ReviewCycleGroupDTO> reviewCycleGroups = reviewCycleGroupService.findByBooleanState(booleanState);
        return ResponseEntity.ok(reviewCycleGroups);
    }

    @GetMapping("/range/{value}")
    @Operation(summary = "Get review cycle groups where value falls in range")
    public ResponseEntity<List<ReviewCycleGroupDTO>> getReviewCycleGroupsByValueInRange(@PathVariable Integer value) {
        List<ReviewCycleGroupDTO> reviewCycleGroups = reviewCycleGroupService.findByValueInRange(value);
        return ResponseEntity.ok(reviewCycleGroups);
    }

    @GetMapping("/idi/{idi}")
    @Operation(summary = "Get review cycle groups containing specific IDI")
    public ResponseEntity<List<ReviewCycleGroupDTO>> getReviewCycleGroupsByListOfIdis(@PathVariable String idi) {
        List<ReviewCycleGroupDTO> reviewCycleGroups = reviewCycleGroupService.findByListOfIdisContaining(idi);
        return ResponseEntity.ok(reviewCycleGroups);
    }

    @GetMapping("/search")
    @Operation(summary = "Search review cycle groups by name")
    public ResponseEntity<Page<ReviewCycleGroupDTO>> searchReviewCycleGroups(
            @RequestParam String reviewGroupName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewCycleGroupDTO> reviewCycleGroups = reviewCycleGroupService.searchByGroupName(reviewGroupName, pageable);
        return ResponseEntity.ok(reviewCycleGroups);
    }

    @GetMapping("/review-cycle/{reviewCycleId}/count")
    @Operation(summary = "Get count of review cycle groups by review cycle ID")
    public ResponseEntity<Long> getCountByReviewCycleId(@PathVariable Long reviewCycleId) {
        long count = reviewCycleGroupService.countByReviewCycleId(reviewCycleId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Check if review cycle group exists")
    public ResponseEntity<Boolean> checkExists(@PathVariable Long id) {
        boolean exists = reviewCycleGroupService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count")
    @Operation(summary = "Get total count of review cycle groups")
    public ResponseEntity<Long> getCount() {
        long count = reviewCycleGroupService.count();
        return ResponseEntity.ok(count);
    }
}