/**
 * 
 */
module pubnub_listener {
	exports com.test.pubnub_listener.config;
	exports com.test.pubnub_listener;

	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.dataformat.yaml;
	requires static lombok;
	requires java.base;
	requires pubnub.gson.api;
	requires pubnub.kotlin.core.api.jvm;
	requires com.google.gson;
	requires com.fasterxml.jackson.annotation;
}