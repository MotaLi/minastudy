package com.eric.mina.test.server.endecoder.multi;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.eric.mina.test.client.endecoder.multi.CmccSipcDecoder;



public class CmccSipcCodecFactory implements ProtocolCodecFactory {
	private final CmccSipcEncoder encoder;
	private final CmccSipcDecoder decoder;
	
	public CmccSipcCodecFactory() {
		this(Charset.defaultCharset());
	}
	
	public CmccSipcCodecFactory(Charset charset) {
		this.encoder = new CmccSipcEncoder(charset);
		this.decoder = new CmccSipcDecoder(charset);		
	}
	

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}

}
