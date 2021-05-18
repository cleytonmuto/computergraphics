package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

// desenha triangulos dentro de triangulos
public class Exercicio03 extends JFrame {

	private static final long serialVersionUID = -5322021457621365077L;

	public Exercicio03() {
		super("Exercicio03");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		for (int i = 0; i < 600; i++) {
			g.drawLine(0, i, 799, i);
		}
		int xa = 750, xb = 600, xc = 50;
		int ya = 50, yb = 550, yc = 200;
		g.setColor(Color.black);
		for (int i = 0; i < 7; i++) {
			g.drawLine(xa, ya, xb, yb);
			g.drawLine(xb, yb, xc, yc);
			g.drawLine(xc, yc, xa, ya);
			int tempXa = xa, tempXb = xb, tempXc = xc;
			int tempYa = ya, tempYb = yb, tempYc = yc;
			xa = (tempXa + tempXb) / 2;
			ya = (tempYa + tempYb) / 2;
			xb = (tempXb + tempXc) / 2;
			yb = (tempYb + tempYc) / 2;
			xc = (tempXc + tempXa) / 2;
			yc = (tempYc + tempYa) / 2;
		}
	}

	public static void main(String[] args) {
		Exercicio03 obj = new Exercicio03();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
