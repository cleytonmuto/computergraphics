package computergraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

/**
 * desenha um envelope
 */
public class Exercicio01 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7622069305261823915L;
	private int MAX_RES_X = 800;
	private int MAX_RES_Y = 600;

	public Exercicio01() {
		super("Exercicio01");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		Dimension d = getSize();
		int centroX = d.width / 2;
		int centroY = d.height / 2;
		int[] x = { centroX - 150, centroX + 150, centroX + 150, centroX, centroX - 150 };
		int[] y = { centroY + 150, centroY + 150, centroY, centroY - 75, centroY };
		Polygon polygon = new Polygon(x, y, 5);
		g.setColor(Color.yellow);
		g.fillPolygon(polygon);
		g.setColor(Color.blue);
		g.drawPolygon(polygon);
		g.drawLine(x[2], y[2], x[4], y[4]);
		g.drawLine(x[1], y[1], x[4], y[4]);
		g.drawLine(x[0], y[0], x[2], y[2]);
	}

	public static void main(String[] args) {
		Exercicio01 obj = new Exercicio01();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
