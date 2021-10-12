package com.microservice.book.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.microservice.commons.entity.BookEntity;


public interface BookJpaRepository extends CrudRepository<BookEntity, String> {

}
