package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MazeDrawer extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int GRID_SIZE_X = 36; // Grid width
	private final int GRID_SIZE_Y = 24; // Grid height
	private final int MAX_RES_X = 1500;
	private final int MAX_RES_Y = 1050; // Increased to accommodate button
	private int cellSize;
	private int startX, startY; // Top-left corner of the grid
	
	// Edge representation: connects two cells
	private static class Edge {
		int from, to; // Cell indices
		double weight; // Random weight for Kruskal's algorithm
		
		Edge(int from, int to, double weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}
	
	// Union-Find data structure for Kruskal's algorithm
	private class UnionFind {
		private int[] parent;
		private int[] rank;
		
		UnionFind(int size) {
			parent = new int[size];
			rank = new int[size];
			for (int i = 0; i < size; i++) {
				parent[i] = i;
				rank[i] = 0;
			}
		}
		
		int find(int x) {
			if (parent[x] != x) {
				parent[x] = find(parent[x]); // Path compression
			}
			return parent[x];
		}
		
		boolean union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			
			if (rootX == rootY) {
				return false; // Already in same set
			}
			
			// Union by rank
			if (rank[rootX] < rank[rootY]) {
				parent[rootX] = rootY;
			} else if (rank[rootX] > rank[rootY]) {
				parent[rootY] = rootX;
			} else {
				parent[rootY] = rootX;
				rank[rootX]++;
			}
			return true;
		}
	}
	
	private List<Edge> mazeEdges; // Edges that form the maze
	private boolean showRedPath = false; // Toggle flag for red path visibility
	private JButton toggleButton;
	private int longestPathStart = -1; // Start vertex of longest path
	private int longestPathEnd = -1; // End vertex of longest path
	
	public MazeDrawer() {
		super("Maze Drawer - Kruskal Algorithm");
		getContentPane().setBackground(Color.WHITE);
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		
		setVisible(true);
		
		// Calculate cell size to fit grid in window with margins
		// Account for button space at the top
		int buttonHeight = 50; // Space reserved for button
		int margin = 50;
		int availableWidth = MAX_RES_X - 2 * margin;
		int availableHeight = MAX_RES_Y - 2 * margin - buttonHeight; // Subtract button space
		
		// Calculate optimal cell size to best fit the rectangular maze
		// Use the minimum to ensure both dimensions fit, maximizing the cell size
		cellSize = Math.min(availableWidth / GRID_SIZE_X, availableHeight / GRID_SIZE_Y);
		
		// Center the grid (vertically accounting for button space)
		int gridWidth = GRID_SIZE_X * cellSize;
		int gridHeight = GRID_SIZE_Y * cellSize;
		startX = (MAX_RES_X - gridWidth) / 2;
		startY = (MAX_RES_Y - gridHeight) / 2 + buttonHeight / 2; // Shift down to account for button
		
		// Create toggle button - position it at the top center, above the maze
		toggleButton = new JButton("Show Longest Path");
		int buttonWidth = 180;
		int buttonX = (MAX_RES_X - buttonWidth) / 2; // Center horizontally
		int buttonY = 10; // Top of window
		toggleButton.setBounds(buttonX, buttonY, buttonWidth, 35);
		toggleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showRedPath = !showRedPath;
				toggleButton.setText(showRedPath ? "Hide Longest Path" : "Show Longest Path");
				repaint();
			}
		});
		getContentPane().setLayout(null); // Use null layout for absolute positioning
		getContentPane().add(toggleButton);
		
		// Generate maze using Kruskal's algorithm
		generateMaze();
	}
	
	/**
	 * Generates a maze using Kruskal's algorithm to create a minimal spanning tree.
	 * This ensures all cells are connected with the minimum number of edges (N-1 for N vertices),
	 * resulting in no cycles (closed corridors).
	 */
	private void generateMaze() {
		mazeEdges = new ArrayList<>();
		
		// Create all possible edges between adjacent cells
		List<Edge> allEdges = new ArrayList<>();
		
		for (int row = 0; row < GRID_SIZE_Y; row++) {
			for (int col = 0; col < GRID_SIZE_X; col++) {
				int cellIndex = row * GRID_SIZE_X + col;
				
				// Right neighbor
				if (col < GRID_SIZE_X - 1) {
					int rightIndex = row * GRID_SIZE_X + (col + 1);
					allEdges.add(new Edge(cellIndex, rightIndex, Math.random()));
				}
				
				// Bottom neighbor
				if (row < GRID_SIZE_Y - 1) {
					int bottomIndex = (row + 1) * GRID_SIZE_X + col;
					allEdges.add(new Edge(cellIndex, bottomIndex, Math.random()));
				}
			}
		}
		
		// Sort edges by random weight
		Collections.sort(allEdges, (a, b) -> Double.compare(a.weight, b.weight));
		
		// Kruskal's algorithm: add edges that don't create cycles
		// For a spanning tree, we need exactly (N-1) edges for N vertices
		int totalVertices = GRID_SIZE_X * GRID_SIZE_Y;
		int requiredEdges = totalVertices - 1;
		UnionFind uf = new UnionFind(totalVertices);
		
		for (Edge edge : allEdges) {
			// Early termination: we have enough edges for a spanning tree
			if (mazeEdges.size() >= requiredEdges) {
				break;
			}
			
			// Only add edge if it doesn't create a cycle
			if (uf.union(edge.from, edge.to)) {
				mazeEdges.add(edge);
			}
		}
		
		// Reduce dead ends by adding some additional edges
		// Only adds edges that don't create cycles (ensures no closed paths)
		reduceDeadEnds(uf, allEdges);
	}
	
	/**
	 * Reduces dead ends by adding edges that connect dead ends to nearby vertices.
	 * Only adds edges that don't create cycles (checked using Union-Find).
	 */
	private void reduceDeadEnds(UnionFind uf, List<Edge> allEdges) {
		// Calculate degree of each vertex
		int[] degree = new int[GRID_SIZE_X * GRID_SIZE_Y];
		for (Edge edge : mazeEdges) {
			degree[edge.from]++;
			degree[edge.to]++;
		}
		
		// Find dead ends (vertices with degree 1)
		List<Integer> deadEnds = new ArrayList<>();
		for (int i = 0; i < degree.length; i++) {
			if (degree[i] == 1) {
				deadEnds.add(i);
			}
		}
		
		// Shuffle dead ends to process them randomly
		Collections.shuffle(deadEnds);
		
		// Try to connect some dead ends to nearby vertices
		// Limit to about 30% of dead ends
		int maxConnections = Math.max(1, deadEnds.size() / 3);
		int connectionsAdded = 0;
		
		for (int deadEnd : deadEnds) {
			if (connectionsAdded >= maxConnections) {
				break;
			}
			
			int[] deadEndPos = indexToRowCol(deadEnd);
			
			// Find nearby vertices (within 2 cells) that could be connected
			List<Edge> candidateEdges = new ArrayList<>();
			for (Edge edge : allEdges) {
				// Check if this edge involves the dead end
				if (edge.from == deadEnd || edge.to == deadEnd) {
					// Check if edge is not already in maze
					boolean alreadyExists = false;
					for (Edge existing : mazeEdges) {
						if ((existing.from == edge.from && existing.to == edge.to) ||
						    (existing.from == edge.to && existing.to == edge.from)) {
							alreadyExists = true;
							break;
						}
					}
					
					if (!alreadyExists) {
						// Check distance - prefer closer connections
						int otherVertex = (edge.from == deadEnd) ? edge.to : edge.from;
						int[] otherPos = indexToRowCol(otherVertex);
						int distance = Math.abs(deadEndPos[0] - otherPos[0]) + 
						               Math.abs(deadEndPos[1] - otherPos[1]);
						
						if (distance <= 2) {
							candidateEdges.add(edge);
						}
					}
				}
			}
			
			// Sort candidates by distance (prefer closer)
			candidateEdges.sort((a, b) -> {
				int otherA = (a.from == deadEnd) ? a.to : a.from;
				int otherB = (b.from == deadEnd) ? b.to : b.from;
				int[] posA = indexToRowCol(otherA);
				int[] posB = indexToRowCol(otherB);
				int distA = Math.abs(deadEndPos[0] - posA[0]) + Math.abs(deadEndPos[1] - posA[1]);
				int distB = Math.abs(deadEndPos[0] - posB[0]) + Math.abs(deadEndPos[1] - posB[1]);
				return Integer.compare(distA, distB);
			});
			
			// Try to add the closest candidate edge that doesn't create a cycle
			for (Edge candidate : candidateEdges) {
				// Only add edge if it doesn't create a cycle (vertices are in different sets)
				if (uf.union(candidate.from, candidate.to)) {
					mazeEdges.add(candidate);
					connectionsAdded++;
					break; // Only add one edge per dead end
				}
			}
		}
	}
	
	/**
	 * Converts cell index to row and column
	 */
	private int[] indexToRowCol(int index) {
		int row = index / GRID_SIZE_X;
		int col = index % GRID_SIZE_X;
		return new int[] { row, col };
	}
	
	/**
	 * Converts row and column to screen coordinates (center of cell)
	 */
	private int[] rowColToScreen(int row, int col) {
		int x = startX + col * cellSize + cellSize / 2;
		int y = startY + row * cellSize + cellSize / 2;
		return new int[] { x, y };
	}
	
	/**
	 * Gets the screen coordinates of a grey dot at a square center (row, col in square grid)
	 */
	private int[] getGreyDotCenter(int squareRow, int squareCol) {
		int[] topLeft = rowColToScreen(squareRow, squareCol);
		int[] topRight = rowColToScreen(squareRow, squareCol + 1);
		int[] bottomLeft = rowColToScreen(squareRow + 1, squareCol);
		int[] bottomRight = rowColToScreen(squareRow + 1, squareCol + 1);
		int centerX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4;
		int centerY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4;
		return new int[] { centerX, centerY };
	}
	
	/**
	 * Calculates the longest path in the maze (tree diameter) and stores start/end vertices.
	 * Uses BFS twice: first to find one end of the diameter, then to find the other end.
	 */
	private void calculateLongestPath() {
		int totalVertices = GRID_SIZE_X * GRID_SIZE_Y;
		
		// Build adjacency list from maze edges
		java.util.List<java.util.List<Integer>> adjList = new ArrayList<>();
		for (int i = 0; i < totalVertices; i++) {
			adjList.add(new ArrayList<>());
		}
		for (Edge edge : mazeEdges) {
			adjList.get(edge.from).add(edge.to);
			adjList.get(edge.to).add(edge.from);
		}
		
		// Step 1: BFS from vertex 0 to find the farthest vertex
		int[] firstBFS = bfs(0, adjList, totalVertices);
		int farthestVertex1 = firstBFS[0];
		
		// Step 2: BFS from the farthest vertex to find the other end of the diameter
		int[] secondBFS = bfs(farthestVertex1, adjList, totalVertices);
		int farthestVertex2 = secondBFS[0];
		
		// Step 3: Reconstruct the path between the two farthest vertices
		java.util.List<Integer> longestPath = reconstructPath(farthestVertex1, farthestVertex2, adjList, totalVertices);
		
		// Store start and end vertices for blue highlighting
		if (longestPath.size() > 0) {
			longestPathStart = longestPath.get(0);
			longestPathEnd = longestPath.get(longestPath.size() - 1);
		}
	}
	
	/**
	 * Finds and draws the longest path in the maze (tree diameter).
	 * Uses BFS twice: first to find one end of the diameter, then to find the other end.
	 */
	private void drawLongestPath(java.awt.Graphics2D g2d) {
		int totalVertices = GRID_SIZE_X * GRID_SIZE_Y;
		
		// Build adjacency list from maze edges
		java.util.List<java.util.List<Integer>> adjList = new ArrayList<>();
		for (int i = 0; i < totalVertices; i++) {
			adjList.add(new ArrayList<>());
		}
		for (Edge edge : mazeEdges) {
			adjList.get(edge.from).add(edge.to);
			adjList.get(edge.to).add(edge.from);
		}
		
		// Step 1: BFS from vertex 0 to find the farthest vertex
		int[] firstBFS = bfs(0, adjList, totalVertices);
		int farthestVertex1 = firstBFS[0];
		
		// Step 2: BFS from the farthest vertex to find the other end of the diameter
		int[] secondBFS = bfs(farthestVertex1, adjList, totalVertices);
		int farthestVertex2 = secondBFS[0];
		
		// Step 3: Reconstruct the path between the two farthest vertices
		java.util.List<Integer> longestPath = reconstructPath(farthestVertex1, farthestVertex2, adjList, totalVertices);
		
		// Step 4: Draw the path in red
		if (longestPath.size() > 1) {
			g2d.setColor(Color.RED);
			java.awt.Stroke oldRedStroke = g2d.getStroke();
			g2d.setStroke(new java.awt.BasicStroke(3.0f));
			
			for (int i = 0; i < longestPath.size() - 1; i++) {
				int from = longestPath.get(i);
				int to = longestPath.get(i + 1);
				
				int[] fromCoords = indexToRowCol(from);
				int[] toCoords = indexToRowCol(to);
				
				int[] fromScreen = rowColToScreen(fromCoords[0], fromCoords[1]);
				int[] toScreen = rowColToScreen(toCoords[0], toCoords[1]);
				
				g2d.drawLine(fromScreen[0], fromScreen[1], toScreen[0], toScreen[1]);
			}
			
			g2d.setStroke(oldRedStroke);
		}
	}
	
	/**
	 * Performs BFS from a starting vertex and returns [farthestVertex, maxDistance]
	 */
	private int[] bfs(int start, java.util.List<java.util.List<Integer>> adjList, int totalVertices) {
		boolean[] visited = new boolean[totalVertices];
		int[] distance = new int[totalVertices];
		java.util.Queue<Integer> queue = new java.util.LinkedList<>();
		
		queue.offer(start);
		visited[start] = true;
		distance[start] = 0;
		
		int farthestVertex = start;
		int maxDistance = 0;
		
		while (!queue.isEmpty()) {
			int current = queue.poll();
			
			for (int neighbor : adjList.get(current)) {
				if (!visited[neighbor]) {
					visited[neighbor] = true;
					distance[neighbor] = distance[current] + 1;
					queue.offer(neighbor);
					
					if (distance[neighbor] > maxDistance) {
						maxDistance = distance[neighbor];
						farthestVertex = neighbor;
					}
				}
			}
		}
		
		return new int[] { farthestVertex, maxDistance };
	}
	
	/**
	 * Reconstructs the path between two vertices using BFS
	 */
	private java.util.List<Integer> reconstructPath(int start, int end, 
	                                                java.util.List<java.util.List<Integer>> adjList, 
	                                                int totalVertices) {
		boolean[] visited = new boolean[totalVertices];
		int[] parent = new int[totalVertices];
		java.util.Queue<Integer> queue = new java.util.LinkedList<>();
		
		queue.offer(start);
		visited[start] = true;
		parent[start] = -1;
		
		while (!queue.isEmpty()) {
			int current = queue.poll();
			
			if (current == end) {
				// Reconstruct path
				java.util.List<Integer> path = new ArrayList<>();
				int node = end;
				while (node != -1) {
					path.add(0, node);
					node = parent[node];
				}
				return path;
			}
			
			for (int neighbor : adjList.get(current)) {
				if (!visited[neighbor]) {
					visited[neighbor] = true;
					parent[neighbor] = current;
					queue.offer(neighbor);
				}
			}
		}
		
		return new ArrayList<>(); // No path found (shouldn't happen in a connected tree)
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		// Draw white background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
		
		// Draw start and end vertices of longest path in blue (always visible)
		// Calculate longest path if not already calculated
		if (longestPathStart < 0 || longestPathEnd < 0) {
			calculateLongestPath();
		}
		
		if (longestPathStart >= 0 && longestPathEnd >= 0) {
			g.setColor(Color.BLUE);
			int blueDotRadius = 5; // Slightly larger to make them stand out
			
			// Draw start vertex
			int[] startCoords = indexToRowCol(longestPathStart);
			int[] startScreen = rowColToScreen(startCoords[0], startCoords[1]);
			g.fillOval(startScreen[0] - blueDotRadius, startScreen[1] - blueDotRadius,
			           2 * blueDotRadius, 2 * blueDotRadius);
			
			// Draw end vertex
			int[] endCoords = indexToRowCol(longestPathEnd);
			int[] endScreen = rowColToScreen(endCoords[0], endCoords[1]);
			g.fillOval(endScreen[0] - blueDotRadius, endScreen[1] - blueDotRadius,
			           2 * blueDotRadius, 2 * blueDotRadius);
		}
		
		// Draw grey dots at the center of each square formed by four vertices
		g.setColor(Color.GRAY);
		int centerDotRadius = 2;
		for (int row = 0; row < GRID_SIZE_Y - 1; row++) {
			for (int col = 0; col < GRID_SIZE_X - 1; col++) {
				// Calculate center of square formed by vertices at:
				// (row, col), (row, col+1), (row+1, col), (row+1, col+1)
				int[] topLeft = rowColToScreen(row, col);
				int[] topRight = rowColToScreen(row, col + 1);
				int[] bottomLeft = rowColToScreen(row + 1, col);
				int[] bottomRight = rowColToScreen(row + 1, col + 1);
				
				// Center is the average of the four corners
				int centerX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4;
				int centerY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4;
				
				g.fillOval(centerX - centerDotRadius, centerY - centerDotRadius,
				           2 * centerDotRadius, 2 * centerDotRadius);
			}
		}
		
		// Draw additional row of grey dots on top, bottom, left, and right
		// These are aligned with the existing grey dots (square centers), not the black vertex dots
		g.setColor(Color.GRAY);
		int perimeterDotRadius = 2;
		
		// Top row - aligned with the top row of square centers
		// Square centers are at columns 0 to GRID_SIZE_X-2, so perimeter dots should be at same columns
		for (int col = 0; col < GRID_SIZE_X - 1; col++) {
			// Get the center of the top-left square (row 0, col)
			int[] topLeft = rowColToScreen(0, col);
			int[] topRight = rowColToScreen(0, col + 1);
			int[] bottomLeft = rowColToScreen(1, col);
			int[] bottomRight = rowColToScreen(1, col + 1);
			int centerX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4;
			int centerY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4;
			// Position one cellSize above this center
			g.fillOval(centerX - perimeterDotRadius, centerY - cellSize - perimeterDotRadius,
			           2 * perimeterDotRadius, 2 * perimeterDotRadius);
		}
		
		// Bottom row - aligned with the bottom row of square centers
		for (int col = 0; col < GRID_SIZE_X - 1; col++) {
			// Get the center of a square in the bottom row (row GRID_SIZE_Y-2, col)
			int[] topLeft = rowColToScreen(GRID_SIZE_Y - 2, col);
			int[] topRight = rowColToScreen(GRID_SIZE_Y - 2, col + 1);
			int[] bottomLeft = rowColToScreen(GRID_SIZE_Y - 1, col);
			int[] bottomRight = rowColToScreen(GRID_SIZE_Y - 1, col + 1);
			int centerX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4;
			int centerY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4;
			// Position one cellSize below this center
			g.fillOval(centerX - perimeterDotRadius, centerY + cellSize - perimeterDotRadius,
			           2 * perimeterDotRadius, 2 * perimeterDotRadius);
		}
		
		// Left column - aligned with the left column of square centers
		for (int row = 0; row < GRID_SIZE_Y - 1; row++) {
			// Get the center of a square in the left column (row, col 0)
			int[] topLeft = rowColToScreen(row, 0);
			int[] topRight = rowColToScreen(row, 1);
			int[] bottomLeft = rowColToScreen(row + 1, 0);
			int[] bottomRight = rowColToScreen(row + 1, 1);
			int centerX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4;
			int centerY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4;
			// Position one cellSize to the left of this center
			g.fillOval(centerX - cellSize - perimeterDotRadius, centerY - perimeterDotRadius,
			           2 * perimeterDotRadius, 2 * perimeterDotRadius);
		}
		
		// Right column - aligned with the right column of square centers
		for (int row = 0; row < GRID_SIZE_Y - 1; row++) {
			// Get the center of a square in the right column (row, col GRID_SIZE_X-2)
			int[] topLeft = rowColToScreen(row, GRID_SIZE_X - 2);
			int[] topRight = rowColToScreen(row, GRID_SIZE_X - 1);
			int[] bottomLeft = rowColToScreen(row + 1, GRID_SIZE_X - 2);
			int[] bottomRight = rowColToScreen(row + 1, GRID_SIZE_X - 1);
			int centerX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4;
			int centerY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4;
			// Position one cellSize to the right of this center
			g.fillOval(centerX + cellSize - perimeterDotRadius, centerY - perimeterDotRadius,
			           2 * perimeterDotRadius, 2 * perimeterDotRadius);
		}
		
		// Draw corner grey dots aligned with the grey dot grid
		// Top-left corner
		int[] topLeftSquare = rowColToScreen(0, 0);
		int[] topLeftSquare2 = rowColToScreen(0, 1);
		int[] bottomLeftSquare = rowColToScreen(1, 0);
		int[] bottomLeftSquare2 = rowColToScreen(1, 1);
		int topLeftCenterX = (topLeftSquare[0] + topLeftSquare2[0] + bottomLeftSquare[0] + bottomLeftSquare2[0]) / 4;
		int topLeftCenterY = (topLeftSquare[1] + topLeftSquare2[1] + bottomLeftSquare[1] + bottomLeftSquare2[1]) / 4;
		g.fillOval(topLeftCenterX - cellSize - perimeterDotRadius, topLeftCenterY - cellSize - perimeterDotRadius,
		           2 * perimeterDotRadius, 2 * perimeterDotRadius);
		
		// Top-right corner
		int[] topRightSquare = rowColToScreen(0, GRID_SIZE_X - 2);
		int[] topRightSquare2 = rowColToScreen(0, GRID_SIZE_X - 1);
		int[] bottomRightSquare = rowColToScreen(1, GRID_SIZE_X - 2);
		int[] bottomRightSquare2 = rowColToScreen(1, GRID_SIZE_X - 1);
		int topRightCenterX = (topRightSquare[0] + topRightSquare2[0] + bottomRightSquare[0] + bottomRightSquare2[0]) / 4;
		int topRightCenterY = (topRightSquare[1] + topRightSquare2[1] + bottomRightSquare[1] + bottomRightSquare2[1]) / 4;
		g.fillOval(topRightCenterX + cellSize - perimeterDotRadius, topRightCenterY - cellSize - perimeterDotRadius,
		           2 * perimeterDotRadius, 2 * perimeterDotRadius);
		
		// Bottom-left corner
		int[] bottomLeftSquareTop = rowColToScreen(GRID_SIZE_Y - 2, 0);
		int[] bottomLeftSquareTop2 = rowColToScreen(GRID_SIZE_Y - 2, 1);
		int[] bottomLeftSquareBot = rowColToScreen(GRID_SIZE_Y - 1, 0);
		int[] bottomLeftSquareBot2 = rowColToScreen(GRID_SIZE_Y - 1, 1);
		int bottomLeftCenterX = (bottomLeftSquareTop[0] + bottomLeftSquareTop2[0] + bottomLeftSquareBot[0] + bottomLeftSquareBot2[0]) / 4;
		int bottomLeftCenterY = (bottomLeftSquareTop[1] + bottomLeftSquareTop2[1] + bottomLeftSquareBot[1] + bottomLeftSquareBot2[1]) / 4;
		g.fillOval(bottomLeftCenterX - cellSize - perimeterDotRadius, bottomLeftCenterY + cellSize - perimeterDotRadius,
		           2 * perimeterDotRadius, 2 * perimeterDotRadius);
		
		// Bottom-right corner
		int[] bottomRightSquareTop = rowColToScreen(GRID_SIZE_Y - 2, GRID_SIZE_X - 2);
		int[] bottomRightSquareTop2 = rowColToScreen(GRID_SIZE_Y - 2, GRID_SIZE_X - 1);
		int[] bottomRightSquareBot = rowColToScreen(GRID_SIZE_Y - 1, GRID_SIZE_X - 2);
		int[] bottomRightSquareBot2 = rowColToScreen(GRID_SIZE_Y - 1, GRID_SIZE_X - 1);
		int bottomRightCenterX = (bottomRightSquareTop[0] + bottomRightSquareTop2[0] + bottomRightSquareBot[0] + bottomRightSquareBot2[0]) / 4;
		int bottomRightCenterY = (bottomRightSquareTop[1] + bottomRightSquareTop2[1] + bottomRightSquareBot[1] + bottomRightSquareBot2[1]) / 4;
		g.fillOval(bottomRightCenterX + cellSize - perimeterDotRadius, bottomRightCenterY + cellSize - perimeterDotRadius,
		           2 * perimeterDotRadius, 2 * perimeterDotRadius);
		
		// Draw black edges connecting adjacent grey dots (only if they don't cross green edges)
		g.setColor(Color.BLACK);
		java.awt.Graphics2D g2dBlack = (java.awt.Graphics2D) g;
		java.awt.Stroke oldStrokeBlack = g2dBlack.getStroke();
		g2dBlack.setStroke(new java.awt.BasicStroke(1.0f));
		
		// Helper method to check if a green edge exists between two vertices
		java.util.function.BiFunction<Integer, Integer, Boolean> hasGreenEdge = (v1, v2) -> {
			for (Edge edge : mazeEdges) {
				if ((edge.from == v1 && edge.to == v2) || (edge.from == v2 && edge.to == v1)) {
					return true;
				}
			}
			return false;
		};
		
		// Connect main grid grey dots horizontally (same row, adjacent columns)
		// A horizontal black edge crosses the vertical green edge between (row, col+1) and (row+1, col+1)
		for (int row = 0; row < GRID_SIZE_Y - 1; row++) {
			for (int col = 0; col < GRID_SIZE_X - 2; col++) {
				// Check if there's a green edge that would be crossed
				int vertexTop = row * GRID_SIZE_X + (col + 1);
				int vertexBottom = (row + 1) * GRID_SIZE_X + (col + 1);
				if (!hasGreenEdge.apply(vertexTop, vertexBottom)) {
					int[] leftDot = getGreyDotCenter(row, col);
					int[] rightDot = getGreyDotCenter(row, col + 1);
					g2dBlack.drawLine(leftDot[0], leftDot[1], rightDot[0], rightDot[1]);
				}
			}
		}
		
		// Connect main grid grey dots vertically (same column, adjacent rows)
		// A vertical black edge crosses the horizontal green edge between (row+1, col) and (row+1, col+1)
		for (int row = 0; row < GRID_SIZE_Y - 2; row++) {
			for (int col = 0; col < GRID_SIZE_X - 1; col++) {
				// Check if there's a green edge that would be crossed
				int vertexLeft = (row + 1) * GRID_SIZE_X + col;
				int vertexRight = (row + 1) * GRID_SIZE_X + (col + 1);
				if (!hasGreenEdge.apply(vertexLeft, vertexRight)) {
					int[] topDot = getGreyDotCenter(row, col);
					int[] bottomDot = getGreyDotCenter(row + 1, col);
					g2dBlack.drawLine(topDot[0], topDot[1], bottomDot[0], bottomDot[1]);
				}
			}
		}
		
		// Connect top row perimeter dots horizontally (all adjacent outside grey dots)
		for (int col = 0; col < GRID_SIZE_X - 2; col++) {
			int[] topLeft = rowColToScreen(0, col);
			int[] topRight = rowColToScreen(0, col + 1);
			int[] bottomLeft = rowColToScreen(1, col);
			int[] bottomRight = rowColToScreen(1, col + 1);
			int leftX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4;
			int leftY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4 - cellSize;
			
			int[] topLeft2 = rowColToScreen(0, col + 1);
			int[] topRight2 = rowColToScreen(0, col + 2);
			int[] bottomLeft2 = rowColToScreen(1, col + 1);
			int[] bottomRight2 = rowColToScreen(1, col + 2);
			int rightX = (topLeft2[0] + topRight2[0] + bottomLeft2[0] + bottomRight2[0]) / 4;
			int rightY = (topLeft2[1] + topRight2[1] + bottomLeft2[1] + bottomRight2[1]) / 4 - cellSize;
			
			g2dBlack.drawLine(leftX, leftY, rightX, rightY);
		}
		
		// Connect top row perimeter dots to main grid below
		// A vertical black edge crosses the horizontal green edge between (0, col) and (0, col+1)
		for (int col = 0; col < GRID_SIZE_X - 1; col++) {
			int vertexLeft = 0 * GRID_SIZE_X + col;
			int vertexRight = 0 * GRID_SIZE_X + (col + 1);
			if (!hasGreenEdge.apply(vertexLeft, vertexRight)) {
				int[] topDot = getGreyDotCenter(0, col);
				int topPerimeterX = topDot[0];
				int topPerimeterY = topDot[1] - cellSize;
				g2dBlack.drawLine(topPerimeterX, topPerimeterY, topDot[0], topDot[1]);
			}
		}
		
		// Connect bottom row perimeter dots horizontally (all adjacent outside grey dots)
		for (int col = 0; col < GRID_SIZE_X - 2; col++) {
			int[] topLeft = rowColToScreen(GRID_SIZE_Y - 2, col);
			int[] topRight = rowColToScreen(GRID_SIZE_Y - 2, col + 1);
			int[] bottomLeft = rowColToScreen(GRID_SIZE_Y - 1, col);
			int[] bottomRight = rowColToScreen(GRID_SIZE_Y - 1, col + 1);
			int leftX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4;
			int leftY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4 + cellSize;
			
			int[] topLeft2 = rowColToScreen(GRID_SIZE_Y - 2, col + 1);
			int[] topRight2 = rowColToScreen(GRID_SIZE_Y - 2, col + 2);
			int[] bottomLeft2 = rowColToScreen(GRID_SIZE_Y - 1, col + 1);
			int[] bottomRight2 = rowColToScreen(GRID_SIZE_Y - 1, col + 2);
			int rightX = (topLeft2[0] + topRight2[0] + bottomLeft2[0] + bottomRight2[0]) / 4;
			int rightY = (topLeft2[1] + topRight2[1] + bottomLeft2[1] + bottomRight2[1]) / 4 + cellSize;
			
			g2dBlack.drawLine(leftX, leftY, rightX, rightY);
		}
		
		// Connect bottom row perimeter dots to main grid above
		// A vertical black edge crosses the horizontal green edge between (GRID_SIZE_Y-1, col) and (GRID_SIZE_Y-1, col+1)
		for (int col = 0; col < GRID_SIZE_X - 1; col++) {
			int vertexLeft = (GRID_SIZE_Y - 1) * GRID_SIZE_X + col;
			int vertexRight = (GRID_SIZE_Y - 1) * GRID_SIZE_X + (col + 1);
			if (!hasGreenEdge.apply(vertexLeft, vertexRight)) {
				int[] bottomDot = getGreyDotCenter(GRID_SIZE_Y - 2, col);
				int bottomPerimeterX = bottomDot[0];
				int bottomPerimeterY = bottomDot[1] + cellSize;
				g2dBlack.drawLine(bottomPerimeterX, bottomPerimeterY, bottomDot[0], bottomDot[1]);
			}
		}
		
		// Connect left column perimeter dots vertically (all adjacent outside grey dots)
		for (int row = 0; row < GRID_SIZE_Y - 2; row++) {
			int[] topLeft = rowColToScreen(row, 0);
			int[] topRight = rowColToScreen(row, 1);
			int[] bottomLeft = rowColToScreen(row + 1, 0);
			int[] bottomRight = rowColToScreen(row + 1, 1);
			int topX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4 - cellSize;
			int topY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4;
			
			int[] topLeft2 = rowColToScreen(row + 1, 0);
			int[] topRight2 = rowColToScreen(row + 1, 1);
			int[] bottomLeft2 = rowColToScreen(row + 2, 0);
			int[] bottomRight2 = rowColToScreen(row + 2, 1);
			int bottomX = (topLeft2[0] + topRight2[0] + bottomLeft2[0] + bottomRight2[0]) / 4 - cellSize;
			int bottomY = (topLeft2[1] + topRight2[1] + bottomLeft2[1] + bottomRight2[1]) / 4;
			
			g2dBlack.drawLine(topX, topY, bottomX, bottomY);
		}
		
		// Connect left column perimeter dots to main grid to the right
		// A horizontal black edge crosses the vertical green edge between (row, 0) and (row+1, 0)
		for (int row = 0; row < GRID_SIZE_Y - 1; row++) {
			int vertexTop = row * GRID_SIZE_X + 0;
			int vertexBottom = (row + 1) * GRID_SIZE_X + 0;
			if (!hasGreenEdge.apply(vertexTop, vertexBottom)) {
				int[] leftDot = getGreyDotCenter(row, 0);
				int leftPerimeterX = leftDot[0] - cellSize;
				int leftPerimeterY = leftDot[1];
				g2dBlack.drawLine(leftPerimeterX, leftPerimeterY, leftDot[0], leftDot[1]);
			}
		}
		
		// Connect right column perimeter dots vertically (all adjacent outside grey dots)
		for (int row = 0; row < GRID_SIZE_Y - 2; row++) {
			int[] topLeft = rowColToScreen(row, GRID_SIZE_X - 2);
			int[] topRight = rowColToScreen(row, GRID_SIZE_X - 1);
			int[] bottomLeft = rowColToScreen(row + 1, GRID_SIZE_X - 2);
			int[] bottomRight = rowColToScreen(row + 1, GRID_SIZE_X - 1);
			int topX = (topLeft[0] + topRight[0] + bottomLeft[0] + bottomRight[0]) / 4 + cellSize;
			int topY = (topLeft[1] + topRight[1] + bottomLeft[1] + bottomRight[1]) / 4;
			
			int[] topLeft2 = rowColToScreen(row + 1, GRID_SIZE_X - 2);
			int[] topRight2 = rowColToScreen(row + 1, GRID_SIZE_X - 1);
			int[] bottomLeft2 = rowColToScreen(row + 2, GRID_SIZE_X - 2);
			int[] bottomRight2 = rowColToScreen(row + 2, GRID_SIZE_X - 1);
			int bottomX = (topLeft2[0] + topRight2[0] + bottomLeft2[0] + bottomRight2[0]) / 4 + cellSize;
			int bottomY = (topLeft2[1] + topRight2[1] + bottomLeft2[1] + bottomRight2[1]) / 4;
			
			g2dBlack.drawLine(topX, topY, bottomX, bottomY);
		}
		
		// Connect right column perimeter dots to main grid to the left
		// A horizontal black edge crosses the vertical green edge between (row, GRID_SIZE_X-1) and (row+1, GRID_SIZE_X-1)
		for (int row = 0; row < GRID_SIZE_Y - 1; row++) {
			int vertexTop = row * GRID_SIZE_X + (GRID_SIZE_X - 1);
			int vertexBottom = (row + 1) * GRID_SIZE_X + (GRID_SIZE_X - 1);
			if (!hasGreenEdge.apply(vertexTop, vertexBottom)) {
				int[] rightDot = getGreyDotCenter(row, GRID_SIZE_X - 2);
				int rightPerimeterX = rightDot[0] + cellSize;
				int rightPerimeterY = rightDot[1];
				g2dBlack.drawLine(rightPerimeterX, rightPerimeterY, rightDot[0], rightDot[1]);
			}
		}
		
		// Connect corner dots to adjacent outside grey dots (all connections)
		// Top-left corner to top row (col 0) and left column (row 0)
		int[] topLeftCorner = new int[] { topLeftCenterX - cellSize, topLeftCenterY - cellSize };
		int[] topRowDot0 = getGreyDotCenter(0, 0);
		int[] leftColDot0 = getGreyDotCenter(0, 0);
		g2dBlack.drawLine(topLeftCorner[0], topLeftCorner[1], topRowDot0[0], topRowDot0[1] - cellSize);
		g2dBlack.drawLine(topLeftCorner[0], topLeftCorner[1], leftColDot0[0] - cellSize, leftColDot0[1]);
		
		// Top-right corner to top row (col GRID_SIZE_X-2) and right column (row 0)
		int[] topRightCorner = new int[] { topRightCenterX + cellSize, topRightCenterY - cellSize };
		int[] topRowDotLast = getGreyDotCenter(0, GRID_SIZE_X - 2);
		int[] rightColDot0 = getGreyDotCenter(0, GRID_SIZE_X - 2);
		g2dBlack.drawLine(topRightCorner[0], topRightCorner[1], topRowDotLast[0], topRowDotLast[1] - cellSize);
		g2dBlack.drawLine(topRightCorner[0], topRightCorner[1], rightColDot0[0] + cellSize, rightColDot0[1]);
		
		// Bottom-left corner to bottom row (col 0) and left column (row GRID_SIZE_Y-2)
		int[] bottomLeftCorner = new int[] { bottomLeftCenterX - cellSize, bottomLeftCenterY + cellSize };
		int[] bottomRowDot0 = getGreyDotCenter(GRID_SIZE_Y - 2, 0);
		int[] leftColDotLast = getGreyDotCenter(GRID_SIZE_Y - 2, 0);
		g2dBlack.drawLine(bottomLeftCorner[0], bottomLeftCorner[1], bottomRowDot0[0], bottomRowDot0[1] + cellSize);
		g2dBlack.drawLine(bottomLeftCorner[0], bottomLeftCorner[1], leftColDotLast[0] - cellSize, leftColDotLast[1]);
		
		// Bottom-right corner to bottom row (col GRID_SIZE_X-2) and right column (row GRID_SIZE_Y-2)
		int[] bottomRightCorner = new int[] { bottomRightCenterX + cellSize, bottomRightCenterY + cellSize };
		int[] bottomRowDotLast = getGreyDotCenter(GRID_SIZE_Y - 2, GRID_SIZE_X - 2);
		int[] rightColDotLast = getGreyDotCenter(GRID_SIZE_Y - 2, GRID_SIZE_X - 2);
		g2dBlack.drawLine(bottomRightCorner[0], bottomRightCorner[1], bottomRowDotLast[0], bottomRowDotLast[1] + cellSize);
		g2dBlack.drawLine(bottomRightCorner[0], bottomRightCorner[1], rightColDotLast[0] + cellSize, rightColDotLast[1]);
		
		g2dBlack.setStroke(oldStrokeBlack);
		
		// Draw maze edges (connections between dots) - HIDDEN
		// Green edges are now hidden as requested
		
		// Find and draw the longest path between two distant vertices (if toggle is on)
		java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
		if (showRedPath) {
			drawLongestPath(g2d);
		}
	}
	
	public static void main(String[] args) {
		MazeDrawer maze = new MazeDrawer();
		maze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
