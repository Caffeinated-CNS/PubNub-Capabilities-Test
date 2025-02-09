package com.test.yaml_config_creator.load_target_classes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class BasicSubscriberConfigSettings {
	@NonNull
	private String pubNubUser;
	@NonNull
	private String pubNubSubscribeKey;
	@NonNull
	private List<PubNubSubscription> pubNubSubscriptions;

	@JsonGetter
	public String getPubNubUser() {
		return pubNubUser;
	}

	public void setPubNubUser(String pubNubUser) {
		this.pubNubUser = pubNubUser;
	}

	@JsonGetter
	public String getPubNubSubscribeKey() {
		return pubNubSubscribeKey;
	}

	public void setPubNubSubscribeKey(String pubNubSubscribeKey) {
		this.pubNubSubscribeKey = pubNubSubscribeKey;
	}

	@JsonGetter
	public List<PubNubSubscription> getPubNubSubscriptions() {
		return pubNubSubscriptions;
	}

	public void setPubNubSubscriptions(List<PubNubSubscription> pubNubSubscriptions) {
		this.pubNubSubscriptions = pubNubSubscriptions;
	}

}
