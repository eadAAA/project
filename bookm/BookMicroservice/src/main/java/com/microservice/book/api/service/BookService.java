package com.microservice.book.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.commons.dto.BookDTO;


@Service
public interface BookService {


	BookDTO insert(BookDTO movie);
	

	BookDTO update(BookDTO movie);

	public List<BookDTO> getAll();
	

	public BookDTO findById(String id);

	public void delete(String id);

}
