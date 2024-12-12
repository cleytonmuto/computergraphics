package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

/**
 * desenha uma estrela de 5 pontas
 */
public class Exercicio10 extends JFrame {

	private static final long serialVersionUID = -8111962056673432276L;
	private final int MAX_RES_X = 800;
	private final int MAX_RES_Y = 600;
	private final int RAIO = 200;
	private final int VERTICES = 5;

	public Exercicio10() {
		super("Exercicio10");
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
			coordX[i] = centroX + (int) Math.round(RAIO * Math.cos((2 * Math.PI) * (VERTICES / 2) * i / VERTICES));
			coordY[i] = centroY - (int) Math.round(RAIO * Math.sin((2 * Math.PI) * (VERTICES / 2) * i / VERTICES));
		}
		g.setColor(Color.black);
		Polygon polygon = new Polygon(coordX, coordY, VERTICES);
		g.drawPolygon(polygon);
	}

	public static void main(String[] args) {
		Exercicio10 obj = new Exercicio10();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
