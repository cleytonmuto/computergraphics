package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class ArcoIris extends JFrame {

	private static final long serialVersionUID = 2857342998347063589L;
	private int[] arrayCores = { 0xFF0000, // vermelho
			0xFFCC00, // laranja
			0xFFFF00, // amarelo
			0x00FF00, // verde
			0x0000FF, // azul
			0x000066, // indigo
			0x9966CC, // violeta
			0xFFFFFF };

	public ArcoIris() {
		super("ArcoIris");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);
		for (int k = 0; k < 8; k++) {
			g.setColor(new Color(arrayCores[k]));
			g.fillOval(50 * k + 50, 50 * k + 50, 1600 - (50 * k + 50), 1200 - (50 * k + 50));
		}

	}

	public static void main(String[] args) {
		ArcoIris obj = new ArcoIris();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}