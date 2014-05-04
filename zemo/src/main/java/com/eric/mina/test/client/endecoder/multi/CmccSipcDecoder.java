package com.eric.mina.test.client.endecoder.multi;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.eric.mina.test.server.endecoder.multi.SmsObject;


public class CmccSipcDecoder extends CumulativeProtocolDecoder {
	private final Charset charset;

	

	public CmccSipcDecoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		Context ctx = getContext(session);		
		CharsetDecoder cd = charset.newDecoder();
		
		IoBuffer buffer = ctx.innerBuffer;
		int matchCount = ctx.getMatchCount();
		int line = ctx.getLine();
		String statusLine = ctx.getStatusline(),
			   sender = ctx.getSender(),
			   receiver = ctx.getReceiver(),
			   length = ctx.getLength(),
			   sms = ctx.getSms();
		
		while (in.hasRemaining()) {
			byte b = in.get();
			matchCount++;
			buffer.put(b);
			if (b == 10 && line < 4) {				
				if (line==0) {
					buffer.flip();
					statusLine = buffer.getString(matchCount, cd);
					statusLine = statusLine.substring(0, statusLine.length()-1);
					matchCount = 0;
					buffer.clear();
					ctx.setStatusline(statusLine);
				}
				if (line==1) {
					buffer.flip();
					sender = buffer.getString(matchCount, cd);
					sender = sender.substring(0, sender.length()-1);
					matchCount = 0;
					buffer.clear();
					ctx.setSender(sender);
				}
				if (line==2) {
					buffer.flip();
					receiver = buffer.getString(matchCount, cd);
					receiver = receiver.substring(0, receiver.length()-1);
					matchCount = 0;
					buffer.clear();
					ctx.setReceiver(receiver);					
				}
				if (line==3) {
					buffer.flip();
					length = buffer.getString(matchCount, cd);
					length = length.substring(0, length.length()-1);
					matchCount = 0;
					buffer.clear();
					ctx.setLength(length);
				}
				line++;
			} else if(line==4) {				
				if(matchCount == Long.parseLong(length.split(":")[1])) {
					buffer.flip();
					sms = buffer.getString(matchCount, cd);
					ctx.setSms(sms);
					// 由于下面的break, 这里需要调用else外面的两行代码
					ctx.setMatchCount(matchCount);
					ctx.setLine(line);
					break;
				}
				
			}
			ctx.setMatchCount(matchCount);
			ctx.setLine(line);			
		}
		if (ctx.getLine() == 4 && Long.parseLong(length.split(":")[1]) == ctx.getMatchCount()) {
			SmsObject smsObject  = new SmsObject();
			smsObject.setSender(sender.split(":")[1]);
			smsObject.setReceiver(receiver.split(":")[1]);
			smsObject.setMessage(sms);
			out.write(smsObject);
			ctx.reset();
			return true;
		} else {
			return false;
		}		
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
		private final IoBuffer innerBuffer;
		private String statusline = "";
		private String sender = "";
		private String receiver = "";
		private String length = "";
		private String sms = "";
		private int matchCount = 0;
		private int line = 0;
		
		public Context() {
			this.innerBuffer = IoBuffer.allocate(100).setAutoExpand(true);
		}
		
		public void reset() {
			this.innerBuffer.clear();
			this.statusline = "";
			this.sender = "";
			this.receiver = "";
			this.length = "";
			this.sms = "";
			this.matchCount = 0;
			this.line = 0;
		}
		public String getStatusline() {
			return statusline;
		}
		public void setStatusline(String statusline) {
			this.statusline = statusline;
		}
		public String getSender() {
			return sender;
		}
		public void setSender(String sender) {
			this.sender = sender;
		}
		public String getReceiver() {
			return receiver;
		}
		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}
		public String getLength() {
			return length;
		}
		public void setLength(String length) {
			this.length = length;
		}
		public String getSms() {
			return sms;
		}
		public void setSms(String sms) {
			this.sms = sms;
		}
		public int getMatchCount() {
			return matchCount;
		}
		public void setMatchCount(int matchCount) {
			this.matchCount = matchCount;
		}
		public int getLine() {
			return line;
		}
		public void setLine(int line) {
			this.line = line;
		}	
	}

}
