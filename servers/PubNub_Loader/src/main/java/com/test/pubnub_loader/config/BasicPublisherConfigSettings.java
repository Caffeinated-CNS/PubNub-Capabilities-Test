package com.test.pubnub_loader.config;

import lombok.ToString;

@ToString
public class BasicPublisherConfigSettings {
	private String pubNubUser = null;
	private String pubNubPublishKey = null;
	private String pubNubPublishChannel = null;
	private String pubNubSubscribeKey = null;
	private String secret = null;

	public String getPubNubPublishChannel() {
		return pubNubPublishChannel;
	}

	public void setPubNubPublishChannel(String pubNubPublishChannel) {
		this.pubNubPublishChannel = pubNubPublishChannel;
	}

	public String getPubNubPublishKey() {
		return pubNubPublishKey;
	}

	public void setPubNubPublishKey(String pubNubPublishKey) {
		this.pubNubPublishKey = pubNubPublishKey;
	}

	public String getPubNubUser() {
		return pubNubUser;
	}

	public void setPubNubUser(String pubNubUser) {
		this.pubNubUser = pubNubUser;
	}

	public String getPubNubSubscribeKey() {
		return pubNubSubscribeKey;
	}

	public void setPubNubSubscribeKey(String pubNubSubscribeKey) {
		this.pubNubSubscribeKey = pubNubSubscribeKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
