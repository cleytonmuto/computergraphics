package computergraphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ImageFiltering extends JFrame {

	private static final long serialVersionUID = -1315225654104814314L;
	int w, h;
	private BufferedImage bi = null;

	public final float[] SHARPEN3x3 = { // sharpening filter kernel
			0.f, -1.f, 0.f, -1.f, 5.f, -1.f, 0.f, -1.f, 0.f };

	public final float[] BLUR3x3 = { 0.1f, 0.1f, 0.1f, // low-pass filter kernel
			0.1f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f };

	public ImageFiltering() {
		super("ImageFiltering");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {

		try {
			bi = ImageIO.read(new FileInputStream("resources/jasmine.jpg"));
			// bi = ImageIO.read( new FileInputStream( "lenna.png" ) );
			w = bi.getWidth(null);
			h = bi.getHeight(null);
			if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
				BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics big = bi2.getGraphics();
				big.drawImage(bi, 0, 0, null);
				bi = bi2;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics2D g2 = (Graphics2D) g;
		float[] data = SHARPEN3x3; // SHARPEN3x3;
		ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data), ConvolveOp.EDGE_NO_OP, null);
		g2.drawImage(bi, cop, 0, 0);
	}

	public static void main(String[] args) {
		ImageFiltering obj = new ImageFiltering();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}