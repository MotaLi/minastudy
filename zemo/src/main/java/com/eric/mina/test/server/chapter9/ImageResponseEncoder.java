package com.eric.mina.test.server.chapter9;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ImageResponseEncoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		ImageResponse imgrep = (ImageResponse) message;
		byte[] img1 = getBytes(imgrep.getImg1());
		byte[] img2 = getBytes(imgrep.getImg2());
		int capacity = img1.length + img2.length + 8;
		IoBuffer buffer = IoBuffer.allocate(capacity, false).setAutoExpand(false);
		buffer.putInt(img1.length);
		buffer.put(img1);
		buffer.putInt(img2.length);
		buffer.put(img2);
		buffer.flip();
		out.write(buffer);
	}

	private byte[] getBytes(BufferedImage img) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "PNG", baos);
		return baos.toByteArray();
	}

}
