package com.microservice.book.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.commons.dto.BookDTO;
import com.microservice.commons.entity.BookEntity;
import com.microservice.book.api.repository.BookJpaRepository;


@Service("bookService")
public class BookServiceImpl implements BookService {

	@Autowired
	private BookJpaRepository repository;
	
	@Override
	public BookDTO insert(BookDTO book) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		BookEntity bookEntity = modelMapper.map(book, BookEntity.class);
		
		bookEntity.setId(UUID.randomUUID().toString());
		
		bookEntity = repository.save(bookEntity);
		
		book = modelMapper.map(book, BookDTO.class);
		
		return book;
	}
	
	@Override
	public BookDTO update(BookDTO book) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		BookEntity bookEntity = modelMapper.map(book, BookEntity.class);
		
		bookEntity = repository.save(bookEntity);


		book = modelMapper.map(book, BookDTO.class);
		
		return book;
	}

	@Override
	public BookDTO findById(String id) {
		BookEntity book = repository.findById(id).orElse(null);
		
		if(book == null)

			return null;
		
		BookDTO bookDTO = new ModelMapper().map(book, BookDTO.class);
		
		return bookDTO;
	}
	
	@Override
	public List<BookDTO> getAll() {
		ArrayList<BookEntity> books = (ArrayList<BookEntity>) repository.findAll();
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<BookDTO> bookDTOS = books
				  .stream()
				  .map(book -> modelMapper.map(book, BookDTO.class))
				  .collect(Collectors.toList());
		
		return bookDTOS;

	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

}
