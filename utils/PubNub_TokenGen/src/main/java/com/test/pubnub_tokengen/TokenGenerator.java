package com.test.pubnub_tokengen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pubnub.api.java.PubNub;
import com.pubnub.api.models.consumer.access_manager.v3.PNGrantTokenResult;
import com.test.pubnub_tokengen.config.ConfigLoader;
import com.test.pubnub_tokengen.config.TokenGenConfigSettings;
import com.test.pubnub_tokengen.utils.connections.AccessTokenGenUtil;
import com.test.pubnub_tokengen.utils.connections.PNConnUtils;

public class TokenGenerator {
	private final static DateTimeFormatter LOG_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private final static String BASIC_PUB_CONFIG = "./configs/TokenGenerator.yaml";

	public static void main(String[] args) throws Exception {
		// Preamble
		LocalDateTime startDT = LocalDateTime.now();
		System.out.println("Starting PubNub Token Generator at: " + startDT.format(LOG_DATETIME_FORMATTER));

		// Load and summarize config we're running with
		TokenGenConfigSettings configSettings = ConfigLoader.loadBasicYAMLConfig(BASIC_PUB_CONFIG,
				TokenGenConfigSettings.class);
		System.out.println(configSettings.toString());

		// Setup connection to PubNub
		PubNub pn = PNConnUtils.pubNubConnectionSetup(configSettings);

		// Generate Access Token string based on config parameters & established PubNub
		// connection.
		PNGrantTokenResult pnGrantTokenResult = AccessTokenGenUtil.genTokenFromConfig(pn, configSettings);

		System.out.println("Grant Token: \n" + pnGrantTokenResult.getToken());

		System.exit(0);
	}
}
