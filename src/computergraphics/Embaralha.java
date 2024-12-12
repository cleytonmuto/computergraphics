package computergraphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Embaralha extends JFrame {

	private static final long serialVersionUID = 7364286291082894314L;
	private BufferedImage imagem;

	public Embaralha() {
		super("Embaralha");
		setSize(800, 600);
		setVisible(true);
		try {
			imagem = ImageIO.read(new File("resources/mini_lenna.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		for (int i = 0; i < 1000; i++) {
			g.drawImage(imagem, (int) (800 * Math.random()), (int) (600 * Math.random()), null);
		}
	}

	public static void main(String[] args) {
		Embaralha obj = new Embaralha();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}