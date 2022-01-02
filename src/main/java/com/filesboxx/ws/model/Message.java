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
@Table(name = "MESSAGE")
public class Message {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "MESSAGE_ID")
	private String messageId;
	@Column(name = "CHAT_ID")
	private String chatId;
	@Column(name = "TEXT")
	private String text;
	@Column(name = "SEEN")
	private Boolean seen;
	@Column(name = "DATE_TIME")
	private Timestamp dateTime;
	
	@ManyToOne(targetEntity = Chat.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "CHAT_ID", referencedColumnName = "CHAT_ID", insertable = false, updatable = false)
	private Chat chat;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}

	public Message(String messageId, String chatId, String text, Boolean seen, Timestamp dateTime) {
		super();
		this.messageId = messageId;
		this.chatId = chatId;
		this.text = text;
		this.seen = seen;
		this.dateTime = dateTime;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
