package com.test.pubnub_loader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.pubnub.api.PubNubException;
import com.pubnub.api.UserId;
import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.PNConfiguration;
import com.pubnub.api.java.v2.entities.Channel;
import com.test.pubnub_loader.config.BasicPublisherConfigSettings;
import com.test.pubnub_loader.config.ConfigLoader;

public class BasicPublisher {
	private final static DateTimeFormatter LOG_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private final static String BASIC_PUB_CONFIG = "./configs/BasicPublisher.yaml";

	// Adjust this higher to prove that 'channel.subscription().subscribe()' is
	// required before submitting all the messages to PubNub
	private final static long SLEEP_TEST = 50; // in msec

	public static void main(String[] args) throws Exception {
		// Preamble
		LocalDateTime startDT = LocalDateTime.now();
		System.out.println("Starting Basic PubNub Publisher at: " + startDT.format(LOG_DATETIME_FORMATTER));

		// Load and summarize config we're running with
		BasicPublisherConfigSettings configSettings = ConfigLoader.loadBasicYAMLConfig(BASIC_PUB_CONFIG);
		System.out.println(configSettings.toString());

		// Setup connection to PubNub
		PNConnTuple pnConnTuple = pubNubConnectionSetup(configSettings);

		// Create threadpool to submit messages en masse
		System.out.println("Running with thread count of: " + configSettings.getThreadCount());
		ExecutorService threadPoolService = Executors.newFixedThreadPool(configSettings.getThreadCount());

		// Install listeners
		ArrayList<MessageLoader> messageLoaders = new ArrayList<>();
		for (int i = 0; i < configSettings.getThreadCount(); i++) {
			MessageLoader newML = MessageLoader.of(pnConnTuple, configSettings);
			// Unneeded, but would fail the app faster if all connections would issues.
//			pnConnTuple.getPubNubObj().addListener(newML);
			messageLoaders.add(newML);
		}

		// Connect to configured channels and submit messages
		pnConnTuple.getChannel().subscription().subscribe();
		System.out.println("Subscribed to channel.");

		// Sleep to space out subscription setup & message submission
		if (SLEEP_TEST > 0L) {
			System.out.println("Sleeping " + SLEEP_TEST + "msec.");
			Thread.sleep(SLEEP_TEST);
		}

		// Submit all Message Loaders to threadpool
		messageLoaders.forEach((curML) -> {
			threadPoolService.submit(curML);
		});

		// Tell threads to finish up
		threadPoolService.shutdown();
		// Wait for threadpool to finish their work for up to config timeout
		threadPoolService.awaitTermination(configSettings.getSubmissionTimeoutSecounds(), TimeUnit.SECONDS);

		LocalDateTime endDT = LocalDateTime.now();
		System.out.println(
				"Ending thread " + Thread.currentThread().getId() + " at: " + endDT.format(LOG_DATETIME_FORMATTER)
						+ ".  Duration in msecs: " + Duration.between(startDT, endDT).toMillis() + " Messages Sent: "
						+ (configSettings.getThreadCount() * configSettings.getPerThreadMessageCount()));

		System.exit(0);
	}

	private static PNConnTuple pubNubConnectionSetup(BasicPublisherConfigSettings configSettings)
			throws PubNubException {

		PNConfiguration.Builder configBuilder = PNConfiguration.builder(new UserId(configSettings.getPubNubUser()),
				configSettings.getPubNubSubscribeKey());
		configBuilder.publishKey(configSettings.getPubNubPublishKey());

		if (configSettings.getSecret() != null) {
			configBuilder.secretKey(configSettings.getSecret());
		}

		PNConfiguration pnConfiguration = configBuilder.build();
		PubNub pubnub = PubNub.create(pnConfiguration);
		Channel channel = pubnub.channel(configSettings.getPubNubPublishChannel());

		return PNConnTuple.of(pubnub, channel);
	}
}
