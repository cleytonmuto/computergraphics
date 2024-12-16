package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class SavePicture extends JFrame {

	private static final long serialVersionUID = -735629593221703551L;
	private int MAX_WIDTH;
	private int MAX_HEIGHT;

	public SavePicture() {
		super("SavePicture");
		MAX_WIDTH = 800;
		MAX_HEIGHT = 600;
		setSize(MAX_WIDTH, MAX_HEIGHT);
		setVisible(true);
	}

	public void paint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D gg = bufferedImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
		gg.setColor(Color.white);
		gg.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
		for (int i = 0; i < 20; i++) {
			int red = (int) ( 256 * Math.random());
			int green = (int) ( 256 * Math.random());
			int blue = (int) ( 256 * Math.random());
			int x1 = (int) (MAX_WIDTH * Math.random() / 2.0);
			int y1 = (int) (MAX_HEIGHT * Math.random() / 2.0);
			int x2 = (int) (MAX_WIDTH * Math.random() / 2.0);
			int y2 = (int) (MAX_HEIGHT * Math.random() / 2.0);
			g.setColor(new Color(red, green, blue));
			g.drawLine(x1,  y1,  x2,  y2);
			gg.setColor(new Color(red, green, blue));
			gg.drawLine(x1,  y1,  x2,  y2);
		}
		try {
			FileOutputStream file = new FileOutputStream("resources/desenho.png");
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SavePicture obj = new SavePicture();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}