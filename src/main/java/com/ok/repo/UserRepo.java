package com.ok.repo;

import com.ok.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
