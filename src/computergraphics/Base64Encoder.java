package computergraphics;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * This class encodes a binary file to base64 format
 * @author Cleyton
 */
public class Base64Encoder {
	
	public Base64Encoder( ) {
		
	}
	
	
	private void run( ) {
		try {
			FileInputStream fis = new FileInputStream( "resources/lena.png" );
			BufferedInputStream bis = new BufferedInputStream( fis );
			byte[ ] data = new byte[ bis.available( ) ];
			bis.read( data );
			String encoded = Base64.getEncoder().encodeToString(data);
			FileOutputStream fos = new FileOutputStream( "resources/base64.txt" );
			OutputStreamWriter osw = new OutputStreamWriter( fos, StandardCharsets.UTF_8 );
			BufferedWriter bw = new BufferedWriter( osw );
			bw.write( encoded );
			bw.close( );
			osw.close( );
			fos.close( );
			bis.close( );
			fis.close( );
		}
		catch ( IOException e ) {
			e.printStackTrace( );
		}
		System.out.println("done.");
	}
	
	public static void main( String[ ] args ) {
		new Base64Encoder( ).run( );
	}

}
