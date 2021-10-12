package com.microservice.book.api.request;

import javax.validation.constraints.NotNull;


public class BookUpdateRequestModel extends BookCreateRequestModel {
	

	@NotNull(message = "id cannot be null")
	private String id;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

}
