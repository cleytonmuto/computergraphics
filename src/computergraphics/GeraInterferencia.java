package computergraphics;

import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class GeraInterferencia {
	
	public GeraInterferencia( ) {
		
	}
	
	private void run( ) {
		try {
			FileInputStream fis = new FileInputStream( "resources/pisa.bmp" );
			BufferedInputStream bis = new BufferedInputStream( fis );
			int SIZE = bis.available( );
			byte[ ] dataIn = new byte[ SIZE ];
			bis.read( dataIn );
			bis.close( );
			fis.close( );
			
			FileOutputStream fos1 = new FileOutputStream( "resources/ruido.bmp" );
			BufferedOutputStream bos1 = new BufferedOutputStream( fos1 );
			byte[ ] randomOut = new byte[ SIZE ];
			int ROLETA = 2; // tamanho da roleta
			for ( int k = 0; k < SIZE; k++ ) {
				int sorteio = ( int ) ( ROLETA * Math.random( ) );
				if ( sorteio == 1 ) {
					randomOut[ k ] = ( byte ) ( 256 * Math.random( ) );
				}
				else {
					randomOut[ k ] = 0;
				}
			}
			int HEADER_SIZE = 54;
			// copy header
			for ( int k = 0; k < HEADER_SIZE; k++ ) {
				randomOut[ k ] = dataIn[ k ];
			}
			bos1.write( randomOut );
			bos1.close( );
			fos1.close( );
			
			FileOutputStream fos2 = new FileOutputStream( "resources/resultado.bmp" );
			BufferedOutputStream bos2 = new BufferedOutputStream( fos2 );
			byte[ ] dataOut = new byte[ SIZE ];
			// copy header
			for ( int k = 0; k < HEADER_SIZE; k++ ) {
				dataOut[ k ] = dataIn[ k ];
			}
			for ( int k = HEADER_SIZE; k < SIZE; k++ ) {
				dataOut[ k ] = ( byte ) ( dataIn[ k ] ^ randomOut[ k ] );
			}
			bos2.write( dataOut );
			bos2.close( );
			fos2.close( );
		}
		catch ( IOException e ) {
			e.printStackTrace( );
		}
	}

	public static void main( String[ ] args ) {
		GeraInterferencia obj = new GeraInterferencia( );
		obj.run( );
	}

}