package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class IlusaoDeOptica extends JFrame {

	private static final long serialVersionUID = -1794645210499197187L;
	private int WIDTH = 1200;
	private int HEIGHT = 900;

	public IlusaoDeOptica() {
		super("Ilusao de Optica");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.darkGray);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 16; j++) {
				g.drawLine(100 * i + j, 0, 100 * i + j, HEIGHT);
			}
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 16; j++) {
				g.drawLine(0, 100 * i + j, WIDTH, 100 * i + j);
			}
		}
		g.setColor(Color.white);
		for (int i = 1; i < 12; i++) {
			for (int j = 1; j < 10; j++) {
				g.fillOval(100 * i - 3, 100 * j - 3, 21, 21);
			}
		}
	}

	public static void main(String[] args) {
		IlusaoDeOptica obj = new IlusaoDeOptica();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}