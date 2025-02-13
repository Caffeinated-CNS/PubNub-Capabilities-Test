package com.test.pubnub_tokengen.utils.connections;

import com.pubnub.api.UserId;
import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.PNConfiguration;
import com.test.pubnub_tokengen.config.TokenGenConfigSettings;

public class PNConnUtils {
	private PNConnUtils() {
	}

	public static PubNub pubNubConnectionSetup(TokenGenConfigSettings configSettings) {

		try {
			PNConfiguration.Builder configBuilder = PNConfiguration.builder(new UserId(configSettings.getPubNubUser()),
					configSettings.getPubNubSubscribeKey());
			configBuilder.publishKey(configSettings.getPubNubPublishKey());

			configBuilder.secretKey(configSettings.getSecret());

			return PubNub.create(configBuilder.build());
		} catch (Exception ex) {
			System.err.println("Failed to load config and create PubNub reference.");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
