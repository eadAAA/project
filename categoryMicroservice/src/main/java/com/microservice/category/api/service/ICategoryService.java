package com.microservice.category.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.commons.dto.CategoryDTO;

@Service
public interface ICategoryService {

	public CategoryDTO insert(CategoryDTO category);

	public CategoryDTO update(CategoryDTO category);

	public List<CategoryDTO> getAll();

	public CategoryDTO findById(String id);

	public void delete(String id);
	
}
