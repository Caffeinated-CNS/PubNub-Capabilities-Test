package com.test.pubnub_loader.message;

import java.time.Instant;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class PNMessage {
	@NonNull
	private String preAmble = "Test Message @ ";

	// Message with preamble and UTC time at call time.
	@Override
	public String toString() {
		return preAmble + Instant.now().toString();
	}

}
