package com.test.pubnub_listener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.PNConfiguration;
import com.pubnub.api.java.v2.callbacks.EventListener;
import com.pubnub.api.java.v2.callbacks.StatusListener;
import com.pubnub.api.java.v2.entities.Channel;
import com.pubnub.api.java.v2.subscriptions.Subscription;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.PubNubException;
import com.pubnub.api.UserId;
import com.pubnub.api.enums.PNStatusCategory;
import com.google.gson.JsonElement;


import com.test.pubnub_listener.config.BasicSubscriberConfigSettings;
import com.test.pubnub_listener.config.ConfigLoader;

public class BasicListener {
	private final static DateTimeFormatter LOG_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	private final static String BASIC_PUB_CONFIG = "./configs/BasicListener.yaml";

	public static void main(String[] args) throws Exception {
		// Preamble
		LocalDateTime startDT = LocalDateTime.now();
		System.out.println("Starting Basic PubNub Publisher at: " + startDT.format(LOG_DATETIME_FORMATTER));

		// Load and summarize config we're running with
		BasicSubscriberConfigSettings configSettings = ConfigLoader.loadBasicYAMLConfig(BASIC_PUB_CONFIG);
		System.out.println(configSettings.toString());

		// Setup connection to PubNub
		PNConnTuple pnConnTuple = pubNubConnectionSetup(configSettings);
		PubNub pubnub = pnConnTuple.getPubNubObj();
		Subscription subscription = pnConnTuple.getChannel().subscription();

		pubnub.addListener(new StatusListener() {

			@Override
			public void status(PubNub pubnub, PNStatus status) {
				if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
					System.out.println("Connected to channel: " + pnConnTuple.getChannel().getName());
				} else {
					System.out.println("Unexpected Status: " + status.getCategory());
					System.out.println("Effected channels: ");
					status.getAffectedChannels().forEach(str -> {
						System.out.println("\t" + str);
					});

					System.out.println("Status: " + status.toString());
				}
			}
		});

		pubnub.addListener(new EventListener() {
			@Override
			public void message(PubNub pubnub, PNMessageResult message) {
				// Handle new message stored in message.message
				if (message.getChannel() != null) {
					// Message has been received on channel group stored in
					// message.getChannel()
				} else {
					// Message has been received on channel stored in
					// message.getSubscription()
				}

				JsonElement receivedMessageObject = message.getMessage();

				System.out.println("Received message at time: " + message.getTimetoken() + " Content: "
						+ receivedMessageObject.toString());
				// extract desired parts of the payload, using Gson
//				String msg = message.getMessage().getAsJsonObject().get("msg").getAsString();
//				System.out.println("The content of the message is: " + msg);

				try {
					subscription.close();
					System.out.println(
							"Ending Basic PubNub Listener at: " + LocalDateTime.now().format(LOG_DATETIME_FORMATTER));
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		subscription.subscribe();

	}

	private static PNConnTuple pubNubConnectionSetup(BasicSubscriberConfigSettings configSettings)
			throws PubNubException {

		PNConfiguration.Builder configBuilder = PNConfiguration.builder(new UserId(configSettings.getPubNubUser()),
				configSettings.getPubNubSubscribeKey());

		PNConfiguration pnConfiguration = configBuilder.build();
		PubNub pubnub = PubNub.create(pnConfiguration);

		String channelName = configSettings.getPubNubSubscriptions().get(0).getChannelName();
		Channel channel = pubnub.channel(channelName);

		return PNConnTuple.of(pubnub, channel);
	}
}
