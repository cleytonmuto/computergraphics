package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

// desenha um tabuleiro de xadrez
public class Exercicio08 extends JFrame {

	private static final long serialVersionUID = 8832655905760662172L;
	private int MAX_RES_X = 800;
	private int MAX_RES_Y = 600;
	
	public Exercicio08() {
		super("Exercicio08");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		g.setColor(Color.black);
		for (int i = 0; i < 9; i++) {
			g.drawLine(100, 100 + 50 * i, 500, 100 + 50 * i);
			g.drawLine(100 + 50 * i, 100, 100 + 50 * i, 500);
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 1) {
					g.fillRect(100 + 50 * i, 100 + 50 * j, 50, 50);
				}
			}
		}
	}

	public static void main(String[] args) {
		Exercicio08 obj = new Exercicio08();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}