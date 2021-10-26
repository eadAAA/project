package com.microservice.category.api.request;

import javax.validation.constraints.NotNull;

public class CategoryCreateRequestModel {

	@NotNull(message = "name cannot be null")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
