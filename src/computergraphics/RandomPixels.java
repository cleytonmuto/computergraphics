package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class RandomPixels extends JFrame {

	private static final long serialVersionUID = -365806127595884985L;
	private int MAX_RES_X = 1024, MAX_RES_Y = 768;

	public RandomPixels() {
		super("Random Pixels");
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
	}

	public void paint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(MAX_RES_X, MAX_RES_Y, BufferedImage.TYPE_INT_RGB);
		Graphics2D gg = bufferedImage.createGraphics();
		for (int i = 0; i < 2000000; i++) {
			int vermelho = (int) (256 * Math.random());
			int verde = (int) (256 * Math.random());
			int azul = (int) (256 * Math.random());
			g.setColor(new Color(vermelho, verde, azul));
			gg.setColor(new Color(vermelho, verde, azul));
			int x = (int) (MAX_RES_X * Math.random());
			int y = (int) (MAX_RES_Y * Math.random());
			g.drawLine(x, y, x, y);
			gg.drawLine(x, y, x, y);
		}
		try {
			FileOutputStream file = new FileOutputStream("resources/random.bmp");
			ImageIO.write(bufferedImage, "bmp", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done.");
	}

	public static void main(String args[]) {
		RandomPixels instance = new RandomPixels();
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}