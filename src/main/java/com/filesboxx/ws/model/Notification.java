package com.filesboxx.ws.model;

import java.security.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "NOTIFICATION_ID")
	private String notificationId;
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "TEXT")
	private String text;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "DATE_TIME")
	private Timestamp dateTime;
	@Column(name = "SEEN")
	private Boolean seen;
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User user;
	
	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public Notification(String notificationId, String userId, String text) {
		super();
		this.notificationId = notificationId;
		this.userId = userId;
		this.text = text;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}
	
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
