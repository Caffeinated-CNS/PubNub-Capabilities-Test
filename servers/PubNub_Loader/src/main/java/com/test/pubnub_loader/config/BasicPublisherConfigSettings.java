package com.test.pubnub_loader.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BasicPublisherConfigSettings {
	private String pubNubUser = null;
	private String pubNubPublishKey = null;
	private String pubNubPublishChannel = null;
	private String pubNubSubscribeKey = null;
	private String secret = null;
	private int perThreadMessageCount = 100;
	private int threadCount = 5;
	private int submissionTimeoutSecounds = 30;
}
