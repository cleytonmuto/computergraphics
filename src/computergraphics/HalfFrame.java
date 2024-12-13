package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class HalfFrame extends JFrame {

	private static final long serialVersionUID = 5789981426446811822L;
	private int MAX_RES_X = 900, MAX_RES_Y = 600;

	public HalfFrame() {
		super("Half Frame");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLUE);
		int padding = 30, divisions = 20, stepX = MAX_RES_X / divisions, stepY = MAX_RES_Y / divisions;
		for (int i = 0; i < divisions; i++) {
			int origemX = 0;
			int origemY = 0;
			int destinoX = stepX * i;
			int destinoY = MAX_RES_Y - 60;
			g.drawLine(origemX + padding, origemY + padding, destinoX + padding, destinoY + padding);
		}
		for (int i = 0; i < divisions; i++) {
			int origemX = (divisions - 1) * stepX;
			int origemY = 0;
			int destinoX = stepX * i;
			int destinoY = MAX_RES_Y - 60;
			g.drawLine(origemX + padding, origemY + padding, destinoX + padding, destinoY + padding);
		}
		for (int i = 0; i < divisions; i++) {
			int origemX = 0;
			int origemY = MAX_RES_Y - 60;
			int destinoX = stepX * i;
			int destinoY = 0;
			g.drawLine(origemX + padding, origemY + padding, destinoX + padding, destinoY + padding);
		}
		for (int i = 0; i < divisions; i++) {
			int origemX = (divisions - 1) * stepX;
			int origemY = MAX_RES_Y - 60;
			int destinoX = stepX * i;
			int destinoY = 0;
			g.drawLine(origemX + padding, origemY + padding, destinoX + padding, destinoY + padding);
		}
		
		for (int i = 0; i < divisions; i++) {
			int origemX = 0;
			int origemY = 0;
			int destinoX = (divisions - 1) * stepX;
			int destinoY = stepY * i;
			g.drawLine(origemX + padding, origemY + padding, destinoX + padding, destinoY + padding);
		}
		for (int i = 0; i < divisions; i++) {
			int origemX = (divisions - 1) * stepX;
			int origemY = 0;
			int destinoX = 0;
			int destinoY = stepY * i;
			g.drawLine(origemX + padding, origemY + padding, destinoX + padding, destinoY + padding);
		}
		for (int i = 0; i < divisions; i++) {
			int origemX = 0;
			int origemY = MAX_RES_Y - 60;
			int destinoX = (divisions - 1) * stepX;
			int destinoY = stepY * i;
			g.drawLine(origemX + padding, origemY + padding, destinoX + padding, destinoY + padding);
		}
		for (int i = 0; i < divisions; i++) {
			int origemX = (divisions - 1) * stepX;
			int origemY = MAX_RES_Y - 60;
			int destinoX = 0;
			int destinoY = stepY * i;;
			g.drawLine(origemX + padding, origemY + padding, destinoX + padding, destinoY + padding);
		}
	}

	public static void main(String args[]) {
		HalfFrame instance = new HalfFrame();
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}