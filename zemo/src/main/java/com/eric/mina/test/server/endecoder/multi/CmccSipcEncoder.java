package com.eric.mina.test.server.endecoder.multi;

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
		String statusLine = "M sip:wap.fetion.com.cn SIP-C/2.0";
		String sender = sms.getSender();
		String receiver = sms.getReceiver();
		String smsContent = sms.getMessage();
		
//		上面的这段代码要配合MyClient来操作，你需要做的是在MyClient中的红色输出语句处设置
//		断点，然后第一调用时CmccSipcEncoder中注释掉蓝、绿色的代码，也就是发送两条短信息的
//		第一部分（红色的代码），依次类推，也就是MyClient的中的三次断点中，分别执行
	
		// step 1 paragraph 1
//		IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
//		buffer.putString(statusLine + '\n', ce);
//		buffer.putString("S:" + sender + '\n' , ce);
//		buffer.putString("R:" + receiver + '\n', ce);
//		buffer.flip();
//		out.write(buffer);
		
		// step 2 paragraph 2
//		IoBuffer buffer2 = IoBuffer.allocate(100).setAutoExpand(true);
//		buffer2.putString("L:" + (smsContent.getBytes(charset).length) + "\n", ce);
//		buffer2.putString(smsContent, ce);
//		buffer2.flip();
//		out.write(buffer2);
		
//		// step 3 paragraph 3
		IoBuffer buffer3 = IoBuffer.allocate(100).setAutoExpand(true);
		buffer3.putString(statusLine + '\n', ce);
		buffer3.putString("S:" + sender + '\n' , ce);
		buffer3.putString("R:" + receiver + '\n', ce);
		buffer3.putString("L:" + (smsContent.getBytes(charset).length) + "\n", ce);
		buffer3.putString(smsContent, ce);
		buffer3.putString(statusLine + '\n', ce);
		buffer3.flip();
		out.write(buffer3);
	}

}
