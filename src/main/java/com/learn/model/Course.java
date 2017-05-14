package com.learn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {
	
	public Course(){};
	public Course(long id, String name, String description, long creatorID) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.creatorID = creatorID;
	}
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	private long creatorID;


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Course(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public long getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(long creatorID) {
		this.creatorID = creatorID;
	}
}
