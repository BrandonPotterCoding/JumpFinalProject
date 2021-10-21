package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // make sure our data loads fast enough w/o getting error
public class User implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	public enum Role {
		ROLE_USER, ROLE_ADMIN
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@Column(unique=true)
	private String displayname;
	
	@Column(unique = true)
	private String email;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 
	private List<Review> reviews;
	
	public User() {
		this(-1L, "N/A", "N/A", false,Role.ROLE_USER, "NA", "NA", new ArrayList<>());
	}

	public User(Long id, String username, String password, boolean enabled, Role role, String displayname, String email,
			List<Review> reviews) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
		this.displayname = displayname;
		this.email = email;
		this.reviews = reviews;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void addReview(Review review) {
		this.reviews.add(review);
		review.setUser(this);
	}
	public String toJson() {
		
		return "{\"id\" : " + id
				+ ", \"username\" : \"" + username + "\""
				+ ", \"password\" : \"" + password + "\""
				+ ", \"displayname\" : \"" + displayname + "\""
				+ ", \"role\" : \"" + role + "\""
				+ ", \"email\" : \"" + email + "\""
				+ ", \"enabled\" : \"" + enabled + "\""
				+ ", \"reviews\" : \"" + reviews + "\"" +
		"}";
	}
	
}
