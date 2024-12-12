package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame implements MouseListener {

	private static final long serialVersionUID = 3188346788129024933L;
	private int MAX_RES_X = 1024, MAX_RES_Y = 768;
	private double xmin = -8, xmax = 8, ymin = -6, ymax = 6;

	public Mandelbrot() {
		super("Fractal de Mandelbrot");
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
		addMouseListener(this);
	}

	public int mSetLevel(double cx, double cy, double maxIter) {
		// function returning level set of a point
		int iter = 0;
		double x = 0, y = 0, temp = 0;
		while ((iter < maxIter) && ((x * x + y * y) < 10000)) {
			temp = x * x - y * y + cx;
			y = 2 * x * y + cy;
			// temp = x * x * x - 3 * x * y * y + cx;
			// y = 3 * x * x * y - y * y * y + cy;
			x = temp;
			iter++;
		}
		return (iter);
	}

	public void paint(Graphics g) {
		super.paint(g);
		double cx = 0, cy = 0;
		double maxIter = 200;
		int nx = MAX_RES_X;
		int ny = MAX_RES_Y;
		int valor = 0;

		// Valores originais
		// xmin = -0.14 ; xmax = 0 ; ymin = -1 ; ymax = -0.9;
		// double xmin = -0.07 , xmax = -0.035, ymin = -1, ymax = -0.975;

		// O problema todo
		// double xmin = -0.7 , xmax = 0, ymin = -1, ymax = -0.5;
		// double xmin = -2.5 , xmax = 1.5, ymin = -1.5, ymax = 1.5;

		// Mandelbrot zoom
		// xmin = -0.0525 ; xmax = -0.035 ; ymin = -0.99375 ; ymax = -0.98125;
		double xmin = -0.048125, xmax = -0.039375, ymin = -0.990625, ymax = -0.984375;

		// Fronteira do universo
		// double xmin = -0.14 , xmax = 0, ymin = -0.70, ymax = -0.60;
		// double xmin = -0.21875 , xmax = -0.13125, ymin = -0.6875, ymax = -0.6250;

		BufferedImage buffImage = new BufferedImage(MAX_RES_X, MAX_RES_Y, BufferedImage.TYPE_INT_RGB);
		Graphics2D gg = buffImage.createGraphics();

		int vermelho = 0;
		int verde = 0;
		int azul = 0;
		for (int iy = 0; iy < ny; iy++) {
			cy = ymin + iy * (ymax - ymin) / (ny - 1);
			for (int ix = 0; ix < nx; ix++) {
				cx = xmin + ix * (xmax - xmin) / (nx - 1);
				valor = mSetLevel(cx, cy, maxIter);
				vermelho = (valor * valor / 200 + 25) % 256;
				verde = (valor * valor / 200 + 45) % 256;
				azul = (valor * valor / 200 + 55) % 256;
				g.setColor(new Color(vermelho, verde, azul));
				// g.setColor( new Color( inverso[ vermelho ], inverso[ verde ], inverso[ azul ]
				// ) );
				g.drawLine(ix, iy, ix, iy);
				// Somente inversao mod 257
				// gg.setColor( new Color( inverso[ vermelho ], inverso[ verde ], inverso[ azul
				// ] ) );
				// Somente operacao XOR
				// gg.setColor( new Color( ( vermelho ^ 255 ) % 256, ( verde ^ 255 ) % 257, (
				// azul ^ 255 ) % 256 ) );
				// Inversao mod 257 --> seguida de operacao XOR
				// gg.setColor( new Color( ( inverso[ vermelho ] ^ 255 ) % 256, ( inverso[ verde
				// ] ^ 255 ) % 256, ( inverso[ azul ] ^ 255 ) % 256 ) );
				// Operacao XOR --> seguida de inversao mod 256
				// gg.setColor( new Color( inverso[ ( vermelho ^ 255 ) % 256 ], inverso[ ( verde
				// ^ 255 ) % 256 ], inverso[ ( azul ^ 255 ) % 256 ] ) );
				// Original

				gg.setColor(new Color(vermelho, verde, azul));
				gg.drawLine(ix, iy, ix, iy);

			}
		}

		try {
			File f = new File("resources/arquivo.png");
			// ImageIO.write( ( RenderedImage ) gg, "bmp", arquivo );
			javax.imageio.stream.ImageOutputStream ios = ImageIO.createImageOutputStream(f);
			Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("png");
			ImageWriter writer = (ImageWriter) iterator.next();
			writer.setOutput(ios);
			writer.write(buffImage); // actually write it
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		if (e.getButton() == 1) { // left button
			double newX = xmin + (double) e.getX() * (xmax - xmin) / (double) MAX_RES_X;
			double newXmin = newX - (xmax - xmin) / 4.0;
			double newXmax = newX + (xmax - xmin) / 4.0;
			double newY = ymin + (double) e.getY() * (ymax - ymin) / (double) MAX_RES_Y;
			double newYmin = newY - (ymax - ymin) / 4.0;
			double newYmax = newY + (ymax - ymin) / 4.0;
			xmin = newXmin;
			xmax = newXmax;
			ymin = newYmin;
			ymax = newYmax;
			super.repaint();
		} else if (e.getButton() == 3) { // right button
			double newX = xmin + (double) e.getX() * (xmax - xmin) / (double) MAX_RES_X;
			double newXmin = newX - (xmax - xmin);
			double newXmax = newX + (xmax - xmin);
			double newY = ymin + (double) e.getY() * (ymax - ymin) / (double) MAX_RES_Y;
			double newYmin = newY - (ymax - ymin);
			double newYmax = newY + (ymax - ymin);
			xmin = newXmin;
			xmax = newXmax;
			ymin = newYmin;
			ymax = newYmax;
			super.repaint();
		} else if (e.getButton() == 2) { // middle button = reset
			xmin = -8;
			xmax = 8;
			ymin = -6;
			ymax = 6;
			super.repaint();
		}
	}

	public static void main(String[] args) {
		new Mandelbrot().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
