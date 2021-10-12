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


@Service("movieService")
public class BookServiceImpl implements BookService {

	@Autowired
	private BookJpaRepository repository;
	
	@Override
	public BookDTO insert(BookDTO movie) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		BookEntity bookEntity = modelMapper.map(movie, BookEntity.class);
		
		bookEntity.setId(UUID.randomUUID().toString());
		
		bookEntity = repository.save(bookEntity);
		
		movie = modelMapper.map(movie, BookDTO.class);
		
		return movie;
	}
	
	@Override
	public BookDTO update(BookDTO movie) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		BookEntity bookEntity = modelMapper.map(movie, BookEntity.class);
		
		bookEntity = repository.save(bookEntity);
		
		movie = modelMapper.map(movie, BookDTO.class);
		
		return movie;
	}

	@Override
	public BookDTO findById(String id) {
		BookEntity movie = repository.findById(id).orElse(null);
		
		if(movie == null)
			return null;
		
		BookDTO bookDTO = new ModelMapper().map(movie, BookDTO.class);
		
		return bookDTO;
	}
	
	@Override
	public List<BookDTO> getAll() {
		ArrayList<BookEntity> movies = (ArrayList<BookEntity>) repository.findAll();
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<BookDTO> moviesDTO = movies
				  .stream()
				  .map(movie -> modelMapper.map(movie, BookDTO.class))
				  .collect(Collectors.toList());
		
		return moviesDTO;
	}

	@Override
	public void delete(String id) {
		repository.deleteById(id);
	}

}
