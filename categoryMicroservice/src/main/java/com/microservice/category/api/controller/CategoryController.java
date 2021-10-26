package com.microservice.category.api.controller;

import com.microservice.category.api.request.CategoryCreateRequestModel;
import com.microservice.category.api.request.CategoryUpdateRequestModel;
import com.microservice.category.api.service.ICategoryService;
import com.microservice.commons.dto.CategoryDTO;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

	@Autowired
	private ICategoryService service;

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryCreateRequestModel categoryRequest) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		CategoryDTO categoryDTO = modelMapper.map(categoryRequest, CategoryDTO.class);
		
		categoryDTO = service.insert(categoryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/{categoryId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CategoryDTO> getById(@PathVariable("categoryId") String categoryId) throws NotFoundException {
		CategoryDTO categoryDTO = service.findById(categoryId);
		return (categoryDTO != null) ? ResponseEntity.status(HttpStatus.OK).body(categoryDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<CategoryDTO>> getAll() {
		List<CategoryDTO> categories = service.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(categories);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryUpdateRequestModel categoryRequest) {
		if(service.findById(categoryRequest.getId()) == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		CategoryDTO categoryDTO = modelMapper.map(categoryRequest, CategoryDTO.class);
		
		categoryDTO = service.update(categoryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@DeleteMapping(path = "/{categoryId}")
	public ResponseEntity<Void> delete(@PathVariable String categoryId) {
		if(service.findById(categoryId) == null)
			return ResponseEntity.notFound().build();
		
		service.delete(categoryId);
		return ResponseEntity.ok().build();
	}

}
