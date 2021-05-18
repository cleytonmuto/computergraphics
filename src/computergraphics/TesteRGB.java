package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class TesteRGB extends JFrame {

	private static final long serialVersionUID = -4123178494195730062L;

	public TesteRGB() {
		super("TesteRGB");
		setSize(1024, 768);
		setVisible(true);
	}

	public void paint(Graphics g) {
		Color[] arrayCor = new Color[8];
		arrayCor[0] = new Color(0, 0, 0);
		arrayCor[1] = new Color(255, 0, 0);
		arrayCor[2] = new Color(0, 255, 0);
		arrayCor[3] = new Color(0, 0, 255);
		arrayCor[4] = new Color(255, 255, 0);
		arrayCor[5] = new Color(255, 0, 255);
		arrayCor[6] = new Color(0, 255, 255);
		arrayCor[7] = new Color(127, 127, 127);
		for (int i = 0; i < 8; i++) {
			g.setColor(arrayCor[i]);
			g.fillRect(100 + 100 * i, 100, 50, 500);
		}
	}

	public static void main(String[] args) {
		TesteRGB obj = new TesteRGB();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}