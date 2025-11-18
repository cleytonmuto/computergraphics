package computergraphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class GeraGrafoAleatorio extends JFrame {

	private static final long serialVersionUID = 6688715309612009300L;
	private int MAX_RES_X = 1200, MAX_RES_Y = 900; // Increased by 50% (800*1.5=1200, 600*1.5=900)
	private final int NODOS = 20;
	private final int MIN_DIST = 150; // Increased by 50% (100*1.5=150)
	private int[] nodoX = new int[NODOS];
	private int[] nodoY = new int[NODOS];
	private int diametro = MAX_RES_X / 25;
	private boolean[][] adjacencia = new boolean[NODOS][NODOS];
	private int[] hamiltonianPath = null; // Stores the minimum closed path

	public GeraGrafoAleatorio() {
		super("Gerador de Grafos Aleatorios");
		getContentPane().setBackground(Color.white);
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		setVisible(true);
	}

	private int distancia(int x1, int y1, int x2, int y2) {
		return ((int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
	}

	private void bsort(int[] array1, int[] array2) {
		for (int i = 0; i < array1.length - 1; i++) {
			for (int j = i + 1; j < array1.length; j++) {
				if (array1[i] > array1[j]) {
					int temp1 = array1[i];
					array1[i] = array1[j];
					array1[j] = temp1;
					int temp2 = array2[i];
					array2[i] = array2[j];
					array2[j] = temp2;
				}
			}
		}
	}

	private void inicializaNodos() {
		// Improved vertex distribution using grid-based approach with jitter
		// Calculate optimal grid dimensions
		int cols = (int) Math.ceil(Math.sqrt(NODOS));
		int rows = (int) Math.ceil((double) NODOS / cols);
		
		// Calculate spacing with margins
		int margin = 80;
		int availableWidth = MAX_RES_X - 2 * margin;
		int availableHeight = MAX_RES_Y - 2 * margin;
		int cellWidth = availableWidth / (cols + 1);
		int cellHeight = availableHeight / (rows + 1);
		
		// Place vertices in grid with random jitter
		int vertexIndex = 0;
		for (int row = 0; row < rows && vertexIndex < NODOS; row++) {
			for (int col = 0; col < cols && vertexIndex < NODOS; col++) {
				// Base position in grid
				int baseX = margin + (col + 1) * cellWidth;
				int baseY = margin + (row + 1) * cellHeight;
				
				// Add random jitter (up to 30% of cell size)
				int jitterX = (int) ((Math.random() - 0.5) * cellWidth * 0.6);
				int jitterY = (int) ((Math.random() - 0.5) * cellHeight * 0.6);
				
				nodoX[vertexIndex] = Math.max(margin, Math.min(MAX_RES_X - margin, baseX + jitterX));
				nodoY[vertexIndex] = Math.max(margin, Math.min(MAX_RES_Y - margin, baseY + jitterY));
				
				vertexIndex++;
			}
		}
		
		// Ensure minimum distance between all vertices
		boolean algumPerto = true;
		int maxIterations = 100;
		int iteration = 0;
		while (algumPerto && iteration < maxIterations) {
			algumPerto = false;
			for (int i = 0; i < NODOS - 1; i++) {
				for (int j = i + 1; j < NODOS; j++) {
					int dist = distancia(nodoX[i], nodoY[i], nodoX[j], nodoY[j]);
					if (dist < MIN_DIST) {
						algumPerto = true;
						// Push vertices apart
						double angle = Math.atan2(nodoY[j] - nodoY[i], nodoX[j] - nodoX[i]);
						int pushDistance = (MIN_DIST - dist) / 2 + 5;
						int dx = (int) (Math.cos(angle) * pushDistance);
						int dy = (int) (Math.sin(angle) * pushDistance);
						
						nodoX[i] = Math.max(margin, Math.min(MAX_RES_X - margin, nodoX[i] - dx));
						nodoY[i] = Math.max(margin, Math.min(MAX_RES_Y - margin, nodoY[i] - dy));
						nodoX[j] = Math.max(margin, Math.min(MAX_RES_X - margin, nodoX[j] + dx));
						nodoY[j] = Math.max(margin, Math.min(MAX_RES_Y - margin, nodoY[j] + dy));
					}
				}
			}
			iteration++;
		}
		for (int i = 0; i < NODOS; i++) {
			for (int j = 0; j < NODOS; j++) {
				adjacencia[i][j] = false;
			}
		}
		boolean[] conectado = new boolean[NODOS];
		for (int i = 0; i < NODOS; i++) {
			int[] vetor_nodos = new int[NODOS];
			int[] vetor_distancia = new int[NODOS];
			for (int j = 0; j < NODOS; j++) {
				vetor_nodos[j] = j;
				int dist = distancia(nodoX[i], nodoY[i], nodoX[j], nodoY[j]);
				vetor_distancia[j] = dist;
			}
			bsort(vetor_distancia, vetor_nodos);
			for (int j = 0; j < NODOS; j++) {
				if (i != vetor_nodos[j]) {
					if (!adjacencia[i][vetor_nodos[j]] && !conectado[vetor_nodos[j]]) {
						adjacencia[i][vetor_nodos[j]] = true;
						adjacencia[vetor_nodos[j]][i] = true;
						conectado[vetor_nodos[j]] = true;
						break;
					}
				}
			}
		}
		
		// Find minimum Hamiltonian cycle (TSP)
		hamiltonianPath = findMinimumHamiltonianCycle();
	}
	
	/**
	 * Finds the minimum closed path visiting all vertices exactly once (TSP).
	 * Uses Nearest Neighbor heuristic + 2-opt improvement.
	 */
	private int[] findMinimumHamiltonianCycle() {
		// Step 1: Nearest Neighbor heuristic
		int[] path = nearestNeighborTSP();
		
		// Step 2: 2-opt improvement
		path = twoOptImprovement(path);
		
		return path;
	}
	
	/**
	 * Nearest Neighbor heuristic for TSP
	 */
	private int[] nearestNeighborTSP() {
		boolean[] visited = new boolean[NODOS];
		int[] path = new int[NODOS + 1]; // +1 to close the cycle
		path[0] = 0; // Start at vertex 0
		visited[0] = true;
		
		for (int i = 1; i < NODOS; i++) {
			int current = path[i - 1];
			int nearest = -1;
			double minDist = Double.MAX_VALUE;
			
			for (int j = 0; j < NODOS; j++) {
				if (!visited[j]) {
					double dist = distancia(nodoX[current], nodoY[current], nodoX[j], nodoY[j]);
					if (dist < minDist) {
						minDist = dist;
						nearest = j;
					}
				}
			}
			
			path[i] = nearest;
			visited[nearest] = true;
		}
		
		path[NODOS] = path[0]; // Close the cycle
		return path;
	}
	
	/**
	 * 2-opt improvement: tries to improve the path by swapping edges
	 */
	private int[] twoOptImprovement(int[] path) {
		boolean improved = true;
		int maxIterations = 100;
		int iteration = 0;
		
		while (improved && iteration < maxIterations) {
			improved = false;
			double bestDistance = calculatePathDistance(path);
			
			for (int i = 1; i < NODOS - 1; i++) {
				for (int j = i + 1; j < NODOS; j++) {
					// Try reversing segment between i and j
					int[] newPath = twoOptSwap(path, i, j);
					double newDistance = calculatePathDistance(newPath);
					
					if (newDistance < bestDistance) {
						path = newPath;
						bestDistance = newDistance;
						improved = true;
					}
				}
			}
			iteration++;
		}
		
		return path;
	}
	
	/**
	 * Performs a 2-opt swap: reverses the segment between indices i and j
	 */
	private int[] twoOptSwap(int[] path, int i, int j) {
		int[] newPath = new int[path.length];
		
		// Copy path[0..i-1]
		for (int k = 0; k < i; k++) {
			newPath[k] = path[k];
		}
		
		// Reverse path[i..j]
		for (int k = i; k <= j; k++) {
			newPath[k] = path[j - (k - i)];
		}
		
		// Copy path[j+1..end]
		for (int k = j + 1; k < path.length; k++) {
			newPath[k] = path[k];
		}
		
		return newPath;
	}
	
	/**
	 * Calculates the total distance of a path
	 */
	private double calculatePathDistance(int[] path) {
		double totalDistance = 0;
		for (int i = 0; i < path.length - 1; i++) {
			totalDistance += distancia(nodoX[path[i]], nodoY[path[i]], 
			                          nodoX[path[i + 1]], nodoY[path[i + 1]]);
		}
		return totalDistance;
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		inicializaNodos();
		
		// Draw graph edges (gray, thinner)
		g.setColor(new Color(200, 200, 200));
		for (int i = 0; i < NODOS; i++) {
			for (int j = 0; j < NODOS; j++) {
				if (i != j) {
					if (adjacencia[i][j]) {
						g.drawLine(nodoX[i], nodoY[i], nodoX[j], nodoY[j]);
					}
				}
			}
		}
		
		// Draw minimum Hamiltonian cycle path (red, thicker)
		if (hamiltonianPath != null) {
			g.setColor(Color.RED);
			java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
			java.awt.Stroke oldStroke = g2d.getStroke();
			g2d.setStroke(new java.awt.BasicStroke(3.0f));
			
			for (int i = 0; i < hamiltonianPath.length - 1; i++) {
				int from = hamiltonianPath[i];
				int to = hamiltonianPath[i + 1];
				g2d.drawLine(nodoX[from], nodoY[from], nodoX[to], nodoY[to]);
			}
			
			g2d.setStroke(oldStroke);
		}
		
		// Draw vertices (white filled, black border)
		for (int i = 0; i < NODOS; i++) {
			g.setColor(Color.white);
			g.fillOval(nodoX[i] - diametro / 2, nodoY[i] - diametro / 2, diametro, diametro);
			g.setColor(Color.black);
			g.drawOval(nodoX[i] - diametro / 2, nodoY[i] - diametro / 2, diametro, diametro);
		}
	}

	public static void main(String[] args) {
		GeraGrafoAleatorio instancia = new GeraGrafoAleatorio();
		instancia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}