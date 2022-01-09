package com.filesboxx.ws.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "CONVERSATION")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "CONVERSATION_ID")
	private String conversationId;
	@Column(name = "SENDER_ID")
	private String senderId;
	@Column(name = "RECIPIENT_ID")
	private String recipientId;
	@Column(name = "CHAT_ID")
	private String chatId;
	
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
