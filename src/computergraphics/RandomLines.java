package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class RandomLines extends JFrame implements MouseListener {

	private static final long serialVersionUID = -365806127595884985L;
	private int MAX_RES_X = 1024, MAX_RES_Y = 768;

	public RandomLines() {
		super("Random Lines");
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
		addMouseListener(this);
	}

	public void paint(Graphics g) {
		for (int i = 0; i < 100000; i++) {
			int vermelho = (int) (256 * Math.random());
			int verde = (int) (256 * Math.random());
			int azul = (int) (256 * Math.random());
			g.setColor(new Color(vermelho, verde, azul));
			int x1 = (int) (MAX_RES_X * Math.random());
			int y1 = (int) (MAX_RES_Y * Math.random());
			int x2 = (int) (MAX_RES_X * Math.random());
			int y2 = (int) (MAX_RES_Y * Math.random());
			g.drawLine(x1, y1, x2, y2);
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		super.repaint();
	}

	public static void main(String args[]) {
		RandomLines instance = new RandomLines();
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}