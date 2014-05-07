package com.eric.mina.test.server.demux;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

import com.eric.mina.test.client.demux.ResultMessageDecoder;
import com.eric.mina.test.client.demux.SendMessage;
import com.eric.mina.test.client.demux.SendMessageEncoder;

public class MathProtocolCodecFactory extends DemuxingProtocolCodecFactory {
	public MathProtocolCodecFactory(boolean server) {
		if (server) {
			super.addMessageEncoder(ResultMessage.class, ResultMessageEncoder.class);
			super.addMessageDecoder(SendMessageDecoderNegative.class);
			super.addMessageDecoder(SendMessageDecoderPositive.class);
		} else {
			super.addMessageEncoder(SendMessage.class, SendMessageEncoder.class);
			super.addMessageDecoder(ResultMessageDecoder.class);
		}
	}
}
