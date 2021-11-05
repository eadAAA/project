package com.example.penalties.repositories;

import com.example.penalties.models.Books;
import org.springframework.data.repository.CrudRepository;

public interface BooksRepository extends CrudRepository<Books, Long> {
}
