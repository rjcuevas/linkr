package com.linkr.user.domain;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.linkr.url.domain.LinkrUrl;


@Entity
@Table(name="USER")
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userID;
	private String username;
	private String password;
	private Set<LinkrUrl> linkUrls;
	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	@Column(name="username", nullable = false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="password", nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="user" , cascade = { CascadeType.REFRESH, CascadeType.REMOVE})
	public Set<LinkrUrl> getLinkUrls() {
		return linkUrls;
	}
	public void setLinkUrls(Set<LinkrUrl> linkUrls) {
		this.linkUrls = linkUrls;
	}
	
}
