package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class GeraGrafoAleatorio extends JFrame {

	private static final long serialVersionUID = 6688715309612009300L;
	private int MAX_RES_X = 800, MAX_RES_Y = 600;
	private final int NODOS = 12;
	private final int MIN_DIST = 100;
	private int[] nodoX = new int[NODOS];
	private int[] nodoY = new int[NODOS];
	private int diametro = MAX_RES_X / 25;
	private boolean[][] adjacencia = new boolean[NODOS][NODOS];

	public GeraGrafoAleatorio() {
		super("Gerador de Grafos Aleatorios");
		getContentPane().setBackground(Color.white);
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
	}

	private int distancia(int x1, int y1, int x2, int y2) {
		return ((int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
	}

	private void bsort(int[] array1, int[] array2) {
		for (int i = 0; i < array1.length - 1; i++) {
			for (int j = i + 1; j < array1.length; j++) {
				if (array1[i] > array1[j]) {
					int temp1 = array1[i];
					array1[i] = array1[j];
					array1[j] = temp1;
					int temp2 = array2[i];
					array2[i] = array2[j];
					array2[j] = temp2;
				}
			}
		}
	}

	private void inicializaNodos() {
		boolean algumPerto = true;
		while (algumPerto) {
			algumPerto = false;
			boolean todosOK = false;
			while (!todosOK) {
				todosOK = true;
				for (int i = 0; i < NODOS; i++) {
					nodoX[i] = (int) (MAX_RES_X * Math.random());
					nodoY[i] = (int) (MAX_RES_Y * Math.random());
					if ((nodoX[i] < 50) || (nodoX[i] > MAX_RES_X - 50) || (nodoY[i] < 50)
							|| (nodoY[i] > MAX_RES_Y - 50)) {
						todosOK = false;
					}
				}
			}

			for (int i = 0; i < NODOS - 1; i++) {
				for (int j = i + 1; j < NODOS; j++) {
					if (distancia(nodoX[i], nodoY[i], nodoX[j], nodoY[j]) < MIN_DIST) {
						algumPerto = true;
					}
				}
			}
		}
		for (int i = 0; i < NODOS; i++) {
			for (int j = 0; j < NODOS; j++) {
				adjacencia[i][j] = false;
			}
		}
		boolean[] conectado = new boolean[NODOS];
		for (int i = 0; i < NODOS; i++) {
			int[] vetor_nodos = new int[NODOS];
			int[] vetor_distancia = new int[NODOS];
			for (int j = 0; j < NODOS; j++) {
				vetor_nodos[j] = j;
				int dist = distancia(nodoX[i], nodoY[i], nodoX[j], nodoY[j]);
				vetor_distancia[j] = dist;
			}
			bsort(vetor_distancia, vetor_nodos);
			for (int j = 0; j < NODOS; j++) {
				if (i != vetor_nodos[j]) {
					if (!adjacencia[i][vetor_nodos[j]] && !conectado[vetor_nodos[j]]) {
						adjacencia[i][vetor_nodos[j]] = true;
						adjacencia[vetor_nodos[j]][i] = true;
						conectado[vetor_nodos[j]] = true;
						break;
					}
				}
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		inicializaNodos();
		for (int i = 0; i < NODOS; i++) {
			for (int j = 0; j < NODOS; j++) {
				if (i != j) {
					if (adjacencia[i][j]) {
						g.drawLine(nodoX[i], nodoY[i], nodoX[j], nodoY[j]);
					}
				}
			}
		}
		for (int i = 0; i < NODOS; i++) {
			g.setColor(Color.white);
			g.fillOval(nodoX[i] - diametro / 2, nodoY[i] - diametro / 2, diametro, diametro);
			g.setColor(Color.black);
			g.drawOval(nodoX[i] - diametro / 2, nodoY[i] - diametro / 2, diametro, diametro);
		}
		// g.drawString( "FIM", MAX_RES_X / 2, MAX_RES_Y / 2 );
	}

	public static void main(String[] args) {
		GeraGrafoAleatorio instancia = new GeraGrafoAleatorio();
		instancia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}