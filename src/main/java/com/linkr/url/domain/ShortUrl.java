package com.linkr.url.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SHORT_URL")
public class ShortUrl implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long shortUrlID;
	private String shortURLAddress;
	private Long srcLookUpID;
	private LinkrUrl linkrUrl;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getShortUrlID() {
		return shortUrlID;
	}
	public void setShortUrlID(Long shortUrlID) {
		this.shortUrlID = shortUrlID;
	}
	
	@Column(name="short_url_address")
	public String getShortURLAddress() {
		return shortURLAddress;
	}
	public void setShortURLAddress(String shortURLAddress) {
		this.shortURLAddress = shortURLAddress;
	}
	
	@Column(name="scr_lookupID")
	public Long getSrcLookUpID() {
		return srcLookUpID;
	}
	public void setSrcLookUpID(Long srcLookUpID) {
		this.srcLookUpID = srcLookUpID;
	}

    @ManyToOne
    @JoinColumn(name="urlID")
	public LinkrUrl getLinkrUrl() {
		return linkrUrl;
	}
	public void setLinkrUrl(LinkrUrl linkrUrl) {
		this.linkrUrl = linkrUrl;
	}


}
