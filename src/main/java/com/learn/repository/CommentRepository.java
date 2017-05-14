package com.learn.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	@Query(value = "Select * from comment where game_id=?1", nativeQuery = true)
	ArrayList<Comment> FindComments(long game_id);
}
