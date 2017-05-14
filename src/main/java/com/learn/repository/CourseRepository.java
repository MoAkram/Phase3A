package com.learn.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.learn.model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
	@Query(value = "Select * from course where creatorID=?1", nativeQuery = true)
	ArrayList<Course> FindBycreatorID(long id);
	@Query(value = "Select * from course where Id=?1", nativeQuery = true)
	Course FindById(long id);
	@Query(value = "Select * from course", nativeQuery = true)
	ArrayList<Course> FindAllCourses();
}