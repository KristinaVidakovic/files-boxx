package com.filesboxx.ws.model.conversation;

import javax.persistence.*;

import com.filesboxx.ws.model.chat.Chat;
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
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SENDER_ID")
	private User sender;
	
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "RECIPIENT_ID")
	private User recipient;
	
	@OneToOne(targetEntity = Chat.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CHAT_ID")
	private Chat chat;

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}
