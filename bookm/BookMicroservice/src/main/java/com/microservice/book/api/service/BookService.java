package com.microservice.book.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.microservice.commons.dto.BookDTO;
import org.springframework.web.client.RestTemplate;


@Service
public interface BookService {
	@Autowired
	RestTemplate restTemplate;
	@HystrixCommand(fallbackMethod = "callBookService_Fallback")
	public String callBookService(String group) {
		String response = restTemplate
				.exchange("http://localhost:8077/getBookDetailsByGroup/{group}"
						, HttpMethod.GET
						, null
						, new ParameterizedTypeReference<String>() {
						}, group).getBody();

		return "It's OK: group: " + group + " users details " + response + new Date();
	}

	@SuppressWarnings("unused")
	private String callUserService_Fallback(String group) {
		return "Error! " + new Date();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	BookDTO insert(BookDTO movie);
	

	BookDTO update(BookDTO movie);

	public List<BookDTO> getAll();
	

	public BookDTO findById(String id);

	public void delete(String id);

}
