package com.test.pubnub_observer.utils.connections;

import com.pubnub.api.PubNubException;
import com.pubnub.api.UserId;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.PNConfiguration;
import com.pubnub.api.java.v2.callbacks.EventListener;
import com.pubnub.api.java.v2.callbacks.StatusListener;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.test.pubnub_observer.config.PNObserverConfigSettings;

public class PNConnUtils {

	private PNConnUtils() {
	}

	public static PNConnTupleMultiChannel pubNubConnectionSetup(PNObserverConfigSettings configSettings)
			throws PubNubException {

		PNConfiguration.Builder configBuilder = PNConfiguration.builder(new UserId(configSettings.getPubNubUser()),
				configSettings.getPubNubSubscribeKey());

		PNConfiguration pnConfiguration = configBuilder.build();
		PubNub pubnub = PubNub.create(pnConfiguration);

		PNConnTupleMultiChannel ret = new PNConnTupleMultiChannel(pubnub);

		System.out.println("Loading configured channels: ");

		(configSettings.getPubNubSubscriptions()).forEach(curChannel -> {
			System.out.println("\t" + curChannel.getChannelName());

			ret.addChannel(pubnub.channel(curChannel.getChannelName()));
		});

		return ret;
	}

	public static void addObservers(PubNub pubnub) {
		pubnub.addListener(new StatusListener() {
			@Override
			public void status(PubNub pubnub, PNStatus status) {
				if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {

					System.out.println("Connected to channel(s): ");

					status.getAffectedChannels().forEach(curChannel -> {
						System.out.println("\t" + curChannel);
					});
				} else {

					System.out.println("Unexpected Status: " + status.getCategory());
					System.out.println("Effected channels: ");

					status.getAffectedChannels().forEach(curChannel -> {
						System.out.println("\t" + curChannel);
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

				System.out.println("Channel: " + message.getChannel() + 
						" - Received message at time: " + message.getTimetoken() + " - With Content: " + message.getMessage());

//				try {
//					subscription.close();
//					System.out.println(
//							"Ending Basic PubNub Listener at: " + LocalDateTime.now().format(LOG_DATETIME_FORMATTER));
//					System.exit(0);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		});
	}

	public static void subscribeAll(PNConnTupleMultiChannel pnConnTuple) {

		(pnConnTuple.getChannels()).forEach(curChannel -> {
			curChannel.subscription().subscribe();
		});
	}
}
