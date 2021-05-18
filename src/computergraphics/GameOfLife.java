package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class GameOfLife extends JFrame implements MouseListener {

	private static final long serialVersionUID = 659277330197559381L;
	private int MAX_RES_X = 640, MAX_RES_Y = 480;
	private boolean[][] matriz;
	private boolean[][] next;

	public GameOfLife() {
		super("Game Of Life");
		matriz = new boolean[MAX_RES_X][MAX_RES_Y];
		initializeMatrix();
		next = new boolean[MAX_RES_X][MAX_RES_Y];
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
		addMouseListener(this);
	}

	private void initializeMatrix() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				int coin = (int) (2 * Math.random());
				matriz[i][j] = (coin == 0) ? false : true;
			}
		}
	}

	public void paint(Graphics g) {
		for (int generation = 0; generation < 20; generation++) {
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[0].length; j++) {
					g.setColor(matriz[i][j] ? Color.WHITE : Color.BLACK);
					g.drawLine(i, j, i, j);
				}
			}
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[0].length; j++) {
					int neighbours = countNeighbours(i, j);
					/**
					 * Qualquer c�lula viva com menos de dois vizinhos vivos morre de solid�o.
					 * Qualquer c�lula viva com mais de tr�s vizinhos vivos morre de superpopula��o.
					 * Qualquer c�lula com exatamente tr�s vizinhos vivos se torna uma c�lula viva.
					 * Qualquer c�lula com dois vizinhos vivos continua no mesmo estado para a
					 * pr�xima gera��o.
					 */
					if ((neighbours < 2) || (neighbours > 3)) {
						next[i][j] = false;
					}
					if (neighbours == 3) {
						next[i][j] = true;
					}
				}
			}
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[0].length; j++) {
					matriz[i][j] = next[i][j];
				}
			}
		}
	}

	public int countNeighbours(int x, int y) {
		int count = 0;
		if (x > 0) {
			if (matriz[x - 1][y]) {
				count++;
			}
		}
		if (y > 0) {
			if (matriz[x][y - 1]) {
				count++;
			}
		}
		if (x < MAX_RES_X - 1) {
			if (matriz[x + 1][y]) {
				count++;
			}
		}
		if (y < MAX_RES_Y - 1) {
			if (matriz[x][y + 1]) {
				count++;
			}
		}
		if ((x > 0) && (y > 0)) {
			if (matriz[x - 1][y - 1]) {
				count++;
			}
		}
		if ((x > 0) && (y < MAX_RES_Y - 1)) {
			if (matriz[x - 1][y + 1]) {
				count++;
			}
		}
		if ((x < MAX_RES_X - 1) && (y > 0)) {
			if (matriz[x + 1][y - 1]) {
				count++;
			}
		}
		if ((x < MAX_RES_X - 1) && (y < MAX_RES_Y - 1)) {
			if (matriz[x + 1][y + 1]) {
				count++;
			}
		}
		return (count);
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
		System.exit(0);
	}

	public static void main(String args[]) {
		GameOfLife instance = new GameOfLife();
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}