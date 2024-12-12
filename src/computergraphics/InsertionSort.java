package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class InsertionSort extends JFrame {

    private static final long serialVersionUID = -6273823019646662684L;
    private final int MAX_X = 1400;
    private final int MAX_Y = 1200;
    
    public InsertionSort( ) {
        setTitle( "Insertion Sort" );
        setSize( MAX_X, MAX_Y );
        setVisible( true );
        setResizable( false );
    }

    public void paint( Graphics g ) {
        g.setColor( Color.white );
        g.fillRect( 0, 0, MAX_X, MAX_Y );
        int SIZE = 20;
        int[ ] array = new int[ SIZE ];
        for ( int k = 0; k < SIZE; k++ ) {
            array[ k ] = k;
        }
        // shuffle the array
        for ( int k = 0; k < SIZE * SIZE; k++ ) {
            int orig = ( int ) ( SIZE * Math.random( ) );
            int dest = ( int ) ( SIZE * Math.random( ) );
            while ( orig == dest ) {
                dest = ( int ) ( SIZE * Math.random( ) );
            }
            int temp = array[ orig ];
            array[ orig ] = array[ dest ];
            array[ dest ] = temp;
        }
        g.setColor( Color.black );
        for ( int k = 0; k < SIZE; k++ ) {
            g.drawRect( 60 * k + 100, 940 - 40 * ( array[ k ] + 1 ), 30, 40 * ( array[ k ] + 1 ) );
        }
        g.setColor( Color.lightGray );
        for ( int k = 0; k < SIZE; k++ ) {
            g.fillRect( 60 * k + 100 + 1, 940 - 40 * ( array[ k ] + 1 ) + 1, 30 - 1, 40 * ( array[ k ] + 1 ) - 1 );
        }
        int i = 0;
        while ( i < array.length ) {
            int j = i;
            while ( j > 0 && array[ j - 1 ] > array[ j ] ) {
                int temp = array[ j - 1 ];
                g.setColor( Color.white );
                g.fillRect( 60 * ( j - 1 ) + 100, 940 - 40 * ( array[ j - 1 ] + 1 ), 30 + 1, 40 * ( array[ j - 1 ] + 1 ) );
                g.setColor( Color.white );
                g.fillRect( 60 * j + 100, 940 - 40 * ( array[ j ] + 1 ), 30 + 1, 40 * ( array[ j ] + 1 ) );
                array[ j - 1 ] = array[ j ];
                g.setColor( Color.black );
                g.drawRect( 60 * ( j - 1 ) + 100, 940 - 40 * ( array[ j - 1 ] + 1 ), 30, 40 * ( array[ j - 1 ] + 1 ) );
                g.setColor( Color.lightGray );
                g.fillRect( 60 * ( j - 1 ) + 100 + 1, 940 - 40 * ( array[ j - 1 ] + 1 ) + 1, 30 - 1, 40 * ( array[ j - 1 ] + 1 ) - 1 );
                array[ j ] = temp;
                g.setColor( Color.black );
                g.drawRect( 60 * j + 100, 940 - 40 * ( temp + 1 ), 30, 40 * ( temp + 1 ) );
                g.setColor( Color.lightGray );
                g.fillRect( 60 * j + 100 + 1, 940 - 40 * ( temp + 1 ), 30 - 1, 40 * ( temp + 1 ) - 1 );
                j--;
                try {
                    Thread.sleep( 100 );
                }
                catch ( InterruptedException e ) {
                    e.printStackTrace( );
                }
            }
            i++;
        }
        g.setColor( Color.black );
        for ( int k = 0; k < SIZE; k++ ) {
            g.drawRect( 60 * k + 100, 940 - 40 * ( array[ k ] + 1 ), 30, 40 * ( array[ k ] + 1 ) );
        }
        g.setColor( Color.gray );
        for ( int k = 0; k < SIZE; k++ ) {
            g.fillRect( 60 * k + 100 + 1, 940 - 40 * ( array[ k ] + 1 ) + 1, 30 - 1, 40 * ( array[ k ] + 1 ) - 1 );
        }
        try {
            Thread.sleep( 1000 );
        }
        catch ( InterruptedException e ) {
            e.printStackTrace( );
        }
    }

    public static void main( String[]  args ) {
        InsertionSort obj = new InsertionSort();
        obj.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
    
}
