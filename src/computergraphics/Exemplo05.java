package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * paleta de cores do arco-iris
 * @author Cleyton
 *
 */
public class Exemplo05 extends JFrame {

	private static final long serialVersionUID = 3888297306215856913L;

	public Exemplo05() {
		super("Exemplo05");
		setSize(500, 650);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 500, 650);
		int[] arrayCores = { 0xFF3300, // vermelho
				0xFFCC33, // laranja
				0xFFFF33, // amarelo
				0x99FF33, // verde
				0x3333FF, // azul
				0x000099, // anil
				0x9966CC // violeta
		};
		Color[] c = new Color[arrayCores.length];
		for (int i = 0; i < arrayCores.length; i++) {
			c[i] = new Color(arrayCores[i]);
			g.setColor(c[i]);
			g.fillRect(100, 75 * (i + 1), 300, 50);
		}
	}

	public static void main(String[] args) {
		Exemplo05 obj = new Exemplo05();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}