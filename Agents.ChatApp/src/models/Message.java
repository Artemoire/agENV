package models;

import java.util.Date;

public class Message {

	private String receiver;
	private String sender;
	private String content;
	private Date date;

	public Message(String receiver, String sender, String content, Date date) {
		this.receiver = receiver;
		this.sender = sender;
		this.content = content;
		this.date = date;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
