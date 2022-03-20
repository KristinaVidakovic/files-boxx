package com.filesboxx.ws.model;

import java.security.Timestamp;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.filesboxx.ws.model.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "NOTIFICATION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "NOTIFICATION_ID")
	private UUID notificationId;
	@Column(name = "SENDER_ID")
	private UUID senderId;
	@Column(name = "TEXT")
	private String text;
	@Column(name = "DATE_TIME")
	private Timestamp dateTime;
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User user;

	public Notification(UUID notificationId, UUID senderId, String text) {
		super();
		this.notificationId = notificationId;
		this.senderId = senderId;
		this.text = text;
	}
	
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
