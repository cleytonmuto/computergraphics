package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Exemplo02 extends JFrame {

	private static final long serialVersionUID = -6300516498908541412L;

	public Exemplo02() {
		super("Meu exemplo 02");
		setSize(1024, 768);
		setVisible(true);
		setResizable(false);
	}

	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.drawOval(100, 100, 500, 300);
		g.setColor(Color.green);
		g.fillOval(700, 500, 200, 200);
	}

	public static void main(String[] args) {
		Exemplo02 obj = new Exemplo02();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}