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
		BufferedImage bufferedImage = new BufferedImage(800, MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D gg = bufferedImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
		g.setColor(Color.blue);
		g.drawLine(100, 100, MAX_WIDTH / 2, MAX_HEIGHT / 2);
		gg.setColor(Color.white);
		gg.fillRect(0, 0, 800, MAX_HEIGHT);
		gg.setColor(Color.blue);
		gg.drawLine(100, 100, MAX_WIDTH / 2, MAX_HEIGHT / 2);
		try {
			FileOutputStream file = new FileOutputStream("resources/desenho_teste.png");
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