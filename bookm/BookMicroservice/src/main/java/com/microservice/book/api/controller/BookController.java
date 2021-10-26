package com.microservice.book.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.microservice.book.api.request.BookCreateRequestModel;
import com.microservice.book.api.service.BookService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.commons.dto.CategoryDTO;
import com.microservice.commons.dto.BookDTO;
import com.microservice.book.api.request.BookUpdateRequestModel;


@RestController
@RequestMapping(path = "/book")
public class BookController {

	@Qualifier("bookService")
	@Autowired
	private BookService service;


	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BookDTO> insert(@Valid @RequestBody BookCreateRequestModel bookRequest) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		BookDTO bookDTO = modelMapper.map(bookRequest, BookDTO.class);
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(bookRequest.getCategoryId());
		bookDTO.setCategory(categoryDTO);
		
		bookDTO = service.insert(bookDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
	}


	@GetMapping(value = "/{bookId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BookDTO> getById(@PathVariable("bookId") String movieId) {
		BookDTO bookDTO = service.findById(movieId);
		return (bookDTO != null) ? ResponseEntity.status(HttpStatus.OK).body(bookDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}


	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<BookDTO>> getAll() {
		List<BookDTO> book = service.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(book);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BookDTO> update(@Valid @RequestBody BookUpdateRequestModel bookRequest) {
		if(service.findById(bookRequest.getId()) == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		BookDTO bookDTO = modelMapper.map(bookRequest, BookDTO.class);
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(bookRequest.getCategoryId());
		bookDTO.setCategory(categoryDTO);
		
		bookDTO = service.update(bookDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
	}


	@DeleteMapping(path = "/{bookId}")
	public ResponseEntity<Void> delete(@PathVariable String bookId) {
		if(service.findById(bookId) == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		service.delete(bookId);
		return ResponseEntity.ok().build();
	}

}
