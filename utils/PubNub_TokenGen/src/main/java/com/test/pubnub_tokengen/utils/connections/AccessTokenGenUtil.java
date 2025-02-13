package com.test.pubnub_tokengen.utils.connections;

import java.util.ArrayList;

import com.pubnub.api.PubNubException;
import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.endpoints.access.builder.GrantTokenObjectsBuilder;
import com.pubnub.api.java.models.consumer.access_manager.v3.ChannelGrant;
import com.pubnub.api.models.consumer.access_manager.v3.PNGrantTokenResult;
import com.test.pubnub_tokengen.config.TokenGenConfigSettings;

public class AccessTokenGenUtil {
	private AccessTokenGenUtil() {
	}

	public static PNGrantTokenResult genTokenFromConfig(PubNub pubNub, TokenGenConfigSettings configSettings) {
		ArrayList<ChannelGrant> totalChannelGrants = new ArrayList<>();

		// Yes, the token creation process being a builder pattern makes this weird...
		GrantTokenObjectsBuilder grantTokenObjectsBuilder = pubNub.grantToken(configSettings.getTtl())
				.authorizedUUID(configSettings.getPubNubUser());

		configSettings.getChannelGrants().forEach(curChannelGrantConfig -> {
			ChannelGrant newElement;

			// Only one of channel name or pattern per config Channel Grant Config object.
			if (curChannelGrantConfig.getChannelName() != null) {
				newElement = ChannelGrant.name(curChannelGrantConfig.getChannelName());
			} else {
				newElement = ChannelGrant.pattern(curChannelGrantConfig.getChannelPattern());
			}

			if (curChannelGrantConfig.isReadEnabled()) {
				newElement = newElement.read();
			}

			if (curChannelGrantConfig.isWriteEnabled()) {
				newElement = newElement.write();
			}

			totalChannelGrants.add(newElement);
		});

		grantTokenObjectsBuilder = grantTokenObjectsBuilder.channels(totalChannelGrants);

		try {
			return grantTokenObjectsBuilder.sync();
		} catch (PubNubException e) {
			System.out.println("Failed to generate an Access Token with given settings.");
			e.printStackTrace();
			
			throw new RuntimeException(e);
		}
	}
}
