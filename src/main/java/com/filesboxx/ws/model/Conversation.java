package com.filesboxx.ws.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
@Table(name = "CONVERSATION", schema = "root")
public class Conversation {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "CONVERSATIN_ID")
	private String conversationId;
	@Column(name = "USER_ONE")
	private String userOne;
	@Column(name = "USER_TWO")
	private String userTwo;
	@Column(name = "CHAT_ID")
	private String chatId;
	@Column(name = "ACTIVE")
	private Boolean active;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ONE", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User one;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_TWO", referencedColumnName = "USER_ID", insertable = false, updatable = false)
	private User two;
	
	@OneToOne(targetEntity = Chat.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "CHAT_ID", referencedColumnName = "CHAT_ID", insertable = false, updatable = false)
	private Chat chat;
	
	public Conversation() {
		// TODO Auto-generated constructor stub
	}

	public Conversation(String conversationId, String userOne, String userTwo, String chatId, Boolean active) {
		super();
		this.conversationId = conversationId;
		this.userOne = userOne;
		this.userTwo = userTwo;
		this.chatId = chatId;
		this.active = active;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getUserOne() {
		return userOne;
	}

	public void setUserOne(String userOne) {
		this.userOne = userOne;
	}

	public String getUserTwo() {
		return userTwo;
	}

	public void setUserTwo(String userTwo) {
		this.userTwo = userTwo;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
	
}
