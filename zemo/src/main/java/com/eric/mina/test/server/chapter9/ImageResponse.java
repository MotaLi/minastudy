package com.eric.mina.test.server.chapter9;

import java.awt.image.BufferedImage;

public class ImageResponse {
	private BufferedImage img1;
	private BufferedImage img2;

	public ImageResponse(BufferedImage img1, BufferedImage img2) {
		this.img1 = img1;
		this.img2 = img2;
	}

	public BufferedImage getImg1() {
		return img1;
	}

	public void setImg1(BufferedImage img1) {
		this.img1 = img1;
	}

	public BufferedImage getImg2() {
		return img2;
	}

	public void setImg2(BufferedImage img2) {
		this.img2 = img2;
	}
}
