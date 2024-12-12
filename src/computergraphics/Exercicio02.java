package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

/**
 * desenha um octogono
 */
public class Exercicio02 extends JFrame {

	private static final long serialVersionUID = 1569020204769506384L;
	private final int MAX_RES_X = 800;
	private final int MAX_RES_Y = 600;
	private final int RAIO = 200;
	private final int VERTICES = 8;

	public Exercicio02() {
		super("Exercicio02");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		int[] coordX = new int[VERTICES];
		int[] coordY = new int[VERTICES];
		int centroX = MAX_RES_X / 2, centroY = MAX_RES_Y / 2;
		for (int i = 0; i < VERTICES; i++) {
			coordX[i] = centroX + (int) Math.round(RAIO * Math.cos((2 * Math.PI) * i / VERTICES));
			coordY[i] = centroY - (int) Math.round(RAIO * Math.sin((2 * Math.PI) * i / VERTICES));
		}
		g.setColor(Color.black);
		Polygon polygon = new Polygon(coordX, coordY, VERTICES);
		g.drawPolygon(polygon);
	}

	public static void main(String[] args) {
		Exercicio02 obj = new Exercicio02();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
