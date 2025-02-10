package com.test.yaml_config_creator.load_target_classes;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.NonNull;
import lombok.ToString;

@ToString
public class BasicSubscriberConfigSettings {
	@NonNull
	private String pubNubUser;
	@NonNull
	private String pubNubSubscribeKey;
	@NonNull
	private ArrayList<PubNubSubscription> pubNubSubscriptions = new ArrayList<>();
	
	

	@JsonGetter
	public String getPubNubUser() {
		return pubNubUser;
	}

	@JsonSetter
	public void setPubNubUser(String pubNubUser) {
		this.pubNubUser = pubNubUser;
	}

	@JsonGetter
	public String getPubNubSubscribeKey() {
		return pubNubSubscribeKey;
	}

	@JsonSetter
	public void setPubNubSubscribeKey(String pubNubSubscribeKey) {
		this.pubNubSubscribeKey = pubNubSubscribeKey;
	}

	@JsonGetter
	public List<PubNubSubscription> getPubNubSubscriptions() {
		return pubNubSubscriptions;
	}

	@JsonSetter
	public void setPubNubSubscriptions(List<PubNubSubscription> pubNubSubscriptions) {
		this.pubNubSubscriptions.addAll(pubNubSubscriptions);
	}
	
	public BasicSubscriberConfigSettings() {}

	public BasicSubscriberConfigSettings(String pubNubUser, String pubNubSubscribeKey,
			List<PubNubSubscription> pubNubSubscriptions) {
		this.pubNubUser = pubNubUser;
		this.pubNubSubscribeKey = pubNubSubscribeKey;
		this.pubNubSubscriptions.addAll(pubNubSubscriptions);
	}

}
