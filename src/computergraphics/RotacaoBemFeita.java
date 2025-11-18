package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Perfect Image Rotation using backward mapping and bilinear interpolation
 * 
 * Features:
 * - Backward mapping (destination to source) to avoid holes and aliasing
 * - Bilinear interpolation for smooth, high-quality results
 * - Rotation around image center
 * - Support for multiple rotation angles
 * - Proper bounds checking
 */
public class RotacaoBemFeita extends JFrame {

	private static final long serialVersionUID = 2847981035539960586L;
	private final int MAX_RES_X = 1200; // Increased by 50% (800 * 1.5 = 1200)
	private final int MAX_RES_Y = 600;
	private BufferedImage sourceImage;
	private JPanel drawingPanel;

	public RotacaoBemFeita() {
		super("RotacaoBemFeita - Perfect Image Rotation");
		setSize(MAX_RES_X, MAX_RES_Y);
		setResizable(false);
		
		// Load the source image
		try {
			// Try to load as PNG first, then BMP
			File pngFile = new File("resources/mini_lenna.png");
			File bmpFile = new File("resources/mini_lenna.bmp");
			
			if (pngFile.exists()) {
				sourceImage = ImageIO.read(pngFile);
			} else if (bmpFile.exists()) {
				sourceImage = loadBMP(bmpFile);
			} else {
				// Create a test image if file not found
				sourceImage = createTestImage(128, 128);
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Create a test image on error
			sourceImage = createTestImage(128, 128);
		}
		
		// Create drawing panel
		drawingPanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				
				// Fill background
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				
				if (sourceImage != null) {
					int spacing = 10;
					
					// Draw all intermediate rotated versions
					// Show rotations at: 0, π/16, 2π/16, 3π/16, 4π/16
					double[] angles = { 0, Math.PI / 16, 2 * Math.PI / 16, 3 * Math.PI / 16, 4 * Math.PI / 16 };
					
					// Pre-calculate all rotated images and find max width
					BufferedImage[] rotatedImages = new BufferedImage[angles.length];
					int maxWidth = 0;
					for (int i = 0; i < angles.length; i++) {
						if (angles[i] == 0) {
							rotatedImages[i] = sourceImage;
						} else {
							rotatedImages[i] = rotateImage(sourceImage, angles[i]);
						}
						maxWidth = Math.max(maxWidth, rotatedImages[i].getWidth());
					}
					
					// Calculate total width needed and center the images
					int totalWidth = angles.length * maxWidth + (angles.length - 1) * spacing;
					int startX = Math.max(10, (MAX_RES_X - totalWidth) / 2); // Center horizontally, with margin
					int startY = MAX_RES_Y / 4;
					
					for (int i = 0; i < angles.length; i++) {
						// Calculate position - arrange horizontally with spacing
						int offsetX = i * (maxWidth + spacing);
						g2d.drawImage(rotatedImages[i], startX + offsetX, startY, null);
					}
				}
			}
		};
		
		add(drawingPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Load BMP file manually (for compatibility with existing resources)
	 */
	private BufferedImage loadBMP(File file) throws IOException {
		java.io.FileInputStream reader = new java.io.FileInputStream(file);
		
		// Skip BMP header (54 bytes)
		for (int i = 0; i < 54; i++) {
			reader.read();
		}
		
		int width = 128;
		int height = 128;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// BMP stores pixels bottom-to-top, BGR format
		for (int linha = height - 1; linha >= 0; linha--) {
			for (int coluna = 0; coluna < width; coluna++) {
				int blue = reader.read();
				int green = reader.read();
				int red = reader.read();
				int rgb = (red << 16) | (green << 8) | blue;
				image.setRGB(coluna, linha, rgb);
			}
		}
		
		reader.close();
		return image;
	}

	/**
	 * Create a test image if source file is not found
	 */
	private BufferedImage createTestImage(int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img.createGraphics();
		
		// Draw a colorful test pattern
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int r = (x * 255) / width;
				int g = (y * 255) / height;
				int b = ((x + y) * 255) / (width + height);
				img.setRGB(x, y, new Color(r, g, b).getRGB());
			}
		}
		
		// Draw some shapes
		g2d.setColor(Color.BLACK);
		g2d.drawRect(10, 10, width - 20, height - 20);
		g2d.drawOval(20, 20, width - 40, height - 40);
		g2d.setColor(Color.WHITE);
		g2d.fillOval(width / 2 - 10, height / 2 - 10, 20, 20);
		
		g2d.dispose();
		return img;
	}

	/**
	 * Rotate image using backward mapping with bilinear interpolation
	 * This method produces perfect, high-quality rotations without holes or aliasing
	 * 
	 * @param source The source image to rotate
	 * @param angle Rotation angle in radians (positive = counterclockwise)
	 * @return Rotated image
	 */
	private BufferedImage rotateImage(BufferedImage source, double angle) {
		int width = source.getWidth();
		int height = source.getHeight();
		
		// Center of rotation (source image center)
		double centerX = width / 2.0;
		double centerY = height / 2.0;
		
		// Calculate rotated image bounds by rotating the four corners
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		// Four corners of source image
		double[][] corners = {
			{0, 0},           // top-left
			{width, 0},       // top-right
			{width, height}, // bottom-right
			{0, height}       // bottom-left
		};
		
		double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
		
		for (double[] corner : corners) {
			// Translate to origin (center)
			double x = corner[0] - centerX;
			double y = corner[1] - centerY;
			
			// Rotate
			double rotX = x * cos - y * sin;
			double rotY = x * sin + y * cos;
			
			// Translate back
			rotX += centerX;
			rotY += centerY;
			
			minX = Math.min(minX, rotX);
			maxX = Math.max(maxX, rotX);
			minY = Math.min(minY, rotY);
			maxY = Math.max(maxY, rotY);
		}
		
		// Create output image with calculated bounds
		int outWidth = (int) Math.ceil(maxX - minX);
		int outHeight = (int) Math.ceil(maxY - minY);
		BufferedImage rotated = new BufferedImage(outWidth, outHeight, BufferedImage.TYPE_INT_RGB);
		
		// Fill with white background
		Graphics2D g = rotated.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, outWidth, outHeight);
		g.dispose();
		
		// Inverse rotation for backward mapping
		double invCos = Math.cos(-angle);
		double invSin = Math.sin(-angle);
		
		// Backward mapping: for each pixel in output, find corresponding source pixel
		for (int y = 0; y < outHeight; y++) {
			for (int x = 0; x < outWidth; x++) {
				// Translate output pixel coordinates to world coordinates
				double worldX = x + minX;
				double worldY = y + minY;
				
				// Translate to source center
				double dx = worldX - centerX;
				double dy = worldY - centerY;
				
				// Apply inverse rotation
				double srcX = dx * invCos - dy * invSin + centerX;
				double srcY = dx * invSin + dy * invCos + centerY;
				
				// Only sample if within source image bounds
				if (srcX >= 0 && srcX < width && srcY >= 0 && srcY < height) {
					// Bilinear interpolation
					int rgb = bilinearInterpolate(source, srcX, srcY);
					rotated.setRGB(x, y, rgb);
				}
			}
		}
		
		return rotated;
	}

	/**
	 * Bilinear interpolation for smooth pixel sampling
	 * Handles edge cases and provides high-quality results
	 * 
	 * @param image Source image
	 * @param x X coordinate (may be fractional)
	 * @param y Y coordinate (may be fractional)
	 * @return Interpolated RGB color
	 */
	private int bilinearInterpolate(BufferedImage image, double x, double y) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		// Clamp coordinates to valid range
		x = Math.max(0, Math.min(width - 1, x));
		y = Math.max(0, Math.min(height - 1, y));
		
		// Get integer coordinates
		int x1 = (int) Math.floor(x);
		int y1 = (int) Math.floor(y);
		int x2 = Math.min(width - 1, x1 + 1);
		int y2 = Math.min(height - 1, y1 + 1);
		
		// Get four corner pixels
		int rgb11 = image.getRGB(x1, y1);
		int rgb21 = image.getRGB(x2, y1);
		int rgb12 = image.getRGB(x1, y2);
		int rgb22 = image.getRGB(x2, y2);
		
		// Extract color components
		int r11 = (rgb11 >> 16) & 0xFF;
		int g11 = (rgb11 >> 8) & 0xFF;
		int b11 = rgb11 & 0xFF;
		
		int r21 = (rgb21 >> 16) & 0xFF;
		int g21 = (rgb21 >> 8) & 0xFF;
		int b21 = rgb21 & 0xFF;
		
		int r12 = (rgb12 >> 16) & 0xFF;
		int g12 = (rgb12 >> 8) & 0xFF;
		int b12 = rgb12 & 0xFF;
		
		int r22 = (rgb22 >> 16) & 0xFF;
		int g22 = (rgb22 >> 8) & 0xFF;
		int b22 = rgb22 & 0xFF;
		
		// Calculate fractional parts
		double fx = x - x1;
		double fy = y - y1;
		
		// Bilinear interpolation
		double r = (1 - fx) * (1 - fy) * r11 + fx * (1 - fy) * r21 + 
		           (1 - fx) * fy * r12 + fx * fy * r22;
		double g = (1 - fx) * (1 - fy) * g11 + fx * (1 - fy) * g21 + 
		           (1 - fx) * fy * g12 + fx * fy * g22;
		double b = (1 - fx) * (1 - fy) * b11 + fx * (1 - fy) * b21 + 
		           (1 - fx) * fy * b12 + fx * fy * b22;
		
		// Clamp and combine
		int rInt = Math.max(0, Math.min(255, (int) Math.round(r)));
		int gInt = Math.max(0, Math.min(255, (int) Math.round(g)));
		int bInt = Math.max(0, Math.min(255, (int) Math.round(b)));
		
		return (rInt << 16) | (gInt << 8) | bInt;
	}

	public static void main(String[] args) {
		RotacaoBemFeita obj = new RotacaoBemFeita();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
