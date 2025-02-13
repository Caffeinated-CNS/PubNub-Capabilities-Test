package com.test.pubnub_observer.utils.pubnub;

import com.pubnub.api.PubNubException;
import com.pubnub.api.java.PubNub;
import com.pubnub.api.java.models.consumer.objects_api.member.PNGetChannelMembersResult;
import com.pubnub.api.java.models.consumer.objects_api.membership.PNGetMembershipsResult;
import com.test.pubnub_observer.config.PNObserverConfigSettings;

public class PNObjectInspector {

	private PNObjectInspector() {
	}

	public static void checkAndPrintExtendedInfo(PubNub pubNub, PNObserverConfigSettings configSettings) {

		if (configSettings.isSetCheckUserMemberships()) {
			PNObjectInspector.printUserMemberships(pubNub);
		}

		if (configSettings.isSetListOtherChannelMembers()) {
			PNObjectInspector.printChannelMembers(pubNub);
		}

	}

	public static void printUserMemberships(PubNub pubNub) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("User: '" + pubNub.getConfiguration().getUserId() + "' has current membership in: \n");

		try {
			PNGetMembershipsResult pnGetMembershipsResult = pubNub.getMemberships().sync();

			stringBuilder.append("mems: " + pnGetMembershipsResult.getTotalCount());

			pnGetMembershipsResult.getData().forEach(curMem -> {
				stringBuilder.append("\t\t" + curMem + "\n");
			});
			System.out.println(stringBuilder.toString());

		} catch (PubNubException e) {
			System.err.println("Failed to retrieve Channel Memberships, check logs for Access Manager settings.");
			System.err.println("Exiting.");

			e.printStackTrace();

			// Quit as there is no recovery from failure this early in setup.
			System.exit(-1);
		}
	}

	public static void printChannelMembers(PubNub pubNub) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("User: '" + pubNub.getConfiguration().getUserId() + "' is accompanied by: \n");

		try {
			PNGetMembershipsResult pnGetMembershipsResult = pubNub.getMemberships().sync();

			pnGetMembershipsResult.getData().forEach(curMembership -> {

				try {
					PNGetChannelMembersResult channelMembers = pubNub
							.getChannelMembers(curMembership.getChannel().getId()).sync();

					stringBuilder.append("\tChannel: '" + "' has members: \n");

					channelMembers.getData().forEach(curMember -> {
						stringBuilder.append("\t\t" + curMember.getUuid().getId() + "\n");
					});

				} catch (PubNubException e) {
					System.err.println("Failed to retrieve Channel Members, check logs for Access Manager settings.");
					System.err.println("Exiting.");

					e.printStackTrace();

					// Quit as there is no recovery from failure this early in setup.
					System.exit(-1);
				}
			});
			System.out.println(stringBuilder.toString());

		} catch (PubNubException e) {
			System.err.println("Failed to retrieve Channel Memberships, check logs for Access Manager settings.");
			System.err.println("Exiting.");

			e.printStackTrace();

			// Quit as there is no recovery from failure this early in setup.
			System.exit(-1);
		}

	}
}
