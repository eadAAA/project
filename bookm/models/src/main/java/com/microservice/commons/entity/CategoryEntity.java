package com.microservice.commons.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "category")
public class CategoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@Id
	@Column(name = "id")
	private String id;
	

	@Column(name = "name", nullable = false, length = 50)
	private String name;
	

	@JsonManagedReference
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<BookEntity> books = new ArrayList<BookEntity>();

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


	public List<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
