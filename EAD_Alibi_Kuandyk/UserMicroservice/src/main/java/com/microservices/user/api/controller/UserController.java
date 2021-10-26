package com.microservices.user.api.controller;

import javax.validation.Valid;

import com.microservices.user.api.model.dto.UserDTO;
import com.microservices.user.api.model.request.UserCreateRequestModel;
import com.microservices.user.api.service.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private IUserService service;
	
	@RestController
	public class UsersServiceController {

	private static Map<String, List<Users>> map = new HashMap<>();

	    static {
		map = new HashMap<>();

		List<Users> list = new ArrayList();
		Users users = new Users("Ben Fisk", 40);
		list.add(users);
		users = new Users("Nikolas Cage", 42);
		list.add(users);

		map.put("coolman", list);

		list = new ArrayList();
		users = new Users("James Smith", 43);
		list.add(users);
		users = new Users("John Winston", 41);
		list.add(users);

		map.put("badboy", list);

	    }

	    @RequestMapping(value = "/getUsersDetailsByGroup/{group}", method = RequestMethod.GET)
	    public List<Users> getUsers(@PathVariable String group) {
		List<Users> usersList = map.get(group);

		if (usersList == null || usersList.isEmpty()) {
		    usersList = new ArrayList<>();
		    Users users = new Users("Users not found", null);
		    usersList.add(users);
		}

		return usersList;
	    }

}


	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserCreateRequestModel userRequest) {
		if(service.findByEmail(userRequest.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
		userDTO = service.insert(userDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
	}

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserDTO> getById(@PathVariable("id") String id) {
		UserDTO userDto = service.findById(id);
		return (userDto != null) ? ResponseEntity.status(HttpStatus.OK).body(userDto) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

}
