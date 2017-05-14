package com.learn.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.model.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
	@Query(value = "Select * from game where name=?1 and cancelled=0 order by id Desc limit 1", nativeQuery = true)
	Game FindByName(String name);
	@Query(value = "Select * from game where courseid=?1 and cancelled=0", nativeQuery = true)
	ArrayList<Game> FindByCourseId(long id);
	@Query(value = "Select * from game where teacherid=?1 and cancelled=0", nativeQuery = true)
	ArrayList<Game> FindByTeacherId(long id);
	@Query(value = "Select * from game where id=?1", nativeQuery = true)
	Game FindById(long id);
	@Query(value = "Select * from game where cancelled=0", nativeQuery = true)
	ArrayList<Game> FindAllGames();
	@Query(value = "Select * from game where teacherid=?1 and cancelled=1", nativeQuery = true)
	ArrayList<Game> FindRetiredByTeacherId(long id);
	
}
