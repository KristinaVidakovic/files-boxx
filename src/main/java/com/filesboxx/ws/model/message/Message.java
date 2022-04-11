package com.filesboxx.ws.model.message;

import java.security.Timestamp;
import java.util.UUID;

import javax.persistence.*;

import com.filesboxx.ws.model.chat.Chat;
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
	@Column(name = "TEXT")
	private String text;
	@Column(name = "STATUS")
	private MessageStatus status;
	@Column(name = "DATE_TIME")
	private Timestamp dateTime;
	
	@ManyToOne(targetEntity = Chat.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CHAT_ID")
	private Chat chat;

	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SENDER_ID")
	private User sender;

	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "RECIPIENT_ID")
	private User recipient;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
