package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// you can upload the resulting files in
// https://ezgif.com/apng-maker
public class GIFGenerator extends JFrame {

	private static final long serialVersionUID = -4076029987688325673L;
	private final int LARGURA = 600;
	private final int ALTURA = 600;
	private final int RAIO = 200;
	private int N;

	public GIFGenerator() {
		super("GIF Generator");
		N = Integer.valueOf(JOptionPane.showInputDialog("Informe a quantidade de lados"));
		setSize(LARGURA, ALTURA);
		setResizable(false);
		setVisible(true);
	}

	public void paint(Graphics g) {
		BufferedImage bufferedImage = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB);
		Graphics2D gg = bufferedImage.createGraphics();
		g.setColor(Color.white);
		gg.setColor(Color.white);
		g.fillRect(0, 0, LARGURA, ALTURA);
		gg.fillRect(0, 0, LARGURA, ALTURA);
		int[] coordX = new int[N];
		int[] coordY = new int[N];
		for (int i = 0; i < N; i++) {
			coordX[i] = LARGURA / 2 + (int) (RAIO * Math.cos(2 * Math.PI * i / N));
			coordY[i] = ALTURA / 2 + (int) (RAIO * Math.sin(2 * Math.PI * i / N));
		}
		g.setColor(Color.lightGray);
		gg.setColor(Color.lightGray);
		for (int i = 0; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				for (int m = 0; m < 3; m++) {
					for (int n = 0; n < 3; n++) {
						g.drawLine(coordX[i] - 1 + m, coordY[i] - 1 + n, coordX[j] - 1 + m, coordY[j] - 1 + n);
						gg.drawLine(coordX[i] - 1 + m, coordY[i] - 1 + n, coordX[j] - 1 + m, coordY[j] - 1 + n);
					}
				}
			}
		}
		int countFiles = 0;
		try {
			String fileName = "resources/animated";
			if (countFiles < 10) {
				fileName = fileName + "0" + countFiles + ".png";
			} else {
				fileName = fileName + countFiles + ".png";
			}
			FileOutputStream file = new FileOutputStream(fileName);
			ImageIO.write(bufferedImage, "png", file);
			countFiles++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (N % 2 == 1) { // N is odd, let's find a Euler's cycle!
			int jumps = (N - 1) / 2;
			for (int k = 1; k <= jumps; k++) {
				int lastDestiny = 0;
				for (int i = 0; i < N; i++) {
					int source = lastDestiny;
					int destiny = (source + k) % N;
					lastDestiny = destiny;
					g.setColor(Color.blue);
					gg.setColor(Color.blue);
					for (int m = 0; m < 3; m++) {
						for (int n = 0; n < 3; n++) {
							g.drawLine(coordX[source] - 1 + m, coordY[source] - 1 + n, coordX[destiny] - 1 + m,
									coordY[destiny] - 1 + n);
							gg.drawLine(coordX[source] - 1 + m, coordY[source] - 1 + n, coordX[destiny] - 1 + m,
									coordY[destiny] - 1 + n);
						}
					}
					try {
						String fileName = "resources/animated";
						if (countFiles < 10) {
							fileName = fileName + "0" + countFiles + ".png";
						} else {
							fileName = fileName + countFiles + ".png";
						}
						FileOutputStream file = new FileOutputStream(fileName);
						ImageIO.write(bufferedImage, "png", file);
						countFiles++;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		GIFGenerator obj = new GIFGenerator();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}