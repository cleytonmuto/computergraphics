package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Cardioide extends JFrame {

	private static final long serialVersionUID = 2085209082880970564L;
	private final int MAX_RES_X = 800;
	private final int MAX_RES_Y = 600;

	public Cardioide() {
		super("Cardioide");
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		int centroX = MAX_RES_X / 2;
		int centroY = MAX_RES_Y / 2;
		int raio = MAX_RES_Y / 3;
		int[] direitoX = new int[20];
		int[] direitoY = new int[20];
		int[] esquerdoX = new int[20];
		int[] esquerdoY = new int[20];
		for (int angulo = 0; angulo <= 18; angulo++) {
			int x = (int) (raio * Math.cos(10 * angulo * Math.PI / 180 - Math.PI / 2));
			int y = (int) (raio * Math.sin(10 * angulo * Math.PI / 180 - Math.PI / 2));
			direitoX[angulo] = centroX + x;
			direitoY[angulo] = centroY - y;
			// g.drawLine( centroX, centroY, centroX + x, centroY - y );
		}
		for (int angulo = 0; angulo >= -18; angulo--) {
			int x = (int) (raio * Math.cos(10 * angulo * Math.PI / 180 - Math.PI / 2));
			int y = (int) (raio * Math.sin(10 * angulo * Math.PI / 180 - Math.PI / 2));
			esquerdoX[-angulo] = centroX + x;
			esquerdoY[-angulo] = centroY - y;
			// g.drawLine( centroX, centroY, centroX + x, centroY - y );
		}
		g.setColor( Color.gray );
		for (int i = 0; i < 18; i++) {
			g.drawLine(direitoX[i], direitoY[i], direitoX[i + 1], direitoY[i + 1]);
			g.drawLine(esquerdoX[i], esquerdoY[i], esquerdoX[i + 1], esquerdoY[i + 1]);
		}
		// g.setColor( Color.black );
		for (int i = 0; i < 10; i++) {
			g.drawLine(direitoX[i], direitoY[i], direitoX[i + 9], direitoY[i + 9]);
			g.drawLine(esquerdoX[i], esquerdoY[i], esquerdoX[i + 9], esquerdoY[i + 9]);
		}
		for (int i = 1; i < 8; i++) {
			g.drawLine(direitoX[2 * i], direitoY[2 * i], esquerdoX[18 - i], esquerdoY[18 - i]);
			g.drawLine(esquerdoX[2 * i], esquerdoY[2 * i], direitoX[18 - i], direitoY[18 - i]);
		}
	}

	public static void main(String args[]) {
		Cardioide cardioide = new Cardioide();
		cardioide.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}