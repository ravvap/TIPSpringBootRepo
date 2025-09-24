package gov.fdic.tip.service.base;

import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

/**
 * Base service interface with proper generic parameters
 */
public interface BaseService<E, ID, C, U, R> {
    
    // Read operations
    R findById(ID id);
    List<R> findAll();
    Page<R> findAllPaginated(int page, int size, String sortBy, String sortDirection);
    boolean existsById(ID id);
    long count();
    Optional<R> findByIdOptional(ID id);

    // Write operations
    R create(C createDTO);
    R update(ID id, U updateDTO);
    void delete(ID id);
    void softDelete(ID id);
    List<R> createAll(List<C> createDTOs);
}