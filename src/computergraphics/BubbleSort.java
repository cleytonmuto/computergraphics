package computergraphics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class BubbleSort extends JFrame {
    
    private static final long serialVersionUID = 1082311450402464013L;
    private final int MAX_X = 1400;
    private final int MAX_Y = 1200;
    
    public BubbleSort( ) {
        super( "Bubble Sort" );
        setSize( MAX_X, MAX_Y );
        setVisible( true );
        setResizable( false );
    }

    public void paint( Graphics g ) {
        g.setColor( Color.white );
        g.fillRect( 0, 0, MAX_X, MAX_Y );
        int SIZE = 20;
        int[ ] array = new int[ SIZE ];
        for ( int i = 0; i < SIZE; i++ ) {
            array[ i ] = i;
        }
        // shuffle the array
        for ( int i = 0; i < SIZE * SIZE; i++ ) {
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
        for ( int i = 0; i < SIZE; i++ ) {
            g.drawRect( 60 * i + 100, 940 - 40 * ( array[ i ] + 1 ), 30, 40 * ( array[ i ] + 1 ) );
        }
        g.setColor( Color.lightGray );
        for ( int i = 0; i < SIZE; i++ ) {
            g.fillRect( 60 * i + 100 + 1, 940 - 40 * ( array[ i ] + 1 ) + 1, 30 - 1, 40 * ( array[ i ] + 1 ) - 1 );
        }
        boolean swapped = true;
        int n = array.length;
        while ( swapped ) {
            swapped = false;
            for ( int i = 0; i < n - 1; i++ ) {
                if ( array[ i ] > array[ i + 1 ] ) {
                    int temp = array[ i ];
                    g.setColor( Color.white );
                    g.fillRect( 60 * i + 100, 940 - 40 * ( array[ i ] + 1 ), 30 + 1, 40 * ( array[ i ] + 1 ) );
                    g.setColor( Color.white );
                    g.fillRect( 60 * ( i + 1 ) + 100, 940 - 40 * ( array[ i + 1 ] + 1 ), 30 + 1, 40 * ( array[ i + 1 ] + 1 ) );
                    array[ i ] = array[ i + 1 ];
                    g.setColor( Color.black );
                    g.drawRect( 60 * i + 100, 940 - 40 * ( array[ i + 1 ] + 1 ), 30, 40 * ( array[ i + 1 ] + 1 ) );
                    g.setColor( Color.lightGray );
                    g.fillRect( 60 * i + 100 + 1, 940 - 40 * ( array[ i + 1 ] + 1 ) + 1, 30 - 1, 40 * ( array[ i + 1 ] + 1 ) - 1 );
                    array[ i + 1 ] = temp;
                    g.setColor( Color.black );
                    g.drawRect( 60 * ( i + 1 ) + 100, 940 - 40 * ( temp + 1 ), 30, 40 * ( temp + 1 ) );
                    g.setColor( Color.lightGray );
                    g.fillRect( 60 * ( i + 1 ) + 100 + 1, 940 - 40 * ( temp + 1 ), 30 - 1, 40 * ( temp + 1 ) - 1 );
                    swapped = true;
                    try {
                        Thread.sleep( 100 );
                    }
                    catch ( InterruptedException e ) {
                        e.printStackTrace( );
                    }
                }
            }
            n--;
        }
        g.setColor( Color.black );
        for ( int i = 0; i < SIZE; i++ ) {
            g.drawRect( 60 * i + 100, 940 - 40 * ( array[ i ] + 1 ), 30, 40 * ( array[ i ] + 1 ) );
        }
        g.setColor( Color.gray );
        for ( int i = 0; i < SIZE; i++ ) {
            g.fillRect( 60 * i + 100 + 1, 940 - 40 * ( array[ i ] + 1 ) + 1, 30 - 1, 40 * ( array[ i ] + 1 ) - 1 );
        }
        try {
            Thread.sleep( 1000 );
        }
        catch ( InterruptedException e ) {
            e.printStackTrace( );
        }
    }

    public static void main(String[] args) {
        BubbleSort obj = new BubbleSort();
        obj.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

}
