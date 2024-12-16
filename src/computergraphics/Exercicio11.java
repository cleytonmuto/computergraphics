package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

// paralelepipedo
public class ExercicioN extends JFrame {

	private static final long serialVersionUID = -8106386933546597194L;

	public ExercicioN() {
		super("ExercicioN");
		setSize(800, 600);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);

		int xA[] = { 100, 175, 400, 325 };
		int yA[] = { 300, 350, 300, 250 };

		Polygon polygonA = new Polygon(xA, yA, 4);
		// g.setColor( new Color( 240, 240, 240 ) );
		// g.fillPolygon( polygonA );
		g.setColor(Color.black);
		g.drawPolygon(polygonA);

		int xB[] = { 100, 175, 400, 325 };
		int yB[] = { 200, 250, 200, 150 };
		Polygon polygonB = new Polygon(xB, yB, 4);
		g.drawPolygon(polygonB);

		for (int i = 0; i < 4; i++) {
			g.drawLine(xA[i], yA[i], xB[i], yB[i]);
		}
	}

	public static void main(String[] args) {
		ExercicioN obj = new ExercicioN();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}