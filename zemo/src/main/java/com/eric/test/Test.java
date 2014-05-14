package com.eric.test;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

public class Test {

	public void testIoBuffer() {
		IoBuffer buffer = IoBuffer.allocate(16);
		// set no use Direct Buffer;
		IoBuffer.setUseDirectBuffer(false);
		
		buffer.setAutoShrink(true);
		buffer.put((byte)1);
		System.out.println("Initial Buffer capacity = "+buffer.capacity());
		buffer.shrink();
		System.out.println("Initial Buffer capacity after shrink = "+buffer.capacity());
		buffer.capacity(32);
		System.out.println("Buffer capacity after incrementing capacity to 32 = "+buffer.capacity());
		buffer.shrink();
		System.out.println("Buffer capacity after shrink= "+buffer.capacity());
		try {
			// when buffer Expand is not true.
			// buffer capacity is 16, so the over limit char '6' will ignore,
			// and cause a BufferOverFlowException
			buffer.putString("1234567890123456", Charset.forName("UTF-8").newEncoder());
			System.out.println("---------------------");	
			System.out.println("Buffer capacity after shrink= "+buffer.capacity());
		} catch (Exception e) {
			// Expand is true; Buffer capacity increased.
			// Test AutoExpand Buffer 
			// Its capacity will double, and its limit will increase to the last position the string is written.
			// This behavior is very similar to the way StringBuffer class works.
			
			/// This mechanism is very likely to be removed from MINA 3.0, 
			/// as it's not really the best way to handle increased buffer size. 
			/// It should be replaced by something like a InputStream hiding a list or an array of fixed sized ByteBuffers. 
			buffer.setAutoExpand(true);
			try {
				buffer.putString("1234567890123456", Charset.forName("UTF-8").newEncoder());			
				System.out.println("Buffer capacity after Expand= "+buffer.capacity());
				buffer.flip();
				String  tmp = buffer.getString(Charset.forName("UTF-8").newDecoder());
				System.out.println(tmp);
			} catch (Exception e1) {				
				System.out.println("Buffer is AutoExpand at moment.");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Test t = new Test();
		t.testIoBuffer();

	}

}
