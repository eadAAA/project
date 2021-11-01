package com.example.catalog.repositories;

import com.example.catalog.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long> { }
