package com.abcwebportal.webportal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.abcwebportal.webportal.model.user.User;
/**
 * {@link UserRepository} extends {@link CrudRepository} will utilize jpa capabilities
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	/*@Query("select u from users u where u.userName = :userName")
	User findByUsername(@Param("userName")String username);*/
	
	User findByUsername(String username);
	
	//@Query("select u from user u where u.id = :id")
	//User findByUserId(//@Param(value = "id") Long id);
	User getById(Long userId);
}
