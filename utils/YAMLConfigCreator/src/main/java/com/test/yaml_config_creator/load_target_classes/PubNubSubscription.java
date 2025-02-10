package com.test.yaml_config_creator.load_target_classes;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@NoArgsConstructor
public class PubNubSubscription {
	@NonNull
	private String channelName;
	private String cursor = null;
	private boolean receivePresenceEvents = false;

	@JsonGetter
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@JsonGetter
	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	@JsonGetter
	public boolean isReceivePresenceEvents() {
		return receivePresenceEvents;
	}

	public void setReceivePresenceEvents(boolean receivePresenceEvents) {
		this.receivePresenceEvents = receivePresenceEvents;
	}
}