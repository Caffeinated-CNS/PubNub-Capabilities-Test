package com.test.pubnub_observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pubnub.api.java.PubNub;
import com.test.pubnub_observer.config.PNObserverConfigSettings;
import com.test.pubnub_observer.config.YAMLConfigLoader;
import com.test.pubnub_observer.utils.connections.PNConnTupleMultiChannel;
import com.test.pubnub_observer.utils.connections.PNConnUtils;

public class PNObserver {
	private final static DateTimeFormatter LOG_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private final static String BASIC_PUB_CONFIG = "./configs/PNObserver.yaml";

	public static void main(String[] args) throws Exception {
		// Preamble
		LocalDateTime startDT = LocalDateTime.now();
		System.out.println("Starting Basic PubNub Publisher at: " + startDT.format(LOG_DATETIME_FORMATTER));

		// Load and summarize config we're running with
		PNObserverConfigSettings configSettings = YAMLConfigLoader.loadBasicYAMLConfig(BASIC_PUB_CONFIG,
				PNObserverConfigSettings.class);
		System.out.println("Settings: \n\t" + configSettings.toString());

		// Setup connection to PubNub
		PNConnTupleMultiChannel pnConnTuple = PNConnUtils.pubNubConnectionSetup(configSettings);
		PubNub pubnub = pnConnTuple.getPubNubObj();

		PNConnUtils.addObservers(pubnub);
		
		PNConnUtils.subscribeAll(pnConnTuple);
		
		// Note - App does not currently disconnect and will need to be killed manually
	}

}
