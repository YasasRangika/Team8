package com.team8.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.team8.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	@Query("select u from User u where username=?1 or phonenumber=?2 or email=?3")
	public User findUser(String uname, int pnumber, String email);
	
	@Query("select u from User u where username=?1 or phonenumber=?2")
	public User getLicense(String uname, int pnumber);
	
	@Query("select u from User u where u.userId=?1")
	public User getUserByUserId(String id);
}
