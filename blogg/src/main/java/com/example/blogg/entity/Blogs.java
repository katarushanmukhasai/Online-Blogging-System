package com.example.blogg.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "blogs")
public class Blogs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long authorid;
	
	private String username;
	private String blogtype;
	private String Title;
	private String content;
	private String date;
	
    public Blogs() {
    }
	
	public Blogs(String username, String blogtype, String Title, String content, String date) {
		this.username = username;
		this.blogtype = blogtype;
		this.Title = Title;
		this.content = content;
		this.date = date;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBlogtype() {
		return blogtype;
	}

	public void setBlogtype(String blogtype) {
		this.blogtype = blogtype;
	}

	public String getTitle() {
		return Title;
	}


	public void setTitle(String title) {
		Title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	public void setAuthorid(Long authorid) {
		this.authorid = authorid;
	}
	
	public Long getAuthorid() {
		return authorid;
	}

}
