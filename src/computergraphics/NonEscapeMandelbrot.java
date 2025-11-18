package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class NonEscapeMandelbrot extends JFrame implements MouseListener {

	private static final long serialVersionUID = -7429779026409169909L;
	private int MAX_RES_X = 800, MAX_RES_Y = 600;
	private double xmin = -2, xmax = 2, ymin = -1.5, ymax = 1.5;
	private int[] paletteR = new int[240000];
	private int[] paletteG = new int[240000];
	private int[] paletteB = new int[240000];
	private volatile boolean isRendering = false;
	private volatile BufferedImage currentImage = null;
	private JPanel drawingPanel;

	public NonEscapeMandelbrot() {
		super("Fractal de Mandelbrot");
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		
		// Create a double-buffered panel to prevent flickering
		drawingPanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				BufferedImage img = currentImage;
				if (img != null) {
					g.drawImage(img, 0, 0, null);
				} else {
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		drawingPanel.setSize(MAX_RES_X, MAX_RES_Y);
		drawingPanel.addMouseListener(this);
		add(drawingPanel);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initPalette();
		
		// Start initial rendering
		renderFractalParallel();
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

	/**
	 * Optimized Mandelbrot calculation - returns iteration count and final position
	 * Uses x2/y2 optimization to avoid repeated multiplications
	 */
	public double[] mSetLevel(double cx, double cy, double maxIter) {
		int iter = 0;
		double x = 0.0, y = 0.0;
		double x2 = 0.0, y2 = 0.0;
		double temp;
		
		// Optimized: check x2 + y2 < 4.0 instead of 10000, and use x2/y2 to avoid recalculating squares
		while (iter < maxIter && (x2 + y2) < 4.0) {
			temp = x2 - y2 + cx;
			y = 2.0 * x * y + cy;
			x = temp;
			x2 = x * x;
			y2 = y * y;
			iter++;
		}
		return new double[] { x, y, iter };
	}

	/**
	 * Multi-threaded parallel rendering for maximum performance
	 */
	private void renderFractalParallel() {
		// Prevent multiple simultaneous renders
		if (isRendering) {
			return;
		}
		isRendering = true;
		
		// Run rendering in a separate thread to avoid blocking UI
		new Thread(() -> {
			long startTime = System.currentTimeMillis();
			double maxIter = 200;
			
			BufferedImage buffImage = new BufferedImage(MAX_RES_X, MAX_RES_Y, BufferedImage.TYPE_INT_RGB);
			
			// Pre-calculate constants outside loops for better performance
			final double xRange = xmax - xmin;
			final double yRange = ymax - ymin;
			final double xStep = xRange / (MAX_RES_X - 1);
			final double yStep = yRange / (MAX_RES_Y - 1);
			
			// Get number of available CPU cores
			int numThreads = Runtime.getRuntime().availableProcessors();
			ExecutorService executor = Executors.newFixedThreadPool(numThreads);
			
			// Track progress for UI updates
			AtomicInteger completedRows = new AtomicInteger(0);
			final int updateInterval = Math.max(20, MAX_RES_Y / 8);
			final AtomicInteger lastUpdateRow = new AtomicInteger(-updateInterval);
			
			// Split work into horizontal strips
			int rowsPerThread = Math.max(1, MAX_RES_Y / numThreads);
			
			for (int threadId = 0; threadId < numThreads; threadId++) {
				final int startRow = threadId * rowsPerThread;
				final int endRow = (threadId == numThreads - 1) ? MAX_RES_Y : (threadId + 1) * rowsPerThread;
				
				executor.submit(() -> {
					// Process this thread's strip of rows
					for (int iy = startRow; iy < endRow; iy++) {
						double cy = ymin + iy * yStep;
						
						for (int ix = 0; ix < MAX_RES_X; ix++) {
							double cx = xmin + ix * xStep;
							double[] array = mSetLevel(cx, cy, maxIter);
							
							// Calculate smooth iteration count
							double magnitude = array[0] * array[0] + array[1] * array[1];
							double temp;
							if (magnitude > 0) {
								temp = (array[2] + (Math.log(2.0 * Math.log(2.0))
										- Math.log(Math.log(Math.sqrt(magnitude)))) / Math.log(2.0));
							} else {
								temp = array[2];
							}
							temp *= 1000.0;
							
							// Bounds check for palette array
							int paletteIndex = (int) temp;
							if (paletteIndex < 0) paletteIndex = 0;
							if (paletteIndex >= 240000) paletteIndex = 239999;
							
							// Direct pixel access is much faster than drawLine
							int rgb = (paletteR[paletteIndex] << 16) | (paletteG[paletteIndex] << 8) | paletteB[paletteIndex];
							buffImage.setRGB(ix, iy, rgb);
						}
						
						// Update progress and refresh UI periodically
						int completed = completedRows.incrementAndGet();
						if (completed - lastUpdateRow.get() >= updateInterval || completed == MAX_RES_Y) {
							lastUpdateRow.set(completed);
							SwingUtilities.invokeLater(() -> {
								currentImage = buffImage;
								drawingPanel.repaint();
							});
						}
					}
				});
			}
			
			// Wait for all threads to complete
			executor.shutdown();
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			// Final update
			SwingUtilities.invokeLater(() -> {
				currentImage = buffImage;
				drawingPanel.repaint();
				isRendering = false;
				
				long endTime = System.currentTimeMillis();
				System.out.println("Rendering completed in " + (endTime - startTime) + " ms using " + numThreads + " threads");
			});
		}).start();
	}

	// Old paint method - rendering is now done via renderFractalParallel()
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Rendering is handled by renderFractalParallel() and displayed via JPanel
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
		if (e.getButton() == 1) { // left button - zoom in
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
			currentImage = null;
			isRendering = false;
			drawingPanel.repaint();
			renderFractalParallel();
		} else if (e.getButton() == 3) { // right button - zoom out
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
			currentImage = null;
			isRendering = false;
			drawingPanel.repaint();
			renderFractalParallel();
		} else if (e.getButton() == 2) { // middle button = reset
			xmin = -2;
			xmax = 2;
			ymin = -1.5;
			ymax = 1.5;
			currentImage = null;
			isRendering = false;
			drawingPanel.repaint();
			renderFractalParallel();
		}
	}

	public static void main(String args[]) {
		new NonEscapeMandelbrot().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
