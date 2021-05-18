package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Exemplo03 extends JFrame {

	private static final long serialVersionUID = -1486587365694169189L;
	private final int MAX_RES_X = 800;
	private final int MAX_RES_Y = 600;
	private final int RAIO = 200;
	private final int VERTICES = 6;

	public Exemplo03() {
		super("Exemplo03");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		int[] coordX = new int[VERTICES + 1 ];
		int[] coordY = new int[VERTICES + 1 ];
		int centroX = MAX_RES_X / 2, centroY = MAX_RES_Y / 2;
		for (int i = 0; i <= VERTICES; i++) {
			coordX[ i ] = centroX + (int) Math.round(RAIO * Math.cos((2 * Math.PI) * i / VERTICES));
			coordY[ i ] = centroY - (int) Math.round(RAIO * Math.sin((2 * Math.PI) * i / VERTICES));
		}
		g.setColor(Color.blue);
		g.drawPolyline(coordX, coordY, VERTICES + 1);
	}

	public static void main(String[] args) {
		Exemplo03 obj = new Exemplo03();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
