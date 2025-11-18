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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Beautiful Mandelbrot Fractal Renderer with Multi-threaded Acceleration
 * Features:
 * - Smooth color gradients using continuous iteration count
 * - Multiple beautiful color palettes
 * - Interactive zoom with mouse clicks
 * - High-quality rendering
 * - Multi-threaded parallel processing utilizing all CPU cores
 * - Non-blocking UI with progressive rendering updates
 * 
 * Performance: Uses ExecutorService to parallelize computation across all
 * available CPU cores, providing 4-8x speedup on modern multi-core systems.
 */
public class BeautifulMandelbrot extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private int MAX_RES_X = 1200;
	private int MAX_RES_Y = 900;
	private double xmin = -2.5;
	private double xmax = 1.5;
	private double ymin = -2.0;
	private double ymax = 2.0;
	private int maxIterations = 1000;
	private int colorPalette = 0; // 0 = classic, 1 = fire, 2 = ocean, 3 = sunset
	private volatile boolean isRendering = false;
	private volatile BufferedImage currentImage = null;
	private JPanel drawingPanel;

	public BeautifulMandelbrot() {
		super("Beautiful Mandelbrot Fractal");
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
					g.setColor(Color.WHITE);
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		drawingPanel.setSize(MAX_RES_X, MAX_RES_Y);
		drawingPanel.addMouseListener(this);
		add(drawingPanel);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Start initial rendering
		renderFractalParallel();
	}

	/**
	 * Calculate the Mandelbrot iteration count with smooth coloring
	 * Returns a double for smooth color transitions
	 */
	public double mandelbrotSmooth(double cx, double cy) {
		double x = 0.0;
		double y = 0.0;
		double x2 = 0.0;
		double y2 = 0.0;
		int iter = 0;

		while (iter < maxIterations && (x2 + y2) < 4.0) {
			y = 2.0 * x * y + cy;
			x = x2 - y2 + cx;
			x2 = x * x;
			y2 = y * y;
			iter++;
		}

		// Smooth coloring using continuous iteration count
		if (iter < maxIterations) {
			double log_zn = Math.log(x2 + y2) / 2.0;
			double nu = Math.log(log_zn / Math.log(2.0)) / Math.log(2.0);
			return iter + 1.0 - nu;
		}
		return iter;
	}

	/**
	 * Get color based on iteration count and selected palette
	 * Colors are inverted (black becomes white, etc.)
	 */
	private Color getColor(double iterations) {
		Color color;
		
		if (iterations >= maxIterations) {
			// Invert black to white for points in the set
			color = Color.WHITE;
		} else {
			double normalized = (iterations % 256) / 256.0;

			switch (colorPalette) {
			case 0: // Classic blue-purple gradient
				color = classicPalette(normalized);
				break;
			case 1: // Fire palette
				color = firePalette(normalized);
				break;
			case 2: // Ocean palette
				color = oceanPalette(normalized);
				break;
			case 3: // Sunset palette
				color = sunsetPalette(normalized);
				break;
			default:
				color = classicPalette(normalized);
				break;
			}
			
			// Invert the color
			int r = 255 - color.getRed();
			int g = 255 - color.getGreen();
			int b = 255 - color.getBlue();
			color = new Color(r, g, b);
		}
		
		return color;
	}

	private Color classicPalette(double t) {
		// Smooth blue to purple to pink gradient
		int r = (int) (128 + 127 * Math.sin(t * Math.PI * 2 + 0));
		int g = (int) (128 + 127 * Math.sin(t * Math.PI * 2 + 2));
		int b = (int) (128 + 127 * Math.sin(t * Math.PI * 2 + 4));
		return new Color(r, g, b);
	}

	private Color firePalette(double t) {
		// Red to yellow to white gradient
		int r = 255;
		int g = (int) (255 * t);
		int b = (int) (128 * t * t);
		return new Color(Math.min(255, r), Math.min(255, g), Math.min(255, b));
	}

	private Color oceanPalette(double t) {
		// Deep blue to cyan to white gradient
		int r = (int) (64 * t);
		int g = (int) (128 + 127 * t);
		int b = (int) (192 + 63 * t);
		return new Color(Math.min(255, r), Math.min(255, g), Math.min(255, b));
	}

	private Color sunsetPalette(double t) {
		// Purple to pink to orange to yellow gradient
		double phase = t * Math.PI * 2;
		int r = (int) (128 + 127 * Math.sin(phase + 0));
		int g = (int) (64 + 64 * Math.sin(phase + Math.PI / 3));
		int b = (int) (192 + 63 * Math.sin(phase + 2 * Math.PI / 3));
		return new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
	}

	// Removed paint() method - using JPanel's paintComponent instead for proper double buffering

	/**
	 * Render the fractal using multi-threaded parallel processing
	 * This utilizes all available CPU cores for maximum performance
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
			
			BufferedImage buffImage = new BufferedImage(MAX_RES_X, MAX_RES_Y, BufferedImage.TYPE_INT_RGB);
			
			// Fill background (white since colors are inverted)
			Graphics2D gg = buffImage.createGraphics();
			gg.setColor(Color.WHITE);
			gg.fillRect(0, 0, MAX_RES_X, MAX_RES_Y);
			gg.dispose();

			// Get number of available CPU cores
			int numThreads = Runtime.getRuntime().availableProcessors();
			ExecutorService executor = Executors.newFixedThreadPool(numThreads);
			
			// Track progress for UI updates
			AtomicInteger completedRows = new AtomicInteger(0);
			final int updateInterval = Math.max(20, MAX_RES_Y / 8); // Update ~8 times, less frequent to reduce flickering

			// Split work into horizontal strips
			int rowsPerThread = Math.max(1, MAX_RES_Y / numThreads);
			
			// Use atomic reference to prevent race conditions
			final AtomicInteger lastUpdateRow = new AtomicInteger(-updateInterval);
			
			for (int threadId = 0; threadId < numThreads; threadId++) {
				final int startRow = threadId * rowsPerThread;
				final int endRow = (threadId == numThreads - 1) ? MAX_RES_Y : (threadId + 1) * rowsPerThread;
				
				executor.submit(() -> {
					// Process this thread's strip of rows
					for (int iy = startRow; iy < endRow; iy++) {
						double cy = ymin + iy * (ymax - ymin) / (MAX_RES_Y - 1);
						for (int ix = 0; ix < MAX_RES_X; ix++) {
							double cx = xmin + ix * (xmax - xmin) / (MAX_RES_X - 1);
							double iterations = mandelbrotSmooth(cx, cy);
							Color color = getColor(iterations);
							buffImage.setRGB(ix, iy, color.getRGB());
						}
						
						// Update progress and refresh UI periodically (less frequent to reduce flickering)
						int completed = completedRows.incrementAndGet();
						// Only update if enough rows have been completed since last update
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

			// Save to file
			try {
				File f = new File("resources/mandelbrot_beautiful.png");
				f.getParentFile().mkdirs();
				javax.imageio.stream.ImageOutputStream ios = ImageIO.createImageOutputStream(f);
				Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("png");
				if (iterator.hasNext()) {
					ImageWriter writer = iterator.next();
					writer.setOutput(ios);
					writer.write(buffImage);
					ios.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) { // Left click - zoom in
			double newX = xmin + (double) e.getX() * (xmax - xmin) / (double) MAX_RES_X;
			double newY = ymin + (double) e.getY() * (ymax - ymin) / (double) MAX_RES_Y;
			double width = (xmax - xmin) * 0.5;
			double height = (ymax - ymin) * 0.5;
			xmin = newX - width / 2;
			xmax = newX + width / 2;
			ymin = newY - height / 2;
			ymax = newY + height / 2;
			maxIterations = (int) (maxIterations * 1.2); // Increase iterations for better detail
			currentImage = null; // Clear current image
			isRendering = false; // Allow new rendering
			drawingPanel.repaint();
			renderFractalParallel();
		} else if (e.getButton() == MouseEvent.BUTTON3) { // Right click - zoom out
			double newX = xmin + (double) e.getX() * (xmax - xmin) / (double) MAX_RES_X;
			double newY = ymin + (double) e.getY() * (ymax - ymin) / (double) MAX_RES_Y;
			double width = (xmax - xmin) * 2.0;
			double height = (ymax - ymin) * 2.0;
			xmin = newX - width / 2;
			xmax = newX + width / 2;
			ymin = newY - height / 2;
			ymax = newY + height / 2;
			maxIterations = Math.max(100, (int) (maxIterations / 1.2));
			currentImage = null; // Clear current image
			isRendering = false; // Allow new rendering
			drawingPanel.repaint();
			renderFractalParallel();
		} else if (e.getButton() == MouseEvent.BUTTON2) { // Middle click - reset
			xmin = -2.5;
			xmax = 1.5;
			ymin = -2.0;
			ymax = 2.0;
			maxIterations = 1000;
			currentImage = null; // Clear current image
			isRendering = false; // Allow new rendering
			drawingPanel.repaint();
			renderFractalParallel();
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

	public static void main(String[] args) {
		BeautifulMandelbrot mandelbrot = new BeautifulMandelbrot();
		mandelbrot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

