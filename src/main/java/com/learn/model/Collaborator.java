package com.learn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Collaborator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	Long collabratorId;
	Long gameAssociated;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCollabratorId() {
		return collabratorId;
	}
	public void setCollabratorId(Long collabratorId) {
		this.collabratorId = collabratorId;
	}
	public Long getGameAssociated() {
		return gameAssociated;
	}
	public void setGameAssociated(Long gameAssociated) {
		this.gameAssociated = gameAssociated;
	}
	public Collaborator(Long id, Long collabratorId, Long gameAssociated) {
		super();
		this.id = id;
		this.collabratorId = collabratorId;
		this.gameAssociated = gameAssociated;
	}
	public Collaborator() {
		
	}
	
}
