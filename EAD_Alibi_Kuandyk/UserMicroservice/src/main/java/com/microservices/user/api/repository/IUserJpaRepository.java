package com.microservices.user.api.repository;

import com.microservices.user.api.model.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserJpaRepository extends MongoRepository<UserEntity, String> {
	UserEntity findByEmail(String email);

	UserEntity findUserById(String id);
	
}
