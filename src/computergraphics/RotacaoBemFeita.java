package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;

public class RotacaoBemFeita extends JFrame {

	private static final long serialVersionUID = 2847981035539960586L;
	private final int MAX_RES_X = 800;
	private final int MAX_RES_Y = 600;

	public RotacaoBemFeita() {
		super("RotacaoBemFeita");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		int[][] pixel = new int[128][128];
		try {
			FileInputStream reader = new FileInputStream("resources/mini_lenna.bmp");
			for (int i = 0; i < 54; i++) {
				reader.read();
			}
			for (int linha = 0; linha < 128; linha++) {
				for (int coluna = 127; coluna >= 0; coluna--) {
					int blue = reader.read();
					int green = reader.read();
					int red = reader.read();
					pixel[linha][coluna] = 256 * 256 * red + 256 * green + blue;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		int[][] coordX = new int[128][128];
		int[][] coordY = new int[128][128];
		for (int linha = 0; linha < 128; linha++) {
			for (int coluna = 0; coluna < 128; coluna++) {
				g.setColor(new Color(pixel[linha][coluna]));
				coordX[linha][coluna] = 3 * MAX_RES_X / 4 + coluna;
				coordY[linha][coluna] = MAX_RES_Y / 4 - linha;
				putPixel(g, coordX[linha][coluna], coordY[linha][coluna]);
			}
		}
		int[][] coordX_ = new int[128][128];
		int[][] coordY_ = new int[128][128];
		double[] angle = { Math.PI / 16, 2 * Math.PI / 16, 3 * Math.PI / 16, 4 * Math.PI / 16 };
		for (int theta = 0; theta < 4; theta++) {
			for (int linha = 0; linha < 128; linha++) {
				for (int coluna = 0; coluna < 128; coluna++) {
					g.setColor(new Color(pixel[linha][coluna]));
					coordX_[linha][coluna] = (int) Math.floor(coordX[linha][coluna] * Math.cos(angle[theta])
							- coordY[linha][coluna] * Math.sin(angle[theta]));
					coordY_[linha][coluna] = (int) Math.floor(coordX[linha][coluna] * Math.sin(angle[theta])
							+ coordY[linha][coluna] * Math.cos(angle[theta]));
					putPixel(g, coordX_[linha][coluna], coordY_[linha][coluna]);
				}
			}
		}

	}

	private void putPixel(Graphics g, int x, int y) {
		g.drawLine(x, y, x, y);
	}

	public static void main(String[] args) {
		RotacaoBemFeita obj = new RotacaoBemFeita();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
