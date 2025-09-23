/**
 * 
 */
package gov.fdic.tip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * 
 */


@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    
	Page<T> findAll(Pageable pageable);
	List<T> findAll();
	Optional<T> findById(ID id);
	void deleteById(ID id);
	boolean existsById(ID id);
	
}
