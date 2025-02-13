package com.test.pubnub_observer.utils.connections;

import com.pubnub.api.PubNubException;
import com.pubnub.api.UserId;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.models.consumer.objects_api.membership.PNMembershipResult;
import com.pubnub.api.java.models.consumer.objects_api.uuid.PNUUIDMetadataResult;
import com.pubnub.api.java.v2.PNConfiguration;
import com.pubnub.api.java.v2.callbacks.EventListener;
import com.pubnub.api.java.v2.callbacks.StatusListener;
import com.pubnub.api.java.v2.entities.Channel;
import com.pubnub.api.java.v2.subscriptions.Subscription;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;
import com.pubnub.api.v2.subscriptions.SubscriptionOptions;
import com.test.pubnub_observer.config.PNObserverConfigSettings;
import com.test.pubnub_observer.config.PubNubSubscription;

public class PNConnUtils {
	private final static SubscriptionOptions EMPTY_SUB_OPTIONS = new SubscriptionOptions();

	private PNConnUtils() {
	}

	private static PubNub pubNubBasicInit(PNObserverConfigSettings configSettings) {
		try {
			PNConfiguration.Builder configBuilder = PNConfiguration.builder(new UserId(configSettings.getPubNubUser()),
					configSettings.getPubNubSubscribeKey());

			// configBuilder.filterExpression("");
			// configBuilder.dedupOnSubscribe(false);

			PNConfiguration pnConfiguration = configBuilder.build();

			PubNub pubNub = PubNub.create(pnConfiguration);

			if (configSettings.getAccessToken() != null) {
				pubNub.setToken(configSettings.getAccessToken());
			}
			
			return pubNub;
		} catch (PubNubException ex) {
			throw new RuntimeException("Failed to initialize PubNub object.", ex);
		}
	}

	public static PNConnTupleMultiChannel pubNubConnectionSetup(PNObserverConfigSettings configSettings) {
		PubNub pubnub = pubNubBasicInit(configSettings);

		PNConnTupleMultiChannel ret = new PNConnTupleMultiChannel(pubnub);

		System.out.println("Loading configured channels: ");

		(configSettings.getPubNubSubscriptions()).forEach(curChannel -> {
			System.out.println("\t" + curChannel.getChannelName());

			ret.addChannel(pubnub.channel(curChannel.getChannelName()));
		});

		return ret;
	}

	public static void addObservers(PNConnTupleMultiChannel pnConnTuple) {
		addObservers(pnConnTuple.getPubNubObj());
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

					// Quit as there is likely no recovery
					System.exit(-1);
				}
			}
		});

		pubnub.addListener(new EventListener() {
			@Override
			public void message(PubNub pubnub, PNMessageResult message) {
				// Handle new message stored in message.message

				System.out.println("Channel: '" + message.getChannel() + "' on subscription: '"
						+ message.getSubscription() + "' - Received message at time: " + message.getTimetoken()
						+ " - With Content: " + message.getMessage());
			}

			@Override
			public void signal(PubNub pubnub, PNSignalResult pnSignalResult) {
				System.out.println("Signal event on channel: " + pnSignalResult.getChannel() + " Signal Message: "
						+ pnSignalResult.getMessage());

			}

			@Override
			public void uuid(PubNub pubnub, PNUUIDMetadataResult pnUUIDMetadataResult) {
				System.out.println("Message UUID event on channel: " + pnUUIDMetadataResult.getChannel() + " UUID: "
						+ pnUUIDMetadataResult.getData());
			}

			@Override
			public void membership(PubNub pubnub, PNMembershipResult pnMembershipResult) {
				System.out.println("Membership Event on channel: " + pnMembershipResult.getChannel()
						+ " MembershipResult: " + pnMembershipResult.getData());
			}

			@Override
			public void messageAction(PubNub pubnub, PNMessageActionResult pnMessageActionResult) {
				System.out.println("Message action on channel: " + pnMessageActionResult.getChannel()
						+ " MessageAction: " + pnMessageActionResult.getMessageAction());

			}

			@Override
			public void presence(PubNub pubnub, PNPresenceEventResult presence) {
				System.out.println("Presence event on channel: '" + presence.getChannel() + "' Subscription: '"
						+ presence.getSubscription() + "' User: '" + presence.getUuid() + "' Event: '"
						+ presence.getEvent() + "' At time: " + presence.getTimetoken() + " Occupancy: "
						+ presence.getOccupancy());
			}
		});

	}

	/**
	 * Subscribe to all channels with default options.
	 * 
	 * @param pnConnTuple PubNub & channels list tuple object for changing setup
	 *                    config
	 * @return PubNub object used to setup connection for future reference.
	 */
	public static PubNub subscribeAll(PNConnTupleMultiChannel pnConnTuple) {

		(pnConnTuple.getChannels()).forEach(curChannel -> {
			curChannel.subscription().subscribe();
		});

		return pnConnTuple.getPubNubObj();

	}

	/**
	 * Subscribe to all channels with subscription options specified in
	 * {@link com.test.pubnub_observer.utils.connections.PNConnTupleMultiChannel}
	 * instance.
	 * 
	 * @param configSettings settings object for changing setup config
	 * @return PubNub object used to setup connection for future reference.
	 */
	public static PubNub subscribeAllWOptions(PNObserverConfigSettings configSettings) {
		PubNub pubnub = pubNubBasicInit(configSettings);

		addObservers(pubnub);

		System.out.println("Loading configured channels: ");

		(configSettings.getPubNubSubscriptions()).forEach(curSubcriptionDef -> {
			System.out.println("\t" + curSubcriptionDef.getChannelName());

			Channel curChannel = pubnub.channel(curSubcriptionDef.getChannelName());

			Subscription curSubscription = curChannel.subscription(loadSubscriptionOptions(curSubcriptionDef));
			System.out.println("\t\tSubscription opts: " + curSubcriptionDef.toString());

			curSubscription.subscribe();
		});

		return pubnub;
	}

	@SuppressWarnings("static-access")
	private static SubscriptionOptions loadSubscriptionOptions(PubNubSubscription subcriptionDef) {
		boolean presenceEvents = subcriptionDef.isReceivePresenceEvents();
		int filterMode = subcriptionDef.getFilterMode();

		if (presenceEvents || filterMode > 0) {
			SubscriptionOptions ret = new SubscriptionOptions();

			if (presenceEvents) {
				// Note: For some reason generates a new SubscriptionOptions object that needs
				// to be used, i.e., doesn't modify initial version in-place
				ret = ret.receivePresenceEvents();
			}

			return ret.plus(setMessageFilter(filterMode));
		} else {
			return EMPTY_SUB_OPTIONS;
		}
	}

	@SuppressWarnings("static-access")
	private static SubscriptionOptions setMessageFilter(int filterMode) {
		// TODO: add other filter modes
		SubscriptionOptions ret = new SubscriptionOptions();

		switch (filterMode) {
		case 1:
			return ret.filter(a -> {
				return true;
			});
		default:
			return EMPTY_SUB_OPTIONS;
		}
	}

}
