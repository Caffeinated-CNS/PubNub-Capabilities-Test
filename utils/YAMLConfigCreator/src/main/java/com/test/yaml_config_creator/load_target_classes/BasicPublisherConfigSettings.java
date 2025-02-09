package com.test.yaml_config_creator.load_target_classes;

public class BasicPublisherConfigSettings {
	private String pubNubUser;
	private String pubNubPublishKey;
	private String pubNubSubscribeKey;
	private String pubNubPublishChannel;

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
}
