package com.test.pubnub_loader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pubnub.api.PubNubException;
import com.pubnub.api.UserId;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.PNConfiguration;
import com.pubnub.api.java.v2.entities.Channel;
import com.pubnub.api.models.consumer.PNStatus;
import com.test.pubnub_loader.config.BasicPublisherConfigSettings;
import com.test.pubnub_loader.config.ConfigLoader;
import com.pubnub.api.java.v2.callbacks.StatusListener;

public class BasicPublisher {
	private final static DateTimeFormatter LOG_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private final static String BASIC_PUB_CONFIG = "./configs/BasicPublisher.yaml";

	public static void main(String[] args) throws Exception {
		// Preamble
		LocalDateTime startDT = LocalDateTime.now();
		System.out.println("Starting Basic PubNub Publisher at: " + startDT.format(LOG_DATETIME_FORMATTER));

		// Load and summarize config we're running with
		BasicPublisherConfigSettings configSettings = ConfigLoader.loadBasicYAMLConfig(BASIC_PUB_CONFIG);
		System.out.println(configSettings.toString());

		// Setup connection to PubNub
		PNConnTuple pnConnTuple = pubNubConnectionSetup(configSettings);

		// Blocking publish message
//		try {
		Channel channel = pnConnTuple.getChannel();
		// PNPublishResult pnPublishResult = channel.publish("Test Message").sync();

		PubNub pn = pnConnTuple.getPubNubObj();
		pn.addListener(new StatusListener() {

			@Override
			public void status(PubNub pubnub, PNStatus status) {
				if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
					// This event happens when radio / connectivity is lost
				} else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
					// Connect event. You can do stuff like publish, and know you'll get it.
					// Or just use the connected event to confirm you are subscribed for
					// UI / internal notifications, etc
					channel.publish("Test Message @ " + LocalDateTime.now()).async(result -> {
						result.onSuccess(res -> {
							System.out.println("Published at: " + res.getTimetoken());
							
							pnConnTuple.getPubNubObj().unsubscribeAll();
							System.out.println("Ending Basic PubNub Publisher at: " + LocalDateTime.now().format(LOG_DATETIME_FORMATTER));
							System.exit(0);
						}).onFailure(exception -> {
							System.out.println("Failed to publish at: " + exception.getMessage());
							pnConnTuple.getPubNubObj().unsubscribeAll();
							System.out.println("Ending Basic PubNub Publisher at: " + LocalDateTime.now().format(LOG_DATETIME_FORMATTER));
							System.exit(0);
						});
					});
				} /* handle other statuses */
			}

		});
		
        channel.subscription().subscribe();

	}

	private static PNConnTuple pubNubConnectionSetup(BasicPublisherConfigSettings configSettings)
			throws PubNubException {

		PNConfiguration.Builder configBuilder = PNConfiguration.builder(new UserId(configSettings.getPubNubUser()),
				configSettings.getPubNubSubscribeKey());
		configBuilder.publishKey(configSettings.getPubNubPublishKey());

		PNConfiguration pnConfiguration = configBuilder.build();
		PubNub pubnub = PubNub.create(pnConfiguration);
		Channel channel = pubnub.channel(configSettings.getPubNubPublishChannel());

		return PNConnTuple.of(pubnub, channel);
	}
}
