package com.example.coffee_shop_manage_api.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractCommonService<T, ID> {
 protected final JpaRepository<T, ID> repository;

 protected AbstractCommonService(JpaRepository<T, ID> repository) {
  this.repository = repository;
 }

 public T create(T entity) {
  return repository.save(entity);
 }

 @Transactional(readOnly = true)
 public List<T> findAll() {
  return repository.findAll();
 }

 @Transactional(readOnly = true)
 public Optional<T> findById(ID id) {
  if (!repository.existsById(id)) {
   throw new IllegalArgumentException("Entity with ID " + id + " not found");
  }
  return repository.findById(id);
 }

 public void delete(ID id) {
  if (!repository.existsById(id)) {
   throw new IllegalArgumentException("Entity with ID " + id + " not found");
  }
  repository.deleteById(id);
 }

}
