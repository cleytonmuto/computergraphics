package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

// desenha uma paleta de cores
public class Exercicio06 extends JFrame {

	private static final long serialVersionUID = -841817419117660922L;

	public Exercicio06() {
		super("Exercicio06");
		setSize(800, 600);
		setVisible(true);
	}

	public void putPixel(Graphics g, int x, int y) {
		g.drawLine(x, y, x, y);
	}

	public void paint(Graphics g) {
		int red = 0, green = 0, blue = 0;
		for (int linha = 100; linha < 465; linha++) {
			for (int coluna = 100; coluna < 465; coluna++) {
				g.setColor(new Color(red, green, blue));
				blue += 5;
				if (blue >= 256) {
					blue = 0;
					green += 5;
					if (green >= 256) {
						green = 0;
						red += 5;
						if (red >= 256) {
							red = 0;
						}
					}
				}

				putPixel(g, linha, coluna);
			}
		}
	}

	public static void main(String[] args) {
		Exercicio06 obj = new Exercicio06();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
