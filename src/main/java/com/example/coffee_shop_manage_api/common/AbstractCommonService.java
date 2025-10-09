package com.example.coffee_shop_manage_api.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
// removed unused PagingAndSortingRepository import
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Transactional
public abstract class AbstractCommonService<T, ID_TYPE> {
 protected final JpaRepository<T, ID_TYPE> repository;

 protected AbstractCommonService(JpaRepository<T, ID_TYPE> repository) {
  this.repository = repository;
 }

 public T create(T entity) {
  return repository.save(entity);
 }

 @Transactional(readOnly = true)
 public Optional<T> findById(ID_TYPE id) {
  if (!repository.existsById(id)) {
   throw new IllegalArgumentException("Entity with ID " + id + " not found");
  }
  return repository.findById(id);
 }

 public void delete(ID_TYPE id) {
  if (!repository.existsById(id)) {
   throw new IllegalArgumentException("Entity with ID " + id + " not found");
  }
  repository.deleteById(id);
 }

 public Page<T> findAll(Pageable pageable) {
  return repository.findAll(pageable);
 }

 public List<T> createAll(List<T> entities) {
  return repository.saveAll(entities);
 }

 public void deleteAll(List<ID_TYPE> ids) {
  repository.deleteAllById(ids);
 }

}
