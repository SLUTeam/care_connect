package com.app.config;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Extend this repository if you only need to read data.
 * 
 * Having our own repository base classes allows us to only selectively expose CRUD methods. I.e., a child of this 
 * class must explicitly declare the additional methods that it wants - it won't get them automatically by inheritance.
 * (So in this case, it will NOT get a save() method.) Note that the methods declared here and in child
 * repositories must exactly match the method signatures defined in JpaRepository, CrudRepository and 
 * PagingAndSortingRepository.
 */
@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {
    
    Optional<T> findById(ID id);
    List<T> findAllById(Iterable<ID> ids);
    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    long count();
}
