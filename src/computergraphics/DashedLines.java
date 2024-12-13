package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class DashedLines extends JFrame {

	private static final long serialVersionUID = -6549905258295550911L;

	public DashedLines() {
		super("DashedLines");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		drawDottedLine(g, 0, 0, 799, 599, Color.black, 5, 5);
	}

	public static void main(String[] args) {
		DashedLines obj = new DashedLines();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void drawDottedLine(Graphics g, int x0, int y0, int x1, int y1, Color color, int dashLen,
			int spaceLen) {
		Color c = g.getColor();
		g.setColor(color);
		int dx = x1 - x0;
		int dy = y1 - y0;
		float t = 0.5f;

		g.setColor(color);
		g.drawLine(x0, y0, x0, y0);

		int dashCount = 0;
		int spaceCount = 0;
		boolean doPlot = dashLen > 1;

		if (Math.abs(dx) > Math.abs(dy)) { // slope < 1
			float m = (float) dy / (float) dx; // compute slope
			t += y0;
			dx = (dx < 0) ? -1 : 1;
			m *= dx;
			while (x0 != x1) {
				x0 += dx; // step to next x value
				t += m;
				if (doPlot) {
					g.drawLine(x0, (int) t, x0, (int) t);
					dashCount++;
					if (dashCount >= dashLen) {
						dashCount = 0;
						spaceCount = 0;
						doPlot = false;
					}
				} else {
					spaceCount++;
					if (spaceCount >= spaceLen) {
						spaceCount = 0;
						dashCount = 0;
						doPlot = true;
					}
				}

			}
		} else if (dy != 0) { // slope >= 1
			float m = (float) dx / (float) dy; // compute slope
			t += x0;
			dy = (dy < 0) ? -1 : 1;
			m *= dy;
			while (y0 != y1) {
				y0 += dy; // step to next y value
				t += m;
				if (doPlot) {
					g.drawLine((int) t, y0, (int) t, y0);
					dashCount++;
					if (dashCount >= dashLen) {
						dashCount = 0;
						spaceCount = 0;
						doPlot = false;
					}
				} else {
					spaceCount++;
					if (spaceCount >= spaceLen) {
						spaceCount = 0;
						dashCount = 0;
						doPlot = true;
					}
				}
			}
		}
		g.setColor(c);
	}

}