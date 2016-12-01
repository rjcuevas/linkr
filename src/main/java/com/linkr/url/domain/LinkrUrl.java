package com.linkr.url.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.linkr.user.domain.UserAccount;

@Entity
@Table(name="URL")
public class LinkrUrl implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UserAccount user;
	private Long urlID;
	private String urlAddress;
	private boolean isPublic;
	private Set<ShortUrl> shortUrlSet = new HashSet<ShortUrl>();
		
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getUrlID() {
		return urlID;
	}
	public void setUrlID(Long urlID) {
		this.urlID = urlID;
	}
		
	@ManyToOne
    @JoinColumn(name="userID", nullable=false)
	public UserAccount getUser() {
		return user;
	}
	public void setUser(UserAccount user) {
		this.user = user;
	}
	
	@Column(name="url_address", nullable=false)
	public String getUrlAddress() {
		return urlAddress;
	}
	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}
	
	@Column(name="is_public", nullable=false)
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	

	@OneToMany(mappedBy = "linkrUrl", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<ShortUrl> getShortUrlSet() {
		return shortUrlSet;
	}
	public void setShortUrlSet(Set<ShortUrl> shortUrlSet) {
		this.shortUrlSet = shortUrlSet;
	}
	
	
}
