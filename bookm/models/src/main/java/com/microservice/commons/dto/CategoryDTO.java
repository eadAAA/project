package com.microservice.commons.dto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {

	private String id;

	private String name;

	private List<BookDTO> books = new ArrayList<BookDTO>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}
}
