package com.test.pubnub_observer.config;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PubNubSubscription {
	@NonNull
	private String channelName = "";
	private boolean receivePresenceEvents = false;
	private int filterMode = 0;

	@JsonGetter
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@JsonGetter
	public boolean isReceivePresenceEvents() {
		return receivePresenceEvents;
	}

	public void setReceivePresenceEvents(boolean receivePresenceEvents) {
		this.receivePresenceEvents = receivePresenceEvents;
	}

	public int getFilterMode() {
		return filterMode;
	}

	public void setFilterMode(int filterMode) {
		this.filterMode = filterMode;
	}
}