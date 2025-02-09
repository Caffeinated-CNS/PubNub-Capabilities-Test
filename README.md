# PubNub-Capabilities-Test

A basic set of functionality test against expected behavior of PubNub SaaS offering.

Targeting staying within the limitations of the free tier / trial usage on PubNub.

---
---

**utils/** folder contains tools to:
- YAMLConfigCreator
	- Make externalizing configurations via YAML easier to write and update within code.

---

**servers/** folder contains example apps for:
- PubNub_Loader
	- Loading a message into a YAML file configured PubNub channel.
	- Simplest implementation just to test connectivity.

---

**clients/** folder contains example apps for:
- PubNub_Listener
	- An app for listening to a single YAML file configured PubNub channel.  
	- Simpliest implementation just to check connectivity.