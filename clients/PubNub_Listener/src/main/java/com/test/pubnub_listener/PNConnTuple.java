package com.test.pubnub_listener;

import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.entities.Channel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class PNConnTuple {
	@NonNull
	private PubNub pubNubObj;
	@NonNull
	private Channel channel;
	
	
	public PubNub getPubNubObj() {
		return pubNubObj;
	}
	public void setPubNubObj(PubNub pubNubObj) {
		this.pubNubObj = pubNubObj;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
