package com.eric.mina.test.server.endecoder;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class CmccSipcEncoder extends ProtocolEncoderAdapter {
	private final Charset charset;

	public CmccSipcEncoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {		
		SmsObject sms = (SmsObject) message;
		CharsetEncoder ce = charset.newEncoder();
		IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
		String statusLine = "M sip:wap.fetion.com.cn SIP-C/2.0";
		String sender = sms.getSender();
		String receiver = sms.getReceiver();
		String smsContent = sms.getMessage();
		buffer.putString(statusLine + '\n', ce);
		buffer.putString("S:" + sender + '\n' , ce);
		buffer.putString("R:" + receiver + '\n', ce);
		buffer.putString("L:" + (smsContent.getBytes(charset).length) + "\n", ce);
		buffer.putString(smsContent, ce);
		
		/* 当你组装数据完毕之后，调用 flip()方法，为输出做好准备，切记在 write()方法之前，
		要调用 IoBuffer 的 flip()方法，否则缓冲区的 position 的后面是没有数据可以用来输
		出的，你必须调用 flip()方法将 position 移至 0，limit 移至刚才的 position。这个
		flip()方法的含义请参看 java.nio.ByteBuffer。 */
		buffer.flip();
		out.write(buffer);

	}

}
