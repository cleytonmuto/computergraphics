package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

public class QuatroCores extends JFrame {

	private static final long serialVersionUID = -9183342770110480516L;
	private final int MAX_RES_X = 800;
	private final int MAX_RES_Y = 600;

	public QuatroCores() {
		super("QuatroCores");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		int centroX = MAX_RES_X / 2;
		int centroY = MAX_RES_Y / 2;
		int vertices = 9, raio = 200;
		double angulo = 2.0 * Math.PI / (double) vertices;
		int[] coordX = new int[vertices];
		int[] coordY = new int[vertices];
		for (int i = 0; i < vertices; i++) {
			coordX[i] = centroX + (int) Math.round(raio * Math.cos(i * angulo));
			coordY[i] = centroY - (int) Math.round(raio * Math.sin(i * angulo));
		}
		Polygon polygon = new Polygon(coordX, coordY, vertices);
		g.setColor(Color.black);
		g.drawPolygon(polygon);
		for (int i = 0; i < vertices; i++) {
			g.drawLine(coordX[i], coordY[i], centroX, centroY);
		}
		g.setColor(Color.white);
		g.fillOval(centroX - raio / 2, centroY - raio / 2, raio, raio);
		g.setColor(Color.black);
		g.drawOval(centroX - raio / 2, centroY - raio / 2, raio, raio);
	}

	public static void main(String[] args) {
		QuatroCores obj = new QuatroCores();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
