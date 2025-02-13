package com.test.pubnub_observer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import com.pubnub.api.java.PubNub;
import com.test.pubnub_observer.config.PNObserverConfigSettings;
import com.test.pubnub_observer.config.YAMLConfigLoader;
import com.test.pubnub_observer.utils.connections.PNConnTupleMultiChannel;
import com.test.pubnub_observer.utils.connections.PNConnUtils;
import com.test.pubnub_observer.utils.pubnub.PNObjectInspector;

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
		PubNub pubNub;

		if (configSettings.areOptionsSet()) {
			System.out.println("Loading with subscription options.");

			pubNub = PNConnUtils.subscribeAllWOptions(configSettings);
		} else {
			System.out.println("Loading without subscription options.");

			PNConnTupleMultiChannel pnConnTuple = PNConnUtils.pubNubConnectionSetup(configSettings);

			PNConnUtils.addObservers(pnConnTuple);
			pubNub = PNConnUtils.subscribeAll(pnConnTuple);
		}

		PNObjectInspector.checkAndPrintExtendedInfo(pubNub, configSettings);

		// Note - App does not currently disconnect and will need to be killed manually
	}

}
