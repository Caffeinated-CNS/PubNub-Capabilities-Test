package com.test.pubnub_loader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.v2.callbacks.StatusListener;
import com.pubnub.api.java.v2.entities.Channel;
import com.pubnub.api.models.consumer.PNStatus;
import com.test.pubnub_loader.config.BasicPublisherConfigSettings;
import com.test.pubnub_loader.message.PNMessage;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class MessageLoader implements StatusListener, Runnable {
	private final static DateTimeFormatter LOG_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	@NonNull
	private PNConnTuple pnConnTuple;
	@NonNull
	private BasicPublisherConfigSettings configSettings;

	private static Channel channelRef = null;

	@Override
	public void status(PubNub pubnub, PNStatus status) {
		if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
			// This event happens when radio / connectivity is lost
			System.err.println("Issue with connection: " + status.getCategory());
			pnConnTuple.getPubNubObj().unsubscribeAll();
			System.exit(-1);
		} else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
			channelRef = pnConnTuple.getChannel();

			// Connect event. You can do stuff like publish, and know you'll get it.
			// Or just use the connected event to confirm you are subscribed for
			// UI / internal notifications, etc
			System.out.println("Connected to channel: " + channelRef.getName() + " on Thread: "
					+ Thread.currentThread().getName());

		}
		/* TODO: handle other statuses */

	}

	@Override
	public void run() {
		int sentMessages = 0;

		System.out.println("Calling message submission on Thread: " + Thread.currentThread().getName());
		channelRef = pnConnTuple.getChannel();

		// Fail if setup is not completed and we're ready to send messages to the PubNub
		// Channel.
		if (channelRef == null) {
			System.err.println(
					"Thread " + Thread.currentThread().getName() + " called without PubNub setup completed at: "
							+ LocalDateTime.now().format(LOG_DATETIME_FORMATTER));
//			return Integer.valueOf(sentMessages);
			throw new RuntimeException("Channel setup not complete at thread run time.");
		}

		// Submit messages up to the per thread count.
		// End thread once submission count is completed & log time.

		LocalDateTime startDT = LocalDateTime.now();

		for (sentMessages = 0; sentMessages < configSettings.getPerThreadMessageCount(); sentMessages++) {
			// TODO: Test with meaningful sized message content. I.e., overwrite default
			// from PNMessage.of()
			channelRef.publish(PNMessage.of()).async(result -> {
				result
//				.onSuccess(res -> {
//					// Uncomment for more info about each message submission time, but will slow
//					// down volume testing _slightly_
//					System.out.println("Published at: " + res.getTimetoken());
//
////					pnConnTuple.getPubNubObj().unsubscribeAll();
////					System.out.println("Ending Basic PubNub Publisher at: "
////							+ LocalDateTime.now().format(LOG_DATETIME_FORMATTER));
////					System.exit(0);
//				})
				.onFailure(exception -> {
					// TODO: Refactor for multi-thread and partial failure scenarios.
					System.err.println("Failed to publish at: " + exception.getMessage());
					pnConnTuple.getPubNubObj().unsubscribeAll();
					System.err.println(
							"Ending Basic PubNub Publisher at: " + LocalDateTime.now().format(LOG_DATETIME_FORMATTER));
					System.exit(0);
				});
			});
		}

		LocalDateTime endDT = LocalDateTime.now();
		System.out.println("Ending thread " + Thread.currentThread().getId() + " at: "
				+ endDT.format(LOG_DATETIME_FORMATTER) + ".  Duration in msecs: "
				+ Duration.between(startDT, endDT).toMillis() + " Messages Sent: " + sentMessages);	
	}

}
