package com.test.pubnub_loader;

import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.entities.Channel;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class PNConnTuple {
	@NonNull
	private PubNub pubNubObj;
	@NonNull
	private Channel channel;
}
