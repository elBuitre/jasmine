package com.bui3;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Location {

	@Id
	private String id;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="parent_id")
	private Location parent;
	
	@OneToMany(mappedBy="parent")
	private List<Location> children;
	
	public Location() {}
	
	public Location(String id) {
		this(id, null);
	}
	
	public Location(String id, Location parent) {
		setId(id);
		setParent(parent);
		
		if (null != parent)
			parent.addChild(this);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Location getParent() {
		return parent;
	}
	
	public void setParent(Location parent) {
		this.parent = parent;
	}
	
	public List<Location> getChildren() {
		return children;
	}
	
	public void setChildren(List<Location> children) {
		this.children = children;
	}
	
	public void addChild(Location child) {
		children.add(child);
	}
	
	public void removeChild(Location child) {
		if (children.contains(child))
			children.remove(child);
	}
	
	@Override
	public String toString() {

		return null == parent ? id : parent.id + id;
	}
}
