package computergraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

// caleidoscopio
public class Exercicio07 extends JFrame {

	private static final long serialVersionUID = 8882625025088203043L;
	private final int MAX_X = 1024;
	private final int MAX_Y = 768;

	public Exercicio07() {
		super("Exercicio07");
		setSize(MAX_X, MAX_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		Dimension d = getSize();
		int maxX = d.width;
		int maxY = d.height;
		g.setColor(Color.black);
		g.drawLine(0, maxY / 2, maxX, maxY / 2);
		g.drawLine(maxX / 2, 0, maxX / 2, maxY);
		int vertices = 3;
		int quadrantes = 4;
		int[][] x = new int[quadrantes][vertices];
		int[][] y = new int[quadrantes][vertices];
		Polygon[] polygon = new Polygon[quadrantes];
		// quadrante original 0
		for (int j = 0; j < vertices; j++) {
			x[0][j] = (int) ((maxX / 2) * Math.random());
			y[0][j] = (int) ((maxY / 2) * Math.random());

		}
		// quadrante sim�trico horizontal 1
		for (int j = 0; j < vertices; j++) {
			x[1][j] = maxX - x[0][j];
			y[1][j] = y[0][j];
		}
		// quadrante sim�trico vertical 2
		for (int j = 0; j < vertices; j++) {
			x[2][j] = x[0][j];
			y[2][j] = maxY - y[0][j];
		}
		// quadrante sim�trico diagonal 3
		for (int j = 0; j < vertices; j++) {
			x[3][j] = maxX - x[0][j];
			y[3][j] = maxY - y[0][j];
		}

		// desenha os poligonos
		for (int k = 0; k < quadrantes; k++) {
			polygon[k] = new Polygon(x[k], y[k], vertices);
			g.drawPolygon(polygon[k]);
		}
	}

	public static void main(String[] args) {
		Exercicio07 obj = new Exercicio07();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
