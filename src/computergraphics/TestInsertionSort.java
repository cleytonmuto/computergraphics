package computergraphics;

public class TestInsertionSort {
    
    public TestInsertionSort( ) {
        
    }
    
    private void run( ) {
        int SIZE = 20;
        int[ ] array = new int[ SIZE ];
        for ( int k = 0; k < SIZE; k++ ) {
            array[ k ] = k;
        }
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
        System.out.print( "before: " );
        for ( int k = 0; k < SIZE; k++ ) {
            if ( k > 0 ) {
                System.out.print( " " );
            }
            System.out.print( array[ k ] );
        }
        System.out.println( );
        int i = 0;
        while ( i < array.length ) {
            int j = i;
            while ( j > 0 && array[ j - 1 ] > array[ j ] ) {
                int temp = array[ j - 1 ];
                array[ j - 1 ] = array[ j ];
                array[ j ] = temp;
                j--;
            }
            i++;
        }
        System.out.print( "after: " );
        for ( int k = 0; k < SIZE; k++ ) {
            if ( k > 0 ) {
                System.out.print( " " );
            }
            System.out.print( array[ k ] );
        }
        System.out.println( );
    }
    
    public static void main( String[ ] args ) {
        TestInsertionSort obj = new TestInsertionSort( );
        obj.run( );
    }
    
}
