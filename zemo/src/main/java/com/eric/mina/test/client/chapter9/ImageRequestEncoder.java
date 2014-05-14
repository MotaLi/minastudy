package com.eric.mina.test.client.chapter9;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ImageRequestEncoder implements ProtocolEncoder {

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		ImageRequest img = (ImageRequest)message;
		IoBuffer buffer = IoBuffer.allocate(12, false).setAutoExpand(false);
		buffer.putInt(img.getWidth());
		buffer.putInt(img.getHeight());
		buffer.putInt(img.getNumberOfCharacters());
		buffer.flip();
		out.write(buffer);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub		
	}
}
