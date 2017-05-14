package com.learn.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.model.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
	@Query(value = "Select * from question where game_id=?1", nativeQuery = true)
	ArrayList<Question> FindByGameId(long id);
}
