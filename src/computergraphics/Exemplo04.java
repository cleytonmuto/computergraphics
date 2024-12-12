package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

public class Exemplo04 extends JFrame {

	private static final long serialVersionUID = 282477839159472269L;

	public Exemplo04() {
		super("Exemplo04");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {

		int centerX = 400;
		int centerY = 400;

		// eixos
		g.setColor(Color.black);
		g.drawLine(0, centerY, 800, centerY);
		g.drawLine(centerX, 0, centerX, 600);

		// ret�ngulo
		int[] x = { centerX, centerX + 200, centerX + 200, centerX };
		int[] y = { centerY, centerY, centerY - 100, centerY - 100 };
		g.setColor(Color.blue);
		Polygon polygon = new Polygon(x, y, 4);
		g.drawPolygon(polygon);

		// retangulo rotacionado
		double theta = -Math.PI / 3.0;
		int[] xLinha = new int[4];
		int[] yLinha = new int[4];
		g.translate(400, 400);
		for (int k = 0; k < 4; k++) {
			xLinha[k] = (int) Math.round((x[k] - centerX) * Math.cos(theta) - (y[k] - centerY) * Math.sin(theta));
			yLinha[k] = (int) Math.round((x[k] - centerX) * Math.sin(theta) + (y[k] - centerY) * Math.cos(theta));
			// yLinha[ k ] = - yLinha[ k ];
		}
		g.setColor(Color.red);
		Polygon polygon2 = new Polygon(xLinha, yLinha, 4);
		g.drawPolygon(polygon2);
	}

	public static void main(String[] args) {
		Exemplo04 obj = new Exemplo04();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
