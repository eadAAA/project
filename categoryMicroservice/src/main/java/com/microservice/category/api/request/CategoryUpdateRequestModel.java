package com.microservice.category.api.request;

import javax.validation.constraints.NotNull;

public class CategoryUpdateRequestModel extends CategoryCreateRequestModel {

	@NotNull(message = "id cannot be null")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
