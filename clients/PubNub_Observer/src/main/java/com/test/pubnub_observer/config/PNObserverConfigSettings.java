package com.test.pubnub_observer.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.NonNull;
import lombok.ToString;

@ToString
public class PNObserverConfigSettings {
	@NonNull
	private String pubNubUser;
	@NonNull
	private String pubNubSubscribeKey;
	@NonNull
	private ArrayList<PubNubSubscription> pubNubSubscriptions = new ArrayList<>();

	private Long cursor;

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

	public PNObserverConfigSettings() {
	}

	public PNObserverConfigSettings(String pubNubUser, String pubNubSubscribeKey,
			List<PubNubSubscription> pubNubSubscriptions) {
		this.pubNubUser = pubNubUser;
		this.pubNubSubscribeKey = pubNubSubscribeKey;
		this.pubNubSubscriptions.addAll(pubNubSubscriptions);
	}

	@JsonGetter
	public Long getCursor() {
		return cursor;
	}

	@JsonSetter
	public void setCursor(Long cursor) {
		this.cursor = cursor;
	}

	public boolean areOptionsSet() {
		for (PubNubSubscription pnSub : pubNubSubscriptions) {
			if (pnSub.isReceivePresenceEvents() || pnSub.getFilterMode() > 0) {
				return true;
			}
		}

		return false;
	}
}
