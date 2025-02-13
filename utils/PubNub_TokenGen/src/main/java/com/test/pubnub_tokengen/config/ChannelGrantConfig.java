package com.test.pubnub_tokengen.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ChannelGrantConfig {
	private String channelName = null;
	private String channelPattern = null;
	private boolean readEnabled;
	private boolean writeEnabled;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelPattern() {
		return channelPattern;
	}

	public void setChannelPattern(String channelPattern) {
		this.channelPattern = channelPattern;
	}

	public boolean isReadEnabled() {
		return readEnabled;
	}

	public void setReadEnabled(boolean readEnabled) {
		this.readEnabled = readEnabled;
	}

	public boolean isWriteEnabled() {
		return writeEnabled;
	}

	public void setWriteEnabled(boolean writeEnabled) {
		this.writeEnabled = writeEnabled;
	}

}
