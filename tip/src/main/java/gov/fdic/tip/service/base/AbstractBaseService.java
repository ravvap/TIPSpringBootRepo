package gov.fdic.tip.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract base service providing common CRUD operations with proper generics
 */
@Transactional(readOnly = true)
public abstract class AbstractBaseService<E, ID, C, U, R> {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final JpaRepository<E, ID> repository = null;
}