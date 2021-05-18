package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

public class Casinha extends JFrame {

	private static final long serialVersionUID = 6371100125411084181L;
	private int[] verticeX;
	private int[] verticeY;
	private int[] quadradoX;
	private int[] quadradoY;
	private int[] trianguloX;
	private int[] trianguloY;
	private int MAX_RES_X = 1024;
	private int MAX_RES_Y = 768;
	private int LADO = 100;

	private int[] verticeX2;
	private int[] verticeY2;
	private int[] quadradoX2;
	private int[] quadradoY2;
	private int[] trianguloX2;
	private int[] trianguloY2;

	public Casinha() {
		super("Casinha");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
		initVertex();
	}

	private void initVertex() {
		verticeX = new int[5];
		verticeY = new int[5];
		verticeX[0] = 0;
		verticeY[0] = 0;
		verticeX[1] = LADO;
		verticeY[1] = 0;
		verticeX[2] = LADO;
		verticeY[2] = LADO;
		verticeX[3] = 0;
		verticeY[3] = LADO;
		verticeX[4] = LADO / 2;
		verticeY[4] = LADO + LADO / 2;

		verticeX2 = new int[5];
		verticeY2 = new int[5];
		for (int k = 0; k < 5; k++) {
			verticeX2[k] = (int) (verticeX[k] * Math.cos(3 * Math.PI / 4) - verticeY[k] * Math.sin(3 * Math.PI / 4));
			verticeY2[k] = (int) (verticeX[k] * Math.sin(3 * Math.PI / 4) + verticeY[k] * Math.cos(3 * Math.PI / 4));
		}

		for (int k = 0; k < 5; k++) {
			verticeY[k] = -verticeY[k];
			verticeY2[k] = -verticeY2[k];
		}

		for (int k = 0; k < 5; k++) {
			verticeX[k] += (MAX_RES_X / 2 + LADO);
			verticeY[k] += (MAX_RES_Y / 2 - LADO);
			verticeX2[k] += (MAX_RES_X / 2 - LADO);
			verticeY2[k] += (MAX_RES_Y / 2 + LADO);
		}
		quadradoX = new int[4];
		quadradoY = new int[4];
		for (int k = 0; k < 4; k++) {
			quadradoX[k] = verticeX[k];
			quadradoY[k] = verticeY[k];
		}
		trianguloX = new int[3];
		trianguloY = new int[3];
		for (int k = 0; k < 3; k++) {
			trianguloX[k] = verticeX[k + 2];
			trianguloY[k] = verticeY[k + 2];
		}

		quadradoX2 = new int[4];
		quadradoY2 = new int[4];
		for (int k = 0; k < 4; k++) {
			quadradoX2[k] = verticeX2[k];
			quadradoY2[k] = verticeY2[k];
		}
		trianguloX2 = new int[3];
		trianguloY2 = new int[3];
		for (int k = 0; k < 3; k++) {
			trianguloX2[k] = verticeX2[k + 2];
			trianguloY2[k] = verticeY2[k + 2];
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		g.setColor(Color.black);
		g.drawLine(MAX_RES_X / 2, 0, MAX_RES_X / 2, MAX_RES_Y);
		g.drawLine(0, MAX_RES_Y / 2, MAX_RES_X, MAX_RES_Y / 2);

		g.setColor(Color.blue);
		Polygon polygon1 = new Polygon(quadradoX, quadradoY, 4);
		g.drawPolygon(polygon1);
		Polygon polygon2 = new Polygon(trianguloX, trianguloY, 3);
		g.drawPolygon(polygon2);

		g.setColor(Color.red);
		Polygon polygon3 = new Polygon(quadradoX2, quadradoY2, 4);
		g.drawPolygon(polygon3);
		Polygon polygon4 = new Polygon(trianguloX2, trianguloY2, 3);
		g.drawPolygon(polygon4);

	}

	public static void main(String[] args) {
		Casinha obj = new Casinha();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}