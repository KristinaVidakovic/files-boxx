package com.filesboxx.ws.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.filesboxx.ws.model.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

@Entity
@Table(name = "CONVERSATION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conversation {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "CONVERSATION_ID")
	private UUID conversationId;
	@Column(name = "SENDER_ID")
	private UUID senderId;
	@Column(name = "RECIPIENT_ID")
	private UUID recipientId;
	@Column(name = "CHAT_ID")
	private UUID chatId;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User one;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "RECIPIENT_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User two;
	
	@OneToOne(targetEntity = Chat.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "CHAT_ID", referencedColumnName = "CHAT_ID", insertable = false, updatable = false)
	private Chat chat;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
}
