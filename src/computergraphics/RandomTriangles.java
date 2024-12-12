package computergraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

public class RandomTriangles extends JFrame {

	private static final long serialVersionUID = 1675927856437169184L;
	private int MAX_RES_X = 1024, MAX_RES_Y = 600;

	public RandomTriangles() {
		super("RandomTriangles");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		Dimension d = getSize();
		int vertices = 3;
		int[] x = new int[vertices];
		int[] y = new int[vertices];
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < vertices; j++) {
				x[j] = (int) (d.width * Math.random());
				y[j] = (int) (d.height * Math.random());
			}
			int red = (int) (256 * Math.random());
			int green = (int) (256 * Math.random());
			int blue = (int) (256 * Math.random());
			g.setColor(new Color(red, green, blue));
			Polygon polygon = new Polygon(x, y, vertices);
			g.fillPolygon(polygon);
		}
	}

	public static void main(String[] args) {
		RandomTriangles obj = new RandomTriangles();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}