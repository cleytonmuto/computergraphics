package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Espiral extends JFrame {

	private static final long serialVersionUID = -1914252707864342512L;

	public Espiral() {
		super("Espiral");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {
		int actualX = 200, actualY = 450, delta = 400;
		g.setColor(Color.red);
		for (int d = 0; d < 40; d++) {
			switch (d % 4) {
			case 0: {
				g.drawLine(actualX, actualY, actualX + delta, actualY);
				actualX += delta;
			}
				;
				break;
			case 1: {
				g.drawLine(actualX, actualY, actualX, actualY - delta);
				actualY -= delta;
			}
				;
				break;
			case 2: {
				g.drawLine(actualX, actualY, actualX - delta, actualY);
				actualX -= delta;
			}
				;
				break;
			case 3: {
				g.drawLine(actualX, actualY, actualX, actualY + delta);
				actualY += delta;
			}
				;
				break;
			default:
			}
			delta -= (int) (0.1 * delta);
		}
	}

	public static void main(String[] args) {
		Espiral obj = new Espiral();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
