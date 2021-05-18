package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class NonEscapeMandelbrot extends JFrame implements MouseListener {

	private static final long serialVersionUID = -7429779026409169909L;
	private int MAX_RES_X = 800, MAX_RES_Y = 600;
	private double xmin = -8, xmax = 8, ymin = -6, ymax = 6;
	private int[] paletteR = new int[240000];
	private int[] paletteG = new int[240000];
	private int[] paletteB = new int[240000];

	public NonEscapeMandelbrot() {
		super("Fractal de Mandelbrot");
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
		addMouseListener(this);
		initPalette();
	}

	private void initPalette() {
		for (int i = 0; i < 40000; i++) {
			paletteR[i] = 255 * i / 40000;
			paletteG[i] = 0;
			paletteB[i] = 255;
		}
		for (int i = 40000; i < 80000; i++) {
			paletteR[i] = 255;
			paletteG[i] = 0;
			paletteB[i] = 255 - 255 * (i - 40000) / 40000;
		}
		for (int i = 80000; i < 120000; i++) {
			paletteR[i] = 255;
			paletteG[i] = 255 * (i - 80000) / 40000;
			paletteB[i] = 0;
		}
		for (int i = 120000; i < 160000; i++) {
			paletteR[i] = 255 - 255 * (i - 120000) / 40000;
			paletteG[i] = 255;
			paletteB[i] = 0;
		}
		for (int i = 160000; i < 200000; i++) {
			paletteR[i] = 0;
			paletteG[i] = 255;
			paletteB[i] = 255 * (i - 160000) / 40000;
		}
		for (int i = 200000; i < 240000; i++) {
			paletteR[i] = 0;
			paletteG[i] = 255 - 255 * (i - 200000) / 40000;
			paletteB[i] = 255;
		}
	}

	public double[] mSetLevel(double cx, double cy, double maxIter) {
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
		return (new double[] { x, y, iter });
	}

	public void paint(Graphics g) {
		super.paint(g);
		double cx = 0, cy = 0;
		double maxIter = 200;
		int nx = MAX_RES_X;
		int ny = MAX_RES_Y;

		// Valores originais
		// xmin = -0.14 , xmax = 0, ymin = -1, ymax = -0.9;
		// double xmin = -0.07 , xmax = -0.035, ymin = -1, ymax = -0.975;

		// O problema todo
		// double xmin = -0.7 , xmax = 0, ymin = -1, ymax = -0.5;
		// double xmin = -2.5 , xmax = 1.5, ymin = -1.5, ymax = 1.5;

		// Mandelbrot zoom
		// double xmin = -0.0525 , xmax = -0.035, ymin = -0.99375, ymax = -0.98125;
		// double xmin = -0.048125 , xmax = -0.039375, ymin = -0.990625, ymax =
		// -0.984375;

		// Fronteira do universo
		// double xmin = -0.14 , xmax = 0, ymin = -0.70, ymax = -0.60;
		// double xmin = -0.21875 , xmax = -0.13125, ymin = -0.6875, ymax = -0.6250;

		BufferedImage buffImage = new BufferedImage(MAX_RES_X, MAX_RES_Y, BufferedImage.TYPE_INT_RGB);
		Graphics2D gg = buffImage.createGraphics();

		int vermelho = 0, verde = 0, azul = 0;

		for (int iy = 0; iy < ny; iy++) {
			cy = ymin + iy * (ymax - ymin) / (ny - 1);
			for (int ix = 0; ix < nx; ix++) {
				cx = xmin + ix * (xmax - xmin) / (nx - 1);
				double[] array = mSetLevel(cx, cy, maxIter);
				double temp = (array[2] + (Math.log(2.0 * Math.log(2.0))
						- Math.log(Math.log(Math.sqrt(array[0] * array[0] + array[1] * array[1])))) / Math.log(2.0));
				temp *= 1000.0;
				vermelho = paletteR[(int) temp];
				verde = paletteG[(int) temp];
				azul = paletteB[(int) temp];
				g.setColor(new Color(vermelho, verde, azul));
				// g.setColor( new Color( inverso[ vermelho ], inverso[ verde ], inverso[ azul ]
				// ) );
				// g.setColor( new Color( 255 - vermelho, 255 - verde, 255 - azul ) );
				// g.setColor( new Color( inverso[ ( vermelho ^ 255 ) % 256 ], inverso[ ( verde
				// ^ 255 ) % 256 ], inverso[ ( azul ^ 255 ) % 256 ] ) );
				// g.setColor( new Color( ( inverso[ vermelho ] ^ 255 ) % 256, ( inverso[ verde
				// ] ^ 255 ) % 256, ( inverso[ azul ] ^ 255 ) % 256 ) );

				g.drawLine(ix, iy, ix, iy);
				// Somente invers�o mod 257
				// gg.setColor( new Color( inverso[ vermelho ], inverso[ verde ], inverso[ azul
				// ] ) );
				// Somente opera��o XOR
				// gg.setColor( new Color( ( vermelho ^ 255 ) % 256, ( verde ^ 255 ) % 257, (
				// azul ^ 255 ) % 256 ) );
				// Invers�o mod 257 --> seguida de opera��o XOR
				// gg.setColor( new Color( ( inverso[ vermelho ] ^ 255 ) % 256, ( inverso[ verde
				// ] ^ 255 ) % 256, ( inverso[ azul ] ^ 255 ) % 256 ) );
				// Opera��o XOR --> seguida de invers�o mod 256
				// gg.setColor( new Color( inverso[ ( vermelho ^ 255 ) % 256 ], inverso[ ( verde
				// ^ 255 ) % 256 ], inverso[ ( azul ^ 255 ) % 256 ] ) );
				// Original
				gg.setColor(new Color(vermelho, verde, azul));
				gg.drawLine(ix, iy, ix, iy);
			}
		}

		/*
		 * try { //File arquivo = new File( "arquivo.bmp" ); //ImageIO.write( (
		 * RenderedImage ) gg, "bmp", arquivo ); File f = new File( "arquivo.bmp" );
		 * javax.imageio.stream.ImageOutputStream ios = ImageIO.createImageOutputStream(
		 * f ); java.util.Iterator iterator = ImageIO.getImageWritersByFormatName( "bmp"
		 * ); ImageWriter writer = ( ImageWriter ) iterator.next(); writer.setOutput(
		 * ios ); writer.write( buffImage ); } catch ( IOException e ) {
		 * e.printStackTrace(); }
		 */
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

	public static void main(String args[]) {
		new NonEscapeMandelbrot().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
