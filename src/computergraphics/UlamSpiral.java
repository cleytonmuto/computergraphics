package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class UlamSpiral extends JFrame {

	private static final long serialVersionUID = -4785595547667641855L;
	private int MAX_RES_X = 1000;
	private int MAX_RES_Y = 1000;
	private boolean[][] matriz;

	private boolean isPrime(int n) {
		if (n < 7 || n % 2 == 0 || n % 3 == 0 || n % 5 == 0) {
			return (n == 2 || n == 3 || n == 5);
		}
		int maxP = (int) Math.sqrt(n) + 2;
		for (int p = 5; p < maxP; p += 6) {
			if (n % p == 0 || n % (p + 2) == 0) {
				return false;
			}
		}
		return true;
	}

	public UlamSpiral() {
		super("Ulam Spiral");
		setSize(MAX_RES_X, MAX_RES_Y);
		setLocation(100, 0);
		setVisible(true);
		initSpiral();
	}

	private void initSpiral() {
		matriz = new boolean[MAX_RES_Y][MAX_RES_X];
		int x = MAX_RES_X / 2;
		int y = MAX_RES_Y / 2;
		int n = 0;
		int direction = 0;
		int side = 1, step = 0;
		for (int i = 0; i < MAX_RES_X - 1; i++) {
			for (int j = 0; j < MAX_RES_Y - 1; j++) {
				n++;
				matriz[y][x] = isPrime(n) ? true : false;
				switch (direction) {
				case 0:
					x++;
					break;
				case 1:
					y--;
					break;
				case 2:
					x--;
					break;
				case 3:
					y++;
					break;
				default:
				}
				step++;
				if (step == side) {
					direction = (direction + 1) % 4;
				}
				if (step == (2 * side)) {
					direction = (direction + 1) % 4;
					step = 0;
					side++;
				}
			}
		}
	}

	public void paint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(MAX_RES_X, MAX_RES_Y, BufferedImage.TYPE_INT_RGB);
		Graphics2D gg = bufferedImage.createGraphics();
		g.setColor(Color.black);
		gg.setColor(Color.black);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		gg.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		for (int i = 0; i < MAX_RES_X; i++) {
			for (int j = 0; j < MAX_RES_Y; j++) {
				g.setColor(matriz[j][i] ? Color.green : Color.black);
				gg.setColor(matriz[j][i] ? Color.green : Color.black);
				g.drawLine(i, j, i, j);
				gg.drawLine(i, j, i, j);
			}
		}
		try {
			FileOutputStream file = new FileOutputStream("resources/ulam_spiral.png");
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UlamSpiral instance = new UlamSpiral();
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}