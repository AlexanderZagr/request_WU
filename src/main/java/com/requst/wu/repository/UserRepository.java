package com.requst.wu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.requst.wu.model.User;

@Repository("userRepositoryNew")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
}
