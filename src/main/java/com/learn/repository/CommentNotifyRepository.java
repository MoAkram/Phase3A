package com.learn.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.model.CommentNotify;

@Repository
public interface CommentNotifyRepository extends CrudRepository<CommentNotify, Long> {
	@Query(value = "Select * from comment_notify where creator_id=?1", nativeQuery = true)
	ArrayList<CommentNotify> FindNotify(long user_id);
}