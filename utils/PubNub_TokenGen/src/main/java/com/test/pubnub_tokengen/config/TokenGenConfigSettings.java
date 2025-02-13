package com.test.pubnub_tokengen.config;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class TokenGenConfigSettings {
	@NonNull
	private String pubNubUser;
	private String pubNubPublishKey = null;
	private String pubNubSubscribeKey = null;
	@NonNull
	private String secret;

	private int ttl = 1;

	private List<ChannelGrantConfig> channelGrants = new ArrayList<>();

	public String getPubNubPublishKey() {
		return pubNubPublishKey;
	}

	public void setPubNubPublishKey(String pubNubPublishKey) {
		this.pubNubPublishKey = pubNubPublishKey;
	}

	public String getPubNubUser() {
		return pubNubUser;
	}

	public void setPubNubUser(String pubNubUser) {
		this.pubNubUser = pubNubUser;
	}

	public String getPubNubSubscribeKey() {
		return pubNubSubscribeKey;
	}

	public void setPubNubSubscribeKey(String pubNubSubscribeKey) {
		this.pubNubSubscribeKey = pubNubSubscribeKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public List<ChannelGrantConfig> getChannelGrants() {
		return channelGrants;
	}

	public void setChannelGrants(List<ChannelGrantConfig> channelGrants) {
		this.channelGrants.addAll(channelGrants);
	}

}
