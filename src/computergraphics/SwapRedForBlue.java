package computergraphics;

import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * This class swaps a bitmap's red
 * component for the blue one.
 * @author Cleyton
 */
public class SwapRedForBlue {
	
	public SwapRedForBlue( ) {
		
	}
	
	private void run( ) {
		try {
			FileInputStream fis = new FileInputStream( "resources/group_logo.bmp" );
			BufferedInputStream bis = new BufferedInputStream( fis );
			FileOutputStream fos = new FileOutputStream( "resources/new_group_logo.bmp" );
			BufferedOutputStream bos = new BufferedOutputStream( fos );
			int size = bis.available( );
			for ( int i = 0; i < 54; i++ ) {
				bos.write( bis.read( ) );
			}
			size = size - 54;
			byte[ ] data = new byte[ size ];
			int pixels = size / 3;
			for ( int i = 0; i < pixels; i++ ) {
				int blue = bis.read( );
				int green = bis.read( );
				int red = bis.read( );
				bos.write( red );
				bos.write( green );
				bos.write( blue );
			}
			bos.write( data );
			bos.close( );
			fos.close( );
			bis.close( );
			fis.close( );
			
		}
		catch ( IOException e ) {
			e.printStackTrace( );
		}
		System.out.println( "done." );
	}
	
	public static void main( String[ ] args ) {
		SwapRedForBlue obj = new SwapRedForBlue( );
		obj.run( );
	}

}
