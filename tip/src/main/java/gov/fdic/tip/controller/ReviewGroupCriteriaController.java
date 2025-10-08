package gov.fdic.tip.controller;

import gov.fdic.tip.dto.ReviewGroupCriteriaDTO;
import gov.fdic.tip.enums.GroupCriteriaType;
import gov.fdic.tip.service.ReviewGroupCriteriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Your Name
 * @Project TIP
 * @Module Review Group Criteria
 * @Date 2024-01-15
 * Controller for Review Group Criteria operations.
 */
@RestController
@RequestMapping("/api/v1/review-group-criteria")
@Tag(name = "Review Group Criteria", description = "APIs for managing review group criteria in the TIP system")
public class ReviewGroupCriteriaController {

    private final ReviewGroupCriteriaService reviewGroupCriteriaService;

    public ReviewGroupCriteriaController(ReviewGroupCriteriaService reviewGroupCriteriaService) {
        this.reviewGroupCriteriaService = reviewGroupCriteriaService;
    }

    @Operation(
        summary = "Get review group criteria by ID",
        description = "Retrieves a specific review group criteria by its unique identifier"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Review group criteria found successfully"),
        @ApiResponse(responseCode = "404", description = "Review group criteria not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReviewGroupCriteriaDTO> getById(
            @Parameter(description = "ID of the review group criteria to retrieve", example = "1", required = true)
            @PathVariable Long id) {
        ReviewGroupCriteriaDTO criteria = reviewGroupCriteriaService.findById(id);
        return ResponseEntity.ok(criteria);
    }

    @Operation(
        summary = "Get all review group criteria",
        description = "Retrieves a list of all review group criteria in the system"
    )
    @ApiResponse(responseCode = "200", description = "List of review group criteria retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ReviewGroupCriteriaDTO>> getAll() {
        List<ReviewGroupCriteriaDTO> criteriaList = reviewGroupCriteriaService.findAll();
        return ResponseEntity.ok(criteriaList);
    }

    @Operation(
        summary = "Get paginated review group criteria",
        description = "Retrieves review group criteria with pagination support"
    )
    @ApiResponse(responseCode = "200", description = "Paginated list of review group criteria retrieved successfully")
    @GetMapping("/paginated")
    public ResponseEntity<Page<ReviewGroupCriteriaDTO>> getAllPaginated(
            @Parameter(description = "Pagination parameters")
            @PageableDefault(size = 20, sort = "criteriaName") Pageable pageable) {
        Page<ReviewGroupCriteriaDTO> criteriaPage = reviewGroupCriteriaService.findAllPaginated(pageable);
        return ResponseEntity.ok(criteriaPage);
    }

    @Operation(
        summary = "Create a new review group criteria",
        description = "Creates a new review group criteria with the provided details"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Review group criteria created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "409", description = "Review group criteria with same name already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReviewGroupCriteriaDTO> create(
            @Parameter(description = "Review group criteria details to create", required = true)
            @Valid @RequestBody ReviewGroupCriteriaDTO criteriaDTO,
            
            @Parameter(description = "Username of the creator", example = "system-admin", required = true)
            @RequestHeader("X-User-Id") String createdBy) {
        
        ReviewGroupCriteriaDTO createdCriteria = reviewGroupCriteriaService.create(criteriaDTO, createdBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCriteria);
    }

    @Operation(
        summary = "Update an existing review group criteria",
        description = "Updates the details of an existing review group criteria"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Review group criteria updated successfully"),
        @ApiResponse(responseCode = "404", description = "Review group criteria not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Review group criteria with same name already exists", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReviewGroupCriteriaDTO> update(
            @Parameter(description = "ID of the review group criteria to update", example = "1", required = true)
            @PathVariable Long id,
            
            @Parameter(description = "Updated review group criteria details", required = true)
            @Valid @RequestBody ReviewGroupCriteriaDTO criteriaDTO,
            
            @Parameter(description = "Username of the updater", example = "system-admin", required = true)
            @RequestHeader("X-User-Id") String updatedBy) {
        
        ReviewGroupCriteriaDTO updatedCriteria = reviewGroupCriteriaService.update(id, criteriaDTO, updatedBy);
        return ResponseEntity.ok(updatedCriteria);
    }

    @Operation(
        summary = "Delete a review group criteria",
        description = "Deletes a review group criteria by its ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Review group criteria deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Review group criteria not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the review group criteria to delete", example = "1", required = true)
            @PathVariable Long id) {
        
        reviewGroupCriteriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Find criteria by type",
        description = "Retrieves all review group criteria of a specific type"
    )
    @ApiResponse(responseCode = "200", description = "List of review group criteria retrieved successfully")
    @GetMapping("/by-type/{criteriaType}")
    public ResponseEntity<List<ReviewGroupCriteriaDTO>> findByCriteriaType(
            @Parameter(description = "Criteria type to filter by", example = "FINANCIAL", required = true)
            @PathVariable GroupCriteriaType criteriaType) {
        
        List<ReviewGroupCriteriaDTO> criteriaList = reviewGroupCriteriaService.findByCriteriaType(criteriaType);
        return ResponseEntity.ok(criteriaList);
    }

    @Operation(
        summary = "Search criteria by name",
        description = "Searches review group criteria by name with pagination support"
    )
    @ApiResponse(responseCode = "200", description = "Paginated search results retrieved successfully")
    @GetMapping("/search")
    public ResponseEntity<Page<ReviewGroupCriteriaDTO>> searchByCriteriaName(
            @Parameter(description = "Criteria name to search for", example = "Quality", required = true)
            @RequestParam String criteriaName,
            
            @Parameter(description = "Pagination parameters")
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<ReviewGroupCriteriaDTO> results = reviewGroupCriteriaService.searchByCriteriaName(criteriaName, pageable);
        return ResponseEntity.ok(results);
    }

    @Operation(
        summary = "Count criteria by type",
        description = "Returns the count of review group criteria for a specific type"
    )
    @ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    @GetMapping("/count/by-type/{criteriaType}")
    public ResponseEntity<Long> countByCriteriaType(
            @Parameter(description = "Criteria type to count by", example = "FINANCIAL", required = true)
            @PathVariable GroupCriteriaType criteriaType) {
        
        long count = reviewGroupCriteriaService.countByCriteriaType(criteriaType);
        return ResponseEntity.ok(count);
    }

    @Operation(
        summary = "Check if criteria exists",
        description = "Checks if a review group criteria exists by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Existence check completed")
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(
            @Parameter(description = "ID to check existence for", example = "1", required = true)
            @PathVariable Long id) {
        
        boolean exists = reviewGroupCriteriaService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @Operation(
        summary = "Get all criteria types",
        description = "Retrieves all available criteria types"
    )
    @ApiResponse(responseCode = "200", description = "List of criteria types retrieved successfully")
    @GetMapping("/types")
    public ResponseEntity<GroupCriteriaType[]> getAllCriteriaTypes() {
        GroupCriteriaType[] types = GroupCriteriaType.values();
        return ResponseEntity.ok(types);
    }
}