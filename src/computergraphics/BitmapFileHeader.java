package computergraphics;

import java.io.FileInputStream;
import java.io.IOException;

public class BitmapFileHeader {

	private static void format(int valor) {
		String hex = Integer.toString(valor, 16);
		if (hex.length() == 1) {
			System.out.print("0");
		}
		System.out.print(hex.toUpperCase() + " ");
	}

	public static void main(String[] args) throws IOException {
		String filename = "resources/brandenburger.bmp";
		System.out.print(filename);
		FileInputStream reader = new FileInputStream(filename);
		String[] field = { "Magic number", "Size of BMP file", "Application specific", "Application specific",
				"Offset where the Pixel Array (bitmap data) can be found",
				"Number of bytes in the DIB header (from this point)", "Width of the bitmap in pixels",
				"Height of the bitmap in pixels", "Number of color planes being used", "Number of bits per pixel",
				"BI_RGB, no Pixel Array compression used",
				"Size of the raw data in the Pixel Array (including padding)", "Horizontal resolution of the image",
				"Vertical resolution of the image", "Number of colors in the palette",
				"Important colors: 0 means all colors are important" };
		int[] size = { 2, 4, 2, 2, 4, 4, 4, 4, 2, 2, 4, 4, 4, 4, 4, 4 };

		for (int j = 0; j < field.length; j++) {
			System.out.print("\n" + field[j] + " = ");
			for (int i = 0; i < size[j]; i++) {
				format(reader.read());
			}
		}
		reader.close();
	}

}