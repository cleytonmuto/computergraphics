package computergraphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class LoadPicture extends JFrame {

	private static final long serialVersionUID = -8938418473348269025L;

	public LoadPicture() {
		super("LoadPicture");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {
		BufferedImage imagem1 = null;
		BufferedImage imagem2 = null;
		try {
			imagem1 = ImageIO.read(new File("resources/lenna.png"));
			imagem2 = ImageIO.read(new File("resources/desenho.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(imagem1, 100, 100, null);
		g.drawImage(imagem2, 200, 200, null);
	}

	public static void main(String[] args) {
		LoadPicture obj = new LoadPicture();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}