package com.test.pubnub_observer.utils.connections;

import java.util.ArrayList;
import java.util.List;

import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.entities.Channel;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class PNConnTupleMultiChannel {
	@NonNull
	private PubNub pubNubObj;
	@NonNull
	private List<Channel> channels = new ArrayList<>();

	public PNConnTupleMultiChannel(PubNub pubNubObj) {
		this.pubNubObj = pubNubObj;
	}

	public PubNub getPubNubObj() {
		return pubNubObj;
	}

	public void setPubNubObj(PubNub pubNubObj) {
		this.pubNubObj = pubNubObj;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void addChannel(Channel channel) {
		this.channels.add(channel);
	}
}
