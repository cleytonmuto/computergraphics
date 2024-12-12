package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

// senoide
public class Exercicio09 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1511483063905984038L;
	private int MAX_RES_X = 800;
	private int MAX_RES_Y = 600;
	
	public Exercicio09() {
		super("Exercicio09");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		g.setColor(Color.black);
		g.drawLine(MAX_RES_X / 6, MAX_RES_Y / 2, 5 * MAX_RES_X / 6, MAX_RES_Y / 2);
		g.drawLine(MAX_RES_X / 2, MAX_RES_Y / 4, MAX_RES_X / 2, 3 * MAX_RES_Y / 4);
		g.translate(MAX_RES_X / 2, MAX_RES_Y / 2);
		int N = 400, A = 100;
		int[] array = new int[N];
		for (int x = 0; x < N; x++) {
			array[x] = (int) Math.round(A * Math.sin(2 * Math.PI * x / (N / 2)));
			array[x] = -array[x];
		}
		for (int x = 0; x < N - 1; x++) {
			g.drawLine(x - N / 2, array[x], x + 1 - N / 2, array[x + 1]);
		}
	}

	public static void main(String[] args) {
		Exercicio09 obj = new Exercicio09();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}