package cn.itcast.oa.domain;

import java.io.Serializable;


public class Reply extends Article implements Serializable{
	private Topic topic;

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
}
