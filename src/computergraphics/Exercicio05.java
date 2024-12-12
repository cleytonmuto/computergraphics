package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

// desenha uma circunferencia circunscrita
public class Exercicio05 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5561547091160184592L;

	public Exercicio05() {
		super("Exercicio05");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);
		int centroX = (int) Math.round(4250.0 / 11.0);
		int centroY = (int) Math.round(3250.0 / 11.0);
		int raio = (int) Math.round(50.0 * Math.sqrt(2210.0) / 11.0);
		g.setColor(Color.black);
		g.drawLine(200, 400, 600, 300);
		g.drawLine(600, 300, 300, 100);
		g.drawLine(300, 100, 200, 400);
		g.drawOval(centroX - raio, centroY - raio, 2 * raio, 2 * raio);
	}

	public static void main(String[] args) {
		Exercicio05 obj = new Exercicio05();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
