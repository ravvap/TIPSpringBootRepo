package gov.fdic.tip.controller.base;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

public interface BaseController<T, ID, CreateDTO, UpdateDTO, ResponseDTO> {
    
    ResponseEntity<ResponseDTO> getById(ID id);
    
    ResponseEntity<List<ResponseDTO>> getAll();
    
    ResponseEntity<Page<ResponseDTO>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection);
    
    ResponseEntity<ResponseDTO> create(@Valid @RequestBody CreateDTO createDTO);
    
    ResponseEntity<ResponseDTO> update(ID id, @Valid @RequestBody UpdateDTO updateDTO);
    
    ResponseEntity<Void> delete(ID id);
    
    ResponseEntity<Void> softDelete(ID id);
}
