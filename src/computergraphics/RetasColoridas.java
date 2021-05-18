package computergraphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class RetasColoridas extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7778648783018811892L;
	private int MAX_RES_X = 1024;
	private int MAX_RES_Y = 768;
	private int BASE = MAX_RES_X / 2;
	private int ALTURA = MAX_RES_Y / 2;
	private int QUANTIDADE = 200;
	private int[] retaX1 = new int[QUANTIDADE];
	private int[] retaY1 = new int[QUANTIDADE];
	private int[] retaX2 = new int[QUANTIDADE];
	private int[] retaY2 = new int[QUANTIDADE];
	private Color[] cor = new Color[QUANTIDADE];

	private int[] retaX3 = new int[QUANTIDADE];
	private int[] retaY3 = new int[QUANTIDADE];
	private int[] retaX4 = new int[QUANTIDADE];
	private int[] retaY4 = new int[QUANTIDADE];

	private int[] retaX5 = new int[QUANTIDADE];
	private int[] retaY5 = new int[QUANTIDADE];
	private int[] retaX6 = new int[QUANTIDADE];
	private int[] retaY6 = new int[QUANTIDADE];

	private int[] retaX7 = new int[QUANTIDADE];
	private int[] retaY7 = new int[QUANTIDADE];
	private int[] retaX8 = new int[QUANTIDADE];
	private int[] retaY8 = new int[QUANTIDADE];

	public RetasColoridas() {
		super("RetasColoridas");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	private void inicializaRetas() {
		int deltaX = BASE / 100;
		int deltaY = ALTURA / 100;
		for (int k = 0; k < QUANTIDADE; k++) {
			retaX1[k] = (int) (BASE * Math.random());
			retaY1[k] = (int) (ALTURA * Math.random());
			retaX2[k] = (int) (BASE * Math.random());
			retaY2[k] = (int) (ALTURA * Math.random());
			int red = (int) (256 * Math.random());
			int green = (int) (256 * Math.random());
			int blue = (int) (256 * Math.random());
			cor[k] = new Color(red, green, blue);
			retaY1[k] = -retaY1[k];
			retaY2[k] = -retaY2[k];

			retaX3[k] = -retaX1[k];
			retaY3[k] = retaY1[k];
			retaX4[k] = -retaX2[k];
			retaY4[k] = retaY2[k];

			retaX5[k] = -retaX1[k];
			retaY5[k] = -retaY1[k];
			retaX6[k] = -retaX2[k];
			retaY6[k] = -retaY2[k];

			retaX7[k] = retaX1[k];
			retaY7[k] = -retaY1[k];
			retaX8[k] = retaX2[k];
			retaY8[k] = -retaY2[k];

			retaX1[k] += MAX_RES_X / 2 + deltaX;
			retaY1[k] += MAX_RES_Y / 2 - deltaY;
			retaX2[k] += MAX_RES_X / 2 + deltaX;
			retaY2[k] += MAX_RES_Y / 2 - deltaY;

			retaX3[k] += MAX_RES_X / 2 - deltaX;
			retaY3[k] += MAX_RES_Y / 2 - deltaY;
			retaX4[k] += MAX_RES_X / 2 - deltaX;
			retaY4[k] += MAX_RES_Y / 2 - deltaY;

			retaX5[k] += MAX_RES_X / 2 - deltaX;
			retaY5[k] += MAX_RES_Y / 2 + deltaY;
			retaX6[k] += MAX_RES_X / 2 - deltaX;
			retaY6[k] += MAX_RES_Y / 2 + deltaY;

			retaX7[k] += MAX_RES_X / 2 + deltaX;
			retaY7[k] += MAX_RES_Y / 2 + deltaY;
			retaX8[k] += MAX_RES_X / 2 + deltaX;
			retaY8[k] += MAX_RES_Y / 2 + deltaY;

		}
	}

	public void paint(Graphics g) {
		Dimension d = getSize();
		MAX_RES_X = d.width;
		MAX_RES_Y = d.height;
		BASE = MAX_RES_X / 2;
		ALTURA = MAX_RES_Y / 2;
		inicializaRetas();
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		g.setColor(Color.black);
		g.drawLine(MAX_RES_X / 2, 0, MAX_RES_X / 2, MAX_RES_Y);
		g.drawLine(0, MAX_RES_Y / 2, MAX_RES_X, MAX_RES_Y / 2);
		for (int k = 0; k < QUANTIDADE; k++) {
			g.setColor(cor[k]);
			g.drawLine(retaX1[k], retaY1[k], retaX2[k], retaY2[k]);
			g.drawLine(retaX3[k], retaY3[k], retaX4[k], retaY4[k]);
			g.drawLine(retaX5[k], retaY5[k], retaX6[k], retaY6[k]);
			g.drawLine(retaX7[k], retaY7[k], retaX8[k], retaY8[k]);
		}

	}

	public static void main(String[] args) {
		RetasColoridas obj = new RetasColoridas();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}