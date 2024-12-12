package computergraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

public class GraphicalWriting extends JFrame {

	private static final long serialVersionUID = -8852728290711045785L;
	private final int MAX_RES_X = 1024;
	private final int MAX_RES_Y = 768;

	public GraphicalWriting() {
		super("GraphicalWriting");
		setSize(MAX_RES_X, MAX_RES_Y);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, MAX_RES_X - 1, MAX_RES_Y); // limpa tela
		Font font = new Font("Times New Roman", Font.PLAIN, 24);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString("Faculdade Paraense de Ensino - FAPEN.", 100, 100);
		g.drawString("Curso de Ciencia da Computacao.", 100, 130);
		g.drawString("Disciplina de Computacao Grafica.", 100, 160);
		g.drawString("Este texto foi desenhado de forma grafica.", 100, 220);
		font = new Font("Times New Roman", Font.BOLD, 24);
		g.setFont(font);
		g.drawString("Voce nao pode selecionar este texto! :)", 100, 250);

	}

	public static void main(String[] args) {
		GraphicalWriting obj = new GraphicalWriting();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}