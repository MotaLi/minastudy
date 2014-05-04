package com.eric.mina.test.client.endecoder;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.eric.mina.test.server.endecoder.SmsObject;

public class CmccSipcDecoder extends CumulativeProtocolDecoder {
	private final Charset charset;

	

	public CmccSipcDecoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {		
		IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
		CharsetDecoder cd = charset.newDecoder();
		int matchCount = 0;
		String statusLine = "", sender = "", receiver = "", length = "", sms = "";
		int i = 1;
		while (in.hasRemaining()) {
			byte b = in.get();
			buffer.put(b);
			if (b == 10 && i < 5) {
				matchCount++;
				if (i==1) {
					buffer.flip();
					statusLine = buffer.getString(matchCount, cd);
					statusLine = statusLine.substring(0, statusLine.length()-1);
					matchCount = 0;
					buffer.clear();
				}
				if (i==2) {
					buffer.flip();
					sender = buffer.getString(matchCount, cd);
					sender = sender.substring(0, sender.length()-1);
					matchCount = 0;
					buffer.clear();
				}
				if (i==3) {
					buffer.flip();
					receiver = buffer.getString(matchCount, cd);
					receiver = receiver.substring(0, receiver.length()-1);
					matchCount = 0;
					buffer.clear();
				}
				if (i==4) {
					buffer.flip();
					length = buffer.getString(matchCount, cd);
					length = length.substring(0, length.length()-1);
					matchCount = 0;
					buffer.clear();
				}
				i++;
			} else if(i==5) {
				matchCount++;
				if(matchCount == Long.parseLong(length.split(":")[1])) {
					buffer.flip();
					sms = buffer.getString(matchCount, cd);
					i++;
					break;
				}
				
			} else {
				matchCount++;
			}
			
		}
		SmsObject smsObject  = new SmsObject();
		smsObject.setSender(sender.split(":")[1]);
		smsObject.setReceiver(receiver.split(":")[1]);
		smsObject.setMessage(sms);
		out.write(smsObject);
		return false;
	}
	
	
	private final AttributeKey CONTENT = new AttributeKey(getClass(), "content");
	
	public Context getContext(IoSession session) {
		Context ctx = (Context) session.getAttribute(CONTENT);
		if (null == ctx) {
			ctx = new Context();
			session.setAttribute(CONTENT, ctx);
		}
		return ctx;
	}
	
	private class Context {
		// 状态变量
	}

}
