package com.eric.mina.test.server.demux;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.eric.mina.test.client.demux.SendMessage;
/**
 * 1号解码器
 * 
 */
public class SendMessageDecoderPositive implements MessageDecoder {

	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		if (in.remaining() < 2) {
			return MessageDecoderResult.NEED_DATA;
		} else {
			char symbol = in.getChar();
			if (symbol == '+') {
				return MessageDecoderResult.OK;
			} else {
				return MessageDecoderResult.NOT_OK;
			}
		}
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		SendMessage sm = new SendMessage();
		sm.setSymbol(in.getChar());
		sm.setI(in.getInt());
		sm.setJ(in.getInt());
		out.write(sm);
		return MessageDecoderResult.OK;
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
