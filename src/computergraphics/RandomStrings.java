package computergraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

public class RandomStrings extends JFrame {

	private static final long serialVersionUID = -4388534132459558792L;

	public RandomStrings() {
		super("RandomStrings");
		setSize(1600, 1200);
		setVisible(true);
	}

	public void paint(Graphics g) {
		Font font = new Font("Times New Roman", Font.PLAIN, 72);
		g.setFont(font);
		for (int i = 0; i < 1000; i++) {
			int red = (int) (256 * Math.random());
			int green = (int) (256 * Math.random());
			int blue = (int) (256 * Math.random());
			g.setColor(new Color(red, green, blue));
			int x = (int) (1600 * Math.random());
			int y = (int) (1200 * Math.random());
			// String str = ""+ ( char ) ( 256 * Math.random( ) );
			String str = "BRASIL";
			g.drawString(str, x, y);
		}
	}

	public static void main(String[] args) {
		RandomStrings obj = new RandomStrings();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}