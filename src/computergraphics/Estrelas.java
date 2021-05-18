package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Estrelas extends JFrame {

	private static final long serialVersionUID = -609312063830031858L;
	private final int MAX_RES_X = 1024;
	private final int MAX_RES_Y = 768;
	private final int ARESTA = 200;
	private final int VERTICES = 5;

	public Estrelas() {
		super("Estrelas");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		boolean save_bitmap = true;
		BufferedImage bufferedImage = new BufferedImage(MAX_RES_X, MAX_RES_Y, BufferedImage.TYPE_INT_RGB);
		Graphics2D gg = bufferedImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		if (save_bitmap) {
			gg.setColor(Color.white);
			gg.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		}
		int[] coordX = new int[VERTICES];
		int[] coordY = new int[VERTICES];
		int centroX = MAX_RES_X / 2, centroY = MAX_RES_Y / 2;
		for (int i = 0; i < VERTICES; i++) {
			// coordX[ i ] = centroX + ( int ) Math.round( ARESTA * Math.cos( ( 2 * Math.PI
			// ) * ( VERTICES / 2 ) * i / VERTICES ) );
			coordX[i] = centroX + (int) Math.round(ARESTA * Math.cos((2 * Math.PI) * i / VERTICES));
			// coordY[ i ] = centroY - ( int ) Math.round( ARESTA * Math.sin( ( 2 * Math.PI
			// ) * ( VERTICES / 2 ) * i / VERTICES ) );
			coordY[i] = centroY - (int) Math.round(ARESTA * Math.sin((2 * Math.PI) * i / VERTICES));
		}
		
		int[] coordPentagonX = new int[VERTICES];
		int[] coordPentagonY = new int[VERTICES];
		int raio = (int) Math.round(ARESTA * Math.sin(Math.PI / 10) / Math.sin(7 * Math.PI / 10));
		for (int i = 0; i < VERTICES; i++) {
			coordPentagonX[i] = centroX
					+ (int) Math.round(raio * Math.cos((2 * Math.PI) * i / VERTICES + Math.PI / 5.0));
			coordPentagonY[i] = centroY
					- (int) Math.round(raio * Math.sin((2 * Math.PI) * i / VERTICES + Math.PI / 5.0));
		}
		
		int[] arrayX = new int[2 * VERTICES];
		int[] arrayY = new int[2 * VERTICES];
		for (int i = 0; i < VERTICES; i++) {
			arrayX[2 * i] = coordX[i];
			arrayX[2 * i + 1] = coordPentagonX[i];
			arrayY[2 * i] = coordY[i];
			arrayY[2 * i + 1] = coordPentagonY[i];
		}
		Polygon estrela = new Polygon(arrayX, arrayY, 2 * VERTICES);
		g.setColor(Color.black);
		g.drawPolygon(estrela);
		gg.setColor(Color.black);
		gg.drawPolygon(estrela);
		if (save_bitmap) {
			
			try {
				FileOutputStream file = new FileOutputStream("resources/estrela.png");
				ImageIO.write(bufferedImage, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Estrelas obj = new Estrelas();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
