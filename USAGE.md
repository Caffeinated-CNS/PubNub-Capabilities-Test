# PubNub-Capabilities-Test

A usual workflow would be to:
1. Create a publisher config with your keys + secret & test run
	1. Download [servers/PubNub_Loader](https://github.com/Caffeinated-CNS/PubNub-Capabilities-Test/tree/main/servers/PubNub_Loader) app locally.
	2. Update the ** servers/PubNub_Loader/configs/BasicPublisher - Example.yaml ** file with your values.
		1. pubNubUser for username to publish with.
		2. pubNubPublishKey string with your "Publish key" string from your PubNub "Keysets" page.
		3. pubNubSubscribeKey string with your "Subscribe key" string from your PubNub "Keysets" page.
		4. pubNubPublishChannel string with the channel you want to submit messages to.
		5. secret string with your "Secret key" string from your PubNub "Keysets" page.  ** Note: this is a convience so you don't have to generate an AccessKey, having the secret assigned will let the SDK automatically take care of access control for you. **
	3. Rename the ** servers/PubNub_Loader/configs/BasicPublisher - Example.yaml ** file to ** BasicPublisher.yaml **, retain in the config folder.
	4. Run ** servers/PubNub_Loader/Build_n_Run.bat **, provided your maven is on your classpath, it will compile and execute the BasicPublisher app and submit a plain text message like "Test Message @ 2025-02-12T22:02:26.980000500".
3. Create an observer config with your keys + secret & test run
	1. Download [clients/PubNub_Observer](https://github.com/Caffeinated-CNS/PubNub-Capabilities-Test/tree/main/clients/PubNub_Observer) app locally.
	2. Update the ** clients/PubNub_Observer/configs/PNObserver - Example.yaml ** file with your values.
		1. pubNubUser for username to subscribe with.
		2. pubNubSubscribeKey string with your "Subscribe key" string from your PubNub "Keysets" page.
		3. cursor number can be left alone, unless you want to get messages from message persistence (if enabled).
		4. access token string granting permission for this user + subscribe key to subscribe to the configured channels below.  ** Note: these tokens expire and you will get an error response that states this when it happens. **
		5. setCheckUserChannelMemberships has a bug, leave it as false (see issues for project for more info).
		6. setPrintChannelMetadata not currently enabled.
		7. setListOtherChannelMembers has a bug, leave it as false (see issues for project for more info).
		8. pubNubSubscriptions is a collection of channels you want to subscribe to, you need at least one in the config.  ** Note: these will be required to be passed into the Access Token list in next app setup step. **
			1. A channel subscription has two elements, a string channelName and a boolean flag for receivePresenceEvents.  **Note: if receivePresenceEvents is set to true, you will need to grant permissions to each "-pnpres" channel, in addition to the main channel.  E.g., Channel1 & Channel1-pnpres **
	3. Rename the ** clients/PubNub_Observer/configs/PNObserver - Example.yaml ** file to ** PNObserver.yaml **, retain in the config folder.
	4. Run ** clients/PubNub_Observer/Build_n_Run.bat **, provided your maven is on your classpath, it will compile and execute the PubNub_Observer app.  ** Note: without an active access key, it will fail to connect. **
4. Create an Access Token Generator config with your keys + secret, channel configuration and generate a key for the Observer app.
	1. Download [utils/PubNub_TokenGen](https://github.com/Caffeinated-CNS/PubNub-Capabilities-Test/tree/main/utils/PubNub_TokenGen) app locally.
	2. Update the ** utils/PubNub_TokenGen/configs/TokenGenerator - Example.yaml ** file with your values.
		1. pubNubUser for username to subscribe with.
		2. pubNubPublishKey string with your "Publish key" string from your PubNub "Keysets" page. 
		3. pubNubSubscribeKey string with your "Subscribe key" string from your PubNub "Keysets" page.
		4. secret string with your "Secret key" string from your PubNub "Keysets" page.
		5. Time to Live for access token, units are minutes.
		6. channelGrants is the direct channel names or patterns + permissions to enable anyone with the Access Token to access.  ** Note: if you have presence enabled you need to include the -pnpres version of the channels you are listening to (e.g., Channel2 & Channe2-pnpres). **
	3. Run ** utils/PubNub_TokenGen/Build_n_Run.bat **, provided your maven is on your classpath, it will compile and execute the Access Token generator app.  ** Note: this should be run in an existing command prompt, if run via explorer it immediately closes after printing the token... **
5. Re-run observer with Access Token to start listening for messages.
	1. Take the Access Token string from the generator step 4.3, and update ** clients/PubNub_Observer/configs/PNObserver.yaml **.
	2. Run ** clients/PubNub_Observer/Build_n_Run.bat **, provided your maven is on your classpath, it will compile and execute the PubNub_Observer app and end with something like
		
		> Loading configured channels: 
		>	Channel1
		>		Subscription opts: PubNubSubscription(channelName=Channel1, receivePresenceEvents=false, filterMode=0)
		>	Channel2
		>		Subscription opts: PubNubSubscription(channelName=Channel2, receivePresenceEvents=false, filterMode=1)
		>	Channel3
		>		Subscription opts: PubNubSubscription(channelName=Channel3, receivePresenceEvents=false, filterMode=0)
		>	Connected to channel(s): 
		>		Channel1
		>		Channel2
		>		Channel3 
	
6. Re-run BasicPublisher with config to message on an Observer channel
	1. Run ** servers/PubNub_Loader/Build_n_Run.bat **, provided your maven is on your classpath, it will compile and execute the BasicPublisher app and submit a plain text message like 
	
		> Test Message @ 2025-02-12T22:02:26.980000500
	
	2. Look at the Observer logs, it should show something like
	
		> Channel: 'Channel1' on subscription: 'null' - Received message at time: 17394229470751768 - With Content: "Test Message @ 2025-02-12T22:02:26.980000500"
	
