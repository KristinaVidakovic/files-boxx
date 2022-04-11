package com.filesboxx.ws.model.notification;

import java.security.Timestamp;
import java.util.UUID;

import javax.persistence.*;

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
	@Column(name = "TEXT")
	private String text;
	@Column(name = "DATE_TIME")
	private Timestamp dateTime;
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SENDER_ID")
	private User sender;

	public Notification(UUID notificationId, User senderId, String text) {
		super();
		this.notificationId = notificationId;
		this.sender = senderId;
		this.text = text;
	}
	
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
