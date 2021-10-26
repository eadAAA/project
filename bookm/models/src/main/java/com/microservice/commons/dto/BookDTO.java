package com.microservice.commons.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class BookDTO {
	

	private String id;
	

	private String title;


	@JsonBackReference
	public String getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setIsbn(int isbn) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean equals(final Object o) {
		if (o == this) return true;
		if (!(o instanceof BookDTO)) return false;
		final BookDTO other = (BookDTO) o;
		if (!other.canEqual((Object) this)) return false;
		if (this.getId() != other.getId()) return false;
		final Object this$name = this.getTitle();
		final Object other$name = other.getTitle();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		return true;
	}

	protected boolean canEqual(final Object other) {
		return other instanceof BookDTO;
	}


	public String toString() {
		return "BookDTO(isbn=" + this.getId() + ", name=" + this.getTitle() + ", description=" + ")";
	}

}
