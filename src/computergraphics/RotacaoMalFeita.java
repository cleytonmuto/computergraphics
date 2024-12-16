package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;

public class RotacaoMalFeita extends JFrame {

	private static final long serialVersionUID = 8215035153963619610L;
	private final int MAX_RES_X = 800;
	private final int MAX_RES_Y = 600;
	private final int BASE = MAX_RES_X / 4;
	private final int ALTURA = MAX_RES_Y / 8;

	public RotacaoMalFeita() {
		super("RotacaoMalFeita");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		int centroX = MAX_RES_X / 2;
		int centroY = MAX_RES_Y / 2;
		int[] coordX = { 3 * centroX / 2 - BASE / 2, 3 * centroX / 2 + BASE / 2, 3 * centroX / 2 + BASE / 2,
				3 * centroX / 2 - BASE / 2 };
		int[] coordY = { centroY / 2 - ALTURA / 2, centroY / 2 - ALTURA / 2, centroY / 2 + ALTURA / 2,
				centroY / 2 + ALTURA / 2 };
		Polygon polygon = new Polygon(coordX, coordY, 4);
		g.setColor(Color.black);
		g.drawPolygon(polygon);
		int[] coordX_ = new int[4];
		int[] coordY_ = new int[4];
		double angle = Math.PI / 3;
		for (int i = 0; i < 4; i++) {
			coordX_[i] = (int) Math.round(coordX[i] * Math.cos(angle) - coordY[i] * Math.sin(angle)) + 100;
			coordY_[i] = (int) Math.round(coordX[i] * Math.sin(angle) + coordY[i] * Math.cos(angle)) - 200;
		}
		Polygon polygon_ = new Polygon(coordX_, coordY_, 4);
		g.setColor(Color.black);
		g.drawPolygon(polygon_);
	}
	
	public static void main(String[] args) {
		RotacaoMalFeita obj = new RotacaoMalFeita();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
