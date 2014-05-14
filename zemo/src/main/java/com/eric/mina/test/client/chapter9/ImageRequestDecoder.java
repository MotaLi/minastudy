package com.eric.mina.test.client.chapter9;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ImageRequestDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		if (in.remaining() >= 12) {
			ImageRequest img = new ImageRequest();
			img.setWidth(in.getInt());
			img.setHeight(in.getInt());
			img.setNumberOfCharacters(in.getInt());
			out.write(img);
			return true;
		}
		return false;
	}

}
