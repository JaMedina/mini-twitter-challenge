package com.jorge.twitter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="follow")
@XmlAccessorType(XmlAccessType.FIELD)
public class Follow extends RootDomain{

	public Long userId;
	public Long followId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getFollowId() {
		return followId;
	}
	public void setFollowId(Long followId) {
		this.followId = followId;
	}
}
