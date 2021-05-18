package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Exemplo01 extends JFrame {

	private static final long serialVersionUID = 1082311450402464013L;

	public Exemplo01() {
		super("Meu exemplo 01");
		setSize(1024, 768);
		setVisible(true);
		setResizable(false);
	}

	public void paint(Graphics g) {
		g.setColor(Color.blue);
		g.drawLine(100, 100, 500, 300);
	}

	public static void main(String[] args) {
		Exemplo01 obj = new Exemplo01();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}