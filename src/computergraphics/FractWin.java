package computergraphics;

import java.awt.Color;

public class FractWin {

	private int size; // size in pixels
	private int numColors; // Maximum number of colors used to plot
	private double logicalXMin, logicalXMax, logicalYMin, logicalYMax;
	private Color[] colorList;
	private int lastX, lastY, lastColor;

	public FractWin() {
		size = 600;
		numColors = 13;
		colorList = new Color[numColors];
		lastX = -1;
		lastY = -1;
		lastColor = 0;
	}

	public FractWin(int size) {
		this.size = size;
	}

	public void setBoundaries(double x1, double y1, double x2, double y2) {
		logicalXMin = x1;
		logicalXMax = x2;
		logicalYMin = y1;
		logicalYMax = y2;
	}

	public boolean inBounds(int x, int y) {
		return ((x >= 0) && (x < size) && (y >= 0) && (y < size));
	}

	// Given physical x coordinate, return logical x coordinate.
	public double logicalX(int px) {
		double x = ((double) px * (logicalXMax - logicalXMin) / (double) size) + logicalXMin;
		return (x);
	}

	// Given the physical y coordinate, return logical y coordinate.
	public double logicalY(int py) {
		double y = ((double) py / (double) size * (logicalYMin - logicalYMax)) + logicalYMax;
		return (y);
	}

	// Given logical x coordinate, return physical x coordinate. Return -1 if out of
	// bounds.
	public int physicalX(double x) {
		double px = size * (x - logicalXMin) / (logicalXMax - logicalXMin);
		return (inBounds((int) px, 0) ? (int) px : -1);
	}

	// Given the logical y coordinate, return the physical y coordinate. Return -1
	// if out of bounds.
	public int physicalY(double y) {
		double py = (double) size * ((y - logicalYMax) / (logicalYMin - logicalYMax));
		return (inBounds(0, (int) py) ? (int) py : -1);
	}

	public void setColorList(int i, Color color) {
		colorList[i] = color;
	}

	public Color getColorList(int i) {
		return (colorList[i]);
	}

	public int getLastX() {
		return (lastX);
	}

	public int getLastY() {
		return (lastY);
	}

	public int getLastColor() {
		return (lastColor);
	}

	public int getNumColors() {
		return (numColors);
	}

	public void setLastX(int lastX) {
		this.lastX = lastX;
	}

	public void setLastY(int lastY) {
		this.lastY = lastY;
	}

	public void setLastColor(int lastColor) {
		this.lastColor = lastColor;
	}

	public void setNumColors(int numColors) {
		this.numColors = numColors;
	}

}