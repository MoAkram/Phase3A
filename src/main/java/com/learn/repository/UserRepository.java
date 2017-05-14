package com.learn.repository;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	@Query(value= "Select * from user where name=?1 and password=?2" , nativeQuery=true)
	User FindBynameAndpassword(String name,String password);
	@Query(value= "Select * from user where mail=?1" , nativeQuery=true)
	User FindBymail(String mail);
	@Query(value= "Select * from user where name=?1" , nativeQuery=true)
	User FindByname(String name);
	@Query(value= "Select * from user where id=?1" , nativeQuery=true)
	User FindById(Long id);
	@Query(value= "Select * from user where type='Teacher'" , nativeQuery=true)
	ArrayList<User> FindTeachers();
	
}