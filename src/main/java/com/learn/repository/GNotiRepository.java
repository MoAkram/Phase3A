package com.learn.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.model.NewGameNotification;

@Repository
public interface GNotiRepository extends CrudRepository<NewGameNotification, Long> {
	@Query(value = "Select * from new_game_notification where user_id=?1", nativeQuery = true)
	ArrayList<NewGameNotification> FindNotify(long user_id);
}