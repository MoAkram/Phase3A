package com.learn.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.model.Collaborator;


@Repository
public interface CollaboratorRepository extends CrudRepository<Collaborator, Long> {
	@Query(value = "Select * from collaborator where collabrator_id=?1", nativeQuery = true)
	Collaborator FindCollab(long user_id);
}