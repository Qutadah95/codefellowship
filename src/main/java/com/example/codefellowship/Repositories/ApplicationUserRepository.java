package com.example.codefellowship.Repositories;

import com.example.codefellowship.Models.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser,Integer> {
    ApplicationUser findByUsername(String username);

    List<Object> findAllById(long id);
}
