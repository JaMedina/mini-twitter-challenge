package com.jorge.twitter.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "tweet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tweet extends RootDomain{

	public String message;
	public Long authorId;
	public Date creationDate;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public String toString(){
		return "Tweet{Date: "+ creationDate +", Message: "+ message+"}";
	}
}
