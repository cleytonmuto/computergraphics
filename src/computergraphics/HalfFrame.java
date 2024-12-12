package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class HalfFrame extends JFrame {

	private static final long serialVersionUID = 5789981426446811822L;
	private int MAX_RES_X = 900, MAX_RES_Y = 700;

	public HalfFrame() {
		super("Half Frame");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLUE);
		int dx = 10, dy = 30;
		for (int i = 0; i <= 15; i++) {
			int origemX = 800;
			int origemY = 0;
			int destinoX = 50 * i;
			int destinoY = destinoX * 3 / 4;
			g.drawLine(origemX + dx, origemY + dy, destinoX + dx, destinoY + dy);
		}
		for (int i = 0; i <= 15; i++) {
			int origemX = 0;
			int origemY = 600;
			int destinoX = 50 * i;
			int destinoY = destinoX * 3 / 4;
			g.drawLine(origemX + dx, origemY + dy, destinoX + dx, destinoY + dy);
		}
		for (int i = 0; i <= 15; i++) {
			int origemX = 0;
			int origemY = 0;
			int destinoX = 50 * i;
			int destinoY = 600 - destinoX * 3 / 4;
			g.drawLine(origemX + dx, origemY + dy, destinoX + dx, destinoY + dy);
		}
		for (int i = 0; i <= 15; i++) {
			int origemX = 800;
			int origemY = 600;
			int destinoX = 50 * i;
			int destinoY = 600 - destinoX * 3 / 4;
			g.drawLine(origemX + dx, origemY + dy, destinoX + dx, destinoY + dy);
		}
	}

	public static void main(String args[]) {
		HalfFrame instance = new HalfFrame();
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}