package com.filesboxx.ws.model;

import java.security.Timestamp;
import java.util.UUID;

import javax.persistence.*;

import com.filesboxx.ws.model.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "MESSAGE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "MESSAGE_ID")
	private UUID messageId;
	@Column(name = "CHAT_ID")
	private UUID chatId;
	@Column(name = "TEXT")
	private String text;
	@Column(name = "SENDER_ID")
	private UUID senderId;
	@Column(name = "RECIPIENT_ID")
	private UUID recipientId;
	@Column(name = "STATUS")
	private MessageStatus status;
	@Column(name = "DATE_TIME")
	private Timestamp dateTime;
	
	@ManyToOne(targetEntity = Chat.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "CHAT_ID", referencedColumnName = "CHAT_ID", insertable = false, updatable = false)
	private Chat chat;

	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User one;

	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "RECIPIENT_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User two;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
