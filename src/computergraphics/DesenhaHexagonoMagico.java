package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class DesenhaHexagonoMagico extends JFrame {

	private static final long serialVersionUID = 2829465117046431633L;
	private int MAX_RES_X = 800, MAX_RES_Y = 600;
	private int[] nodoX = new int[19];
	private int[] nodoY = new int[19];
	private int distancia = MAX_RES_X / 10;
	private int diametro = MAX_RES_X / 25;

	public DesenhaHexagonoMagico() {
		super("Hex�gono M�gico");
		getContentPane().setBackground(Color.white);
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
	}

	private void inicializaNodos() {

		nodoX[0] = MAX_RES_X / 2;
		nodoY[0] = MAX_RES_Y / 2;

		nodoX[1] = nodoX[0] + distancia;
		nodoY[1] = nodoY[0];
		nodoX[2] = nodoX[1] + distancia;
		nodoY[2] = nodoY[1];
		nodoX[3] = nodoX[0] - distancia;
		nodoY[3] = nodoY[0];
		nodoX[4] = nodoX[3] - distancia;
		nodoY[4] = nodoY[3];

		nodoX[5] = (nodoX[0] + nodoX[1]) / 2;
		nodoY[5] = nodoY[0] - (int) (distancia * Math.sqrt(3) / 2.0);
		// altura do tri�ngulo equil�tero
		nodoX[6] = nodoX[5] + distancia;
		nodoY[6] = nodoY[5];
		nodoX[7] = nodoX[5] - distancia;
		nodoY[7] = nodoY[5];
		nodoX[8] = nodoX[7] - distancia;
		nodoY[8] = nodoY[5];

		nodoX[9] = (nodoX[0] + nodoX[1]) / 2;
		nodoY[9] = nodoY[0] + (int) (distancia * Math.sqrt(3) / 2.0);
		// altura do tri�ngulo equil�tero
		nodoX[10] = nodoX[9] + distancia;
		nodoY[10] = nodoY[9];
		nodoX[11] = nodoX[9] - distancia;
		nodoY[11] = nodoY[9];
		nodoX[12] = nodoX[11] - distancia;
		nodoY[12] = nodoY[11];

		nodoX[13] = nodoX[0];
		nodoY[13] = nodoY[5] - (int) (distancia * Math.sqrt(3) / 2.0);
		nodoX[14] = nodoX[13] + distancia;
		nodoY[14] = nodoY[13];
		nodoX[15] = nodoX[13] - distancia;
		nodoY[15] = nodoY[13];

		nodoX[16] = nodoX[0];
		nodoY[16] = nodoY[9] + (int) (distancia * Math.sqrt(3) / 2.0);
		nodoX[17] = nodoX[16] + distancia;
		nodoY[17] = nodoY[16];
		nodoX[18] = nodoX[16] - distancia;
		nodoY[18] = nodoY[16];
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		inicializaNodos();
		g.drawLine(nodoX[4], nodoY[4], nodoX[2], nodoY[2]);
		g.drawLine(nodoX[8], nodoY[8], nodoX[6], nodoY[6]);
		g.drawLine(nodoX[12], nodoY[12], nodoX[10], nodoY[10]);
		g.drawLine(nodoX[15], nodoY[15], nodoX[14], nodoY[14]);
		g.drawLine(nodoX[18], nodoY[18], nodoX[17], nodoY[17]);

		g.drawLine(nodoX[14], nodoY[14], nodoX[18], nodoY[18]);
		g.drawLine(nodoX[13], nodoY[13], nodoX[12], nodoY[12]);
		g.drawLine(nodoX[6], nodoY[6], nodoX[16], nodoY[16]);
		g.drawLine(nodoX[15], nodoY[15], nodoX[4], nodoY[4]);
		g.drawLine(nodoX[2], nodoY[2], nodoX[17], nodoY[17]);

		g.drawLine(nodoX[15], nodoY[15], nodoX[17], nodoY[17]);
		g.drawLine(nodoX[8], nodoY[8], nodoX[16], nodoY[16]);
		g.drawLine(nodoX[13], nodoY[13], nodoX[10], nodoY[10]);
		g.drawLine(nodoX[4], nodoY[4], nodoX[18], nodoY[18]);
		g.drawLine(nodoX[14], nodoY[14], nodoX[2], nodoY[2]);
		for (int i = 0; i < 19; i++) {
			g.setColor(Color.white);
			g.fillOval(nodoX[i] - diametro / 2, nodoY[i] - diametro / 2, diametro, diametro);
			g.setColor(Color.black);
			g.drawOval(nodoX[i] - diametro / 2, nodoY[i] - diametro / 2, diametro, diametro);
		}
	}

	public static void main(String[] args) {
		DesenhaHexagonoMagico instancia = new DesenhaHexagonoMagico();
		instancia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}