package computergraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

public class HelloWorld extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8968651500269644662L;

	public HelloWorld() {
		super("HelloWorld");
		setSize(800, 600);
		setResizable(false);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 800, 600);
		Font font = new Font("Times New Roman", Font.BOLD + Font.ITALIC, 72);
		g.setFont(font);
		// g.setColor( Color.white );
		// g.drawString( "Hello, world!", 198, 298 );
		g.setColor(Color.blue);
		g.drawString("Hello, world!", 200, 200);
	}

	public static void main(String[] args) {
		HelloWorld obj = new HelloWorld();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}