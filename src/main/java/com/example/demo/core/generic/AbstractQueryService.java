package com.example.demo.core.generic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface AbstractQueryService<T extends AbstractEntity> {

  List<T> findAll();

  List<T> findAll(Pageable pageable);

  T findById(UUID id);

  boolean existsById(UUID id);

}
