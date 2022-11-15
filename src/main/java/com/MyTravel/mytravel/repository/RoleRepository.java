package com.MyTravel.mytravel.repository;

import com.MyTravel.mytravel.model.ERole;
import com.MyTravel.mytravel.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
