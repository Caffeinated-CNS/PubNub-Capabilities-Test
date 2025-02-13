package com.test.pubnub_observer.config;

import java.util.ArrayList;
import java.util.List;

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

	private String accessToken = null;

	private Long cursor;

	// Debugging / extended info print settings.
	private boolean setCheckUserChannelMemberships = true;
	private boolean setPrintChannelMetadata = true;
	private boolean setListOtherChannelMembers = true;

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

	public boolean isSetCheckUserMemberships() {
		return setCheckUserChannelMemberships;
	}

	public void setSetCheckUserMemberships(boolean setCheckUserMemberships) {
		this.setCheckUserChannelMemberships = setCheckUserMemberships;
	}

	public boolean isSetListOtherChannelMembers() {
		return setListOtherChannelMembers;
	}

	public void setSetListOtherChannelMembers(boolean setListOtherChannelMembers) {
		this.setListOtherChannelMembers = setListOtherChannelMembers;
	}

	public boolean isSetPrintChannelMetadata() {
		return setPrintChannelMetadata;
	}

	public void setSetPrintChannelMetadata(boolean setPrintChannelMetadata) {
		this.setPrintChannelMetadata = setPrintChannelMetadata;
	}

	public boolean isSetCheckUserChannelMemberships() {
		return setCheckUserChannelMemberships;
	}

	public void setSetCheckUserChannelMemberships(boolean setCheckUserChannelMemberships) {
		this.setCheckUserChannelMemberships = setCheckUserChannelMemberships;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
