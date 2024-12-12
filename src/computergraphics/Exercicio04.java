package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

// desenha um triangulo equilatero giratorio
public class Exercicio04 extends JFrame {

	private static final long serialVersionUID = 3719358388983050457L;
	final int MAX_X = 1024;
	final int MAX_Y = 768;
	double step = 0;

	public Exercicio04() {
		super("Exercicio04");
		setSize(MAX_X, MAX_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		for (int i = 0; i < MAX_Y - 1; i++) {
			g.drawLine(0, i, MAX_X - 1, i);
		}
		int centroX = MAX_X / 2, centroY = MAX_Y / 2;
		double RAIO = 300.0, raio = RAIO;
		g.setColor(Color.black);
		// g.setColor( new Color( ( int ) ( 256 * Math.random( ) ), ( int ) ( 256 *
		// Math.random( ) ), ( int ) ( 256 * Math.random( ) ) ) );
		g.drawOval(MAX_X / 2 - (int) raio, MAX_Y / 2 - (int) raio, 2 * (int) raio, 2 * (int) raio);
		int N = 3;
		double fase = 2.0 * Math.PI / (double) N;
		int[] x = new int[N];
		int[] y = new int[N];
		double delta = 1;
		double decremento = 10.0 / delta;
		double divisor = 90.0 * delta;
		for (int i = 0; i < (int) (RAIO / decremento); i++) {
			for (int j = 0; j < N; j++) {
				x[j] = centroX + (int) (raio * Math.cos(i * Math.PI / divisor + j * fase + step));
				y[j] = centroY - (int) (raio * Math.sin(i * Math.PI / divisor + j * fase + step));
			}
			for (int j = 0; j < N; j++) {
				g.drawLine(x[j], y[j], x[(j + 1) % N], y[(j + 1) % N]);
			}
			raio -= decremento;
		}
		step = (step + 1) % 360;
		// repaint( );
	}

	public static void main(String[] args) {
		Exercicio04 obj = new Exercicio04();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
