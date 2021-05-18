package computergraphics;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Rotacao extends JFrame {

	private static final long serialVersionUID = -3569952372102675919L;
	private int centerX, centerY, currentX, currentY;
	private double pixelSize, rWidth = 100.0, rHeight = 100.0;

	public Rotacao() {
		super("Rotacao");
		setSize(800, 600);
		setVisible(true);
	}

	private void initgr() {
		Dimension d = getSize();
		int maxX = d.width - 1, maxY = d.height - 1;
		pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
		centerX = maxX / 2;
		centerY = maxY / 2;
	}

	private int iX(double x) {
		return (int) Math.round(centerX + x / pixelSize);
	}

	private int iY(double y) {
		return (int) Math.round(centerY - y / pixelSize);
	}

	private void moveTo(double x, double y) {
		currentX = iX(x);
		currentY = iY(y);
	}

	private void lineTo(Graphics g, double x, double y) {
		int x1 = iX(x);
		int y1 = iY(y);
		g.drawLine(currentX, currentY, x1, y1);
		currentX = x1;
		currentY = y1;
	}

	private void drawArrow(Graphics g, double[] x, double[] y) {
		moveTo(x[0], y[0]);
		for (int k = 0; k < 4; k++) {
			lineTo(g, x[(k % 3) + 1], y[(k % 3) + 1]);
		}
	}

	public void paint(Graphics g) {
		double r = 40.0;
		double[] x = { r, r, r - 2.0, r + 2.0 };
		double[] y = { -7.0, 7.0, 0.0, 0.0 };
		initgr();
		moveTo(30, 0);
		lineTo(g, 0, 0);
		lineTo(g, 0, 30);
		drawArrow(g, x, y);
		double phi = 2.0 * Math.PI / 3.0;
		double c = Math.cos(phi);
		double s = Math.sin(phi);
		double r11 = c, r12 = s, r21 = -s, r22 = c;
		for (int j = 0; j < 4; j++) {
			double xNew = x[j] * r11 + y[j] * r21;
			double yNew = x[j] * r12 + y[j] * r22;
			x[j] = xNew;
			y[j] = yNew;
		}
		drawArrow(g, x, y);
	}

	public static void main(String[] args) {
		Rotacao obj = new Rotacao();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
