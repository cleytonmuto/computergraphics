package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class TesteCores extends JFrame {

	private static final long serialVersionUID = 1800460999607965839L;

	public TesteCores() {
		super("TesteCores");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {
		int[] arrayCores = { 0x663300, // marrom
				0x000066, // indigo
				0x9966CC // violeta
		};
		Color[] c = new Color[arrayCores.length];
		for (int i = 0; i < arrayCores.length; i++) {
			c[i] = new Color(arrayCores[i]);
			g.setColor(c[i]);
			g.fillRect(100, 75 * (i + 1), 300, 50);
		}
	}

	public static void main(String[] args) {
		TesteCores obj = new TesteCores();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}