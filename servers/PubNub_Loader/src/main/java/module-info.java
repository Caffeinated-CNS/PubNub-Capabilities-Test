/**
 * 
 */
module pubnub_loader {
	exports com.test.pubnub_loader.config;
	exports com.test.pubnub_loader;

	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.dataformat.yaml;
	requires static lombok;
	requires java.base;
	requires pubnub.gson.api;
	requires pubnub.kotlin.core.api.jvm;
}